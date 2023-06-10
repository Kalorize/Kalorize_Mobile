package com.kalorize.kalorizeappmobile.ui.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.data.remote.response.FoodItem
import com.kalorize.kalorizeappmobile.data.remote.response.PastRecommendation
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey

@Composable
fun recommendationHistoryPage(
    recommendation: PastRecommendation,
    navHostController: NavHostController
){
    val needCalories = recommendation.calories
    val foodCalories = recommendation.breakfast.calories.toString().toFloat() + recommendation.lunch.calories.toString().toFloat() + recommendation.dinner.calories.toString().toFloat()
    val needProtein = recommendation.protein
    val foodProtein = recommendation.breakfast.protein.toString().toFloat() + recommendation.lunch.protein.toString().toFloat() + recommendation.dinner.protein.toString().toFloat()

    Column(modifier = Modifier.padding(20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(color = LightGrey)
                    .padding(vertical = 15.dp, horizontal = 30.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Calories")
                Text(
                    text = "${foodCalories}/${needCalories}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = if (foodCalories >= needCalories){
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
                    text = "${foodProtein}/${needProtein}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = if (foodProtein >= needProtein){
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
        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            foodItem(item = recommendation.breakfast, isRecommendation = false, navHostController = navHostController)
        }

        Text(
            text = "Lunch",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        )

        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            foodItem(item = recommendation.lunch, isRecommendation = false, navHostController = navHostController)
        }

        Text(
            text = "Dinner",
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp)
        )

        Column(
            modifier = Modifier.padding(start = 20.dp)
        ) {
            foodItem(item = recommendation.dinner, isRecommendation = false, navHostController = navHostController)
        }
    }
}