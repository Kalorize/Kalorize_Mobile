package com.kalorize.kalorizeappmobile.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object OnBoarding : Screen("onboarding_screen")
    object Login : Screen("login")
    object Home : Screen("home")
    object Register : Screen("register")
    object Otp : Screen("otp_screen")
    object ReInputEmail : Screen("re_input_email_screen")
    object ChangePassword : Screen("change_password_screen")
    object GetStartedChangePassword : Screen("get_stated_change_password_screen")
    object Questionnare1 : Screen("questionnaire1")
    object Questionnare2 : Screen("questionnaire2")
    object Questionnare3 : Screen("questionnaire3")
    object QuestionnareSuccess : Screen("questionnaire_success")
    object Camera : Screen("camera_screen")

    object CameraEdit : Screen("camera_edit_screen")

    object FoodDetail: Screen("detail/{id}"){
        fun createRoute(id: String) = "detail/$id"
    }

    object UserDetail: Screen("user")
    object EditProfile:Screen("edit_profile")
    object EditPaswword:Screen("edit_password")

}