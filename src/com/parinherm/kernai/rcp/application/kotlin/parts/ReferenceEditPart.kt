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

class ReferenceEditPart {
	
			
	init{
		
	}
	
	@Inject
	private lateinit var part: MPart
	
	private lateinit var txtBody: Text
	private lateinit var dteCreated: DateTime
	private lateinit var dteCreatedTime: DateTime
	private lateinit var btnBool: Button
	private lateinit var spinInt: Spinner
	private lateinit var txtLong: Text
	private lateinit var cbvCombo: ComboViewer
	
	
	private lateinit var lblError: Label
	
	val model = ReferenceItemViewModel()
	val ctx = DataBindingContext()
	var pauseDirtyListener: Boolean = false
	
	@PostConstruct
	fun createComposite(parent: Composite) {
		parent.setLayout(GridLayout(2, false))
		
		
		val lblBody = Label(parent, SWT.NONE)
		lblBody.text = "Body"
		txtBody = Text(parent, SWT.BORDER)
		txtBody.setMessage("Enter text to mark part as dirty.")
		//txtBody.addModifyListener({ _ -> part.setDirty(true) })
		txtBody.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		
		val lblDateCreated = Label(parent, SWT.NONE)
		lblDateCreated.text = "Created"
		dteCreated = DateTime(parent, SWT.DROP_DOWN or SWT.DATE)
		
		val lblTimeCreated = Label(parent, SWT.NONE)
		lblTimeCreated.text = "Time"
		dteCreatedTime= DateTime(parent, SWT.DROP_DOWN or SWT.TIME)
		
		var lblBool = Label(parent, SWT.NONE)
		lblBool.text = "Boolean"
		btnBool = Button(parent, SWT.CHECK)
		
		var lblInt = Label(parent, SWT.NONE)
		lblInt.text = "Integer"
		spinInt = Spinner(parent, SWT.NONE)
		spinInt.setMinimum(Int.MIN_VALUE)
		spinInt.setMaximum(Int.MAX_VALUE)
		
		var lblLong = Label(parent, SWT.NONE)
		lblLong.text = "Long"
		txtLong = Text(parent, SWT.SINGLE)
		txtLong.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		
		
		var lblCombo = Label(parent, SWT.NONE)
		lblCombo.text = "Combo"
		cbvCombo = ComboViewer(parent, SWT.NONE)
		cbvCombo.setContentProvider(ArrayContentProvider.getInstance())
		cbvCombo.setLabelProvider(object: LabelProvider() {
			override fun getText(element: Any): String {
				val item: LookupDetail = element as LookupDetail
				return item.label
			}
		})
		cbvCombo.setInput(model.comboLookups)
		
		lblError = Label(parent, SWT.NONE)
		val errLayout = GridData(GridData.FILL_HORIZONTAL);
		errLayout.horizontalSpan = 2
		lblError.setLayoutData(errLayout)

		val target_body = WidgetProperties.text<Text>(SWT.Modify).observe(txtBody)
		val model_body = PojoProperties.value<ReferenceItem, String>("body").observeDetail<ReferenceItem>(model.selectedItem)
		
		//this is from the vogella tutorial on how to bind to java 8 LocalDate 
		val dateTimeSelectionProperty: DateTimeSelectionProperty<DateTime, Any> = DateTimeSelectionProperty()
		val dateTimeObservableValue = dateTimeSelectionProperty.observe(dteCreated)
		val model_created = PojoProperties.value<ReferenceItem, LocalDate>("createdDate").observeDetail<ReferenceItem>(model.selectedItem)
		
		val timeProp: DateTimeSelectionProperty<DateTime, Any> = DateTimeSelectionProperty()
		val timeOb = timeProp.observe(dteCreatedTime)
		val model_time = PojoProperties.value<ReferenceItem, LocalTime>("createdTime").observeDetail<ReferenceItem>(model.selectedItem)
		
		
		val propBool = WidgetProperties.buttonSelection().observe(btnBool)
		val modelBool = PojoProperties.value<ReferenceItem, Boolean>("testBool").observeDetail<ReferenceItem>(model.selectedItem)
		
		val propInt = WidgetProperties.spinnerSelection().observe(spinInt)
		val modelInt = PojoProperties.value<ReferenceItem, Int>("testInt") .observeDetail<ReferenceItem>(model.selectedItem)
		
		val propLong = WidgetProperties.text<Text>(SWT.Modify).observe(txtLong)
		val modelLong = PojoProperties.value<ReferenceItem, Long>("testLong").observeDetail<ReferenceItem>(model.selectedItem)
		
		val bindBody = ctx.bindValue(target_body, model_body)
		ctx.bindValue(dateTimeObservableValue, model_created)
		ctx.bindValue(timeOb, model_time)
		ctx.bindValue(propBool, modelBool)
		ctx.bindValue(propInt, modelInt)
		
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
			binding.getTarget().addChangeListener{e -> 
				if(!pauseDirtyListener) {
					part.setDirty(true)
				}
			}
		}
 		
		
		val  errorObservable: IObservableValue<String> = WidgetProperties.text<Label>().observe(lblError)
		val allValidationBinding: Binding = ctx.bindValue(errorObservable, AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null)
		
		
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
	
	
	@Inject
    @Optional
    fun setModel(@Named(IServiceConstants.ACTIVE_SELECTION) item: ReferenceItem?) {
		//this WILL get called with null item
        if (item != null) {
            model.selectedItem.value = item		 
        }
    }

	@Focus
	fun setFocus() {
		txtBody.setFocus()
		
	}

	@Persist
	fun save() {
		//prove that the model has been updated via databinding
		println(model.selectedItem.value.body)
		println(model.selectedItem.value.createdDate)
		part.setDirty(false)
	}
}