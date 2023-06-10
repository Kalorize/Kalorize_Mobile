package com.kalorize.kalorizeappmobile.ui.screen.feature.camera

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.vm.MainViewModel


private val REQUIRED_PERMISSIONS = buildList {
    add(Manifest.permission.CAMERA)

    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
        add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionCameraScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val multiplePermissionState =
        rememberMultiplePermissionsState(permissions = REQUIRED_PERMISSIONS)


    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    multiplePermissionState.launchMultiplePermissionRequest()
                }
                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        contentAlignment = Alignment.Center
    ) {
        when {
            multiplePermissionState.allPermissionsGranted -> {
                CameraScreen(navController, mainViewModel)
            }
            multiplePermissionState.shouldShowRationale -> {
                ForcePermissionScreen(
                    message = "Izinkan untuk membuka kamera",
                    requestPermission = {
                        multiplePermissionState.launchMultiplePermissionRequest()
                    }
                )
            }
            else -> {
                ForcePermissionScreen(
                    message = "Untuk menggunakan fungsi kamera, izinkan dari pengaturan sistem",
                    requestPermission = {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        ).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        }
                        ContextCompat.startActivity(context, intent, null)
                    }
                )
            }
        }
    }
}

@Composable
private fun ForcePermissionScreen(
    message: String,
    requestPermission: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = requestPermission,
            colors = ButtonDefaults.buttonColors(
                Color(0xFFF94917)
            ),
        ) {
            Text(text = "Izinkan kamera")
        }
    }
}