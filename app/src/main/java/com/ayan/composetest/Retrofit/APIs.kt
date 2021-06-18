package com.ayan.composetest.Retrofit

import com.ayan.composetest.DataPackets.ReturnObject
import retrofit2.Call
import retrofit2.http.GET

interface APIs {

    @GET("/v2/top-headlines?country=in&apiKey=087582ff2a3344aab9ab089f7ca44c48")
    fun getNews(): Call<ReturnObject>
}