package com.ayan.composeexamples

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.ayan.composeexamples.Data.Lists
import com.ayan.composeexamples.ui.theme.ComposeExamplesTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeExamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting()
                }
            }
        }
    }
    @Composable
    fun Greeting() {
        Column() {
            Button(
                modifier=Modifier.padding(25.dp),onClick = {
                startActivity(Intent(this@MainActivity, ThemeActivity::class.java))

            }) {
                Text(text = "Go to Second Screen")
            }
            FlowRow(mainAxisSize = SizeMode.Expand){
                Lists.name.forEachIndexed{index,quantity->
                    Box() {
                        Text(
                            text = quantity,
                            maxLines=1,
                            color=Color.Red,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(10.dp)
                                .background(Color.Yellow, shape = RoundedCornerShape(15.dp))
                                .padding(8.dp)
                        )
                    }
                }
            }
        }


    }
}



