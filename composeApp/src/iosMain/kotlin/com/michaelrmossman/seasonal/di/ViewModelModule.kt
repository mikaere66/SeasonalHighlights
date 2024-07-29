package com.michaelrmossman.seasonal.di

import com.michaelrmossman.seasonal.presentation.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::MainViewModel)
}