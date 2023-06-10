package com.kalorize.kalorizeappmobile

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kalorize.kalorizeappmobile.ui.navigation.SetupNavGraph
import com.kalorize.kalorizeappmobile.ui.screen.auth.login.LoginScreen
import com.kalorize.kalorizeappmobile.ui.theme.KalorizeAppMobileTheme
import com.kalorize.kalorizeappmobile.vm.MainModelFactory
import com.kalorize.kalorizeappmobile.vm.MainViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels {
        MainModelFactory(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KalorizeAppMobileTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, viewModel = viewModel)
            }
        }
    }
}
