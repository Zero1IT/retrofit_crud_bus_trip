package com.example.service.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelsFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when(modelClass) {
            ListBusViewModel::class.java -> ListBusViewModel(application) as T
            else -> throw IllegalArgumentException("Unknown viewModel: $modelClass")
        }
    }
}