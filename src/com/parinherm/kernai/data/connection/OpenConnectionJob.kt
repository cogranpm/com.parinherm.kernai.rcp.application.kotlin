package com.parinherm.kernai.data.connection

import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.e4.ui.di.UISynchronize
import org.eclipse.swt.widgets.Text

class OpenConnectionJob: Job {
	
	private val sync: UISynchronize
	private val manager: DatabaseManager
	private val connectionProperties: DataConnectionProperties
	//private val txtWidget: Text
	
	init {
	
	}
	
	constructor (name:String, sync: UISynchronize, manager: DatabaseManager, connectionProperties: DataConnectionProperties) : super (name){
		this.sync = sync
		this.manager = manager
		this.connectionProperties = connectionProperties
		//this.txtWidget = txtWidget
	}
	
	override fun run (monitor: IProgressMonitor) : IStatus {
		
		//long running operation
		manager.openConnection(connectionProperties)
		manager.createTables()
		
		// If you want to update the UI
	    sync.asyncExec{
			println ("database connected")
			
		}
			
		return Status.OK_STATUS
	}
}