/*
 * Copyright 2017-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.koin.android.viewmodel.ext

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.ViewModel
import org.koin.android.viewmodel.ViewModelParameters
import org.koin.android.viewmodel.resolveViewModelInstance
import org.koin.core.Koin
import org.koin.core.context.GlobalContext
import org.koin.core.parameter.ParametersDefinition
import kotlin.reflect.KClass

/**
 * LifecycleOwner extensions to help for ViewModel
 *
 * @author Arnaud Giuliani
 */

/**
 * Lazy getByClass a viewModel instance
 *
 * @param VIEW_MODEL_KEY - ViewModel Factory VIEW_MODEL_KEY (if have several instances from same ViewModel)
 * @param name - Koin BeanDefinition name (if have several ViewModel beanDefinition of the same type)
 * @param parameters - parameters to pass to the BeanDefinition
 * @param koin - Custom koin for context isolation
 */
inline fun <reified T : ViewModel> LifecycleOwner.viewModel(
    name: String? = null,
    noinline parameters: ParametersDefinition? = null,
    koin: Koin = GlobalContext.get().koin
) = lazy { getViewModel<T>(name, parameters) }

/**
 * Get a viewModel instance
 *
 * @param VIEW_MODEL_KEY - ViewModel Factory VIEW_MODEL_KEY (if have several instances from same ViewModel)
 * @param name - Koin BeanDefinition name (if have several ViewModel beanDefinition of the same type)
 * @param parameters - parameters to pass to the BeanDefinition
 * @param koin - Custom koin for context isolation
 */
inline fun <reified T : ViewModel> LifecycleOwner.getViewModel(
    name: String? = null,
    noinline parameters: ParametersDefinition? = null,
    koin: Koin = GlobalContext.get().koin
) = resolveViewModelInstance(
    ViewModelParameters(
        T::class,
        name,
        null,
        parameters
    ), koin
)


/**
 * Lazy getByClass a viewModel instance
 *
 * @param clazz - Class of the BeanDefinition to retrieve
 * @param VIEW_MODEL_KEY - ViewModel Factory VIEW_MODEL_KEY (if have several instances from same ViewModel)
 * @param name - Koin BeanDefinition name (if have several ViewModel beanDefinition of the same type)
 * @param from - ViewModelStoreOwner that will store the viewModel instance. null to assume "this" as the ViewModelStoreOwner
 * @param parameters - parameters to pass to the BeanDefinition
 */
fun <T : ViewModel> LifecycleOwner.getViewModel(
    clazz: KClass<T>,
    name: String? = null,
    parameters: ParametersDefinition? = null,
    koin: Koin = GlobalContext.get().koin
) = resolveViewModelInstance(
    ViewModelParameters(
        clazz,
        name,
        null,
        parameters
    ), koin
)

