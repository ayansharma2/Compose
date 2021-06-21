package com.ayan.testapp

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ayan.testapp.Model.ChatMessage


@Database(entities = [ChatMessage::class],version = 1)
abstract class MessageDatabase:RoomDatabase() {
    abstract fun chatDao():ChatDao

    companion object{
        private lateinit var instance:MessageDatabase
        fun getInstance(context: Context):MessageDatabase{
            if(!::instance.isInitialized){
                instance=Room.databaseBuilder(context,MessageDatabase::class.java,"Chat Database")
                    .build()
            }
            return instance
        }
    }


}