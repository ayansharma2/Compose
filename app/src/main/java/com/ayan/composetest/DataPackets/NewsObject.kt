package com.ayan.composetest.DataPackets

data class NewsObject(
    val source:SourceObject,
    val author:String,
    val title: String,
    val description:String,
    val url:String,
    val urlToImage:String,
    val publishedAt:String,
    val content:String,
)
