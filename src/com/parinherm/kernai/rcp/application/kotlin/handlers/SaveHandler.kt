package com.parinherm.kernai.rcp.application.kotlin.handlers

import org.eclipse.e4.core.di.annotations.CanExecute
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.services.IServiceConstants
import org.eclipse.e4.ui.workbench.modeling.EPartService
import javax.inject.Named
import org.eclipse.e4.core.di.annotations.Execute

class SaveHandler {
	
	@CanExecute
	fun canExecute(partService: EPartService, @Optional @Named(IServiceConstants.ACTIVE_PART)  activePart:MPart?): Boolean {
		val dirtyParts = partService.getDirtyParts()
		return dirtyParts.isNotEmpty()
	}
	
	@Execute
	fun execute(partService: EPartService, @Optional @Named(IServiceConstants.ACTIVE_PART)  activePart:MPart?) {
		if(activePart == null) {
			return
		}
		partService.savePart(activePart, false )

	}
}