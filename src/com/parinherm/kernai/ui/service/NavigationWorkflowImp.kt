package com.parinherm.kernai.ui.service

import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.e4.ui.model.application.ui.basic.MCompositePart
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.model.application.ui.basic.MPartSashContainer
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack
import org.eclipse.e4.ui.model.application.ui.basic.MWindow
import org.eclipse.e4.ui.workbench.modeling.EModelService
import org.eclipse.e4.ui.workbench.modeling.EPartService
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState
import org.osgi.framework.FrameworkUtil
import javax.inject.Inject



class NavigationWorkflowImpl @Inject constructor (modelService: EModelService, application: MApplication, window: MWindow, partService: EPartService) : NavigationWorkflow {

    private var modelService: EModelService
    private var application: MApplication
    private var window: MWindow
    private var partService: EPartService
	
	init {
		this.modelService = modelService
        this.application = application
        this.window = window
        this.partService = partService
	}

    private val mainSash: MPartSashContainer
        get() = modelService.find("com.parinherm.kernai.rcp.application.kotlin.partsashcontainer.main",
                window) as MPartSashContainer

    private val mainStack: MPartStack
        get() = modelService.find(MAIN_STACK_ID, window) as MPartStack



//    constructor(modelService: EModelService, application: MApplication, window: MWindow, partService: EPartService) {
//        this.modelService = modelService
//        this.application = application
//        this.window = window
//        this.partService = partService
//    }

    
    override fun navigateTo(view: String) {

        when (view) {
            "masterproperty" -> {

                loadMainPart(MASTERPROPERTY_SNIPPET_ID)
                return
            }
            "template" -> {
                loadMainPart(TEMPLATE_SNIPPET_ID)
                return
            }
            "script" -> {
                //loadMainPart(SCRIPT_SNIPPET_ID);
                loadGenericListAndEditPart("ScriptListPart", "ScriptEditPart", "Script")
                return
            }
            "list" -> loadGenericListAndEditPart("LookupListPart", "LookupEditPart", "List")
			"kotlin" -> loadGenericListAndEditPart("ReferenceListPart", "ReferenceEditPart", "Kotlin")
        }
    }

    private fun loadGenericListAndEditPart(listClassName: String, editClassName: String, label: String) {
        val snippet = modelService.cloneSnippet(application, LISTANDEDIT_SNIPPET_ID, window) as MPartStack
        val container = mainStack
		val child = snippet.getChildren().get(0) as MCompositePart
        child.setVisible(true)
        child.setCloseable(true)
        child.setLabel(label)

		        
		/* set the uri on the parts */
        val partSash = child.getChildren().get(0) as MPartSashContainer
        val listPart = partSash.getChildren().get(0) as MPart
        val editPart = partSash.getChildren().get(1) as MPart
        val listPartURI = "${BUNDLE_PREFIX}${BUNDLE_NAME}/${VIEW_PACKAGE}.${listClassName}"
        val editPartURI = "${BUNDLE_PREFIX}${BUNDLE_NAME}/${VIEW_PACKAGE}.${editClassName}"
        listPart.setContributionURI(listPartURI)
        editPart.setContributionURI(editPartURI)
        //val showPart = partService.showPart(child, PartState.ACTIVATE)
        container.getChildren().add(child)
		partService.activate(listPart, true)
 
        
    }

    private fun loadMainPart(snippetId: String) {
        /* this was old method using individual snippets for each view, changing to use single
         * snippet for each kind of view and set label and so on dynamically
         */
        val snippet = modelService.cloneSnippet(application, snippetId, window) as MPartStack ?: return

        val container = mainStack
        if (container != null) {
            val child = snippet.getChildren().get(0) as MCompositePart
            container!!.getChildren().add(child)
            child.setVisible(true)
            child.setCloseable(true)
            val showPart = partService.showPart(child, PartState.ACTIVATE)
        }
    }

    companion object {

		val MAIN_STACK_ID = "com.parinherm.kernai.rcp.application.kotlin.partstack.main"
        val MASTERPROPERTY_SNIPPET_ID = "com.parinherm.kernai.rcp.application.plugin.compositepart.masterProperty"
        val TEMPLATE_SNIPPET_ID = "com.parinherm.kernai.rcp.application.partstack.template"
        val SCRIPT_SNIPPET_ID = "com.parinherm.kernai.rcp.application.partstack.script"
        val LISTANDEDIT_SNIPPET_ID = "com.parinherm.kernai.rcp.application.kotlin.partstack.listedit"
        val VIEW_PACKAGE = "com.parinherm.kernai.rcp.application.kotlin.parts"
		val BUNDLE_NAME = FrameworkUtil.getBundle(NavigationWorkflowImpl::class.java).symbolicName //getBundle(getClass().getVersion()) // "com.parinherm.kernai.rcp.application.kotlin"
        val BUNDLE_PREFIX = "bundleclass://"
    }


}
