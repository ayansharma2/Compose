package com.ayan.composetest

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayan.composetest.DataPackets.ReturnObject
import com.ayan.composetest.Retrofit.SingleTon
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import perfetto.protos.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.EmptyCoroutineContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson


class NewsList : Fragment(), Callback<ReturnObject> {

    val returnObject:MutableState<ReturnObject?> = mutableStateOf(null)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title="News List"
        val view= ComposeView(requireContext())
        view.setContent {
            setUi()
        }

        val request=SingleTon.newsService.getNews()
        request.enqueue(this)

        return view;
    }

    @ExperimentalMaterialApi
    @Composable
    private fun setUi() {
        if(returnObject.value==null){
            CircularProgressIndicator()
        }else{
            LazyColumn{
                items(returnObject.value!!.articles.size){it->
                    val article=returnObject.value!!.articles[it]
                    Card(modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = Color.White,
                    onClick = {
                        val bundle=Bundle()
                        val gson=Gson()
                        val articleString=gson.toJson(article)
                        bundle.putSerializable("article",articleString)
                        findNavController().navigate(
                            R.id.action_newsList_to_newsDetail,
                            bundle
                        )
                    }) {
                       Column(
                           verticalArrangement = Arrangement.Center,
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {

                           if(article.urlToImage!=null){
                               val image=loadPicture(url = article.urlToImage, defaultImage =R.drawable.ic_launcher_background ).value
                               image?.let {image->
                                   Image(modifier = Modifier.padding(10.dp),bitmap = image.asImageBitmap(), contentDescription ="News Image" )
                               }
                           }
                           if(article.author!=null){
                               Text(modifier = Modifier.align(Alignment.Start).padding(start = 12.dp),text = article.author, style=MaterialTheme.typography.caption)
                           }
                           if(article.title!=null){
                               Text(modifier = Modifier.padding(12.dp),text = article.title,style = MaterialTheme.typography.h6)
                           }
                       }

                    }
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

    override fun onResponse(call: Call<ReturnObject>, response: Response<ReturnObject>) {
        returnObject.value=response.body()
        Log.i("Datais", response.body().toString())
    }

    override fun onFailure(call: Call<ReturnObject>, t: Throwable) {
            Log.i("Erroris",t.localizedMessage)
    }
}