package org.koin.test.core

import org.koin.core.Koin
import org.koin.core.KoinConfiguration
import org.koin.core.KoinContext
import org.koin.core.bean.BeanRegistry
import org.koin.core.instance.InstanceRegistry
import org.koin.core.path.PathRegistry
import org.koin.core.property.PropertyRegistry
import org.koin.core.scope.ScopeRegistry
import org.koin.dsl.module.Module
import org.koin.log.Logger
import org.koin.standalone.StandAloneContext
import org.koin.test.core.instance.SandboxInstanceFactory
import org.koin.test.ext.koin.dryRun

/**
 * Check all definition's dependencies
 */
fun StandAloneContext.checkModules(list: List<Module>, logger: Logger) {
    Koin.logger = logger //PrintLogger(showDebug = true)
    val sandboxContext = createSandBoxContext()

    // Build list
    val configuration = KoinConfiguration(sandboxContext)
    StandAloneContext.koinConfiguration = configuration
    configuration.loadModules(list)

    // Run checks
    sandboxContext.dryRun()
}

private fun createSandBoxContext(): KoinContext {
    val scopeRegistry = ScopeRegistry()
    return KoinContext(
        InstanceRegistry(
            BeanRegistry(),
            SandboxInstanceFactory(),
            PathRegistry(),
            scopeRegistry
        ), scopeRegistry, PropertyRegistry()
    )
}