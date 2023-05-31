package com.kalorize.kalorizeappmobile.ui.screen.onboarding

import androidx.compose.ui.graphics.Color
import com.kalorize.kalorizeappmobile.ui.theme.BlackFul


data class OnBoardingData(
    val image: Int,
    val title: String,
    val desc: String,
    val backgroundColor: Color,
    val mainColor: Color = BlackFul
)
