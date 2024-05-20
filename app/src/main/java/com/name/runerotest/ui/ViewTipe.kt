@file:OptIn(ExperimentalMaterial3Api::class)

package com.name.runerotest.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.name.runerotest.R
import com.name.runerotest.ui.theme.CustomColors


@Composable
fun CardItem(
    imageTypeFull: Boolean,
    textNameGlass: String,
    volume: String,
    price: String?
) {
    val gradientColors = listOf(
        Color(0xFF18100D),
        Color(0xFF150D0C),
        Color(0xFF110A0A)
    )

    val bottomGradientColors = listOf(
        Color(0xFF170F0D),
        Color(0xFF231917)
    )

    Box(
        modifier = Modifier
            .widthIn(min = 150.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Brush.linearGradient(gradientColors), shape = RoundedCornerShape(9.dp))
                .clip(RoundedCornerShape(8.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier.size(width = 80.dp, height = 130.dp),
                painter = if (imageTypeFull) painterResource(id = R.drawable.ic_full_glass) else painterResource(
                    id = R.drawable.ic_not_full_glass
                ),
                contentDescription = null,
                contentScale = ContentScale.FillHeight
            )

            Text(
                modifier = Modifier.padding(horizontal = 4.dp),
                text = textNameGlass,
                style = MaterialTheme.typography.bodyMedium,
                color = CustomColors().brandLightGray,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(12.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.linearGradient(bottomGradientColors)),
                horizontalArrangement = if (price != null) Arrangement.SpaceBetween else Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = volume,
                    modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 4.dp),
                    color = CustomColors().brandDarkGray
                )

                if (price != null) {
                    Text(
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp, bottom = 4.dp),
                        text = price + "₽",
                        color = CustomColors().brandOrange
                    )
                }
            }

        }

    }

}




@Composable
fun ListItem(imageTypeFull: Boolean, name: String?, price: String?) {
    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
        items(25) {
            Spacer(modifier = Modifier.size(8.dp))
            CardItem(
                imageTypeFull,
                name ?: "Капучино эконом",
                "0.33",
                price
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputItem(
    modifier: Modifier,
    inputText: String,
    showRubleSign: Boolean,
    KeyboardType: KeyboardType,
    focusRequester: FocusRequester,
    keyBoardActionenExt: () -> Unit,
    changeHandler: (newString: String) -> Unit
) {

    Row(
        modifier = modifier
            .height(45.dp)
            .background(Color(0xFF150E0C), shape = RoundedCornerShape(4.dp))
            .clip(RoundedCornerShape(4.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextField(
            value = inputText,
            onValueChange = { changeHandler(it) },
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(color = Color.White, fontSize = 12.sp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                keyBoardActionenExt()
            }),
            maxLines = 1,
        )
        if (showRubleSign) {
            Text(
                text = "₽",
                color = CustomColors().brandLightGray,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 8.dp, end = 4.dp)
            )
        }
    }
}


@Composable
fun CardSelector(
    modifier: Modifier,
    activeStatus: Boolean,
    changeStatusHandler: (newState: Boolean) -> Unit
) {
    Box(
        modifier = modifier
    ) {

        Row(
            modifier = modifier
                .background(Color.Black)
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "Продавать бесплатно",
                color = CustomColors().brandLightGray,
                modifier = Modifier.padding(start = 8.dp)
            )
            Switch(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .scale(0.7f),
                checked = activeStatus,
                onCheckedChange = {
                    changeStatusHandler(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedBorderColor = CustomColors().brandOrange,
                    checkedTrackColor = CustomColors().brandOrange,
                    uncheckedTrackColor = CustomColors().brandDarkGray,
                    uncheckedBorderColor = CustomColors().brandDarkGray,
                    uncheckedThumbColor = Color.White
                )
            )
        }

    }
}

@Composable
fun ItemTypeButtle(imagePainter: Painter, selected: Boolean, clickHandler: () -> Unit) {
    Box() {
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier
                .height(140.dp)
                .clickable {
                    clickHandler()
                },
            contentScale = ContentScale.FillHeight
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .background(
                        CustomColors().brandOrange,
                        shape = RoundedCornerShape(128.dp)
                    )
                    .size(32.dp)
                    .clip(RoundedCornerShape(128.dp))
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sucsecc),
                    contentDescription = null
                )
            }
        }
    }
}