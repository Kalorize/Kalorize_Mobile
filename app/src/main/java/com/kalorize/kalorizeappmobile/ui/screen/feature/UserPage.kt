package com.kalorize.kalorizeappmobile.ui.screen.feature

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.response.FoodDetailResponse
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.theme.Orange0
import com.kalorize.kalorizeappmobile.ui.theme.Orange1
import com.kalorize.kalorizeappmobile.vm.MainViewModel

@Composable
fun userPage(
    viewModel: MainViewModel,
    navigateBack: () -> Unit
){
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val lifecycle = LocalLifecycleOwner.current
    val user = userPreferences.getUser()
    val getMeResponse = remember {
        mutableStateOf(RecommendationResponse(null , ""))
    }
    val isDataLoaded = remember {
        mutableStateOf(false)
    }

    viewModel.homeViewModel.getRecommendation(user.token)
    viewModel.homeViewModel.recommendation.observe(lifecycle){
        getMeResponse.value = it
    }

    LaunchedEffect(key1 = getMeResponse.value, block = {
        if (getMeResponse.value.data != null){
            isDataLoaded.value = true
        }
    })

    if (isDataLoaded.value){
        userDetail(userDetail =getMeResponse.value , navigateBack)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun userDetail(
    userDetail : RecommendationResponse,
    onBackClick: () -> Unit
){
    val picture = remember {
        mutableStateOf("https://storage.googleapis.com/${userDetail.data!!.user.picture}")
    }
    Log.d("Check pic " , picture.value)
    val name = userDetail.data!!.user.name
    val email = userDetail.data.user.email
    val age = remember {
        mutableStateOf(userDetail.data.user.age.toString())
    }
    val gender = userDetail.data.user.gender
    val weight = remember {
        mutableStateOf(userDetail.data.user.weight.toString())
    }
    val height = remember {
        mutableStateOf(userDetail.data.user.height)
    }

    Icon(
        imageVector = Icons.Default.ArrowBack,
        contentDescription = "Back",
        modifier = Modifier
            .padding(top = 15.dp, start = 15.dp)
            .clickable { onBackClick() }
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            modifier = Modifier.padding(20.dp)
        ){
            AsyncImage(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(0.1.dp, Color.Black), CircleShape),
                model = picture.value,
                contentDescription = "User Picture",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.user_icon),)
            IconButton(
                    onClick = { /*TODO*/ },
            modifier = Modifier
                .clip(CircleShape)
                .background(Orange1)
                .size(32.dp)
                .align(Alignment.BottomEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Text(
            text = name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Email")
            Text(text = email)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Age")
            TextField(
                value = age.value,
                onValueChange = {
                    age.value = it
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Gender")
            Text(text = gender)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Weight")
            TextField(
                value = weight.value,
                onValueChange = {
                    weight.value = it
                },
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}