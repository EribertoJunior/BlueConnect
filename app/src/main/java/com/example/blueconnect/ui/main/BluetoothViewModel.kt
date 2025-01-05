package com.example.blueconnect.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.blueconnect.repository.BluetoothRepository

class BluetoothViewModel(private val bluetoothRepository: BluetoothRepository) : ViewModel() {
    private val _bluetoothPermissionGranted = MutableLiveData<Boolean>()
    val bluetoothPermissionGranted: LiveData<Boolean> get() = _bluetoothPermissionGranted
    fun checkBluetoothPermission() {
        _bluetoothPermissionGranted.value = bluetoothRepository.isBluetoothPermissionGranted()
    }
}