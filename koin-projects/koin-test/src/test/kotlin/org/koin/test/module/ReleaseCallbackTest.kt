package org.koin.test.core

import org.junit.Assert
import org.junit.Test
import org.koin.core.instance.ModuleCallBack
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.release
import org.koin.test.AutoCloseKoinTest

class ReleaseCallbackTest : AutoCloseKoinTest() {

    val module = module {
        module(path = "A") {
            single { ComponentA() }
        }
    }

    class ComponentA

    @Test
    fun `should release context - from B`() {
        val koin = startKoin(listOf(module))

        var name = ""

        koin.registerModuleCallBack(object : ModuleCallBack {
            override fun onRelease(path: String) {
                name = path
            }
        })

        release("A")

        Assert.assertEquals("A", name)
    }
}