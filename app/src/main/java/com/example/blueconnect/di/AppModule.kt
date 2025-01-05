package com.example.blueconnect.di

import com.example.blueconnect.repository.BluetoothRepository
import com.example.blueconnect.repository.BluetoothRepositoryImpl
import com.example.blueconnect.ui.main.BluetoothViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { BluetoothViewModel(get()) }
    single<BluetoothRepository> { BluetoothRepositoryImpl(androidContext()) }
}