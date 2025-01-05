package com.example.blueconnect.repository

fun interface BluetoothRepository {
    fun isBluetoothPermissionGranted(): Boolean
}