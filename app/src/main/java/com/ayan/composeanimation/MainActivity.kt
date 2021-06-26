package com.ayan.composeanimation

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import com.ayan.composeanimation.ui.theme.ComposeAnimationTheme
import com.ayan.composeanimation.ui.theme.graySurface
import com.guru.composecookbook.moviesapp.ui.home.components.MoviePagerItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
}
fun Bitmap.generateDominantColorState(): Palette.Swatch = Palette.Builder(this)
    .resizeBitmapArea(0)
    .maximumColorCount(16)
    .generate()
    .swatches
    .maxByOrNull { swatch -> swatch.population }!!


fun Modifier.gradientBackground(
    colors: List<Color>,
    brushProvider: (List<Color>, Size) -> Brush
): Modifier = composed {
    var size by remember { mutableStateOf(Size.Zero) }
    val gradient = remember(colors, size) { brushProvider(colors, size) }
    drawWithContent {
        size = this.size
        drawRect(brush = gradient)
        drawContent()
    }
}

fun Modifier.verticalGradientBackground(
    colors: List<Color>
) = gradientBackground(colors) { gradientColors, size ->
    Brush.verticalGradient(
        colors = gradientColors,
        startY = 0f,
        endY = size.width
    )
}



@Composable
fun Greeting() {
    val navType = rememberSaveable { mutableStateOf(MovieNavType.SHOWING) }
    val context = LocalContext.current
    var defaultBitmap =
        ImageBitmap.imageResource(context.resources, R.drawable.camelia).asAndroidBitmap()
    val currentBitmap = remember { mutableStateOf(defaultBitmap) }
    val swatch = currentBitmap.value.generateDominantColorState()
    val dominantColors = listOf(Color(swatch.rgb), Color.Black)
    navType.value=MovieNavType.SHOWING
    Scaffold(bottomBar = {BottomNavBar(navType)}) {


        Column(modifier = Modifier.fillMaxSize().verticalGradientBackground(dominantColors)) {
            loadMoviePager(defaultBitmap)
        }
    }
}

@Composable
fun loadMoviePager(defaultBitmap: Bitmap) {
    val pagerState = remember { PagerState(maxPage = imageIds.size - 1) }
    Pager(state = pagerState, modifier = Modifier.height(645.dp)) {
        val imageId= imageIds[page]
        val isSelected = pagerState.currentPage == page

        // Only one page before and one page after the selected page needs to receive non zero offset
        val filteredOffset = if (kotlin.math.abs(pagerState.currentPage - page) < 2) {
            currentPageOffset
        } else 0f
        Log.e("FilteredOffset",filteredOffset.toString())
        MoviePagerItem(
            imageId=imageId,
            isSelected=isSelected,
          offset = filteredOffset
        )
    }
}

@Composable
fun BottomNavBar(navType: MutableState<MovieNavType>) {
    BottomAppBar(backgroundColor = graySurface) {
        BottomNavigationItem(
            selected = navType.value == MovieNavType.SHOWING,
            onClick = { navType.value = MovieNavType.SHOWING },
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription ="Home",tint = if(navType.value == MovieNavType.SHOWING)MaterialTheme.colors.primary else Color.White)},
            label ={ Text(text = "Home",color = if(navType.value == MovieNavType.SHOWING)MaterialTheme.colors.primary else Color.White)}
            )
        BottomNavigationItem(
            selected = navType.value == MovieNavType.TRENDING,
            onClick = { navType.value = MovieNavType.TRENDING },
            icon = { Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription ="Trending",tint = if(navType.value == MovieNavType.TRENDING)MaterialTheme.colors.primary else Color.White)},
            label ={ Text(text = "Trending",color = if(navType.value == MovieNavType.TRENDING)MaterialTheme.colors.primary else Color.White)}
        )
        BottomNavigationItem(
            selected = navType.value == MovieNavType.WATCHLIST,
            onClick = { navType.value = MovieNavType.WATCHLIST },
            icon = { Icon(imageVector = Icons.Filled.Favorite, contentDescription ="Favourite",tint = if(navType.value == MovieNavType.WATCHLIST)MaterialTheme.colors.primary else Color.White)},
            label ={ Text(text = "Saved",color = if(navType.value == MovieNavType.WATCHLIST)MaterialTheme.colors.primary else Color.White)}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeAnimationTheme {
        Greeting()
    }
}

enum class MovieNavType {
    SHOWING, TRENDING, WATCHLIST
}

val imageIds =
    listOf(
        R.drawable.camelia,
        R.drawable.camelia,
        R.drawable.khalid,
        R.drawable.lana,
        R.drawable.edsheeran,
        R.drawable.dualipa,
        R.drawable.sam,
        R.drawable.marsh,
        R.drawable.wolves,
        R.drawable.camelia,
        R.drawable.khalid,
        R.drawable.lana,
        R.drawable.edsheeran,
        R.drawable.dualipa,
        R.drawable.sam,
        R.drawable.marsh,
        R.drawable.wolves,
        R.drawable.camelia,
        R.drawable.khalid,
        R.drawable.lana,
        R.drawable.edsheeran,
        R.drawable.dualipa,
        R.drawable.sam,
        R.drawable.marsh,
        R.drawable.wolves,
    )