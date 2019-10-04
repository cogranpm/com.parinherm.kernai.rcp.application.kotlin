package com.parinherm.kernai.rcp.application.kotlin.parts

//import com.parinherm.kernai.data.connection.DataConnection
import com.parinherm.kernai.data.connection.DataConnectionProperties
import com.parinherm.kernai.data.connection.DatabaseManager
import com.parinherm.kernai.data.connection.DatabaseManagerFactory
import com.parinherm.kernai.data.connection.OpenConnectionJob
import com.parinherm.kernai.data.connection.applicationName
import com.parinherm.kernai.data.connection.derbyEmbeddedManagerKey
import com.parinherm.kernai.data.connection.h2ManagerKey
import com.parinherm.kernai.ui.service.ApplicationController
import com.parinherm.kernai.ui.service.NavigationWorkflowImpl
import com.parinherm.kernai.ui.viewModel.NavigationItem
import com.parinherm.kernai.ui.viewModel.NavigationTreeContentProvider
import com.parinherm.kernai.ui.viewModel.NavigationTreeLabelProvider
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.e4.core.contexts.IEclipseContext
import org.eclipse.e4.core.services.log.Logger
import org.eclipse.e4.ui.di.Focus
import org.eclipse.e4.ui.di.Persist
import org.eclipse.e4.ui.di.UISynchronize
import org.eclipse.e4.ui.model.application.MApplication
import org.eclipse.e4.ui.model.application.ui.basic.MPart
import org.eclipse.e4.ui.model.application.ui.basic.MWindow
import org.eclipse.e4.ui.services.IServiceConstants
import org.eclipse.e4.ui.workbench.modeling.EModelService
import org.eclipse.e4.ui.workbench.modeling.EPartService
import org.eclipse.jface.viewers.ISelectionChangedListener
import org.eclipse.jface.viewers.IStructuredSelection
import org.eclipse.jface.viewers.SelectionChangedEvent
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TreeViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.layout.FillLayout
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.widgets.Text
import org.eclipse.swt.widgets.Tree
import java.util.Arrays
import javax.annotation.PostConstruct
import javax.inject.Inject
import javax.inject.Named
import org.eclipse.e4.core.contexts.ContextInjectionFactory

class NavigationPart {

	@Inject
	private lateinit var managerFactory: DatabaseManagerFactory
	//private lateinit var tableViewer: TableViewer
	//private lateinit var txtInput: Text

	@Inject
	private lateinit var application: MApplication;

	@Inject
	private lateinit var context: IEclipseContext


	@Inject
	private lateinit var part: MPart

	@Inject
	private lateinit var sync: UISynchronize
	
	
	@Inject
	private lateinit var logger: Logger
	
	@Inject
	private lateinit var modelService: EModelService
	

	
	@Inject
	private lateinit var window: MWindow 
	
	@Inject
	private lateinit var partService: EPartService
	
    @Inject
    @Named (IServiceConstants.ACTIVE_SHELL)
    private lateinit var shell: Shell
	
	private lateinit var navTreeViewer: TreeViewer
	private lateinit var navTree: Tree
	
	private lateinit var navigationWorkflow: NavigationWorkflowImpl
	

	@PostConstruct
	fun createComposite(parent: Composite) {
		/*parent.setLayout(GridLayout(1, false))
		txtInput = Text(parent, SWT.BORDER)
		txtInput.setMessage("Enter text to mark part as dirty")
		txtInput.addModifyListener({ e -> part.setDirty(true) })
		txtInput.setLayoutData(GridData(GridData.FILL_HORIZONTAL))
		tableViewer = TableViewer(parent)
		tableViewer.setContentProvider(ArrayContentProvider.getInstance())
		tableViewer.setInput(createInitialDataModel())
		tableViewer.getTable().setLayoutData(GridData(GridData.FILL_BOTH))
		*/
		logger.error("logging message yo")
		val root = Composite(parent, SWT.NONE)
		
		//new trick, to get my own classes instantiated with DI use the following method
		//this allows the class NavigationWorkflowImpl to use @Inject itself
		navigationWorkflow = ContextInjectionFactory.make(NavigationWorkflowImpl::class.java, context)
        ApplicationController.init(shell)
        ApplicationController.Register(navigationWorkflow)
 		
        root.setLayout(FillLayout(SWT.VERTICAL))

        navTreeViewer = TreeViewer(root, SWT.BORDER)
        navTree = navTreeViewer.getTree()
        navTreeViewer.setLabelProvider(NavigationTreeLabelProvider())
        navTreeViewer.setContentProvider(NavigationTreeContentProvider())
        navTreeViewer.setInput(ApplicationController.navigationItems)

//        navTreeViewer.addSelectionChangedListener(object : ISelectionChangedListener {
//
//            
//          override  fun selectionChanged(event: SelectionChangedEvent) {
//                val selection = navTreeViewer.getSelection() as IStructuredSelection
//                val firstElement = selection.getFirstElement()
//                val item = firstElement as NavigationItem
//                if (item.viewName != "") {
//                   
//                   ApplicationController.navigateTo(item.viewName)
//                }
//
//            }
//        })
		
		navTreeViewer.addSelectionChangedListener({
				_ ->
				val selection = navTreeViewer.getSelection() as IStructuredSelection
                val firstElement = selection.getFirstElement()
                val item = firstElement as NavigationItem
                if (item.viewName != "") {
                   
                   ApplicationController.navigateTo(item.viewName)
                }
		})
		setupConnections()

	}


	private fun setupConnections() {
		
		//if database preference is not set, show the database selector here
		//which gets the type of database and connection properties
		val connectionProperties: DataConnectionProperties = DataConnectionProperties()
		//hard coding embedded derby
		//connectionProperties.databaseType = derbyEmbeddedManagerKey
		connectionProperties.databaseType = h2ManagerKey
		//hard coding to the home directory here
		val seperator = System.getProperty("file.separator")
		val homeDir = System.getProperty("user.home");
		val targetDir = homeDir + seperator + "Documents" + seperator + applicationName
		connectionProperties.folder = targetDir

		val manager: DatabaseManager = managerFactory.getManager(connectionProperties.databaseType)
		val job: OpenConnectionJob = OpenConnectionJob("Open Connection", sync, manager, connectionProperties)
		// Start the Job
		job.setPriority(Job.SHORT)
		job.schedule();

	}


	@Focus
	fun setFocus() {
		//txtInput.setFocus()
		//tableViewer.getTable().setFocus()
	}

	@Persist
	fun save() {
		part.setDirty(false)
	}


}