package com.parinherm.kernai.rcp.application.kotlin.handlers

import org.eclipse.e4.core.di.annotations.CanExecute
import org.eclipse.e4.core.di.annotations.Execute
import org.eclipse.e4.ui.workbench.IWorkbench
import org.eclipse.e4.ui.workbench.modeling.ESelectionService
import org.eclipse.swt.widgets.Shell

class DeleteHandler {
	@Execute
	fun execute(workbench:IWorkbench, shell:Shell) {
	  println("delete")
	}
	
	@CanExecute
	fun canExecute(selService: ESelectionService): Boolean {
		return selService.getSelection() != null
	}
		
}