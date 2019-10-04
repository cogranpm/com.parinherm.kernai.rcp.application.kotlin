package com.parinherm.kernai.data.entity

import java.time.LocalDate
import java.time.LocalTime
import java.time.LocalDateTime


data class ReferenceItem (var body: String = "",
						  var createdDate: LocalDate,
						  var createdTime: LocalTime,
						  var createdDateTime: LocalDateTime,
						  var testInt: Int,
						  var testLong: Long,
						  var testShort: Short,
						  var testByte: Byte,
						  var testFloat: Float,
						  var testDouble: Double,
						  var testBool: Boolean){
	
//	constructor (body: String): this(){
//		this.body = body
//	}
	
	init {
		
	}
	
	//var body: String = ""
}