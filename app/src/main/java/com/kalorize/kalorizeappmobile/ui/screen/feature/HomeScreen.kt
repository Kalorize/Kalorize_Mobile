package com.kalorize.kalorizeappmobile.ui.screen.feature


import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.R
import com.kalorize.kalorizeappmobile.data.local.UserPreference
import com.kalorize.kalorizeappmobile.data.remote.response.LoginData
import com.kalorize.kalorizeappmobile.data.remote.response.LoginUser
import com.kalorize.kalorizeappmobile.data.remote.response.RecommendationHistoryResponse
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import com.kalorize.kalorizeappmobile.ui.theme.LightGrey
import com.kalorize.kalorizeappmobile.vm.MainViewModel
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val userPreferences = UserPreference(context)
    val lifecycle = LocalLifecycleOwner.current
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val historyResponse = remember {
        mutableStateOf(
            RecommendationHistoryResponse("", null)
        )
    }
    val user = userPreferences.getUser()
    val date = remember {
        mutableStateOf("$year-${month + 1}-$dayOfMonth")
    }
    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            date.value = "$selectedYear-${selectedMonth + 1}-$selectedDayOfMonth"
        }, year, month, dayOfMonth
    )
    datePicker.datePicker.maxDate = calendar.timeInMillis

    viewModel.homeViewModel.getHistory(user.token, date.value)
    viewModel.homeViewModel.history.observe(lifecycle) {
        historyResponse.value = it
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, top = 30.dp, start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier
                        .align(Alignment.Start),
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    text = "Hey ${user.user.name} \uD83D\uDC4B"
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.Start),
                    text = "Let's Pratice"
                )
            }
            AsyncImage(
                model = user.user.picture,
                contentDescription = "User Picture",
                error = painterResource(id = R.drawable.user_icon),
                modifier = Modifier.
                        clickable {
                            navController.navigate(Screen.UserDetail.route)
                        }
            )
        }


        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = LightGrey)
                .padding(horizontal = 30.dp, vertical = 10.dp)
                .clickable {
                    datePicker.show()
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = convertDateFormat(date.value))
            Icon(
                painter = painterResource(id = R.drawable.baseline_calendar),
                contentDescription = "Pick Date"
            )

        }

        if (historyResponse.value.pastRecommendation != null){
            recommendationHistoryPage(recommendation = historyResponse.value.pastRecommendation!! , navController)
        }else{
            if (date.value == "${calendar[Calendar.YEAR]}-${calendar[Calendar.MONTH] + 1}-${calendar[Calendar.DAY_OF_MONTH]}") {
                recommendationPage(viewModel, user, lifecycle , date.value , navController)
            }else {
                EmptyRecommendation()
            }
        }

    }

}


fun monthName(month: Int): String {
    val name = when (month) {
        1 -> "Januari"
        2 -> "Februari"
        3 -> "Maret"
        4 -> "April"
        5 -> "Mei"
        6 -> "Juni"
        7 -> "Juli"
        8 -> "Agustus"
        9 -> "September"
        10 -> "Oktober"
        11 -> "November"
        12 -> "Desember"
        else -> {
            "Invalid Month"
        }
    }
    return name
}

fun convertDateFormat(date: String): String {
    val newDate = date.split("-")
    return "${newDate[2]} ${monthName(newDate[1].toInt() + 1)} ${newDate[0]}"
}

@Composable
fun EmptyRecommendation() {
    Column(
        modifier = Modifier.padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "No Recommendation Chosen this day ☹️")
    }
}
