package com.parinherm.kernai.ui.service

import com.parinherm.kernai.ui.viewModel.NavigationItem
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.jface.resource.ImageRegistry
import org.eclipse.swt.widgets.Shell
import java.util.ArrayList

object ApplicationController {
	
	private lateinit var instance: NavigationWorkflow
    private var argument: Any? = null
       
    private lateinit var imageRegistry: ImageRegistry
     
    private val IMAGES_PATH = "/icons/"

    //properties
    //work
    //models
    //lookup all the models instead of static models item
    /*
        NavigationItem model = new NavigationItem("Models", "timekeeping", "model");
        modelRoot.getItems().add(model);

        NavigationItem entity = new NavigationItem("Entities", "timekeeping", "entity");
        model.getItems().add(entity);

        NavigationItem attribute = new NavigationItem("Attributes", "timekeeping", "attribute");
        entity.getItems().add(attribute);

        NavigationItem association = new NavigationItem("Associations", "timekeeping", "association");
        model.getItems().add(association);
        *///code generation
    val navigationItems: List<NavigationItem>
        get() {
            val items = ArrayList<NavigationItem>()
            val properties = NavigationItem("Settings", "", "settings")
            items.add(properties)
			
            properties.items.add(NavigationItem("Lists", "list", "list"))
            properties.items.add(NavigationItem("Property Types", "propertytype", "type"))
            properties.items.add(NavigationItem("Property Groups", "propertygroup", "group"))
            properties.items.add(NavigationItem("Properties", "masterproperty", "properties"))
            val work = NavigationItem("Work", "", "wrench")
            items.add(work)
            work.items.add(NavigationItem("Projects", "timekeeping", "project"))
            work.items.add(NavigationItem("Timekeeping", "timekeeping", "clock"))
            work.items.add(NavigationItem("Tasks", "timekeeping", "tag_blue"))
            work.items.add(NavigationItem("Knowledge Base", "timekeeping", "book"))
            val modelRoot = NavigationItem("Modelling", "timekeeping", "models")
            items.add(modelRoot)
            val requirements = NavigationItem("Requirements", "timekeeping", "model")
            modelRoot.items.add(requirements)

            val data = NavigationItem("Data", "timekeeping", "model")
            modelRoot.items.add(data)

            val entity = NavigationItem("Entity", "timekeeping", "model")
            modelRoot.items.add(entity)
            val codeGeneration = NavigationItem("Code Generation", "", "code-generation")
            items.add(codeGeneration)
            codeGeneration.items.add(NavigationItem("Scripts", "script", "script"))
            codeGeneration.items.add(NavigationItem("Templates", "template", "template"))
            codeGeneration.items.add(NavigationItem("Builds", "build", "build"))
			
			
			val reference = NavigationItem("Reference", "", "model" )
			items.add(reference)
			
			val kotlin = NavigationItem("Kotlin", "kotlin", "script")
			reference.items.add(kotlin)
			
			
            return items
        }

    fun init(shell: Shell) {
        imageRegistry = ImageRegistry()
        val clock = getImageDescriptor("clock.png")
        imageRegistry.put("clock", clock)
        imageRegistry.put("project", getImageDescriptor("project.png"))
        imageRegistry.put("association", getImageDescriptor("association.png"))
        imageRegistry.put("attribute", getImageDescriptor("attribute.png"))
        imageRegistry.put("build", getImageDescriptor("build.png"))
        imageRegistry.put("code-generation", getImageDescriptor("code-generation.png"))
        imageRegistry.put("delete", getImageDescriptor("delete.png"))
        imageRegistry.put("folder", getImageDescriptor("folder.png"))
        imageRegistry.put("model", getImageDescriptor("model.png"))
        imageRegistry.put("models", getImageDescriptor("models.png"))
        imageRegistry.put("new", getImageDescriptor("new.png"))
        imageRegistry.put("save", getImageDescriptor("save.png"))
        imageRegistry.put("script", getImageDescriptor("script.png"))
        imageRegistry.put("template", getImageDescriptor("template.png"))
        imageRegistry.put("settings", getImageDescriptor("settings.png"))
        imageRegistry.put("properties", getImageDescriptor("properties.png"))
        imageRegistry.put("list", getImageDescriptor("list.png"))
        imageRegistry.put("group", getImageDescriptor("group.png"))
        imageRegistry.put("type", getImageDescriptor("type.png"))
        imageRegistry.put("entity", getImageDescriptor("entity.png"))
        imageRegistry.put("wrench", getImageDescriptor("wrench.png"))
        imageRegistry.put("tag_blue", getImageDescriptor("tag_blue.png"))
        imageRegistry.put("book", getImageDescriptor("book.png"))
    }

    private fun getImageDescriptor(imageName: String): ImageDescriptor {
        return ImageDescriptor.createFromFile(ApplicationController::class.java, String.format("%s%s", IMAGES_PATH, imageName))
    }

    fun Register(service: NavigationWorkflow) {
        instance = service

    }

    fun navigateTo(view: String) {
        instance.navigateTo(view)
    }

    fun navigateTo(view: String, argument: Any) {
        this.argument = argument
        navigateTo(view)
    }

	fun getImageRegistry(): ImageRegistry {
		return this.imageRegistry
	}
	
}