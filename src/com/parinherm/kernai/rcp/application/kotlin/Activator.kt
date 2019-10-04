package com.parinherm.kernai.rcp.application.kotlin

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import org.eclipse.core.runtime.FileLocator
import org.eclipse.core.runtime.Path
import org.osgi.framework.Bundle
import org.osgi.framework.BundleActivator
import org.osgi.framework.BundleContext
import org.slf4j.LoggerFactory

class Activator : BundleActivator {
	
	override fun start(bundleContext: BundleContext) {
		configureLogbackInBundle(bundleContext.getBundle())
	}
	
	override fun stop(bundleContext: BundleContext) {
		
	}
	
	fun configureLogbackInBundle(bundle: Bundle) {
		val context: LoggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
		val jc: JoranConfigurator = JoranConfigurator()
		jc.setContext(context)
		context.reset()
		val logbackConfigFileUrl = FileLocator.find(bundle, Path("logback.xml"), null)
		jc.doConfigure(logbackConfigFileUrl.openStream())
	}
			
}