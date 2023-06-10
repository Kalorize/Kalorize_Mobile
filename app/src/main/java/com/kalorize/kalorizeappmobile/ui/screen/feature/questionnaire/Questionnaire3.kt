package com.kalorize.kalorizeappmobile.ui.screen.feature.questionnaire

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Composable
fun Questionnaire3(
    navController: NavController,
    viewModel: MainViewModel
) {
    val options = listOf(
        "Reduce Weight",
        "Increase Muscle",
        "Be Healthy"
    )
    var selectedOption = remember {
        mutableStateOf("")
    }
    val onSelectionChange = { text: String ->
        selectedOption.value = text
    }
    val recommendationResponse = remember {
        mutableStateOf(
            RecommendationResponse(null, "")
        )
    }
    val loading = remember {
        mutableStateOf(false)
    }
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val user = userPreferences.getUser()
    val gender = userPreferences.getGender()
    val weight = userPreferences.getWeight()
    val height = userPreferences.getHeight()
    val age = userPreferences.getAge()
    val activity = userPreferences.getActivity()
    Box {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, top = 40.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier.clickable {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_orange),
                    contentDescription = null,
                    tint = Color(red = 243, green = 73, blue = 23)
                )
                Text(
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    text = "Back",
                )
            }
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Please answer this question\n" +
                        "to know more about you",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Choose your main target",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .align(Alignment.Start),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(10.dp))
            options.forEach { text ->
                Button(
                    onClick = {
                        onSelectionChange(text)
                    },
                    colors = if (text == selectedOption.value) {
                        ButtonDefaults.buttonColors(
                            Color.Black
                        )
                    } else {
                        ButtonDefaults.buttonColors(
                            Color.White
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(2.dp, Color(44, 42, 63))
                ) {
                    Text(
                        text = text,
                        color = if (text == selectedOption.value) {
                            Color.White
                        } else {
                            Color(44, 42, 63)
                        },
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Button(
                enabled = selectedOption.value != "",
                onClick = {
                    Log.i(
                        "target",
                        selectedOption.value
                            .replace("\\s*\\([^()]*\\)\\s*".toRegex(), "")
                            .replace("\\s".toRegex(), "_")
                            .uppercase()
                    )
                    loading.value = true
                    viewModel.questionnareViewModel.doFoodRec(
                        user.token,
                        gender!!.toRequestBody("text/plain".toMediaType()),
                        age!!,
                        height!!,
                        weight!!,
                        activity!!.toRequestBody("text/plain".toMediaType()),
                        selectedOption.value
                            .replace("\\s*\\([^()]*\\)\\s*".toRegex(), "")
                            .replace("\\s".toRegex(), "_")
                            .uppercase()
                            .toRequestBody("text/plain".toMediaType())

                    )
                    viewModel.questionnareViewModel.foodRec.observe(lifecycle) {
                        recommendationResponse.value = it
                        if (recommendationResponse.value.status == "success") {
                            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                            userPreferences.setUser(
                                LoginData(
                                    token = user.token,
                                    user = LoginUser(
                                        password = user.user.password,
                                        id = user.user.id,
                                        email = user.user.email,
                                        name = user.user.name,
                                        gender = gender,
                                        picture = user.user.picture,
                                        weight = weight,
                                        age = age,
                                        height = height,
                                        activity = activity,
                                        target = selectedOption.value
                                    )
                                )
                            )
                            loading.value = false
                            userPreferences.deleteProfilingSharedPref()
                            navController.navigate(Screen.QuestionnareSuccess.route)
                        } else {
                            loading.value = false
                            Toast.makeText(context, it.status, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFFF94917)
                ),
                content = {
                    Text(
                        text = "Submit",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            )
        }
        if (loading.value) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(80.dp),
                color = Color(0xFFF94917),
                strokeWidth = 6.dp
            )
        }
    }
}


