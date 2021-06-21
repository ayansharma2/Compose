package com.ayan.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ayan.testapp.Model.ChatMessage
import com.ayan.testapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.EmptyCoroutineContext

class ChatActivity : AppCompatActivity() {
    private val chats by lazy{ mutableStateListOf<ChatMessage>() }
    private lateinit var rId: String
    private lateinit var chatDao: ChatDao
    private lateinit var rName:String
    private lateinit var currentUserName:String
    private lateinit var message:MutableState<String>
    private lateinit var uid:String
    private lateinit var ref:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rId= intent.getStringExtra("uid").toString()
        rName=intent.getStringExtra("uname").toString()
        chatDao= MessageDatabase.getInstance(applicationContext).chatDao()
        uid= FirebaseAuth.getInstance().currentUser?.uid.toString()
        ref=FirebaseDatabase.getInstance().reference.child("Chats")
        getCurrentUserName()
        getMessageFromRoom()
        startMessageReceiver()

        setContent {
            createUi()
        }
    }

    private fun getCurrentUserName() {
        FirebaseDatabase.getInstance().reference.child("Users").child(uid).addValueEventListener(
            object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    currentUserName= snapshot.getValue(User::class.java)?.name!!
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    private fun getMessageFromRoom() {
        CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
            val oldChats=chatDao.getMessages(rId)
            oldChats.forEach {
                chats.add(it)
            }
        }
    }

    private fun startMessageReceiver() {
        FirebaseDatabase.getInstance().reference.child("Chats").child(uid).child(rId).addChildEventListener(
            object:ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    deleteChild(snapshot)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }

    private fun deleteChild(snapshot: DataSnapshot) {
        ref.child(uid).child(rId).child(snapshot.key.toString()).removeValue().addOnCompleteListener {task->
            if(task.isSuccessful){
                addToRoom(snapshot.getValue(ChatMessage::class.java)!!)
                chats.add(snapshot.getValue(ChatMessage::class.java)!!)
            }
        }
    }

    private fun addToRoom(chatMessage: ChatMessage) {
        CoroutineScope(EmptyCoroutineContext).launch(Dispatchers.IO) {
            chatDao.insertMessage(chatMessage)
        }
    }

    @Composable
    private fun createUi() {
        message=remember{
            mutableStateOf("")
        }
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = rName)
                },
                navigationIcon = {
                    IconButton(onClick = { finish() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Button"
                        )
                    }
                }, elevation = 12.dp
            )
        }) {
            Column(modifier=Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.weight(1f),verticalArrangement = Arrangement.Bottom) {
                    itemsIndexed(items=chats){index,chat->
                        Row(modifier=Modifier.padding(top=15.dp)) {
                            if(index==0){
                                Box( modifier = Modifier.size(50.dp,50.dp).padding(7.dp).clip(RoundedCornerShape(50)).
                                        background(color=MaterialTheme.colors.primary)
                                )
                                {
                                    if(chat.sender==uid){
                                        Text(text = "You",color = Color.White,modifier=Modifier.align(Alignment.Center))
                                    }else{
                                        Text(text = rId[0].toString(),color = Color.White,modifier=Modifier.align(Alignment.Center))
                                    }
                                }
                            }else{
                                if(chats[index].sender!=chats[index-1].sender){
                                    Box(modifier = Modifier.background(MaterialTheme.colors.primary)){
                                        if(chat.sender==uid){
                                            Text(text = "You",color = Color.White,modifier=Modifier.align(Alignment.Center))
                                        }else{
                                            Text(text = rId[0].toString(),color = Color.White,modifier=Modifier.align(Alignment.Center))
                                        }
                                    }
                                }
                            }
                            Card(shape = RoundedCornerShape(10),modifier = Modifier.padding(start=25.dp)) {
                                Text(text = chat.message!!,modifier=Modifier.padding(7.dp))
                            }
                        }
                    }
                }
                Row(modifier = Modifier.padding(10.dp)) {
                    TextField(value = message.value,
                        modifier=Modifier.weight(1f),
                        label = { Text(text ="Message")},
                        onValueChange = {message.value=it}
                        )
                    Image(painter = painterResource(id = R.drawable.ic_baseline_send_24), contentDescription ="Send Button",
                    modifier=Modifier.clickable {
                        sendMessage()
                    }.border(1.dp,color=MaterialTheme.colors.primary).padding(9.dp))
                }
            }
        }
    }

    private fun sendMessage() {


        val id=FirebaseDatabase.getInstance().reference.push().key
        val chat=ChatMessage(id.toString(),uid,rId,message.value)
        FirebaseDatabase.getInstance().reference.child("Chats").child(rId).child(uid).child(id!!)
            .setValue(chat).addOnCompleteListener {task->
                if(task.isSuccessful){
                    addToRoom(chat)
                }
            }
    }
}