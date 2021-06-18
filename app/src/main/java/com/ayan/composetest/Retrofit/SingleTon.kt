package com.ayan.composetest.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object SingleTon{
    private const val url="https://newsapi.org"
    private val retrofit:Retrofit by lazy{
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(url)
            .build()
    }
    val newsService:APIs by lazy {
        retrofit.create(APIs::class.java)
    }
}