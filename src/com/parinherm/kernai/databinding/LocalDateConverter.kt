package com.parinherm.kernai.databinding

import org.eclipse.core.databinding.conversion.Converter
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateConverter : Converter <String, LocalDate> (String::class, LocalDate::class) {
	override fun convert(fromObject: String) : LocalDate {
		return LocalDateTime.now().toLocalDate()
	}
}