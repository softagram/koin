package org.koin.test.core

import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.koin.core.KoinConfiguration
import org.koin.core.KoinContext
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ComponentA()

val module1 = module {
    single { ComponentA() }
}

val module2 = module {
    single { ComponentA() }
}

class ManualContext {


    abstract class CustomKoinComponent : KoinComponent {
        val manualContext: KoinContext by lazy {
            KoinConfiguration.create().loadModules(listOf(module1)).koinContext
        }

        override fun getKoin(): KoinContext = manualContext
    }

    class CustomComponent : CustomKoinComponent() {
        val componentA: ComponentA by inject()
    }


    @Test
    fun `get different instances`() {
        val a_1 =
            KoinConfiguration.create().loadModules(listOf(module1)).koinContext.get<ComponentA>()

        val a_2 =
            KoinConfiguration.create().loadModules(listOf(module2)).koinContext.get<ComponentA>()
        assertNotEquals(a_1, a_2)
    }

    @Test
    fun `custom KoinComponent`() {
        val a_2 =
            KoinConfiguration.create().loadModules(listOf(module2)).koinContext.get<ComponentA>()
        assertNotEquals(CustomComponent().componentA, a_2)
    }
}