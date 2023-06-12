package com.kalorize.kalorizeappmobile.ui.screen.feature

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.material3.ListItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.body.LoginBody
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.ui.theme.Orange1
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun userPage(
    viewModel: MainViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    val lifecycle = LocalLifecycleOwner.current
    val user = userPreferences.getUser()
    val getMeResponse = remember {
        mutableStateOf(RecommendationResponse(null, ""))
    }
    val isDataLoaded = remember {
        mutableStateOf(false)
    }

    viewModel.homeViewModel.getRecommendation(user.token)
    viewModel.homeViewModel.recommendation.observe(lifecycle) {
        getMeResponse.value = it
    }

    LaunchedEffect(key1 = getMeResponse.value, block = {
        if (getMeResponse.value.data != null) {
            isDataLoaded.value = true
        }
    })

    if (isDataLoaded.value) {
        userDetail(userDetail = getMeResponse.value, navController)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
    userDetail: RecommendationResponse,
    navController: NavController
) {

    val picture = remember {
        mutableStateOf("https://storage.googleapis.com/${userDetail.data!!.user.picture}")
    }
    Log.d("Check pic ", picture.value)
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
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_orange),
                    contentDescription = "Back",
                    tint = Color(red = 243, green = 73, blue = 23),
                    modifier = Modifier
                        .clickable {
                            navController.popBackStack()
                        }
                )
            },
            title = {
                Text(text = "")
            }
        )

        Box(
            modifier = Modifier
                .clickable {
                    scope.launch {
                        sheetState.show()
                    }
                }
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(BorderStroke(0.1.dp, Color.Black), CircleShape),
                model = picture.value,
                contentDescription = "User Picture",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.person),
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Orange1)
                    .size(32.dp)
                    .align(Alignment.BottomEnd),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        if (sheetState.isVisible) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    scope.launch {
                        sheetState.hide()
                    }
                },
            ) {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .height(200.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 40.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFF94917)
                        ),
                        onClick = {
                        },
                    ) {
                        Text(text = "Camera")
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 40.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFF94917)
                        ),
                        onClick = {
                        },
                    ) {
                        Text(text = "Gallery")
                    }
                }

            }
        }
        Spacer(
            modifier = Modifier
                .height(12.dp)
        )
        Row(
            modifier = Modifier
                .clickable { },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(id = R.drawable.edit),
                contentDescription = null,
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        listTile(
            navController = navController,
            title = "Email",
            content = email,
            isPassword = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        listTile(
            navController = navController,
            title = "Change Password",
            content = "",
            isPassword = true
        )
        Spacer(modifier = Modifier.height(12.dp))
        listTile(
            navController = navController,
            title = "Age",
            content = age.value,
            isPassword = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        listTile(
            navController = navController,
            title = "Gender",
            content = gender,
            isPassword = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        listTile(
            navController = navController,
            title = "Weight",
            content = "${weight.value} Kg",
            isPassword = false
        )
        Spacer(modifier = Modifier.height(12.dp))
        listTile(
            navController = navController,
            title = "Height",
            content = "${height.value} cm",
            isPassword = false
        )
        Spacer(modifier = Modifier.height(50.dp))
        listTile(
            navController = navController,
            title = "Logout",
            content = "",
            isPassword = false,
            isLogout = true,
        )
    }
}

@Composable
fun listTile(
    navController: NavController,
    title: String,
    content: String,
    isPassword: Boolean,
    isLogout: Boolean? = false
) {
    val context = LocalContext.current
    val userPreferences = UserPreference(context)
    ListItem(
        headlineContent = {
            Text(
                title,
                fontSize = 17.5.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = if (isLogout!!) {
                    Color.Red
                } else {
                    Color.Black
                }
            )
        },
        colors = ListItemDefaults.colors(
            containerColor = Color(235, 238, 240),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp)
            .height(60.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable {
                if (isLogout!!) {
                    userPreferences.deleteUser()
                    navController.popBackStack()
                    navController.navigate(Screen.Login.route)
                } else {

                }
            },
        trailingContent = {
            Row(
                modifier = Modifier
                    .width(180.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(100.dp),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Text(
                        text = if (isPassword) {
                            "********"
                        } else {
                            content
                        },
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(148, 145, 167),
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(color = Color.White),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        modifier = Modifier
                            .size(34.dp),
                        painter = painterResource(id = R.drawable.arrow_right),
                        contentDescription = null,
                        tint = if (isLogout!!) {
                            Color.Red
                        } else {
                            Color.Black
                        }
                    )
                }
            }
        }
    )
}