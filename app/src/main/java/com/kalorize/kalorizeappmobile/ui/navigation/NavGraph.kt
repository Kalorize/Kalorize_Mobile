package com.kalorize.kalorizeappmobile.ui.navigation

import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kalorize.kalorizeappmobile.ui.screen.AnimatedSplashScreen
import com.kalorize.kalorizeappmobile.ui.screen.onboarding.OnBoardingScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.login.LoginScreen
import com.kalorize.kalorizeappmobile.ui.screen.feature.HomeScreen
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
){
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route){
            AnimatedSplashScreen(navHostController = navController)
        }
        
        composable(route = Screen.OnBoarding.route){
            OnBoardingScreen(navHostController = navController)
        }
        
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = viewModel, navHostController = navController)
        }

        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController = navController, viewModel = viewModel)
        }
    }
}