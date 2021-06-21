package com.ayan.testapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity()
data class ChatMessage(
    @PrimaryKey val id:String,
    val sender:String?=null,
    val receiver:String?=null,
    val message:String?=null,
    val time:String?=null
){
    constructor() : this("","")
}
