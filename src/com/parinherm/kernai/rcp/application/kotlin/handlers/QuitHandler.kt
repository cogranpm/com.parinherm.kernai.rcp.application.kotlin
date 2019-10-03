package com.parinherm.kernai.rcp.application.kotlin.handlers

import org.eclipse.e4.core.di.annotations.Execute
import org.eclipse.e4.ui.workbench.IWorkbench
import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.widgets.Shell

class QuitHandler {
  @Execute
  fun execute(workbench:IWorkbench, shell:Shell) {
    if (MessageDialog.openConfirm(shell, "Confirmation",
                                  "Do you want to exit?"))
    {
      workbench.close()
    }
  }
}