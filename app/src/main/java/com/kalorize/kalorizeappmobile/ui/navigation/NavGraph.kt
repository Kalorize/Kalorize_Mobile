package com.kalorize.kalorizeappmobile.ui.navigation

import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kalorize.kalorizeappmobile.ui.screen.AnimatedSplashScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword.ChangePasswordScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.changepassword.GetStartedChangePasswordScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.otp.OtpScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.reinputemail.ReInputEmailScreen
import com.kalorize.kalorizeappmobile.ui.screen.auth.login.LoginScreen
import com.kalorize.kalorizeappmobile.ui.screen.feature.HomeScreen
import com.kalorize.kalorizeappmobile.ui.screen.feature.camera.PermissionCameraScreen
import com.kalorize.kalorizeappmobile.ui.screen.feature.foodDetail
import com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire.Questionnaire1
import com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire.Questionnaire2
import com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire.Questionnaire3
import com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire.QuestionnaireSuccess
import com.kalorize.kalorizeappmobile.ui.screen.feature.userPage
import com.kalorize.kalorizeappmobile.ui.screen.onboarding.OnBoardingScreen
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            AnimatedSplashScreen(navHostController = navController)
        }
        composable(route = Screen.OnBoarding.route) {
            OnBoardingScreen(navHostController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(viewModel = viewModel, navHostController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, viewModel)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navHostController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.ReInputEmail.route
        ) {
            ReInputEmailScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.Otp.route
        ) {
            OtpScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.ChangePassword.route
        ) {
            ChangePasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.GetStartedChangePassword.route
        ) {
            GetStartedChangePasswordScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = Screen.FoodDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("id") ?: "-1"
            foodDetail(viewModel = viewModel, foodId = id, navigateBack = {
                navController.navigateUp()
            })
        }
        composable(
            route = Screen.UserDetail.route
        ) {
            userPage(viewModel = viewModel, navController)
        }
        composable(
            route = Screen.Questionnare1.route
        ) {
            Questionnaire1(navController)
        }
        composable(
            route = Screen.Questionnare2.route
        ) {
            Questionnaire2(navController)
        }
        composable(
            route = Screen.Questionnare3.route
        ) {
            Questionnaire3(navController, viewModel)
        }
        composable(
            route = Screen.QuestionnareSuccess.route
        ) {
            QuestionnaireSuccess(navController)
        }
        composable(
            route = Screen.Camera.route
        ) {
            PermissionCameraScreen(navController, viewModel)
        }
    }
}