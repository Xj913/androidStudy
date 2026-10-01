package com.xiajun.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


val replyShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

object MyTextStyles {
    val textPrimary = TextStyle(color = MyColors.TextPrimary)
    val textPrimary11sp = textPrimary.copy(fontSize = 11.sp)
    val textPrimary12sp = textPrimary.copy(fontSize = 12.sp)
    val textPrimary13sp = textPrimary.copy(fontSize = 13.sp)
    val textPrimary14sp = textPrimary.copy(fontSize = 14.sp)
    val textPrimary15sp = textPrimary.copy(fontSize = 15.sp)
    val textPrimary16sp = textPrimary.copy(fontSize = 16.sp)
    val textPrimary17sp = textPrimary.copy(fontSize = 17.sp)
    val textPrimary18sp = textPrimary.copy(fontSize = 18.sp)
    val textSecond = TextStyle(color = MyColors.TextSecond)
    val textSecond11sp = textSecond.copy(fontSize = 11.sp)
    val textSecond12sp = textSecond.copy(fontSize = 12.sp)
    val textSecond13sp = textSecond.copy(fontSize = 13.sp)
    val textSecond14sp = textSecond.copy(fontSize = 14.sp)
    val textSecond15sp = textSecond.copy(fontSize = 15.sp)
    val textSecond16sp = textSecond.copy(fontSize = 16.sp)
    val textSecond17sp = textSecond.copy(fontSize = 17.sp)
    val textSecond18sp = textSecond.copy(fontSize = 18.sp)
    val textGray = TextStyle(color = MyColors.TextGray)
    val textGray11sp = textGray.copy(fontSize = 11.sp)
    val textGray12sp = textGray.copy(fontSize = 12.sp)
    val textGray13sp = textGray.copy(fontSize = 13.sp)
    val textGray14sp = textGray.copy(fontSize = 14.sp)
    val textGray15sp = textGray.copy(fontSize = 15.sp)
    val textGray16sp = textGray.copy(fontSize = 16.sp)
    val textGray17sp = textGray.copy(fontSize = 17.sp)
    val textGray18sp = textGray.copy(fontSize = 18.sp)
    val textGrayLight = TextStyle(color = MyColors.TextGrayLight)
    val textGrayLight11sp = textGrayLight.copy(fontSize = 11.sp)
    val textGrayLight12sp = textGrayLight.copy(fontSize = 12.sp)
    val textGrayLight13sp = textGrayLight.copy(fontSize = 13.sp)
    val textGrayLight14sp = textGrayLight.copy(fontSize = 14.sp)
    val textGrayLight15sp = textGrayLight.copy(fontSize = 15.sp)
    val textGrayLight16sp = textGrayLight.copy(fontSize = 16.sp)
    val textGrayLight17sp = textGrayLight.copy(fontSize = 17.sp)
    val textGrayLight18sp = textGrayLight.copy(fontSize = 18.sp)
    val textWhite = TextStyle(color = Color.White)
    val textWhite11sp = textWhite.copy(fontSize = 11.sp)
    val textWhite12sp = textWhite.copy(fontSize = 12.sp)
    val textWhite13sp = textWhite.copy(fontSize = 13.sp)
    val textWhite14sp = textWhite.copy(fontSize = 14.sp)
    val textWhite15sp = textWhite.copy(fontSize = 15.sp)
    val textWhite16sp = textWhite.copy(fontSize = 16.sp)
    val textWhite17sp = textWhite.copy(fontSize = 17.sp)
    val textWhite18sp = textWhite.copy(fontSize = 18.sp)
}

    // 防抖点击
    fun Modifier.clickableOnce(
        intervalMs: Long = 500,
        onClick: () -> Unit
    ): Modifier = composed {
        val lastClickTime = remember { mutableLongStateOf(0L) }
        this.clickable {
            val now = System.currentTimeMillis()
            if (now - lastClickTime.longValue >= intervalMs) {
                lastClickTime.longValue = now
                onClick()
            }
        }
    }

    fun Modifier.ifTrue(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
        return if (condition) this.block() else this
    }

    fun <T> T. applyIf (condition: Boolean , block: T .() -> Unit ) : T {
        if (condition) this .block()
        return this
    }


