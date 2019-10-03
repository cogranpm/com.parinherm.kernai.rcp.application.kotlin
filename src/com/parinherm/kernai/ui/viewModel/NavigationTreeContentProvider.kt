package com.parinherm.kernai.ui.viewModel

import org.eclipse.jface.viewers.ITreeContentProvider
import java.util.ArrayList

class NavigationTreeContentProvider : ITreeContentProvider {

    override fun getElements(inputElement: Any): Array<Any> {
        val items = inputElement as ArrayList<NavigationItem>
        return items.toArray()
    }

    
    override fun getChildren(parentElement: Any): Array<Any> {
        val item = parentElement as NavigationItem
        return item.items.toTypedArray()
    }

    override fun getParent(element: Any): Any? {
        return null
    }

    override fun hasChildren(element: Any): Boolean {
        val item = element as NavigationItem
		return item.items.size > 0
    }

}