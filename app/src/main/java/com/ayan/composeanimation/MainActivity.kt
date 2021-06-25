package com.ayan.composeanimation

import android.graphics.Color.WHITE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.collection.mutableVectorOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.ayan.composeanimation.ui.theme.ComposeAnimationTheme
import com.ayan.composeanimation.ui.theme.primary

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            ComposeAnimationTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }

        }
    }
}

@ExperimentalAnimationApi
@Composable
fun Greeting() {
    val isRotated = remember { mutableStateOf(false) }
    var angle =
        animateFloatAsState(targetValue = if (isRotated.value) 0f else 1000f, tween(2000)).value
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Animation") },
            elevation = 8.dp,
            navigationIcon = {
                Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "Home",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .clickable {
                            isRotated.value = !isRotated.value
                        }
                        .rotate(angle))
            }
        )
    }) {
        LazyColumn(
            state = rememberLazyListState(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { colorStateAnimation() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { colorAndSizeAnimation() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { opacityAnimation() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { offsetAnimation() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { vectorAnimation() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { animateImages() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item{ animateVisibility() }
            item { Spacer(modifier = Modifier.height(30.dp)) }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item{ animateVisibilityWithChildren()}
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item { animateSlideInSlideOut() }
            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item{
                animateExpandAndShrink()
            }

            item {
                Divider(
                    color = MaterialTheme.colors.onBackground,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            item{
                animateContentSize()
            }

        }

    }
}

@Composable
fun animateContentSize() {
    Text(
        text = "Multiple Children Visibility", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var count = remember { mutableStateOf(1) }
    Row(modifier= Modifier
        .animateContentSize()
        .clickable { if (count.value < 10) count.value += 3 else count.value = 1 }) {
        (0..count.value).forEach {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null)
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@ExperimentalAnimationApi
@Composable
fun animateExpandAndShrink() {
    Text(
        text = "Multiple Children Visibility", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var expanded=remember{
        mutableStateOf(true)
    }
    Row(
        Modifier
            .padding(12.dp)
            .width(200.dp)
            .height(60.dp)
            .background(MaterialTheme.colors.primary)
            .clickable {
                expanded.value = !expanded.value
            }) {
        AnimatedVisibility(visible = expanded.value,
            modifier =Modifier.align(Alignment.CenterVertically),
            enter= expandIn(Alignment.Center,{fullSize: IntSize -> fullSize * 4}),
            exit = shrinkOut(Alignment.Center)
            ) {
            Button(
                modifier = Modifier.padding(start = 12.dp),
                onClick = { expanded.value = !expanded.value }) {
                Text(text = "Shrink/Expand")
            }
        }
    }
    Spacer(modifier = Modifier.height(30.dp))
}

@ExperimentalAnimationApi
@Composable
fun animateSlideInSlideOut() {
    Text(
        text = "Multiple Children Visibility", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var expanded=remember{
        mutableStateOf(true)
    }


    Row(
        Modifier
            .padding(12.dp)
            .width(200.dp)
            .height(60.dp)
            .background(MaterialTheme.colors.primary)
            .clickable {
                expanded.value = !expanded.value
            }
    ) {
            AnimatedVisibility(visible = expanded.value,
            enter = slideIn(
                {IntOffset(0,120)},
                tween(500,easing = LinearOutSlowInEasing)
            )+ fadeIn(1f, tween(500,easing = LinearOutSlowInEasing)),
            exit = slideOut(
                {IntOffset(0,120)},
tween(500,easing = LinearOutSlowInEasing)
            )+ fadeOut(0f, tween(500,easing = LinearOutSlowInEasing))
            ) {
              Text(text = "This will slide")
            }
    }

}

@ExperimentalAnimationApi
@Composable
fun animateVisibilityWithChildren() {
    Text(
        text = "Multiple Children Visibility", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var expanded=remember{
        mutableStateOf(true)
    }
    val colors = listOf(Color.Blue, Color.Green, Color.Black, Color.Yellow)
    IconButton(onClick = { expanded.value = !expanded.value }) {
        Icon(imageVector = Icons.Default.Edit, contentDescription = "")
    }
    AnimatedVisibility(visible = expanded.value) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            colors.forEachIndexed{index,color->
                val springAnim=remember{
                        spring<IntOffset>(
stiffness =Spring.StiffnessLow*(1f-index*0.2f)
                        )
                }
                Card(backgroundColor = color,
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
                    .animateEnterExit(
                        enter = slideInHorizontally({
                            it
                        }, springAnim),
                        exit = ExitTransition.None

                    )){}

            }
        }
    }
    Spacer(modifier = Modifier.padding(bottom = 50.dp))
}


@ExperimentalAnimationApi
@Composable
fun animateVisibility() {
    Text(
        text = "Image Animation", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var expanded=remember{
        mutableStateOf(true)
    }
    FloatingActionButton(onClick = { expanded.value = !expanded.value },

    modifier = Modifier.padding(16.dp)) {
        Row() {
            Icon(painter = painterResource(id = R.drawable.twitter), contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(start = 8.dp),
            tint = Color.Black)
            AnimatedVisibility(visible = expanded.value,
            modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(modifier = Modifier.padding(end = 12.dp), text = "Tweet")
            }
        }
    }
}

@Composable
fun animateImages() {

    Text(
        text = "Image Animation", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var draw = remember { mutableStateOf(false) }
    Box {
        Image(
            painter = painterResource(id = R.drawable.ben1),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw.value) 30f else 5f).value,
                    translationY = 0f,
                    translationX = animateFloatAsState(if (draw.value) -300f else 0f).value
                )
                .clickable {
                    draw.value = !draw.value
                }
        )
        Image(painter = painterResource(id = R.drawable.ben2), contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw.value) 30f else 0f).value,
                    translationY = animateFloatAsState(if (!draw.value) 30f else 0f).value,
                    translationX = animateFloatAsState(if (draw.value) 300f else 0f).value
                )
                .clickable {
                    draw.value = !draw.value
                }
        )

        Image(painter = painterResource(id = R.drawable.ben3), contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw.value) 30f else 0f).value,
                    translationY = animateFloatAsState(if (!draw.value) 50f else 0f).value,
                    translationX = 0f
                )
                .clickable {
                    draw.value = !draw.value
                })
    }
    Spacer(modifier = Modifier.height(60.dp))
    var draw2 = remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(bottom = 50.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ben1),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw2.value) 30f else 5f).value,
                    translationY = 0f,
                    translationX = animateFloatAsState(if (draw2.value) -320f else 0f).value,
                    rotationY = animateFloatAsState(if (draw2.value) 45f else 0f).value

                )
                .clickable {
                    draw2.value = !draw2.value
                }
        )
        Image(painter = painterResource(id = R.drawable.ben2), contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw2.value) 30f else 0f).value,
                    translationY = animateFloatAsState(if (!draw2.value) 30f else 0f).value,
                    translationX = animateFloatAsState(if (draw2.value) 320f else 0f).value,
                    rotationY = animateFloatAsState(if (draw2.value) 45f else 0f).value
                )
                .clickable {
                    draw2.value = !draw2.value
                }
        )

        Image(painter = painterResource(id = R.drawable.ben3), contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw2.value) 30f else 0f).value,
                    translationY = animateFloatAsState(if (!draw2.value) 50f else 0f).value,
                    translationX = 0f,
                    rotationY = animateFloatAsState(if (draw2.value) 45f else 0f).value
                )
                .clickable {
                    draw2.value = !draw2.value
                })
    }
    Spacer(modifier = Modifier.height(40.dp))
    var draw3 = remember { mutableStateOf(false) }
    Box(modifier = Modifier.padding(bottom = 50.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ben1),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw3.value) 30f else 5f).value,
                    translationY = 0f,
                    translationX = animateFloatAsState(if (draw3.value) -320f else 0f).value,
                    rotationZ = animateFloatAsState(if (draw3.value) 45f else 0f).value

                )
                .clickable {
                    draw3.value = !draw3.value
                }
        )
        Image(painter = painterResource(id = R.drawable.ben2), contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw3.value) 30f else 0f).value,
                    translationY = animateFloatAsState(if (!draw3.value) 30f else 0f).value,
                    translationX = animateFloatAsState(if (draw3.value) 320f else 0f).value,
                    rotationZ = animateFloatAsState(if (draw3.value) 45f else 0f).value
                )
                .clickable {
                    draw3.value = !draw3.value
                }
        )

        Image(painter = painterResource(id = R.drawable.ben3), contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    shadowElevation = animateFloatAsState(if (draw3.value) 30f else 0f).value,
                    translationY = animateFloatAsState(if (!draw3.value) 50f else 0f).value,
                    translationX = 0f,
                    rotationZ = animateFloatAsState(if (draw3.value) 45f else 0f).value
                )
                .clickable {
                    draw3.value = !draw3.value
                })
    }
}

data class CustomAnimationState(val width: Dp, val rotation: Float)

@Composable
fun vectorAnimation() {
    Text(
        text = "Offset Animation", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    var enabled = remember { mutableStateOf(true) }
    val initUiState = CustomAnimationState(300.dp, 0f)
    val targetUiState = CustomAnimationState(500.dp, 20f)
    val uiState = if (enabled.value) initUiState else targetUiState
    val animatedUiState =
        animateValueAsState(targetValue = uiState, typeConverter = TwoWayConverter(
            convertToVector = {
                AnimationVector(it.width.value, it.rotation)
            },
            convertFromVector = {
                CustomAnimationState(it.v1.dp, it.v2)
            }
        ),
            animationSpec = tween(600))
    Button(
        onClick = { enabled.value = !enabled.value },
        modifier = Modifier
            .padding(16.dp)
            .width(animatedUiState.value.width)
            .rotate(animatedUiState.value.rotation)
    ) {
        Text("Custom State Animation")
    }

}

@Composable
fun offsetAnimation() {
    Text(
        text = "Offset Animation", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(20.dp))
    val enabled = remember { mutableStateOf(true) }
    val cords = animateOffsetAsState(
        targetValue = if (enabled.value)
            Offset(0f, 0f) else Offset(50f, 25f)
    )


    Row(horizontalArrangement = Arrangement.SpaceAround) {
        Image(painterResource(id = R.drawable.img1), contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
                .offset(x = Dp(cords.value.x), y = Dp(cords.value.y))
                .clickable { enabled.value = !enabled.value }
        )

        Image(painterResource(id = R.drawable.img2), contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp)
                .offset(x = -Dp(cords.value.x), y = -Dp(cords.value.y))
                .clickable { enabled.value = !enabled.value }
        )
    }
}

@Composable
fun opacityAnimation() {
    val enabled = remember { mutableStateOf(true) }
    val alpha = animateFloatAsState(
        targetValue = if (enabled.value)
            1f else 0.5f
    )

    Text(
        text = "Opacity Animation", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    Button(
        onClick = { enabled.value = !enabled.value },
        modifier = Modifier
            .padding(16.dp)
            .alpha(alpha = alpha.value)
            .fillMaxWidth()
    ) {
        Text(text = "Animate Opacity")
    }

}


@Composable
fun colorAndSizeAnimation() {
    Text(
        text = "Animation of Size and Color", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(10.dp))
    var enabled = remember { mutableStateOf(true) }
    var color = animateColorAsState(
        targetValue =
        if (enabled.value) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    )
    var backgroundColor = ButtonDefaults.buttonColors(
        backgroundColor = color.value
    )

    var width = animateDpAsState(
        targetValue = if (enabled.value)
            150.dp else 300.dp
    )
    var height = animateDpAsState(
        targetValue = if (enabled.value)
            40.dp else 90.dp
    )

    Button(
        onClick = { enabled.value = !enabled.value },
        colors = backgroundColor,
        modifier = Modifier
            .height(height.value)
            .width(width.value)

    ) {
        Text(text = "Animate Color")
    }

}


@Composable
fun colorStateAnimation() {
    Text(
        text = "Animation of Color", style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(8.dp),
        color = Color.Gray
    )
    Spacer(modifier = Modifier.height(10.dp))
    var enabled = remember { mutableStateOf(true) }
    var color = animateColorAsState(
        targetValue =
        if (enabled.value) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    )
    var backgroundColor = ButtonDefaults.buttonColors(
        backgroundColor = color.value
    )
    Button(
        onClick = { enabled.value = !enabled.value },
        colors = backgroundColor,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Animate Color")
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAnimationTheme {
        Greeting()
    }
}