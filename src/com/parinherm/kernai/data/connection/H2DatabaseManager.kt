package com.parinherm.kernai.data.connection

import java.sql.DriverManager
import java.sql.SQLException

class H2DatabaseManager : DatabaseManager  {
	
	val driverName= "org.h2.Driver"
	val defaultSchema = "PUBLIC"
	val tableNameNote = "NOTE"
	val tableNameNoteheader = "NOTEHEADER"
	val tableNameReferenceItem = "REFERENCEITEM"
	
	val createNote =
		"""
		CREATE TABLE $tableNameNote
		(${tableNameNote}_ID IDENTITY NOT NULL PRIMARY KEY, BODY VARCHAR(256));
		"""
	val createNoteHeader =
		"""
		CREATE TABLE $tableNameNoteheader
		(${tableNameNoteheader}_ID IDENTITY NOT NULL PRIMARY KEY, NAME VARCHAR(50) NOT NULL, BODY VARCHAR(256));
		"""
	
	val createReferenceItem =
		"""
		CREATE TABLE $tableNameReferenceItem
		(${tableNameReferenceItem}_ID IDENTITY NOT NULL PRIMARY KEY, BODY VARCHAR(256));
		"""
	
	private lateinit var connection:DataConnection
	private lateinit var connectionProperties: DataConnectionProperties
	
	override fun openConnection(connectionProperties: DataConnectionProperties)
	{
		this.connectionProperties = connectionProperties
		val connectionURL = "jdbc:h2:${this.connectionProperties.folder};SCHEMA=${defaultSchema}"
		connection = SqlConnection(driverName, connectionURL, defaultSchema)
			
	}
	
	override fun closeConnection()
	{
		println("connecton closed on exit")
		connection.close()
	}
	
	override fun getConnection() : DataConnection {
		return this.connection
	}
	
	override fun createTables() {
		try{
			//create the tables
			connection.create(createNote, tableNameNote)
			connection.create(createNoteHeader, tableNameNoteheader)
			connection.create(createReferenceItem, tableNameReferenceItem)
			connection.commit()
		}
		catch(e: SQLException){
			e.printStackTrace()
			connection.rollback()
		}
	}
}