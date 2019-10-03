package com.parinherm.kernai.data.connection

import java.sql.DriverManager
import java.sql.SQLException

class DerbyEmbeddedDatabaseManager : DatabaseManager {

	val driverNameDerbyEmbedded = "org.apache.derby.jdbc.EmbeddedDriver"
	val defaultSchema = "APP"
	val tableNameNote = "NOTE"
	val tableNameNoteheader = "NOTEHEADER"
	val createNote =
		"""
	 	CREATE TABLE $tableNameNote
		(${tableNameNote}_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY
		CONSTRAINT ${tableNameNote}_PK PRIMARY KEY, BODY VARCHAR(256))
		"""
	val createNoteHeader =
		"""
	 	CREATE TABLE $tableNameNoteheader
		(${tableNameNoteheader}_ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY
		CONSTRAINT ${tableNameNoteheader}_PK PRIMARY KEY, NAME VARCHAR(50), BODY VARCHAR(256))
		"""	
	
	private lateinit var connection:DataConnection
	private lateinit var connectionProperties: DataConnectionProperties
	
	override fun openConnection(connectionProperties: DataConnectionProperties)
	{
		this.connectionProperties = connectionProperties
		val connectionURL = "jdbc:derby:directory:${this.connectionProperties.folder};create=true"
		connection = SqlConnection(driverNameDerbyEmbedded, connectionURL, defaultSchema)
			
	}
	
	override fun closeConnection()
	{
		connection.close()
		var gotSQLExc: Boolean = false;
		try {
			DriverManager.getConnection("jdbc:derby:;shutdown=true");
		} catch (se: SQLException) {
			if (se.getSQLState().equals("XJ015")) {
				gotSQLExc = true;
			}
		}
		if (!gotSQLExc) {
			println("Database did not shut down normally");
		} else {
			println("Database shut down normally");
		}
		
	}
	
	override fun getConnection() : DataConnection {
		return this.connection
	}
	
	override fun createTables() {
		try{
			//set default schema on connection
			connection.execute("SET SCHEMA ?", defaultSchema)
			//create the tables
			connection.create(createNote, tableNameNote)
			connection.create(createNoteHeader, tableNameNoteheader)
			connection.commit()
		}
		catch(e: SQLException){
			e.printStackTrace()
			connection.rollback()
		}
	}
}