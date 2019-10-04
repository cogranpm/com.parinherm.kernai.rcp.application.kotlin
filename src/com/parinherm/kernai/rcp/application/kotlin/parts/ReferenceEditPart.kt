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

class ReferenceEditPart {
	
			
	init{
		
	}
	
	@Inject
	private lateinit var part: MPart
	
	private lateinit var txtBody: Text
	private lateinit var dteCreated: DateTime
	private lateinit var dteCreatedTime: DateTime
	private lateinit var btnBool: Button
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
		var modelBool = PojoProperties.value<ReferenceItem, Boolean>("testBool").observeDetail<ReferenceItem>(model.selectedItem)
		
		ctx.bindValue(target_body, model_body)
		ctx.bindValue(dateTimeObservableValue, model_created)
		ctx.bindValue(timeOb, model_time)
		ctx.bindValue(propBool, modelBool)
		
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