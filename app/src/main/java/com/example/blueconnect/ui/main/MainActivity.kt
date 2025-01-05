package com.example.blueconnect.ui.main

import android.Manifest.permission.BLUETOOTH_CONNECT
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.blueconnect.ui.theme.BlueConnectTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val bluetoothViewModel: BluetoothViewModel by viewModel()

    private val enableBluetoothLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Bluetooth ativado com sucesso
            Toast.makeText(this, "Bluetooth ativado", Toast.LENGTH_SHORT).show()
        } else {
            // O usuário cancelou a ativação do Bluetooth
            Toast.makeText(this, "Ativação do Bluetooth cancelada", Toast.LENGTH_SHORT).show()
        }
    }
    private val requestBluetoothPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permissão concedida
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(enableBtIntent)
            } else {
                // Permissão negada
            }
        }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            bluetoothViewModel.bluetoothPermissionGranted.observe(this) { granted ->
                if (granted) {
                    // Permissão concedida
                    Toast.makeText(this, "Permissão concedida", Toast.LENGTH_SHORT).show()
                } else {
                    // Solicitar permissão
                    Toast.makeText(this, "Permissão não concedida", Toast.LENGTH_SHORT).show()
                    requestBluetoothPermission()
                }
            }
            bluetoothViewModel.checkBluetoothPermission()

            BlueConnectTheme {
                Scaffold(modifier = Modifier.Companion.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.Companion.padding(innerPadding)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestBluetoothPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Permissão já concedida
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                enableBluetoothLauncher.launch(enableBtIntent)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                BLUETOOTH_CONNECT
            ) -> {
                // Mostrar uma explicação ao usuário e depois solicitar a permissão
                requestBluetoothPermissionLauncher.launch(BLUETOOTH_CONNECT)
            }

            else -> {
                // Solicitar a permissão diretamente
                requestBluetoothPermissionLauncher.launch(BLUETOOTH_CONNECT)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlueConnectTheme {
        Greeting("Android")
    }
}
