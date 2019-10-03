package com.parinherm.kernai.ui.viewModel

import com.parinherm.kernai.data.entity.ReferenceItem
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.value.WritableValue
import java.time.LocalDateTime

class ReferenceItemViewModel {
	var selectedItem: WritableValue<ReferenceItem> = WritableValue()
	var items: MutableList<ReferenceItem> = mutableListOf()
	val input = WritableList(items, ReferenceItem::class);
	
	init {
		var item1  = ReferenceItem("Kitlin is printint lines", LocalDateTime.now().toLocalDate())
		var item2  = ReferenceItem("some  match stuff", LocalDateTime.now().toLocalDate())
		items.add(item1)
		items.add(item2)
		selectedItem.value = item1
	}
	
}