package com.parinherm.kernai.ui.viewModel

import org.eclipse.jface.viewers.StyledCellLabelProvider
import org.eclipse.jface.viewers.StyledString
import org.eclipse.jface.viewers.ViewerCell
import org.eclipse.swt.graphics.Image
import com.parinherm.kernai.ui.service.ApplicationController

class NavigationTreeLabelProvider : StyledCellLabelProvider() {

    
    override fun update(cell: ViewerCell) {
        val item = cell.getElement() as NavigationItem
        val text = StyledString()
        text.append(item.label)
        cell.setImage(getImage(item.imageURL))
        cell.setText(text.toString())
        cell.setStyleRanges(text.getStyleRanges())
        super.update(cell)
    }

    internal fun getImage(imageURL: String): Image {
       return ApplicationController.getImageRegistry().get(imageURL)
    }
}