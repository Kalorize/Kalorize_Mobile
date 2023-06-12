package com.kalorize.kalorizeappmobile.ui.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.remote.response.FoodItem
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey
import com.kalorize.kalorizeappmobile.ui.theme.Orange0
import com.kalorize.kalorizeappmobile.ui.theme.Purple40
import kotlin.math.roundToInt

@Composable
fun foodItem(
    item: FoodItem,
    foodChoice: MutableState<Int>? = null,
    calories: MutableState<Float>? = null,
    protein: MutableState<Float>? = null,
    isRecommendation: Boolean,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .width(200.dp)
            .height(350.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = LightGrey),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            error = painterResource(id = R.drawable.picture_placeholder),
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 12.dp)
                .fillMaxWidth()
                .height(180.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .clickable {
                    navHostController.navigate(Screen.FoodDetail.createRoute(item.id.toString()))
                },
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = item.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${item.calories.roundToInt()} Kcal",
                color = Orange0,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${item.protein.roundToInt()} gr",
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        if (isRecommendation) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                colors = if (item.id == foodChoice!!.value) {
                    ButtonDefaults.buttonColors(
                        Color.LightGray
                    )
                } else {
                    ButtonDefaults.buttonColors(
                        Color(44, 42, 63)
                    )
                },
                onClick = {
                    foodChoice.value = item.id
                    calories!!.value = item.calories.toString().toFloat()
                    protein!!.value = item.protein.toString().toFloat()
                }
            ) {
                Text(
                    text = if (item.id == foodChoice.value) {
                        "Dipilih"
                    } else {
                        "Pilih"
                    },
                    color = Color.White
                )
            }
        }

    }
}