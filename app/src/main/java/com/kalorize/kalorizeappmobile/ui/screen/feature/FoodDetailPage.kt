package com.kalorize.kalorizeappmobile.ui.screen.feature

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.response.FoodDetailResponse
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@Composable
fun foodDetail(
    viewModel: MainViewModel,
    foodId: String,
    navigateBack: () -> Unit
){
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val lifecycle = LocalLifecycleOwner.current
    val user = userPreferences.getUser()
    val foodDetailResponse = remember {
        mutableStateOf(FoodDetailResponse("" , null))
    }
    val isDataLoaded = remember {
        mutableStateOf(false)
    }

    viewModel.homeViewModel.getFoodDetail(user.token , foodId)
    viewModel.homeViewModel.foodDetail.observe(lifecycle){
        foodDetailResponse.value = it
    }

    LaunchedEffect(key1 = foodDetailResponse.value, block = {
        if (foodDetailResponse.value.foodData != null){
            isDataLoaded.value = true
        }
    })

    if (isDataLoaded.value){
        foodDetailPage(foodDetail = foodDetailResponse.value , navigateBack)
    }else{
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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

@Composable
fun foodDetailPage(
    foodDetail: FoodDetailResponse,
    onBackClick: () -> Unit
){
    val ingredients = foodDetail.foodData!!.foodItem.ingredients.split(",")
    val cookingStep = foodDetail.foodData.foodItem.instructions.split(",")

    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp)
            .clickable { onBackClick() }
    )

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(30.dp)
            .fillMaxWidth(),
    ) {

        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Detail",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .height(140.dp)
                    .width(240.dp),
                model = foodDetail.foodData.foodItem.imageUrl,
                contentDescription = "Food Image",
                contentScale = ContentScale.Fit,
                error = painterResource(id = R.drawable.picture_placeholder)
            )
        }

        Text(
            modifier = Modifier.padding(vertical =15.dp),
            text = foodDetail.foodData.foodItem.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = foodDetail.foodData.foodItem.calories.toString() + " Kcal")
            Text(text = foodDetail.foodData.foodItem.protein.toString() + " gr")
        }
        Text(
            modifier = Modifier.padding(vertical = 15.dp),
            text = "Ingredient",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )

        for (i in ingredients.indices){
            Text(text = "${i+1} . ${ingredients[i]}")
        }

        Text(
            modifier = Modifier.padding(vertical = 15.dp),
            text = "Cooking Steps",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )

        for (i in cookingStep.indices){
            Text(text = "${i+1} . ${cookingStep[i]}")
        }


    }
}