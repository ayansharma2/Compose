package com.ayan.composeexamples

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ayan.composeexamples.Data.Lists
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState


@ExperimentalPagerApi
@Composable
fun Home(LocalContext: ProvidableCompositionLocal<Context>) {
    Text(text = "Today's Dishes", modifier = Modifier.fillMaxWidth().padding(start = 40.dp,top = 20.dp), textAlign = TextAlign.Start,
    style = MaterialTheme.typography.body2.copy(
        color = MaterialTheme.colors.onPrimary,
        fontWeight = FontWeight.Bold
    ))
    val pagerState1= rememberPagerState(pageCount = Lists.urlLists.size,
    initialOffscreenLimit = 2)
    HorizontalPager(state = pagerState1,modifier = Modifier.fillMaxWidth(1f).padding(top = 20.dp)) {
        page->
            ImageLoader(LocalContext,url = Lists.urlLists[page])
    }

    Text(text = "Favourite Pets Today", modifier = Modifier.fillMaxWidth().padding(start = 40.dp,top = 20.dp), textAlign = TextAlign.Start,
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.onPrimary,
            fontWeight = FontWeight.Bold
        ))

    val pagerState2= rememberPagerState(pageCount = Lists.petLists.size,
        initialOffscreenLimit = 2)
    HorizontalPager(state = pagerState2,modifier = Modifier.fillMaxWidth(1f).padding(top = 20.dp)) {
            page->
        ImageLoader(LocalContext = LocalContext,url = Lists.petLists[page])
    }

}