package com.kalorize.kalorizeappmobile.ui.screen.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.remote.response.FoodItem
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey
import com.kalorize.kalorizeappmobile.ui.theme.Orange0
import kotlin.math.roundToInt

@Composable
fun foodItemChoosen(
    item: FoodItem,
    navHostController: NavHostController
) {
    Row(
        modifier = Modifier
            .height(220.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = LightGrey)
            .clickable {
                navHostController.navigate(Screen.FoodDetail.createRoute(item.id.toString()))
            },
    ) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.name,
            error = painterResource(id = R.drawable.picture_placeholder),
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 12.dp)
                .width(180.dp)
                .height(180.dp)
                .clip(shape = RoundedCornerShape(20.dp)),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .padding(vertical = 20.dp)
                .padding(end = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
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
    }
}