package com.parinherm.kernai.data.connection

import org.eclipse.e4.core.di.annotations.Creatable
import javax.inject.Singleton

@Creatable
@Singleton
class DatabaseManagerFactory {
	
	private lateinit var manager: DatabaseManager
	
	fun getManager(type:String) : DatabaseManager
	{
		if(type == derbyEmbeddedManagerKey){
			manager = DerbyEmbeddedDatabaseManager()
			return manager
		}
		else if(type == h2ManagerKey){
			manager = H2DatabaseManager()
			return manager
		}
		else {
			throw Exception("not defined")
		}
	}
	
	fun getManager() : DatabaseManager
	{
		return manager
	}
	
}