package com.parinherm.kernai.data.connection

interface DatabaseManager {
	fun openConnection(connectionProperties: DataConnectionProperties)
	fun closeConnection()
	fun createTables()
	fun getConnection() : DataConnection
}