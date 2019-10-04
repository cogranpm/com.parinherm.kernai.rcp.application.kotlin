/*
 custom class to implement databinding for LocalDate java 8 new date classes
 take from the vogella tutorials on datababinding:
 https://www.vogella.com/tutorials/EclipseDataBinding/article.html
 use this class in databinding statements when using the SWT DateTime control
 ie:
 		val dateTimeSelectionProperty: DateTimeSelectionProperty<DateTime, Any> = DateTimeSelectionProperty()
		val dateTimeObservableValue = dateTimeSelectionProperty.observe(dteCreated)
		val model_created = PojoProperties.value<ReferenceItem, LocalDate>("createdDate").observeDetail<ReferenceItem>(model.selectedItem)
 
*/

package com.parinherm.kernai.databinding

import org.eclipse.jface.databinding.swt.WidgetValueProperty
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Widget
import org.eclipse.swt.widgets.DateTime
import java.time.temporal.TemporalAccessor
import java.time.LocalTime
import java.time.LocalDate
import java.time.temporal.ChronoField
import java.util.Date
import java.time.LocalDateTime
import java.util.Calendar
		
class DateTimeSelectionProperty<DateTime, Any> () : WidgetValueProperty<Widget, Any> (SWT.Selection) {
	
	val MONTH_MAPPING_VALUE = 1
	
	override fun getValueType() : Any {
		return TemporalAccessor::class.java as Any
	}
	
	override fun doGetValue(source: Widget) : Any {
		val dateTime: org.eclipse.swt.widgets.DateTime = source as org.eclipse.swt.widgets.DateTime
		if ((dateTime.getStyle() and SWT.TIME) != 0) {
			return LocalTime.of(dateTime.getHours(), dateTime.getMinutes(), dateTime.getSeconds()) as Any
		}
		
		return LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay()) as Any
				
	}
	

	override fun doSetValue(source: Widget, value: Any) {
		val dateTime: org.eclipse.swt.widgets.DateTime = source as org.eclipse.swt.widgets.DateTime
		val ta = getTemporalAccessor(value)
		
		if(ta != null){
			if((dateTime.getStyle() and SWT.TIME) != 0) {
			dateTime.setTime(ta.get(ChronoField.HOUR_OF_DAY),
				ta.get(ChronoField.MINUTE_OF_HOUR),
				ta.get(ChronoField.SECOND_OF_MINUTE))
			} else {
				dateTime.setDate(ta.get(ChronoField.YEAR),
					ta.get(ChronoField.MONTH_OF_YEAR) - MONTH_MAPPING_VALUE,
					ta.get(ChronoField.DAY_OF_MONTH))
			}
		}
	}
	
	private fun getTemporalAccessor(value: Any): TemporalAccessor? {
		var ta: TemporalAccessor? = null
		if (value is Date) {
			ta = LocalDateTime.from((value as Date).toInstant())			
		} else if(value is TemporalAccessor){
			ta = value
		} else if(value is Calendar) {
			ta = LocalDateTime.from((value as Calendar).toInstant())
		}
		return ta
	}
	

}