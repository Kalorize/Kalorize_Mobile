package com.kalorize.kalorizeappmobile.ui.screen.feature

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.FoodItem
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import kotlin.math.roundToInt

@Composable
fun recommendationPage(
    viewModel: MainViewModel,
    user: LoginData,
    lifecycle: LifecycleOwner,
    date: String,
    navHostController: NavHostController
) {
    val recommendation = remember {
        mutableStateOf(RecommendationResponse(status = "", data = null))
    }
    val calories = remember {
        mutableStateOf(0f)
    }
    val protein = remember {
        mutableStateOf(0f)
    }

    val isDataLoaded = remember {
        mutableStateOf(false)
    }

    val breakfastId = remember {
        mutableStateOf(-1)
    }

    val lunchId = remember {
        mutableStateOf(-1)
    }

    val dinnerId = remember {
        mutableStateOf(-1)
    }
    val breakfastCalories = remember { mutableStateOf(0f) }
    val breakfastProtein = remember { mutableStateOf(0f) }
    val lunchCalories = remember { mutableStateOf(0f) }
    val lunchProtein = remember { mutableStateOf(0f) }
    val dinnerCalories = remember { mutableStateOf(0f) }
    val dinnerProtein = remember { mutableStateOf(0f) }

    viewModel.homeViewModel.getRecommendation(user.token)
    viewModel.homeViewModel.recommendation.observe(lifecycle) {
        recommendation.value = it
    }

    LaunchedEffect(key1 = recommendation.value) {
        if (recommendation.value.data != null) {
            isDataLoaded.value = true
        }
    }

    LaunchedEffect(
        key1 = breakfastProtein.value,
        key2 = lunchProtein.value,
        key3 = dinnerProtein.value
    ) {
        protein.value = breakfastProtein.value + lunchProtein.value + dinnerProtein.value
    }

    LaunchedEffect(
        key1 = breakfastCalories.value,
        key2 = lunchCalories.value,
        key3 = dinnerCalories.value
    ) {
        calories.value = breakfastCalories.value + lunchCalories.value + dinnerCalories.value
    }

    if (isDataLoaded.value) {
        Log.d("Check data rekomendasi", recommendation.value.toString())
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = LightGrey)
                    .padding(vertical = 15.dp, horizontal = 30.dp)
                    .fillMaxWidth()
                    .height(330.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.breakfast_bed),
                    contentDescription = "breakfast_bed",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(height = 250.dp)
                )
                Text(
                    text = "Jangan Lupa Pilih Variasi Makanmu Ya!",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        navHostController.navigate(Screen.Questionnare1.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 40.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        Color(44, 42, 63)
                    ),
                ) {
                    Text(
                        text = "Atur",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp
                        ),
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = LightGrey)
                        .padding(vertical = 15.dp, horizontal = 30.dp)
                        .width(115.dp)
                        .height(70.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Calories")
                    Row() {
                        Text(
                            text = "${
                                calories.value.roundToInt()
                            }",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Text(
                            text = "/${recommendation.value.data!!.user.reccomendation.calories.roundToInt()} kcal",
                            style = TextStyle(
                                color = Color(148, 145, 167),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        text = if (calories.value >= recommendation.value.data!!.user.reccomendation.calories.toString()
                                .toFloat()
                        ) {
                            "Reached ✅"
                        } else {
                            "Unreached ❌"
                        }
                    )
                }
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = LightGrey)
                        .padding(vertical = 15.dp, horizontal = 30.dp)
                        .width(115.dp)
                        .height(70.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Protein")
                    Row() {
                        Text(
                            text = "${protein.value.roundToInt()}",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        Text(
                            text = "/${recommendation.value.data!!.user.reccomendation.protein} gr",
                            style = TextStyle(
                                color = Color(148, 145, 167),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            ),
                        )
                    }
                    Text(
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        ),
                        text = if (protein.value >= recommendation.value.data!!.user.reccomendation.protein.toString()
                                .toFloat()
                        ) {
                            "Reached ✅"
                        } else {
                            "Unreached ❌"
                        }

                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Food",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Breakfast",
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                state = rememberLazyListState()
            ) {
                items(
                    items = recommendation.value.data!!.user.reccomendation.breakfast,
                    itemContent = {
                        foodItem(
                            item = it,
                            breakfastId,
                            breakfastCalories,
                            breakfastProtein,
                            true,
                            navHostController
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    })
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Lunch",
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                state = rememberLazyListState()
            ) {
                items(items = recommendation.value.data!!.user.reccomendation.lunch,
                    itemContent = {
                        foodItem(
                            item = it,
                            lunchId,
                            lunchCalories,
                            lunchProtein,
                            true,
                            navHostController
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Dinner",
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                state = rememberLazyListState()
            ) {
                items(
                    items = recommendation.value.data!!.user.reccomendation.dinner,
                    itemContent = {
                        foodItem(
                            item = it,
                            dinnerId,
                            dinnerCalories,
                            dinnerProtein,
                            true,
                            navHostController
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                enabled = calories.value > recommendation.value.data!!.user.reccomendation.calories.toString()
                    .toFloat(),
                onClick = {
                    viewModel.homeViewModel.chooseFood(
                        user.token,
                        date,
                        breakfastId.value,
                        lunchId.value,
                        dinnerId.value
                    )
                }) {
                Text(text = "Simpan")
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 250.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(80.dp),
                color = Color(0xFFF94917),
                strokeWidth = 6.dp
            )
        }

    }

}

