package com.example.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocationAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyApp()
                }
            }
        }
    }
}

@Composable

fun LocationDisplay (
    locationUtils: LocationUtils,
    context: Context
){
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = {permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
                && permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                ){
            } else{
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
               if (rationaleRequired){
                   Toast.makeText(context,
                       "Location Permission is required for this feature to work",
                       Toast.LENGTH_LONG).show()
               } else{
                   Toast.makeText(context,
                       "Enable it on Android Settings",
                       Toast.LENGTH_LONG).show()
               }
            }
        }
    )
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Location not available")

        Button(onClick = {
            if (locationUtils.hasLocationPermission(context)){
            } else{
                requestPermissionLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            }
 }) {
            Text(text = "Get location")
        }
    }

}

@Composable
fun MyApp(){
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    LocationDisplay(locationUtils = locationUtils, context = context)
}