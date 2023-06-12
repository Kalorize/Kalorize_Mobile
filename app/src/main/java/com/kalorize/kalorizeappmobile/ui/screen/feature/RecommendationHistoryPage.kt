package com.kalorize.kalorizeappmobile.ui.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.data.remote.response.FoodItem
import com.kalorize.kalorizeappmobile.data.remote.response.PastRecommendation
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey
import kotlin.math.roundToInt

@Composable
fun recommendationHistoryPage(
    recommendation: PastRecommendation,
    navHostController: NavHostController
) {
    val needCalories = recommendation.calories
    val foodCalories = recommendation.breakfast.calories.toString()
        .toFloat() + recommendation.lunch.calories.toString()
        .toFloat() + recommendation.dinner.calories.toString().toFloat()
    val needProtein = recommendation.protein
    val foodProtein = recommendation.breakfast.protein.toString()
        .toFloat() + recommendation.lunch.protein.toString()
        .toFloat() + recommendation.dinner.protein.toString().toFloat()

    Column(
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
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
                Row {
                    Text(
                        text = "${foodCalories.toInt()}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "/${needCalories.toInt()} kcal",
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
                    text = if (foodCalories >= needCalories) {
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
                Row {
                    Text(
                        text = "${foodProtein.toInt()}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        text = "/${needProtein.toInt()} gr",
                        style = TextStyle(
                            color = Color(148, 145, 167),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                    )
                }
                Text(
                    text = if (foodProtein >= needProtein) {
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

        foodItemChoosen(
            item = recommendation.breakfast,
            navHostController = navHostController
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Lunch",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))

        foodItemChoosen(
            item = recommendation.lunch,
            navHostController = navHostController
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Dinner",
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.height(10.dp))

        foodItemChoosen(
            item = recommendation.dinner,
            navHostController = navHostController
        )

    }
}