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


class Lifecycle {
	
	@Inject
	private lateinit var managerFactory: DatabaseManagerFactory
	
	@PostContextCreate
	fun postContextCreate(appContext: IApplicationContext, workbenchContext: IEclipseContext, display: Display, eventBroker: IEventBroker) {
		
		try {

			//MessageDialog.openInformation(display.getActiveShell(), "Starting", "Message on startup");
			
			eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, object: EventHandler {
				
				override fun handleEvent(event: Event) {
					if (display.getActiveShell() == null){
						return
					}
					display.getActiveShell().addDisposeListener(object: DisposeListener {

						override fun widgetDisposed(e: DisposeEvent) {
							
							//managerFactory.getManager().closeConnection()
							/* example of getting object from context
							if(workbenchContext != null){
								val connectionVal: Any? = workbenchContext.get("connection")
								if(connectionVal != null){
									val connection = connectionVal as DataConnection
									connection.close()
								}
							}
 							*/
						}
					});
					eventBroker.unsubscribe(this);
				}
			});

			
			
		
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
	fun partActivation(@UIEventTopic(UIEvents.UILifeCycle.ACTIVATE) event: Event, application: MApplication)
	{
		/* an example here of how to respond to active part change, perhaps to disable/enable menus etc */
		var apart = event.getProperty(UIEvents.EventTags.ELEMENT)
		val activePart: MPart? = apart as MPart?
		val context = application.getContext();
		if(activePart != null) {
			println("Active part changed in Lifecycle class: $activePart.getElementId()");
		}
		 
	}
	
	
}