package com.ayan.composetest

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ayan.composetest.DataPackets.NewsObject
import com.ayan.composetest.DataPackets.ReturnObject
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson


class NewsDetail : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var article: NewsObject


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson=Gson()
        activity?.title="News Detail"
        arguments?.let {

            article = gson.fromJson(it.getString("article"),NewsObject::class.java)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        val view=ComposeView(requireContext())
        view.setContent {
            createUi(article)

        }
        return view
    }
    
    
    @Composable
    private fun createUi(article: NewsObject) {
        Card(modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = Color.White,) {
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if(article.urlToImage!=null){
                    val image=loadPicture(url = article.urlToImage, defaultImage =R.drawable.ic_launcher_background ).value
                    image?.let {image->
                        Image(modifier = Modifier.padding(10.dp),bitmap = image.asImageBitmap(), contentDescription ="News Image" )
                    }
                }
                if(article.author!=null){
                    Text(text = article.author,style = MaterialTheme.typography.caption,
                    modifier = Modifier.align(Alignment.Start).padding(start = 5.dp))
                }
                if(article.title!=null){
                    Text(text = article.title,style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(start = 0.dp))
                }
                if(article.description!=null){
                    Text(text = article.description,style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 0.dp))
                }
                if(article.content!=null){
                    Text(text = article.content,style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 0.dp))
                }
            }
        }
    }



    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun loadPicture(url: String, @DrawableRes defaultImage: Int): MutableState<Bitmap?> {

        val bitmapState: MutableState<Bitmap?> = mutableStateOf(null)

        // show default image while image loads
        Glide.with(LocalContext.current)
            .asBitmap()
            .load(defaultImage)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) { }
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    bitmapState.value = resource
                }
            })

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

        return bitmapState
    }
}