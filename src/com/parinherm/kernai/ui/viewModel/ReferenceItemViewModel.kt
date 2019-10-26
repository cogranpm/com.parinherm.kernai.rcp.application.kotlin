package com.parinherm.kernai.ui.viewModel

import com.parinherm.kernai.data.entity.ReferenceItem
import org.eclipse.core.databinding.observable.value.WritableValue
import java.time.LocalDate
import java.time.LocalDateTime
import com.parinherm.kernai.data.entity.LookupDetail
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.Realm

class ReferenceItemViewModel {
	//var selectedItem: WritableValue<ReferenceItem> = WritableValue()
	var items: ArrayList<ReferenceItem> = ArrayList<ReferenceItem>()
	val input = WritableList<ReferenceItem>(items, Realm.getDefault());
	val comboLookups = listOf(LookupDetail("mn", "Minnesota"), LookupDetail("ca", "California"), LookupDetail("ny", "New York"))
	
	init {
		var item1  = ReferenceItem("Kitlin is printint lines",
			 LocalDateTime.now().toLocalDate(),
			LocalDateTime.now().toLocalTime(),
			LocalDateTime.now(),
			1_147_483_647,
			3000000000,
			32767,
			127,
			2.7182818284f,
			3.14,
			false,
			"mn"
			)
		
		var item2  = ReferenceItem("some  match stuff",
			 LocalDate.of(1971, 12, 4),
			LocalDateTime.of(1971, 12, 4, 8, 45, 20).toLocalTime(),
			LocalDateTime.now(),
			-1_147_483_647,
			-3000000000,
			-32767,
			-127,
			-2.7182818284f,
			-3.14,
			true,
			"ca"
			)
		
		
		items.add(item1)
		items.add(item2)
		//selectedItem.value = item1
	}
	
}