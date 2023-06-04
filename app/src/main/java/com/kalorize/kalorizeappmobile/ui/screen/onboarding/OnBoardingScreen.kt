package com.kalorize.kalorizeappmobile.ui.screen.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun OnBoardingScreen(navHostController: NavHostController){
    val item = ArrayList<OnBoardingData>()

    item.add(
        OnBoardingData(
            R.drawable.onboarding1,
            "Manage your Diet",
            "The best solution to control your diet",
            backgroundColor = Color(0xFF0189C5),
            mainColor = Color(0xFFF94917)
        )
    )

    item.add(
        OnBoardingData(
            R.drawable.onboarding2,
            "Schedule your Time",
            "The best solution to control your diet",
            backgroundColor = Color(0xFFE4AF19),
            mainColor = Color(0xFFF94917)
        )
    )

    item.add(
        OnBoardingData(
            R.drawable.onboarding3,
            "Choose your Food",
            "The best solution to control your food",
            backgroundColor = Color(0xFF96E172),
            mainColor = Color(0xFFF94917)
        )
    )

    val pagerState = rememberPagerState(
        pageCount = item.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    OnBoardingPager(
        navHostController = navHostController ,
        item = item,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Black)
    )


}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    navHostController: NavHostController,
    item: List<OnBoardingData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Align content vertically in the center
        ) {
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(item[page].backgroundColor),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center // Align content vertically in the center
                ) {
                    Image(
                        painter = painterResource(id = item[page].image),
                        contentDescription = item[page].title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1920.dp)
                            .clip(RectangleShape),
                        contentScale = ContentScale.Crop
                    )
                }

            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
        ) {
            Column(
                modifier = Modifier.padding(50.dp, 0.dp, 50.dp, 28.dp),
                horizontalAlignment = Alignment.CenterHorizontally // Align content horizontally in the center
            ) {
                PagerIndicator(items = item, currentPage = pagerState.currentPage)
                Text(
                    text = item[pagerState.currentPage].title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    color = item[pagerState.currentPage].mainColor,
                    textAlign = TextAlign.Center, // Align text in the center
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = item[pagerState.currentPage].desc,
                    modifier = Modifier.padding(top = 8.dp),
                    color = Color.Black,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center, // Align text in the center
                    fontWeight = FontWeight.ExtraLight
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (pagerState.currentPage != 2) {
                        TextButton(
                            onClick = {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Login.route)
                            }
                        ) {
                            Text(
                                text = "Skip Now",
                                color = Color(0xFF292D32),
                                textAlign = TextAlign.Right,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        NextPageButton(
                            pagerState = pagerState,
                            item = item[pagerState.currentPage]
                        )
                    } else {
                        Button(
                            onClick = {
                                navHostController.popBackStack()
                                navHostController.navigate(Screen.Login.route)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF94917)
                            ),
                            contentPadding = PaddingValues(vertical = 12.dp),
                        ) {
                            Text(
                                text = "Get Started",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PagerIndicator(currentPage: Int, items: List<OnBoardingData>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(items.size) {
            Indicator(isSelected = it == currentPage, color = items[it].mainColor)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean, color: Color) {
    val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
            )
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NextPageButton(
    pagerState: PagerState,
    item: OnBoardingData
) {
    val coroutineScope = rememberCoroutineScope()

    OutlinedButton(
        onClick = {
            coroutineScope.launch {
                pagerState.scrollToPage(
                    pagerState.currentPage + 1,
                    pageOffset = 0f
                )
            }
        },
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.dp, item.mainColor),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = item.mainColor
        )
    ) {
        Text(
            text = "Next",
            color = item.mainColor,
            fontSize = 14.sp,
        )
    }
}
