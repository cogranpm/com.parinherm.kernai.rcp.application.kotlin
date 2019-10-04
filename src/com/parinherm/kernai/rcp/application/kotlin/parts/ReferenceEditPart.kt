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
import org.eclipse.jface.databinding.swt.WidgetProperties
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

class ReferenceEditPart {
	
			
	init{
		
	}
	
	@Inject
	private lateinit var part: MPart
	
	private lateinit var txtBody: Text
	private lateinit var dteCreated: DateTime
	
	val model = ReferenceItemViewModel()
	val ctx = DataBindingContext()
	
	
	@PostConstruct
	fun createComposite(parent: Composite) {
		parent.setLayout(GridLayout(2, false))
		val lblBody = Label(parent, SWT.NONE)
		lblBody.text = "Body"
		txtBody = Text(parent, SWT.BORDER)
		txtBody.setMessage("Enter text to mark part as dirty.")
		txtBody.addModifyListener({ e -> part.setDirty(true) })
		txtBody.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		
		val lblDateCreated = Label(parent, SWT.NONE)
		lblDateCreated.text = "Created"
		dteCreated = DateTime(parent, SWT.DATE)
		
		/* databinding stuff
 
 converters:
     public IConverter convertStringToInteger = StringToNumberConverter.toInteger(false);
    public IConverter convertIntegerToString = NumberToStringConverter.fromInteger(false);
    
	*/
		
		val target_body = WidgetProperties.text(SWT.Modify).observe(txtBody)
//		val model_body = PojoProperties.value("body").observeDetail<ReferenceItem>(model.selectedItem)
		val model_body = PojoProperties.value<ReferenceItem, String>("body").observeDetail<ReferenceItem>(model.selectedItem)
		
		val target_created = WidgetProperties.selection().observe(dteCreated)
		//val model_created = PojoProperties.value("body").observeDetail<ReferenceItem>(model.selectedItem)
		val model_created = PojoProperties.value<ReferenceItem, String>("createdDate").observeDetail<ReferenceItem>(model.selectedItem)
		
		val convertToDate : IConverter<String, LocalDate> = IConverter.create(String::class, LocalDate::class, {v :String  -> LocalDateTime.now().toLocalDate()})
		val convertToString: IConverter<LocalDate, String> = IConverter.create(LocalDate::class, String::class, {v :LocalDate -> ""})
		
		ctx.bindValue(target_body, model_body)
		ctx.bindValue(target_created, model_created)
		
		
		val convToDate: UpdateValueStrategy<String, LocalDate> = UpdateValueStrategy.create(convertToDate)
		val convFromDate:  UpdateValueStrategy<LocalDate, String> = UpdateValueStrategy.create(convertToString)
	
		val converter : Converter<String, LocalDate> = LocalDateConverter()
		val nother : StringToNumberConverter<Int>  = StringToNumberConverter.toInteger(false)
		
		
		val modelToTarget : UpdateValueStrategy<String, LocalDate> =
		UpdateValueStrategy<String, LocalDate>().setConverter(converter)
		
		val iconv: IConverter<String, Integer> = IConverter.create(String::class, Integer::class,  { a:String -> Integer(0)})
			
		
		
		/*val modelToTarget : UpdateValueStrategy<String, String> =
		UpdateValueStrategy<String, String>().setConverter(Converter<String, String>(String::class, String::class) {
			fun convert(savedEmail: Object) {
				return ""
			}
      	})
 		*/
		
		
		//val binding = ctx.bindValue(target_created, model_created, UpdateValueStrategy.create(iconv),UpdateValueStrategy.create(nother))
		
		//try the side effects api
		//val changeLabel = Label(parent, SWT.NONE)
		//val sideEffectFactory = WidgetSideEffects.createFactory(txtBody)
		//val sideEffect : ISideEffect  = sideEffectFactory.create({ -> "You know I changed mon ${target.getValue().toString()}"}, changeLabel::setText)
		//val sideEffect = ISideEffect.create({ -> "bogshat ${target.getValue()}"}, changeLabel::setText)
		
		/*
		final IObservableValue errorObservable = WidgetProperties.text().observe(lblError)
		Binding allValidationBinding = ctx.bindValue(errorObservable, new AggregateValidationStatus(ctx.getBindings(), AggregateValidationStatus.MAX_SEVERITY), null, null)
		
		
		
		// set a dirty state if one of the bindings is changed
		ctx.getBindings().forEach{ item -> 
			Binding binding = (Binding) item
			binding.getTarget().addChangeListener{e -> 
				if(!pauseDirtyListener) {
					dirty.setDirty(true)
				}
			}
		}
 		*/

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
		part.setDirty(false)
	}
}