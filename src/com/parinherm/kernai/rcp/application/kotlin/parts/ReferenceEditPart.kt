package com.parinherm.kernai.rcp.application.kotlin.parts

import com.parinherm.kernai.data.entity.ReferenceItem
import com.parinherm.kernai.databinding.LocalDateConverter
import com.parinherm.kernai.ui.viewModel.ReferenceItemViewModel
import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.core.databinding.UpdateValueStrategy
//import org.eclipse.core.databinding.beans.PojoProperties
import org.eclipse.core.databinding.beans.typed.PojoProperties
import org.eclipse.core.databinding.conversion.IConverter
import org.eclipse.core.databinding.conversion.StringToNumberConverter
import org.eclipse.e4.core.di.annotations.Optional
import org.eclipse.e4.ui.di.Focus
import org.eclipse.e4.ui.di.Persist
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.services.IServiceConstants
import org.eclipse.jface.databinding.swt.typed.WidgetProperties
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.DateTime
import org.eclipse.swt.widgets.Label
import org.eclipse.swt.widgets.Text
import java.time.LocalDate
import java.time.LocalDateTime
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Named
import org.eclipse.core.databinding.conversion.Converter
import com.parinherm.kernai.databinding.DateTimeSelectionProperty
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.core.databinding.AggregateValidationStatus
import org.eclipse.core.databinding.Binding
import java.time.LocalTime
import org.eclipse.swt.widgets.Button
import org.eclipse.swt.widgets.Spinner
import org.eclipse.jface.databinding.fieldassist.ControlDecorationSupport
import org.eclipse.core.databinding.conversion.NumberToStringConverter
import org.eclipse.core.databinding.validation.ValidationStatus

import kotlin.text.*
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.swt.widgets.Combo
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.LabelProvider
import com.parinherm.kernai.data.entity.LookupDetail
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.viewers.Viewer
import org.eclipse.jface.databinding.viewers.typed.ViewerProperties
import org.eclipse.core.databinding.observable.list.IObservableList
import org.eclipse.core.databinding.ValidationStatusProvider
import org.eclipse.core.databinding.observable.IChangeListener
import org.eclipse.core.databinding.observable.ChangeEvent
import org.eclipse.jface.layout.GridLayoutFactory
import javax.annotation.PreDestroy
import org.eclipse.swt.custom.SashForm
import org.eclipse.swt.layout.FillLayout
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.jface.viewers.ColumnLabelProvider
import org.eclipse.jface.layout.TableColumnLayout
import org.eclipse.jface.viewers.ColumnWeightData
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.e4.ui.workbench.modeling.ESelectionService
import org.eclipse.jface.databinding.viewers.IViewerObservableValue
import org.eclipse.jface.databinding.viewers.IViewerObservableList
import org.eclipse.jface.databinding.viewers.IViewerValueProperty

class ReferenceEditPart {
	
			
	init{
		
	}
	
	@Inject
	private lateinit var part: MPart
	
	@Inject
	lateinit var selectionService: ESelectionService
		
	
	private var txtBody: Text? = null
	private lateinit var dteCreated: DateTime
	private lateinit var dteCreatedTime: DateTime
	private lateinit var btnBool: Button
	private lateinit var spinInt: Spinner
	private lateinit var txtLong: Text
	private lateinit var cbvCombo: ComboViewer
	
	
	private lateinit var lblError: Label
	
	lateinit var listViewer: TableViewer
	val contentProvider: ObservableListContentProvider<Any> = ObservableListContentProvider()
	
	
	lateinit var listContainer: Composite
	
	
	val model = ReferenceItemViewModel()
	val ctx = DataBindingContext()
	var pauseDirtyListener: Boolean = false
	private val listener: IChangeListener = IChangeListener {
		ev: ChangeEvent -> 
		if(!pauseDirtyListener) {
			part.setDirty(true)
		}
	
	}
	
	@PostConstruct
	fun createComposite(parent: Composite) {
		
		//get the part state from persisted state
		//String string = part.getPersistedState().get(Todo.FIELD_ID);
		
		// retrieve the todo based on the persistedState
		//todo = todoService.getTodo(idOfTodo);
		
		//if (todo.isPresent()) {
		//else just put a label up
		
		//here's how you call a command programmatically
		/*
 			Button button = new Button(parent, SWT.PUSH);
			button.setText("Save");
			button.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ParameterizedCommand command = commandService.createCommand("org.eclipse.ui.file.saveAll", null);
					handlerService.executeHandler(command);
				}
			});
 		*/
		
		
		//to do
		//setup a sashform with list on 1 side, edit on the other
		val sashForm: SashForm = SashForm(parent, SWT.HORIZONTAL)
		val compositeList = Composite(sashForm, SWT.NONE)
		val compositeEdit = Composite(sashForm, SWT.NONE)
		sashForm.setWeights(intArrayOf(1, 2))
		parent.setLayout(FillLayout())
		
		compositeEdit.setLayout(GridLayout(2, false))
		compositeList.setLayout(FillLayout())
		
		
		val lblBody = Label(compositeEdit, SWT.NONE)
		lblBody.text = "Body"
		txtBody = Text(compositeEdit, SWT.BORDER)
		txtBody?.setMessage("Enter text to mark part as dirty.")
		//txtBody.addModifyListener({ _ -> part.setDirty(true) })
		txtBody?.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		
		val lblDateCreated = Label(compositeEdit, SWT.NONE)
		lblDateCreated.text = "Created"
		dteCreated = DateTime(compositeEdit, SWT.DROP_DOWN or SWT.DATE)
		
		val lblTimeCreated = Label(compositeEdit, SWT.NONE)
		lblTimeCreated.text = "Time"
		dteCreatedTime= DateTime(compositeEdit, SWT.DROP_DOWN or SWT.TIME)
		
		var lblBool = Label(compositeEdit, SWT.NONE)
		lblBool.text = "Boolean"
		btnBool = Button(compositeEdit, SWT.CHECK)
		
		var lblInt = Label(compositeEdit, SWT.NONE)
		lblInt.text = "Integer"
		spinInt = Spinner(compositeEdit, SWT.NONE)
		spinInt.setMinimum(Int.MIN_VALUE)
		spinInt.setMaximum(Int.MAX_VALUE)
		
		var lblLong = Label(compositeEdit, SWT.NONE)
		lblLong.text = "Long"
		txtLong = Text(compositeEdit, SWT.SINGLE)
		txtLong.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		
		
		//combo box with lookup data
		var lblCombo = Label(compositeEdit, SWT.NONE)
		lblCombo.text = "Combo"
		cbvCombo = ComboViewer(compositeEdit, SWT.NONE)
		//cbvCombo.setContentProvider(ObservableListContentProvider<LookupDetail>())
		cbvCombo.setContentProvider(ArrayContentProvider.getInstance())
		cbvCombo.setLabelProvider(object: LabelProvider() {
			override fun getText(element: Any): String {
				val item: LookupDetail = element as LookupDetail
				return item.label
			}
		})
		//note: a LookupDetail entity is stored in the combo
		//can get it from a selection listener
		cbvCombo.setInput(model.comboLookups)		
		
		lblError = Label(compositeEdit, SWT.NONE)
		val errLayout = GridData(GridData.FILL_HORIZONTAL);
		errLayout.horizontalSpan = 2
		lblError.setLayoutData(errLayout)
		
		
		/*
 You can also use the ObservableMapLabelProvider class to observe changes of the list elements.

ObservableListContentProvider contentProvider =
    new ObservableListContentProvider();

// create the label provider which includes monitoring
// of changes to update the labels

IObservableSet knownElements = contentProvider.getKnownElements();

final IObservableMap firstNames = BeanProperties.value(Person.class,
    "firstName").observeDetail(knownElements);
final IObservableMap lastNames = BeanProperties.value(Person.class,
    "lastName").observeDetail(knownElements);

IObservableMap[] labelMaps = { firstNames, lastNames };

ILabelProvider labelProvider =
    new ObservableMapLabelProvider(labelMaps) {
    public String getText(Object element) {
        return firstNames.get(element) + " " + lastNames.get(element);
    }
};
 */
		
		listContainer = Composite(compositeList, SWT.NONE);
		
		listViewer = TableViewer(listContainer, SWT.SINGLE or SWT.H_SCROLL or SWT.V_SCROLL or SWT.FULL_SELECTION or SWT.BORDER)
		val listTable = listViewer.getTable();
		listTable.setHeaderVisible(true);
		listTable.setLinesVisible(true);
		
		val bodyColumn: TableViewerColumn = getListColumn(listViewer, "Body", SWT.LEFT)
		bodyColumn.setLabelProvider(object: ColumnLabelProvider() {
			override fun getText(element: Any): String {
				val item: ReferenceItem = element as ReferenceItem
				return item.body
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
		
		enableUserInterface(false)

		//GridLayoutFactory.swtDefaults().numColumns(2).generateLayout(parent)
		
		
		
		
		//attempts to get the conversion going
//		val convertToDate : IConverter<String, LocalDate> = IConverter.create(String::class, LocalDate::class, {v :String  -> LocalDateTime.now().toLocalDate()})
//		val convertToString: IConverter<LocalDate, String> = IConverter.create(LocalDate::class, String::class, {v :LocalDate -> ""})
//		val convToDate: UpdateValueStrategy<String, LocalDate> = UpdateValueStrategy.create(convertToDate)
//		val convFromDate:  UpdateValueStrategy<LocalDate, String> = UpdateValueStrategy.create(convertToString)
//		val converter : Converter<String, LocalDate> = LocalDateConverter()
//		val nother : StringToNumberConverter<Int>  = StringToNumberConverter.toInteger(false)
//		val modelToTarget : UpdateValueStrategy<String, LocalDate> =
//		UpdateValueStrategy<String, LocalDate>().setConverter(converter)
//		val iconv: IConverter<String, Integer> = IConverter.create(String::class, Integer::class,  { a:String -> Integer(0)})
			
		
		
		
		


	}
	
	fun getListColumn(listViewer: TableViewer, columnText: String, style: Int): TableViewerColumn
	{
		val column: TableViewerColumn = TableViewerColumn(listViewer, style)
		column.getColumn().setText(columnText)
		column.getColumn().setResizable(true)
		column.getColumn().setMoveable(false)
		return column
	}
	
	private fun enableUserInterface(enable: Boolean) {
		if(txtBody != null && !(txtBody?.isDisposed() ?: false)){
			txtBody?.setEnabled(enable)
			dteCreated.setEnabled(enable)
			dteCreatedTime.setEnabled(enable)
			btnBool.setEnabled(enable)
			spinInt.setEnabled(enable)
			txtLong.setEnabled(enable)
			cbvCombo.getCombo().setEnabled(enable)
		}
	
	}
	
	private fun updateUserInterface() {
		
		if(txtBody == null){
			return
		}
		
		val providers: IObservableList<ValidationStatusProvider?> = ctx.getValidationStatusProviders()
		val providerIterator = providers.iterator()
		while(providerIterator.hasNext()){
			val b:Binding = providerIterator.next() as Binding
			b.getTarget().removeChangeListener(listener)
		}
		
		ctx.dispose()
		
		//trying to get master detail to work
////		val x : IViewerValueProperty<TableViewer, ReferenceItem> = ViewerProperties.singleSelection<TableViewer, ReferenceItem>();
	//	val listSelection = x.observe(listViewer as Viewer)
		val listSelection = ViewerProperties.singleSelection<TableViewer, ReferenceItem>().observe(listViewer as Viewer)
		val model_body = PojoProperties.value<ReferenceItem, String>("body").observeDetail<ReferenceItem>(listSelection)
		
		val target_body = WidgetProperties.text<Text>(SWT.Modify).observe(txtBody)
		//val model_body = PojoProperties.value<ReferenceItem, String>("body").observeDetail<ReferenceItem>(model.selectedItem)
		
		//this is from the vogella tutorial on how to bind to java 8 LocalDate 
		val dateTimeSelectionProperty: DateTimeSelectionProperty<DateTime, Any> = DateTimeSelectionProperty()
		val dateTimeObservableValue = dateTimeSelectionProperty.observe(dteCreated)
		val model_created = PojoProperties.value<ReferenceItem, LocalDate>("createdDate").observeDetail<ReferenceItem>(listSelection)
		
		val timeProp: DateTimeSelectionProperty<DateTime, Any> = DateTimeSelectionProperty()
		val timeOb = timeProp.observe(dteCreatedTime)
		val model_time = PojoProperties.value<ReferenceItem, LocalTime>("createdTime").observeDetail<ReferenceItem>(listSelection)
		
		
		val propBool = WidgetProperties.buttonSelection().observe(btnBool)
		val modelBool = PojoProperties.value<ReferenceItem, Boolean>("testBool").observeDetail<ReferenceItem>(listSelection)
		//val modelBool = PojoProperties.value<ReferenceItem, Boolean>("testBool").observeDetail<ReferenceItem>(model.selectedItem)
		
		val propInt = WidgetProperties.spinnerSelection().observe(spinInt)
		val modelInt = PojoProperties.value<ReferenceItem, Int>("testInt") .observeDetail<ReferenceItem>(listSelection)
		
		val propLong = WidgetProperties.text<Text>(SWT.Modify).observe(txtLong)
		val modelLong = PojoProperties.value<ReferenceItem, Long>("testLong").observeDetail<ReferenceItem>(listSelection)
		
		val propCombo = WidgetProperties.comboSelection().observe(cbvCombo.getCombo())
		val modelCombo = PojoProperties.value<ReferenceItem, String>("testCombo").observeDetail(listSelection)

		val bindBody = ctx.bindValue(target_body, model_body)
		ctx.bindValue(dateTimeObservableValue, model_created)
		ctx.bindValue(timeOb, model_time)
		ctx.bindValue(propBool, modelBool)
		ctx.bindValue(propInt, modelInt)
		ctx.bindValue(propCombo, modelCombo)
	

		
		//long integer binding with validation and conversion
		//the regex allows leading sign symbol and comma's as the
		//StringToNumberConverter seems to want to put in commas
		val longConverter = StringToNumberConverter.toLong(true)
		val longConverterBack = NumberToStringConverter.fromLong(true)
		val longUpdate = UpdateValueStrategy.create(longConverter)
		longUpdate.setAfterGetValidator(
			{x ->
				val entry = x as String
				val regex = "^[+-]?(\\d+(,\\d{3})*)$".toRegex()
				if(regex.matches(entry)){
					ValidationStatus.ok()
				}else {
					ValidationStatus.error("Invalid number entered")
				}
			})

		val longUpdateBack = UpdateValueStrategy.create(longConverterBack)
		val bindLong = ctx.bindValue(propLong, modelLong, longUpdate, longUpdateBack)
		
		ControlDecorationSupport.create(bindLong, SWT.TOP or SWT.LEFT)
		
				// set a dirty state if one of the bindings is changed
		ctx.getBindings().forEach{ item -> 
			var binding =  item
			binding.getTarget().addChangeListener(listener)
			
		}
 		
		
		val  errorObservable: IObservableValue<String> = WidgetProperties.text<Label>().observe(lblError)
		val allValidationBinding: Binding = ctx.bindValue(errorObservable, AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null)

	}
	
	
	@Inject
    @Optional
    fun setModel(@Named(IServiceConstants.ACTIVE_SELECTION) item: ReferenceItem?) {
		//this WILL get called with null item
        if (item != null) {
			enableUserInterface(true)
			pauseDirtyListener = true
			//not really using this any longer, master detail binding to the listviewer
            //model.selectedItem.value = item
			updateUserInterface()
			pauseDirtyListener = false
        }
		else {
			enableUserInterface(false)
		}
    }

	@Focus
	fun setFocus() {
		txtBody?.setFocus()
		
	}

	/* the real save will use an injected service
		public void save(ITodoService todoService) {
		todo.ifPresent(todo -> todoService.saveTodo(todo));
	*/
	
	
	@Persist
	fun save() {
		//prove that the model has been updated via databinding
		//println(model.selectedItem.value.body)
		//println(model.selectedItem.value.createdDate)
		model.items.forEach{
			println (it.body)
			println (it.createdDate)
			println (it.createdDateTime)
		}
		part.setDirty(false)
	}
	
	@PreDestroy
	fun dispose() {
		ctx.dispose()
	}
}