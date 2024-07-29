package com.michaelrmossman.seasonal.di

import com.michaelrmossman.seasonal.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::MainViewModel)
}