package com.ayan.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ayan.testapp.Model.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class Home : AppCompatActivity(), ChildEventListener {

    private val users by lazy{ mutableStateListOf<User>()}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataFromFirebase()
        setContent {
            createUi()
        }
    }

    @Composable
    private fun createUi() {

        LazyColumn(modifier = Modifier.padding(top = 20.dp)){
            items(items=users){user->
                Card(modifier=Modifier.background(color = Color.White).padding(10.dp).fillMaxWidth().
                        clickable {
                            startActivity(Intent(this@Home,ChatActivity::class.java).putExtra("uid",user.id).putExtra("uname",user.name))
                        }
                ) {
                    Text(text = user.name!!, style = MaterialTheme.typography.h6,modifier=Modifier.padding(6.dp))
                }
            }
        }
    }

    private fun getDataFromFirebase() {
        FirebaseDatabase.getInstance().reference.child("Users").addChildEventListener(this)
    }

    override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
        val user=snapshot.getValue(User::class.java)
        users.add(user!!)
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