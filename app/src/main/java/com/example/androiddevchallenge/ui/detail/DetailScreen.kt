package com.example.androiddevchallenge.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.model.Filter
import com.example.androiddevchallenge.data.model.Puppy
import com.example.androiddevchallenge.ui.home.FilterChip
import com.example.androiddevchallenge.ui.home.PuppyHeadImg
import com.example.androiddevchallenge.ui.theme.customFontFamily
import com.example.androiddevchallenge.ui.theme.purple500
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalStdlibApi
@Composable
fun DetailScreen(navController: NavController, puppy: Puppy) {
    val scroll = rememberScrollState(0)
    var height = 0
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Surface(Modifier
            .navigationBarsPadding(),
            color = MaterialTheme.colors.background) {
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Box {
                    Column {
                        if ( scroll.value < 265 ) {
                            height = (350 - scroll.value)
                        }
                        PuppyHeadImg(puppy = puppy,
                            modifier = Modifier
                                .height(height.dp)
                                .statusBarsPadding(),
                            isDetail = true,
                            navController = navController,
                            scroll = height
                        )
                        Column(modifier = Modifier.verticalScroll(scroll)) {
                            Box(
                                modifier = Modifier.padding(16.dp)
                            ){
                                Column {
                                    Text(
                                        text = "Description",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = customFontFamily,
                                            color = Color.DarkGray,
                                            fontSize = 20.sp
                                        )
                                    )
                                    Text(
                                        text = puppy.description ?: "",
                                        style = TextStyle(
                                            fontWeight = FontWeight.Normal,
                                            fontFamily = customFontFamily,
                                            color = Color.Gray,
                                            fontSize = 16.sp
                                        ),
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )
                                }
                            }
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Puppy Tags",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = customFontFamily,
                                    color = Color.DarkGray,
                                    fontSize = 20.sp
                                )
                            )
                            LazyRow(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 80.dp),
                                modifier = Modifier
                                    .heightIn(min = 56.dp)
                            ) {
                                items(puppy.tags) { tag ->
                                    val filterTag = Filter(name = tag)
                                    FilterChip(filterTag)
                                }
                            }
                        }
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp),
                        onClick = {},
                        backgroundColor = purple500
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_message_24),
                            tint = Color.White,
                            contentDescription = "Message Icon",
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}