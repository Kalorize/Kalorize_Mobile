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

@Composable
fun foodItem(
    item: FoodItem,
    foodChoice: MutableState<Int>? = null,
    calories: MutableState<Float>? = null,
    protein: MutableState<Float>? = null,
    isRecommendation: Boolean,
    navHostController: NavHostController
){
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(300.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = LightGrey)
            .padding(vertical = 15.dp, horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            error = painterResource(id = R.drawable.picture_placeholder),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .height(140.dp)
                .width(120.dp)
                .clickable {
                    navHostController.navigate(Screen.FoodDetail.createRoute(item.id.toString()))
                },
            contentScale = ContentScale.FillBounds
            )
        Text(
            text = item.name ,
            maxLines = 1 ,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(bottom = 10.dp))
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${item.calories} Kcal" ,
                color = Orange0,
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                )
            Text(
                text = "${item.protein} gr" ,
                color = Color.DarkGray,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
        if (isRecommendation){
            Button(
                colors = if (item.id == foodChoice!!.value) { ButtonDefaults.buttonColors(
                    Color.LightGray
                )}else {
                    ButtonDefaults.buttonColors(
                        Purple40
                    ) },
                onClick = {
                    foodChoice.value = item.id
                    calories!!.value = item.calories.toString().toFloat()
                    protein!!.value = item.protein.toString().toFloat()
                }) {
                Text(
                    text = if (item.id == foodChoice.value) {
                        "Dipilih"
                    }else {
                        "Pilih" },
                    color = Color.White
                )
            }
        }

    }
}