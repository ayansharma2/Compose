package com.ayan.composetest.DataPackets

data class ReturnObject(
    val status:String,
    val totalResults:Int,
    val articles:List<NewsObject>

)
