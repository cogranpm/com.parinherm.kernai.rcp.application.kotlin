package com.parinherm.kernai.rcp.application.kotlin.parts

import com.parinherm.kernai.data.entity.ReferenceItem
import com.parinherm.kernai.ui.viewModel.ReferenceItemViewModel
import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.e4.ui.di.Focus
import org.eclipse.e4.ui.di.Persist
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.workbench.modeling.ESelectionService
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.layout.TableColumnLayout
import org.eclipse.jface.viewers.ColumnLabelProvider
import org.eclipse.jface.viewers.ColumnWeightData
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Composite
import javax.annotation.PostConstruct
import javax.inject.Inject

class ReferenceListPart {
	
	//no databinding required for readonly list
	//val ctx: DataBindingContext = DataBindingContext()
	
	lateinit var listView: TableViewer
	val contentProvider: ObservableListContentProvider<Any> = ObservableListContentProvider()
	//lateinit var knownElements: IObservableSet<ReferenceItem>
	
	lateinit var listContainer: Composite
	val model = ReferenceItemViewModel()
	
	@Inject
	lateinit var selectionService: ESelectionService
		
	init{

	}
	
	@Inject
	private lateinit var part: MPart
	
	
//	fun setSelectionService( selectionService: ESelectionService)
//	{
//		this.selectionService = selectionService 
//	}
	
	@PostConstruct
	fun createComposite(parent: Composite) {
		parent.setLayout(FillLayout(SWT.HORIZONTAL))
		listContainer = Composite(parent, SWT.NONE);
		
		
		val listViewer: TableViewer  = TableViewer(listContainer, SWT.SINGLE or SWT.H_SCROLL or SWT.V_SCROLL or SWT.FULL_SELECTION or SWT.BORDER)
		val listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		
		val bodyColumn: TableViewerColumn = getListColumn(listViewer, "Body", SWT.LEFT)
		bodyColumn.setLabelProvider(object: ColumnLabelProvider() {
			override fun getText(element: Any): String {
				val item: ReferenceItem = element as ReferenceItem
				return item.getBody()
			}
		})
		
		
		//val knownElements = contentProvider.getKnownElements()
		//need this is you are doing map type styff
		
		val tableLayout: TableColumnLayout = TableColumnLayout()
		listContainer.setLayout(tableLayout)
		tableLayout.setColumnData(bodyColumn.getColumn(), ColumnWeightData(100))

		listViewer.addSelectionChangedListener { e ->
			val item: IStructuredSelection = e.getStructuredSelection()
			val selectedItem = item.getFirstElement() as ReferenceItem
			selectionService.setSelection(selectedItem)
			
		 }
		
		//no databing required for readonly lists
		//ctx.dispose();
        listViewer.setContentProvider(contentProvider)
        listViewer.setInput(model.input);
       
	}
	
	fun getListColumn(listViewer: TableViewer, columnText: String, style: Int): TableViewerColumn
	{
		val column: TableViewerColumn = TableViewerColumn(listViewer, style)
		column.getColumn().setText(columnText)
		column.getColumn().setResizable(true)
		column.getColumn().setMoveable(false)
		return column
	}

	@Focus
	fun setFocus() {
	
		
	}

	@Persist
	fun save() {
		part.setDirty(false)
	}
}