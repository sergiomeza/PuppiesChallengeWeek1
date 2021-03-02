package com.example.androiddevchallenge.ui.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.transform.CircleCropTransformation
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.ui.home.PuppyHeadImg
import com.example.androiddevchallenge.ui.theme.customFontFamily
import com.example.androiddevchallenge.ui.theme.purple500
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.util.*

@ExperimentalStdlibApi
@Composable
fun DetailScreen(navController: NavController, puppy: Puppy) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
    ) {
        Surface(Modifier
            .navigationBarsPadding(),
            color = MaterialTheme.colors.background) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Box(
                    Modifier
                        .weight(0.55f)
                        .fillMaxWidth()
                ) {
                    Column {
                        PuppyHeadImg(puppy = puppy,
                            modifier = Modifier
                                .height(350.dp)
                                .statusBarsPadding(),
                            isDetail = true,
                            navController = navController
                        )
                        Box(modifier = Modifier.padding(16.dp)){
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
                                    text = puppy.description,
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
                    }
                    FloatingActionButton(
                        modifier = Modifier.align(Alignment.BottomEnd)
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