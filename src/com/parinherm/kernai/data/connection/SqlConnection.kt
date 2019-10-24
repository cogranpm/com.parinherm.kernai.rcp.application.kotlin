/* represents a jdbc connection
 
 */

package com.parinherm.kernai.data.connection

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import org.apache.commons.dbutils.QueryRunner

class SqlConnection (driver:String, url:String, schema:String) : DataConnection{
	
	val connection: Connection
	val driver: String
	val url: String
	val schema: String
	
	init {
		this.driver = driver
		this.url = url
		this.schema = schema
		try
		{
			Class.forName(this.driver)
			connection = DriverManager.getConnection(this.url)
			connection.setAutoCommit(false)
		}
		catch(e: SQLException){
			//e.printStackTrace()
			throw e
		}
	}
	
	
	override fun close() {
		connection.close()
		
		
	}
	
	override fun commit(){
		connection.commit()
	}
	
	override fun rollback(){
		connection.rollback()
	}
	
	override fun create(sql:String, elementName:String) : Int{
		var returnCode: Int = 0
		val md = connection.getMetaData()
		val rs = md.getTables(null, this.schema, elementName, arrayOf("TABLE"))
		if(!rs.next()){
			returnCode = QueryRunner().update(connection, sql)			
		}
		rs.close()
		return returnCode
	}
	
	override fun execute(sql:String, param: Any) : Int{
		var returnCode: Int = 0
		try{
			val qr : QueryRunner = QueryRunner()
			returnCode = qr.update(connection, sql, param)
		
		}
		catch(e: SQLException){
			e.printStackTrace()
			//throw e
		}
		
		return returnCode		
	}
	
	override fun execute(sql:String, vararg params: Any) : Int{
		var returnCode: Int = 0
		try{
			val qr : QueryRunner = QueryRunner()
			returnCode = qr.update(connection, sql, params)
		
		}
		catch(e: SQLException){
			e.printStackTrace()
			//throw e
		}
		
		return returnCode
	}

}
