package com.kalorize.kalorizeappmobile.ui.screen.auth.forgotopassword.otp

import android.os.CountDownTimer
import com.kalorize.kalorizeappmobile.R
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kalorize.kalorizeappmobile.ui.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(
    navController: NavHostController
) {

    val millisInFuture: Long = 300000

    val timeData = remember { mutableStateOf(millisInFuture) }
    val isPlaying = remember { mutableStateOf(false) }
    val countDownTimer = object : CountDownTimer(millisInFuture, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timeData.value = millisUntilFinished / 1000
            isPlaying.value = true
        }

        override fun onFinish() {
            isPlaying.value = false
        }
    }

    DisposableEffect(key1 = "key") {
        countDownTimer.start()
        onDispose {
            countDownTimer.cancel()
        }
    }
    val textList = listOf(
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "9", TextRange(index = 0)
                )
            )
        },
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "9", TextRange(index = 0)
                )
            )
        },
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "9", TextRange(index = 0)
                )
            )
        },
        remember {
            mutableStateOf(
                TextFieldValue(
                    text = "9", TextRange(index = 0)
                )
            )
        },
    )

    val requesterList = listOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, top = 40.dp, end = 16.dp),
    ) {
        Row(modifier = Modifier.clickable {
            navController.navigate(Screen.ChangePassword.route) {
                popUpTo(Screen.ChangePassword.route) {
                    inclusive = true
                }
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_orange),
                contentDescription = null,
                tint = Color(red = 243, green = 73, blue = 23)
            )
            Text(
                style = TextStyle(
                    fontSize = 14.sp, fontWeight = FontWeight.Bold
                ),
                text = "Back",
            )
        }
        Text(
            modifier = Modifier
                .padding(top = 30.dp)
                .align(alignment = Alignment.CenterHorizontally), style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Bold
            ), text = "We've sent OTP"
        )
        Text(
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally), style = TextStyle(
                fontSize = 16.sp,
            ), text = "Check your email"
        )
        Image(
            painter = painterResource(id = R.drawable.otp),
            contentDescription = "Otp",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            ), style = TextStyle(
                fontSize = 16.sp, color = Color.Gray
            ), text = "Expired in ${timeData.value} seconds"
        )
        OTPView(
            textList = textList, requesterList = requesterList,
            isPlaying = isPlaying,
            navController = navController
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 18.sp,
                color = Color(85, 93, 101)
            ),
            text = "Didn't have any message?"
        )
        Text(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .clickable {
                    println("Resend OTP")
                },
            style = TextStyle(
                fontSize = 18.sp,
                color = Color(0xFFF94917),
                fontWeight = FontWeight.Bold
            ),
            text = "Resend OTP"
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OTPView(
    textList: List<MutableState<TextFieldValue>>, requesterList: List<FocusRequester>,
    isPlaying: MutableState<Boolean>,
    navController: NavHostController
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var count = 0
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            for (i in textList.indices) {
                if (textList[i].value.text !== "") {
                    count += 1
                }
                InputView(
                    value = textList[i].value,
                    onValueChange = { newValue ->
                        //if old value is not empty, just return
                        if (textList[i].value.text != "") {

                            if (newValue.text == "") {
                                // before return, if the new value is empty, set the text
                                // field to empty
                                textList[i].value = TextFieldValue(
                                    text = "", selection = TextRange(0)
                                )
                            }
                            return@InputView
                        }
                        // set new value and move cursor to the end
                        textList[i].value = TextFieldValue(
                            text = newValue.text, selection = TextRange(newValue.text.length)
                        )
                        nextFocus(textList, requesterList)
                    },
                    focusRequester = requesterList[i],
                    focusManager = focusManager,
                )
            }

        }
        Button(
            enabled = count == 4 && isPlaying.value,
            onClick = {
                connectInputtedCode(textList) {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    if (it) {
                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.ChangePassword.route) {
                            popUpTo(Screen.ReInputEmail.route) {
                                inclusive = false
                            }
                        }
                    } else {
                        Toast.makeText(context, "error, input again", Toast.LENGTH_SHORT).show()
                        for (text in textList) {
                            text.value = TextFieldValue(
                                text = "", selection = TextRange(0)
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(height = 40.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                Color(0xFFF94917)
            ),
        ) {
            Text(
                text = "Submit",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 20.sp
                ),
            )
        }
    }

    LaunchedEffect(key1 = null, block = {
        delay(300)
        requesterList[0].requestFocus()
    })
}

private fun connectInputtedCode(
    textList: List<MutableState<TextFieldValue>>, onVerifyCode: ((success: Boolean) -> Unit)? = null
) {
    var code = ""
    for (text in textList) {
        code += text.value.text
    }
    if (code.length == 4) {
        onVerifyCode?.let {
            it(
                true
            )
        }
    } else {
        onVerifyCode?.let {
            it(false)
        }
    }
}

private fun nextFocus(
    textList: List<MutableState<TextFieldValue>>, requesterList: List<FocusRequester>
) {
    for (index in textList.indices) {
        if (textList[index].value.text == "") {
            if (index < textList.size) {
                requesterList[index].requestFocus()
                break
            }
        }
    }
}

@Composable
private fun InputView(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = false,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color(235, 238, 240))
            .wrapContentSize()
            .focusRequester(focusRequester),
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .width(65.dp)
                    .height(65.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(Color.Black),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        })

    )
}