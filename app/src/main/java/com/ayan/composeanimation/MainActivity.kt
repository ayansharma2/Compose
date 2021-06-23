package com.ayan.composeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayan.composeanimation.ui.theme.ComposeAnimationTheme
import com.ayan.composeanimation.ui.theme.green700

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val names = listOf<String>(
            "abc",
            "cde",
            "cdcd",
            "dcdc",
            "dcdc",
            "dcdcdc",
            "dcdcdc",
            "Cdcdcdc",
            "ASdasd",
            "Asdasda",
            "asdasd",
            "sadasd",
            "Asdasdasd",
            "Asdasda",
            "ASdasd",
            "ASdasdasd",
            "asdasdads"
        )
        setContent {

            // A surface container using the 'background' color from the theme
            ComposeAnimationTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    Greeting(names)
                }
            }

        }
    }
}

@Composable
fun Greeting(names: List<String>) {
    val scrollState = rememberScrollState(0)
    val context = LocalContext.current
    val image = ImageBitmap.imageResource(context.resources, R.drawable.adele21).asAndroidBitmap()
    val swatch = remember { image.generateDominantColorState() }
    val dominantColors = listOf(Color(swatch.rgb), Color.Black)
    val dominantGradient = remember { dominantColors }
    val surfaceGradient = listOf(Color(0xFF2A2A2A), Color.Black).asReversed()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalGradientBackground(dominantGradient)

    ) {
        topBox(scrollState = scrollState)
        topBoxBackground(scrollState)
        bottomContent(scrollState,surfaceGradient)
        topToolbar(scrollState,surfaceGradient)
    }
}


@Composable
fun topToolbar(scrollState: ScrollState, surfaceGradient: List<Color>) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier
        .fillMaxWidth()
        .horizontalGradientBackground(
            if (Dp(scrollState.value.toFloat()) < 1080.dp)
                listOf(Color.Transparent, Color.Transparent) else surfaceGradient
        )) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)

        Text(
            text = "Perfect",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(16.dp)
                .alpha(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
        )
        Icon(
            imageVector = Icons.Default.MoreVert, tint = MaterialTheme.colors.onSurface,
            contentDescription = null
        )
    }
}

@Composable
fun bottomContent(scrollState: ScrollState, surfaceGradient: List<Color>) {
    Column(modifier = Modifier.verticalScroll(state = scrollState)) {
        Spacer(modifier = Modifier.height(480.dp))
        Column(modifier = Modifier.horizontalGradientBackground(surfaceGradient)) {
            Button(onClick = {},
            colors = ButtonDefaults.buttonColors(backgroundColor = green700),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 100.dp)
                .clip(CircleShape)) {
                Text(text = "SHUFFLE PLAY",
                style = typography.h6.copy(fontSize = 14.sp),
                color = MaterialTheme.colors.onSurface, modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
                    )
            }
            Row(modifier= Modifier
                .padding(16.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                    Text(text = "Download",
                    style = typography.h6.copy(fontSize = 14.sp,
                    color = MaterialTheme.colors.onSurface))

                var switched by remember { mutableStateOf(true) }
                Switch(checked = switched,
                colors=SwitchDefaults.colors(
                    checkedThumbColor =Color.Green
                ),
                    modifier =Modifier.padding(8.dp),
                    onCheckedChange ={ switched = it }
                )
            }
            val items = remember { AlbumsList.albums }
            items.forEach{
                SpotifySongItem(album = it)
            }
        }
    }
}


@Composable
fun topBoxBackground(scrollState: ScrollState) {
val dynamicAlpha=((scrollState.value+0.00f)/1000).coerceIn(0f,1f)
    Box(modifier = Modifier
        .height(480.dp)
        .fillMaxWidth()
        .background(
            MaterialTheme.colors.surface.copy(
                alpha = animateFloatAsState(dynamicAlpha).value
            )
        ))
}


@Composable
fun topBox(scrollState: ScrollState) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        )
        val dynamicValue =
            if (250.dp - Dp(scrollState.value / 50f) < 10.dp) 10.dp //prevent going 0 cause crash
            else 250.dp - Dp(scrollState.value / 20f)
        val animateImageSize = animateDpAsState(dynamicValue).value
        Image(
            painter = painterResource(id = R.drawable.adele21), contentDescription = "Hello",
            modifier = Modifier
                .size(animateImageSize)
                .padding(8.dp)
        )
        Text(
            text = "Perfect", style = typography.h5.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        Text(text = "FOLLOWING",style = typography.h6.copy(fontSize = 12.sp),
        modifier = Modifier
            .padding(4.dp)
            .border(
                border = BorderStroke(width = 2.dp, Color.Green),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 4.dp, horizontal = 24.dp),
            color=MaterialTheme.colors.onSurface
        )
        Text(text = "Album by Ed Sheeran-2016", style = typography.subtitle2,
            color=Color.LightGray,
        modifier = Modifier.padding(4.dp))

    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeAnimationTheme {
//        Greeting("Android")
//    }
//}