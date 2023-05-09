package com.example.notificationpermission13

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.notificationpermission13.ui.theme.NotificationPermission13Theme


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private var PERMISSIONS_REQUIRED =
    arrayOf(Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.CAMERA)

class MainActivity : ComponentActivity() {

    companion object {
        /** Convenience method used to check if all permissions required by this app are granted */
        @RequiresApi(Build.VERSION_CODES.TIRAMISU)
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            NotificationPermission13Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        if (Build.VERSION.SDK_INT <= 33) {
            val permissionList = PERMISSIONS_REQUIRED.toMutableList()
            //Add Item in List
            permissionList.add(Manifest.permission.POST_NOTIFICATIONS)
            permissionList.add(Manifest.permission.CAMERA)
            PERMISSIONS_REQUIRED = permissionList.toTypedArray()
        }
        if (!hasPermissions(this)) {
            activityResultLauncher.launch(PERMISSIONS_REQUIRED)
        } else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
        }

    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in PERMISSIONS_REQUIRED && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show()
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
    NotificationPermission13Theme {
        Greeting("Android")
    }
}