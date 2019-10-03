package com.parinherm.kernai.rcp.application.kotlin.parts.scripting

import com.parinherm.kernai.data.entity.Script
import org.eclipse.e4.ui.di.Focus
import org.eclipse.e4.ui.di.Persist
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Text
import javax.annotation.PostConstruct
import javax.inject.Inject

class ScriptEditorPart (var script: Script = Script()) {
	
	init{
		
	}
	
	@Inject
	private lateinit var part: MPart
	
	private lateinit var txtInput: Text
	
	@PostConstruct
	fun createComposite(parent: Composite) {
		parent.setLayout(GridLayout(1, false))
		txtInput = Text(parent, SWT.BORDER)
		txtInput.setMessage("Enter text to mark part as dirty")
		txtInput.addModifyListener({ e -> part.setDirty(true) })
		txtInput.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		test()
		//txtInput.text  = script.name
	}

	@Focus
	fun setFocus() {
		txtInput.setFocus()
		
	}

	@Persist
	fun save() {
		part.setDirty(false)
	}

	
	fun test(){
		this.script.engine = "groovy"
		this.script.name = "groovy test"
		this.script.entityKey.key = 10009L
	}
	
}