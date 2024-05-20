package com.name.runerotest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.name.runerotest.R
import com.name.runerotest.ui.theme.CustomColors
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val stateTypeWindow = remember {
        mutableStateOf(true)
    }
    val temperature = remember { mutableStateOf(0.0) }
    val currentTime = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            temperature.value = Random.nextDouble(85.0, 95.0).let { (it * 10).toInt() / 10.0 }
            currentTime.value = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
            delay(1000L)
        }
    }
    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier
                    .width(140.dp)
                    .padding(start = 8.dp)
                    .clickable {
                        stateTypeWindow.value = !stateTypeWindow.value
                    },
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "logo",
                contentScale = ContentScale.FillWidth
            )

            Row(verticalAlignment = Alignment.CenterVertically){
                Text(currentTime.value, color = CustomColors().brandDarkGray)
                Spacer(modifier = Modifier.size(8.dp))
                Text(temperature.value.toString() + "°", color = CustomColors().brandDarkGray)
                Spacer(modifier = Modifier.size(8.dp))
                Image(painter = painterResource(id = R.drawable.item_logo), contentDescription = null, modifier = Modifier.size(16.dp), contentScale = ContentScale.FillHeight)
                Text("RU", color = CustomColors().brandDarkGray)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

        Spacer(modifier = Modifier.size(8.dp))


        if (stateTypeWindow.value) {
            ListItem(
                viewModel.getTypeFull(),
                viewModel.getNameState(),
                viewModel.getPriceState()
            )
        } else {
            SettingsEdit(viewModel = viewModel) {
                stateTypeWindow.value = true
            }
        }

    }
}

@Composable
fun SettingsEdit(viewModel: MainViewModel, backHandler: () -> Unit) {

    val focusRequesterName = remember { FocusRequester() }
    val focusRequesterPrice = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val startName = viewModel.getNameState() ?: ""
    val startPrice = viewModel.getPriceState() ?: "0"
    val startActiveStatus = viewModel.getTypeFull()
    val stateStartPriceSwitch = if (viewModel.getPriceState() == null) {
        true
    } else {
        false
    }
    val inputName = remember { mutableStateOf(startName) }
    val inputPrice = remember {
        mutableStateOf(startPrice)
    }
    val activeStatus = remember {
        mutableStateOf(startActiveStatus)
    }
    val priceSwitch = remember {
        mutableStateOf(stateStartPriceSwitch)
    }

    val activeButtonSaveStatus = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(inputName.value, inputPrice.value, activeStatus.value, priceSwitch.value) {
        if (inputName.value != startName || inputPrice.value != startPrice || activeStatus.value != startActiveStatus || priceSwitch.value != stateStartPriceSwitch) {
            activeButtonSaveStatus.value = true
        }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column {
            Text(
                text = "Наименование",
                modifier = Modifier.padding(bottom = 8.dp),
                color = CustomColors().brandLightGray
            )
            InputItem(
                modifier = Modifier.width(250.dp),
                inputText = inputName.value,
                showRubleSign = false,
                KeyboardType = KeyboardType.Text,
                changeHandler = {
                    inputName.value = it
                },
                focusRequester = focusRequesterName,
                keyBoardActionenExt = {
                    focusRequesterPrice.requestFocus()
                }
            )

            Text(
                text = "Цена",
                modifier = Modifier.padding(bottom = 8.dp, top = 16.dp),
                color = CustomColors().brandLightGray
            )
            InputItem(
                modifier = Modifier.width(250.dp),
                inputText = inputPrice.value,
                showRubleSign = true,
                KeyboardType = KeyboardType.Number,
                changeHandler = {
                    inputPrice.value = it
                },
                focusRequester = focusRequesterPrice,
                keyBoardActionenExt = {
                    focusManager.clearFocus()
                }
            )

            Spacer(modifier = Modifier.padding(top = 8.dp))
            CardSelector(modifier = Modifier.width(250.dp), activeStatus = priceSwitch.value) {
                priceSwitch.value = it
            }

            Spacer(modifier = Modifier.padding(top = 24.dp))
            Button(
                enabled = activeButtonSaveStatus.value,
                onClick = {
                    viewModel.setTypeFull(activeStatus.value)
                    viewModel.setNameState(inputName.value)
                    if (priceSwitch.value) {
                        viewModel.setPriceState(null)
                    } else {
                        viewModel.setPriceState(inputPrice.value)
                    }
                    backHandler()
                },
                colors = ButtonDefaults.buttonColors(
                    CustomColors().brandOrange,
                    disabledContainerColor = CustomColors().brandDarkGray
                ),
            ) {
                Text(text = "Сохранить", color = Color.White)
            }

        }

        ItemTypeButtle(
            imagePainter = painterResource(id = R.drawable.ic_full_glass),
            activeStatus.value
        ) {
            activeStatus.value = !activeStatus.value
        }

        ItemTypeButtle(
            imagePainter = painterResource(id = R.drawable.ic_not_full_glass),
            !activeStatus.value
        ) {
            activeStatus.value = !activeStatus.value
        }

    }
}