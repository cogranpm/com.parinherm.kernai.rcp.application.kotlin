/* agnostic version of a database connection
 could be jdbc or could be nosql etc
 */

package com.parinherm.kernai.data.connection

interface DataConnection {
	fun close()
	fun commit()
	fun rollback()
	fun create(sql:String, elementName:String) : Int
	fun execute(sql:String, vararg params: Any) : Int
	fun execute(sql:String, param: Any) : Int
	//fun insert() : Int
	//fun update() : Int
}