package com.kalorize.kalorizeappmobile.ui.screen.feature

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.data.remote.response.FoodItem
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@Composable
fun recommendationPage(
    viewModel: MainViewModel ,
    user: LoginData,
    lifecycle: LifecycleOwner,
    date: String,
    navHostController: NavHostController
){
    val recommendation = remember {
        mutableStateOf(RecommendationResponse(status = "" , data = null))
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
    viewModel.homeViewModel.recommendation.observe(lifecycle){
        recommendation.value = it
    }

    LaunchedEffect(key1 = recommendation.value){
        if (recommendation.value.data != null){
            isDataLoaded.value = true
        }
    }

    LaunchedEffect(key1 = breakfastProtein.value, key2 = lunchProtein.value, key3 = dinnerProtein.value){
        protein.value = breakfastProtein.value + lunchProtein.value + dinnerProtein.value
    }

    LaunchedEffect(key1 = breakfastCalories.value, key2 = lunchCalories.value, key3 = dinnerCalories.value){
        calories.value = breakfastCalories.value + lunchCalories.value + dinnerCalories.value
    }

    if (isDataLoaded.value){
        Log.d("Check data rekomendasi" , recommendation.value.toString())
        Column (
            modifier = Modifier
                .padding(vertical = 20.dp)
                ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = LightGrey)
                        .padding(vertical = 15.dp, horizontal = 30.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Calories")
                    Text(
                        text = "${calories.value}/${recommendation.value.data!!.user.reccomendation.calories}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(text = if (calories.value >= recommendation.value.data!!.user.reccomendation.calories.toString().toFloat()){
                        "Reached"
                    }else {"unreached"})
                }

                Column(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(color = LightGrey)
                        .padding(vertical = 15.dp, horizontal = 30.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(text = "Protein")
                    Text(
                        text = "${protein.value}/${recommendation.value.data!!.user.reccomendation.protein}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(text = if (protein.value >= recommendation.value.data!!.user.reccomendation.protein.toString().toFloat()){
                        "Reached"
                    }else {"unreached"})
                }
            }

            Text(
                text = "Food",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
                )

            Text(
                text = "Breakfast",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
            )
            
            LazyRow(
                state = rememberLazyListState()){
                items(items = recommendation.value.data!!.user.reccomendation.breakfast , itemContent = {
                    foodItem(item = it , breakfastId , breakfastCalories, breakfastProtein, true , navHostController)
                })
            }

            Text(
                text = "Lunch",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
            )

            LazyRow(
                state = rememberLazyListState()){
                items(items = recommendation.value.data!!.user.reccomendation.lunch , itemContent = {
                    foodItem(item = it , lunchId , lunchCalories , lunchProtein, true, navHostController)
                })
            }

            Text(
                text = "Dinner",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
            )

            LazyRow(
                state = rememberLazyListState()){
                items(items = recommendation.value.data!!.user.reccomendation.dinner , itemContent = {
                    foodItem(item = it ,dinnerId, dinnerCalories, dinnerProtein, true, navHostController)
                })
            }

            Button(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFFF94917)
                ),
                enabled = calories.value > recommendation.value.data!!.user.reccomendation.calories.toString().toFloat(),
                onClick = {
                    viewModel.homeViewModel.chooseFood(user.token ,date , breakfastId.value ,lunchId.value , dinnerId.value)
                }) {
                Text(text = "Simpan")
            }
        }
    }else{
        Box{
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

