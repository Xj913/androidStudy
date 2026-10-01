package com.xiajun.base

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xiajun.ui.MyColors
import com.xiajun.ui.MyDimens


@Composable
fun DividerHorizontal16(modifier: Modifier = Modifier.padding(horizontal = 16.dp)) {
    HorizontalDivider(modifier = modifier.fillMaxWidth().height(MyDimens.DividerH),
        thickness = MyDimens.DividerH,
        color = MyColors.Divider)
}

@Composable
fun DividerVertical00(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier = Modifier.fillMaxHeight().width(MyDimens.DividerH),
        thickness = MyDimens.DividerH,
        color = MyColors.Divider)
}

fun Modifier.myitem() = fillMaxWidth().height(MyDimens.ItemDefault)

@Composable
fun CardPrimary(content: @Composable ()-> Unit = {}) {
    val customCardColors = CardDefaults.cardColors(
        contentColor = Color.White,
        containerColor = MyColors.ColorPrimary,
        disabledContentColor = Color.Gray,
        disabledContainerColor = Color.LightGray,
    )
    val customCardElevation = CardDefaults.cardElevation(
        defaultElevation = 8.dp,
        pressedElevation = 2.dp,
        focusedElevation = 4.dp
    )
    Card(
        colors = customCardColors,
        elevation = customCardElevation
    ) {
        content()
    }
}