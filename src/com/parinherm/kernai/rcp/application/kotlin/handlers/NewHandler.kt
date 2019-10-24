package com.parinherm.kernai.rcp.application.kotlin.handlers

import org.eclipse.e4.core.di.annotations.CanExecute
import org.eclipse.e4.core.di.annotations.Execute
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.services.IServiceConstants
import org.eclipse.e4.ui.workbench.modeling.EPartService
import javax.inject.Named

class NewHandler {
	@Execute
	fun execute( partService: EPartService, @Optional @Named(IServiceConstants.ACTIVE_PART)  activePart:MPart? ) {
		if (activePart == null) {
			return
		}
		val partObject:Any? = activePart.getObject()
		//should send a message to the part here
		//need a better solution than getting the "Object" the part points to
		//casting it via interface and calling a method on the interface
	}
	
	
	@CanExecute
	fun canExecute( partService: EPartService, @Optional @Named(IServiceConstants.ACTIVE_PART)  activePart:MPart? ) : Boolean{
		if (activePart == null) {
			return false
		}
		
		return true
	}
}