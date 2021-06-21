package com.ayan.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        if(FirebaseAuth.getInstance().currentUser!=null){
            startActivity(Intent(this,Home::class.java))
        }
        setContent {
            createUi()
        }
    }

    private lateinit var email: MutableState<String>
    private lateinit var password: MutableState<String>

    @Composable
    private fun createUi() {
        password = remember {
            mutableStateOf("")
        }
        email = remember { mutableStateOf("") }
        Column(//modifier=Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 50.dp),
                text = "log In",
                style = MaterialTheme.typography.h3,
                color = Color.White
            )
            Card(
                modifier = Modifier.padding(top = 100.dp, start = 25.dp, end = 25.dp),
                backgroundColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(Modifier.background(color = Color.White)) {
                    TextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Email") },
                        modifier = Modifier
                            .padding(top = 50.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth()
                    )
                    TextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Password") },
                        modifier = Modifier
                            .padding(top = 50.dp, start = 25.dp, end = 25.dp)
                            .fillMaxWidth()
                    )
                    Button(
                        onClick = { login() }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                    ) {
                        Row {
                            Text(text = "Log In")
                        }
                    }
                    Text(text = "Create new Account", style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 10.dp, bottom = 10.dp)
                            .clickable(enabled = true) {

                                val intent = Intent(this@MainActivity, CreateNewAccount::class.java)
                                startActivity(intent)
                            })
                }
            }
        }
    }

    private fun login() {
        if (email.value != "" && password.value != "") {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@MainActivity, Home::class.java)
                        startActivity(intent)
                    }
                }.addOnFailureListener {
                    Log.e("LoginError",it.localizedMessage)
                }
        } else {
            Toast.makeText(this@MainActivity, "Enter all fields", Toast.LENGTH_LONG).show()
        }
    }

}


