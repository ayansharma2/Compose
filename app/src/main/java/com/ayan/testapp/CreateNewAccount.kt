package com.ayan.testapp

import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ayan.testapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class CreateNewAccount : AppCompatActivity() {
    private val firebaseAuth:FirebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            createUi()
        }
    }

    private lateinit var name: MutableState<String>
    private lateinit var email: MutableState<String>
    private lateinit var password: MutableState<String>
    private lateinit var isProgressBarEnabled:MutableState<Boolean>



    @Composable
    private fun createUi() {
        name = remember { mutableStateOf("") }
        email = remember { mutableStateOf("") }
        password = remember { mutableStateOf("") }
        isProgressBarEnabled=remember{ mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Create New Account")
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
            },
            content = {
                Card(
                    modifier = Modifier
                        .padding(top = 100.dp, start = 15.dp, end = 15.dp)
                        .border(width = 1.dp, color = MaterialTheme.colors.primary),
                    shape = RoundedCornerShape(12.dp),
                    elevation = 12.dp
                ) {

//                    if(isProgressBarEnabled.value){
//                        CircularProgressIndicator()
//                    }
                    Column(Modifier.background(color = Color.White)) {

                        TextField(
                            value = name.value,
                            label = { Text("Name") },
                            onValueChange = { name.value = it },
                            modifier = Modifier
                                .padding(top = 50.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White
                            )

                        )
                        TextField(
                            value = email.value,
                            onValueChange = { email.value = it },
                            label = { Text("Email") },
                            modifier = Modifier
                                .padding(top = 50.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White
                            )
                        )
                        TextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = { Text("Password") },
                            modifier = Modifier
                                .padding(top = 50.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White
                            )
                        )
                        Button(
                            onClick = { createAccount() }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 25.dp, start = 15.dp, end = 15.dp, bottom = 20.dp)
                        ) {
                            Row {
                                if(isProgressBarEnabled.value){
                                    CircularProgressIndicator(color=Color.White)
                                }else{
                                    Text(text = "Log In")
                                }
                            }
                        }

                    }
                }
            }
        )
    }

    private fun createAccount() {
        isProgressBarEnabled.value=true
        if (name.value != "" && email.value != "" && password.value != "") {

            firebaseAuth.createUserWithEmailAndPassword(
                email.value, password.value
            ).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    insertIntoDatabase()
                } else {
                    isProgressBarEnabled.value=false
                    Toast.makeText(this, "Error Creating Account", Toast.LENGTH_LONG).show()
                }
            }
        }else{
            isProgressBarEnabled.value=false
            Toast.makeText(this, "Enter all the fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun insertIntoDatabase() {
        Log.i("StartedInsertionProcess","Yes")
        val id=firebaseAuth.currentUser?.uid
        Log.e("IDIS",id!!)
        val user= User(id,name.value,email.value,password.value)
        FirebaseDatabase.getInstance().reference.child("Users").child(id).setValue(user).addOnSuccessListener {
            val intent=Intent(this@CreateNewAccount,Home::class.java)
            startActivity(intent)
        }.addOnFailureListener { task->
            Log.e("ErrorIs",task.localizedMessage)
        }
    }
}