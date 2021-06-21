package com.ayan.testapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayan.testapp.Model.ChatMessage


@Dao
interface ChatDao {
    @Query("Select * from ChatMessage where sender LIKE :uid OR receiver LIKE :uid")
    suspend fun getMessages(uid:String):List<ChatMessage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(chatMessage: ChatMessage)
}