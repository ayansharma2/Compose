package com.guru.composecookbook.moviesapp.ui.home.components

import android.graphics.Movie
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayan.composeanimation.R
import kotlin.math.abs
import kotlin.math.min

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviePagerItem(
    movie: Movie? = null,
    isSelected: Boolean,
    offset: Float,
    imageId: Int,

    ) {
    val animateHeight = getOffsetBasedValue(
        selectedValue = 645,
        nonSelectedValue = 390,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateWidth = getOffsetBasedValue(
        selectedValue = 340,
        nonSelectedValue = 320,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateElevation = getOffsetBasedValue(
        selectedValue = 12,
        nonSelectedValue = 2,
        isSelected = isSelected,
        offset = offset
    ).dp


    Card(
        elevation = animateDpAsState(animateElevation).value,
        modifier = Modifier
            .width(animateWidth)
            .height(animateHeight)
            .padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.onBackground,
        contentColor = MaterialTheme.colors.background,
    ) {
        Column {
            Image(
                painter = painterResource(id = imageId),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(490.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                val clicked = remember { mutableStateOf(false) }

                IconButton(onClick = {

                    clicked.value = !clicked.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        tint = MaterialTheme.colors.primary,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer(
                                rotationY = animateFloatAsState(
                                    if (clicked.value) 720f else 0f, tween(400)
                                ).value
                            )
                    )
                }
            }


            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Get Tickets", modifier = Modifier.padding(8.dp))
            }
        }
    }
}

private fun getOffsetBasedValue(
    selectedValue: Int,
    nonSelectedValue: Int,
    isSelected: Boolean,
    offset: Float,
): Float {
    val actualOffset = if (isSelected) 1 - abs(offset) else abs(offset)
    val delta = abs(selectedValue - nonSelectedValue)
    val offsetBasedDelta = delta * actualOffset

    return min(selectedValue, nonSelectedValue) + offsetBasedDelta
}

