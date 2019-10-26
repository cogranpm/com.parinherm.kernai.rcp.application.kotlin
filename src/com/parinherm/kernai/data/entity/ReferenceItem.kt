package com.parinherm.kernai.data.entity

import org.eclipse.core.databinding.observable.value.WritableValue
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class ReferenceItem constructor (body: String,
								 createdDate: LocalDate,
								 createdTime: LocalTime,
								 createdDateTime: LocalDateTime,
								 testInt: Int,
								 testLong: Long,
								 testShort: Short,
								 testByte: Byte,
								 testFloat: Float,
								 testDouble: Double,
								 testBool: Boolean,
								 testCombo: String){
	
	
		private val body: WritableValue<String> = WritableValue<String>()
  private val createdDate: WritableValue<LocalDate> = WritableValue<LocalDate>()
  private val  createdTime: WritableValue<LocalTime> = WritableValue<LocalTime>()
  private val  createdDateTime: WritableValue<LocalDateTime> = WritableValue<LocalDateTime>()
  private val  testInt: WritableValue<Int> = WritableValue<Int>()
  private val  testLong: WritableValue<Long> = WritableValue<Long>()
  private val  testShort: WritableValue<Short> = WritableValue<Short>()
  private val  testByte: WritableValue<Byte> = WritableValue<Byte>()
  private val  testFloat: WritableValue<Float> = WritableValue<Float>()
  private val  testDouble: WritableValue<Double> = WritableValue<Double>()
  private val  testBool: WritableValue<Boolean> = WritableValue<Boolean>()
  private val  testCombo: WritableValue<String> = WritableValue<String>()
	
	init {
		this.setBody(body)
		this.setCreatedDate(createdDate)
		this.setCreatedTime(createdTime)
		this.setCreatedDateTime(createdDateTime)
		this.setTestInt(testInt)
		this.setTestLong(testLong)
		this.setTestShort(testShort)
		this.setTestByte(testByte)
		this.setTestFloat(testFloat)
		this.setTestDouble(testDouble)
		this.setTestBool(testBool)
		this.setTestCombo(testCombo)
	}
	
	
	

	
	fun getBody(): String {
		return body.getValue()
	}
	
	fun setBody(data: String) = body.setValue(data)
	

	
	fun getCreatedDate(): LocalDate{
		return createdDate.getValue()
	}
	
	fun setCreatedDate(data: LocalDate) = createdDate.setValue(data)
	
	fun getCreatedTime(): LocalTime {
		return createdTime.getValue()
	}
	
	fun setCreatedTime(data: LocalTime) = createdTime.setValue(data)
	
	fun getCreatedDateTime(): LocalDateTime {
		return createdDateTime.getValue()
	}
	
	fun setCreatedDateTime(data: LocalDateTime) = createdDateTime.setValue(data)
	
	fun getTestInt(): Int {
		return testInt.getValue()
	}
	
	fun setTestInt(data: Int) = testInt.setValue(data)
	
	fun getTestLong(): Long {
		return testLong.getValue()
	}
	
	fun setTestLong(data: Long) = testLong.setValue(data)
	
	fun getTestShort(): Short {
		return testShort.getValue()
	}
	
	fun setTestShort(data: Short) = testShort.setValue(data)
	
	fun getTestDouble(): Double {
		return testDouble.getValue()
	}
	
	fun setTestDouble(data: Double) = testDouble.setValue(data)
	
	fun getTestByte(): Byte {
		return testByte.getValue()
	}
	
	fun setTestByte(data: Byte) = testByte.setValue(data)
	
	fun getTestFloat(): Float {
		return testFloat.getValue()
	}
	
	fun setTestFloat(data: Float) = testFloat.setValue(data)
	
	fun getTestBool(): Boolean {
		return testBool.getValue()
	}
	
	fun setTestBool(data: Boolean) = testBool.setValue(data)
	
	fun getTestCombo(): String {
		return testCombo.getValue()
	}
	
	fun setTestCombo(data: String) = testCombo.setValue(data)
	
	
		
}