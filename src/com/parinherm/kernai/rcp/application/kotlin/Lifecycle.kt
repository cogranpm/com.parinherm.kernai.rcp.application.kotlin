package com.parinherm.kernai.rcp.application.kotlin

//import com.parinherm.kernai.data.connection.DataConnection
import com.parinherm.kernai.data.connection.DataConnection
import com.parinherm.kernai.data.connection.DatabaseManagerFactory
import org.eclipse.e4.core.contexts.IEclipseContext
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.core.services.events.IEventBroker
import org.eclipse.e4.ui.di.UIEventTopic
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.workbench.UIEvents
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate
import org.eclipse.e4.ui.workbench.lifecycle.PreSave
import org.eclipse.e4.ui.workbench.lifecycle.ProcessAdditions
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals
import org.eclipse.equinox.app.IApplicationContext
import org.eclipse.swt.events.DisposeEvent
import org.eclipse.swt.events.DisposeListener
import org.eclipse.swt.widgets.Display
import org.osgi.service.event.Event
import org.osgi.service.event.EventHandler
import javax.inject.Inject
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler
import org.eclipse.e4.ui.model.application.ui.basic.MWindow
import org.eclipse.swt.widgets.Shell
import org.eclipse.e4.core.services.log.Logger
import org.eclipse.e4.ui.workbench.UIEvents.EventTags
import com.parinherm.kernai.rcp.application.kotlin.parts.NavigationPart
import org.eclipse.e4.ui.model.application.ui.basic.impl.PartImpl


class Lifecycle {
	
	@Inject
	private lateinit var managerFactory: DatabaseManagerFactory
	
	@Inject
	private lateinit var logger: Logger
	
	@PostContextCreate
	fun postContextCreate(appContext: IApplicationContext, workbenchContext: IEclipseContext, display: Display, eventBroker: IEventBroker, shell: Shell) {
		
		try {

		} catch (ex: Exception) {
			MessageDialog.openInformation(display.getActiveShell(), "Error Message", "Error on startup");
		}
		
	}

	@PreSave
	fun preSave(workbenchContext: IEclipseContext) {
	}

	@ProcessAdditions
	fun processAdditions(workbenchContext: IEclipseContext) {
	}

	@ProcessRemovals
	fun processRemovals( workbenchContext: IEclipseContext) {
	}
	

	
	@Inject
	@Optional
	fun startup(@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) event: Event, application: MApplication)
	{
		logger.error("STARTUP HERE")
	
//		MessageDialog.openInformation(shell, "Starting", "Message on startup");
//
//	
//		shell.addDisposeListener(object: DisposeListener {
//
//			override fun widgetDisposed(e: DisposeEvent) {
//				//this is empty, because close database connection
//				//has been moved to the navigation part					
//			
//			}
//		})
//		
					
	/* here is an example of chaning the close window handler 
				val handler: IWindowCloseHandler = IWindowCloseHandler() {					        
			        fun close( window: MWindow): Boolean {
			            val msg = MessageDialog.openConfirm(shell, "Close","You will loose data. Really close?")
						return false
				        }
				    };
				//window.getContext().set(IWindowCloseHandler.class, handler);
`				*/
		 
	}
	
	@Inject
	@Optional
	fun shutdown(@UIEventTopic(UIEvents.UILifeCycle.APP_SHUTDOWN_STARTED) event: Event, application: MApplication)
	{
		logger.error("SHUTDOWN STARTED	")
	}	
	
	@Inject
	@Optional
	fun partActivation(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) event: Event, application: MApplication)
	{
		/* an example here of how to respond to active part change, perhaps to disable/enable menus etc */
		var apart = event.getProperty(UIEvents.EventTags.ELEMENT)
		val activePart: MPart? = apart as MPart?
		val context = application.getContext()
		if(activePart != null) {
			logger.error("Active part changed in Lifecycle class: $activePart.getElementId()");
		}
		 
		val widget: Any = event.getProperty(EventTags.ELEMENT)
		if(widget is MPart){
			if (widget.elementId.equals("com.parinherm.kernai.rcp.application.kotlin.part.navigation", true)){
				logger.error("NAVIGATION PART ACTIVATED")
				val navigationPart = widget.getObject() as NavigationPart
				//now we can access that actual part implementation
				//change the data, call methods on it etc
			}
			
		}
		logger.error("SELECTED WIDGET: $widget")
	}
	//listen for changes of focus in the ui
	//digs deeper then part activation, ie can tell if going from part stack to part to window etc
	@Inject
	@Optional
	fun selectedElementChanged(@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) event: Event)
	{
		//this occurs any time something new in the ui gets the focus
		val element: Any = event.getProperty(EventTags.ELEMENT)
		val newValue: Any = event.getProperty(EventTags.NEW_VALUE);
		
		if(element is MPart){
			logger.error("PART WAS SELECTED, MOVING TO SOMETHING NEW")
		}
		
		if(newValue is MPart){
			logger.error("SELECTED A PART")
		}
		
		//if element is MPart etc etc
		logger.error("element changed Element:" + element.toString() + " New Value:" + newValue.toString())
	}
	
	
}