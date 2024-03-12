package com.momentousmoss.vktz.ui

import com.momentousmoss.vktz.ui.about.AboutViewModel
import com.momentousmoss.vktz.ui.main.MainViewModel
import com.momentousmoss.vktz.ui.scroll.ScrollViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { MainViewModel() }
    viewModel { ScrollViewModel() }
    viewModel { AboutViewModel() }
}