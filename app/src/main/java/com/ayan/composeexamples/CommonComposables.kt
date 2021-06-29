package com.ayan.composeexamples

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Profile(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(50)),
            )
            Text(
                text = "Steve Carell", style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
            Text(
                text = "youtu.be/fiow565d", style = MaterialTheme.typography.subtitle2.copy(
                    color = Color.Blue
                )
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "0", style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Posts", style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "155", style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Followers", style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "200", style = MaterialTheme.typography.h6.copy(
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Following", style = MaterialTheme.typography.body2.copy(
                    color = MaterialTheme.colors.onPrimary
                )
            )
        }

    }
    Button(onClick = {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(message = "Edit Profile Clicked")
        }
    },
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth()
            .border(1.dp, color = MaterialTheme.colors.onPrimary)) {
        Text(text = "Edit Profile",color = MaterialTheme.colors.onPrimary,fontWeight = FontWeight.Bold)
    }
    Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
        Text(text = "Story Highlights",style = MaterialTheme.typography.body2.
        copy(
            color=MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold
        ),modifier = Modifier.padding(top = 5.dp))
        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null,tint =
        MaterialTheme.colors.onPrimary)
    }
    Text(text = "Story Highlights",style = MaterialTheme.typography.body2.
    copy(
        color=MaterialTheme.colors.onPrimary
    ),modifier = Modifier.padding(top = 5.dp,start = 20.dp).fillMaxWidth(),
    textAlign = TextAlign.Start)

}

