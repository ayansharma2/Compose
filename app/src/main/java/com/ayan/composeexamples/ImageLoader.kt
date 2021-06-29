package com.ayan.composeexamples

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


@SuppressLint("UnrememberedMutableState")
@Composable
fun ImageLoader(LocalContext: ProvidableCompositionLocal<Context>, url: String) {

    val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)



    // get network image
    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) { }
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                bitmapState.value = resource
            }
        })

    Card(
        modifier = Modifier
            .height(250.dp)
            .width(400.dp)
            .padding(horizontal = 20.dp)
            .background(color = MaterialTheme.colors.primary), shape = RoundedCornerShape(12.dp)
    ) {
            if(bitmapState.value==null){
                CircularProgressIndicator(color = MaterialTheme.colors.onPrimary,modifier = Modifier.size(50.dp))
            }else{
                Image(bitmap = bitmapState.value?.asImageBitmap()!!, contentDescription = null,
                modifier=Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop)
            }
    }

}

