package com.tearas.expanemanagementjc.presentation.password.component

import android.media.MediaPlayer
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tearas.expanemanagementjc.R
import com.tearas.expanemanagementjc.presentation.password.PasswordState
import com.tearas.expanemanagementjc.presentation.password.PasswordViewModel
import com.tearas.expanemanagementjc.ui.theme.Blue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class PasscodeViewType {
    TYPE_SET_PASSCODE,
    TYPE_CHECK_PASSCODE
}


@Composable
fun ViewPhonePassword(
    type: PasscodeViewType = PasscodeViewType.TYPE_CHECK_PASSCODE,
    size: Int = 6,
    isSuccess: Boolean,
    txtWarning: String,
    password: String = "",
    input: String,
    inputChanged: (String) -> Unit,
    onConfirmSuccess: () -> Unit
) {
    LaunchedEffect(key1 = isSuccess) {
         if (isSuccess) onConfirmSuccess()
    }
    val scope = rememberCoroutineScope()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White,Blue),
                    tileMode = TileMode.Decal,
                    start = Offset(0.0f, 0f),
                    end = Offset.Infinite
                )
            )
            .fillMaxSize()
            .padding(vertical = 15.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.padlock),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = txtWarning,
                modifier = Modifier.padding(bottom = 10.dp, top = 15.dp),
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            ListPass(
                modifier = Modifier,
                size = size,
                position = input.length,
                isSuccess = isSuccess,
            )
        }
        val keys = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
            listOf(0)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            keys.forEach { row ->
                Row {
                    row.forEach { number ->
                        PhoneKeyItem(number = number.toString()) {
                            inputChanged(it)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ListPass(
    modifier: Modifier = Modifier,
    size: Int,
    position: Int,
    isSuccess: Boolean = false,
) {
    val shakeOffset = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = position) {
        if (position == size) {
            coroutineScope.launch {
                shakeOffset.animateTo(
                    targetValue = 10f,
                    animationSpec = tween(durationMillis = 50),
                )
                shakeOffset.animateTo(
                    targetValue = -10f,
                    animationSpec = tween(durationMillis = 50)
                )
                shakeOffset.animateTo(
                    targetValue = 10f,
                    animationSpec = tween(durationMillis = 50)
                )
                shakeOffset.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 50)
                )
            }
        }
    }
    Row(modifier = modifier
        .graphicsLayer {
            translationX = shakeOffset.value
        }
    ) {
        (1..size).forEach {
            KeyPass(position >= it, isSuccess)
        }
    }
}

@Composable
fun KeyPass(isNotEmpty: Boolean, isSuccess: Boolean = false) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(35.dp)
            .aspectRatio(1f)
            .padding(10.dp)
            .clip(CircleShape)
            .border(BorderStroke(1.dp, Color.Blue), CircleShape)
            .background(if (isSuccess) Color.Green else if (isNotEmpty) Blue else Color.Transparent)
    ) {}
}

@Composable
fun PhoneKeyItem(number: String, onClickKey: (String) -> Unit) {
    val width = LocalConfiguration.current.screenWidthDp / 4.2
    val context = LocalContext.current
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.sound_click) }
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(width.dp)
            .aspectRatio(1f)
            .padding(10.dp)
            .clip(CircleShape)
            .border(BorderStroke(1.dp, Color.White), CircleShape)
            .clickable {
                mediaPlayer.start()
                onClickKey(number)
            }
    ) {
        Text(text = number, style = MaterialTheme.typography.titleLarge, color = Color.White)
    }
}

@Preview
@Composable
private fun PhoneKeyItemPre() {

}