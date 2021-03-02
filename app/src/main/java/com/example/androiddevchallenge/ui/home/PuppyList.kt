package com.example.androiddevchallenge.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Puppy
import com.example.androiddevchallenge.data.PuppyTags
import com.example.androiddevchallenge.ui.theme.customFontFamily
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.util.*


@ExperimentalStdlibApi
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { PuppyListTopLayout(navController = navController) },
    ) {
        MainContent(puppies = puppyList, navController = navController)
    }
}

@ExperimentalStdlibApi
@Composable
fun MainContent(puppies: List<Puppy>, navController: NavController) {
    Box(modifier = Modifier.padding(bottom = 70.dp)) {
        val listState = rememberLazyListState()
        LazyColumn(content = {
            items(puppies){ puppy ->
                PuppyRow(
                    puppy = puppy,
                    modifier = Modifier.fillMaxWidth(),
                    navController = navController)
            }
        }, state = listState)
    }
}

@Composable
fun PuppyListTopLayout(title: String = "Puppies",
                       showBackButton:Boolean = false,
                       navController: NavController) {
    Surface(
        elevation = 2.dp, shape = RoundedCornerShape(bottomStart = 6.dp, bottomEnd = 6.dp),
        modifier = Modifier
            .statusBarsPadding()
            .height(56.dp)
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(height = 56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Box(contentAlignment = Alignment.CenterStart) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (showBackButton) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_back_24),
                                contentDescription = "Back Button",
                                modifier = Modifier
                                    .size(34.dp)
                                    .clickable(onClick = {
                                        navController.popBackStack()
                                    })
                                    .padding(horizontal = 6.dp)
                            )
                        }
                        Text(
                            text = title,
                            style = TextStyle(
                                fontFamily = customFontFamily,
                                color = Color.DarkGray,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalStdlibApi
@Composable
fun PuppyRow(puppy: Puppy, modifier: Modifier, navController: NavController) {
    Box(
        modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .height(200.dp)
            .clickable(onClick = { navController.navigate(route = "puppyScreen/${puppy.id}") })
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = 4.dp,
            modifier = Modifier
                .matchParentSize()
        ) {
            PuppyHeadImg(puppy = puppy, modifier = Modifier,
                navController = navController)
        }
    }
}

@ExperimentalStdlibApi
@Composable
fun PuppyHeadImg(puppy: Puppy,
                 modifier: Modifier,
                 isDetail: Boolean = false,
                 navController: NavController) {
    Box(modifier = modifier) {
        CoilImage(
            data = puppy.image,
            contentDescription = puppy.description,
            contentScale = ContentScale.Crop,
        )
        if (isDetail){
            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.DarkGray,
                                Color.Transparent
                            )
                        )
                    )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                    contentDescription = "Back Button",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                        .clickable(onClick = {
                            navController.popBackStack()
                        }),
                    tint = Color.White
                )
            }
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
        ) {
            Column(modifier =
            Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "${puppy.name}, ".uppercase(Locale.ROOT),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFontFamily,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "${puppy.age} years",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Thin,
                            fontFamily = customFontFamily,
                        )
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_location_on_24),
                        tint = Color.White,
                        contentDescription = "Location Icon",
                        modifier = Modifier
                            .size(16.dp)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Text(
                        text = puppy.location,
                        fontWeight = FontWeight.Medium,
                        fontFamily = customFontFamily,
                        color = Color.White
                    )
                }
            }
        }
    }
}

var puppyList = listOf(
    Puppy(
        id = 0, age = "3",
        description = "\"Lorem ipsum dolor sit amet, consectetur adiplaboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"",
        image = "https://dogtime.com/assets/uploads/2011/03/puppy-development.jpg",
        male = true,
        name = "Marcos pomo",
        location = "Cali, Colombia"
    ),
    Puppy(
        id = 1, age = "2",
        description = "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam ra dolor sit amet, con",
        image = "https://images2.minutemediacdn.com/image/upload/c_fill,g_auto,h_1248,w_2220/v1584388937/shape/mentalfloss/536413-gettyimages-1077470274.jpg?itok=NoDcW5uz",
        male = false,
        name = "Puppy Mateo",
        location = "Bucaramanga, Colombia",
        tags = listOf(PuppyTags(name = "Healthy"), PuppyTags(name = "Fun"),
            PuppyTags(name = "Sweet"), PuppyTags(name = "Love"), PuppyTags(name = "Yep"),
            PuppyTags(name = "Sisa")
        ),
    ),
    Puppy(
        id = 2, age = "1.5",
        description = "But I must explain to you how all this mistaken idea of denouncing but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?",
        image = "https://static.scientificamerican.com/sciam/cache/file/D059BC4A-CCF3-4495-849ABBAFAED10456_source.jpg?w=590&h=800&526ED1E1-34FF-4472-B348B8B4769AB2A1",
        male = true,
        name = "Rocky balboa",
        location = "Bogotá, Colombia"
    ),
    Puppy(
        id = 2, age = "2.5",
        description = "But I must explain to you how all this mistaken idea of denouncing but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful. Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but because occasionally circumstances occur in which toil and pain can procure him some great pleasure. To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it? But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?",
        image = "https://www.nawpic.com/media/2020/puppy-nawpic-2.jpg",
        male = false,
        name = "RMichi michi",
        location = "New York, USA"
    )
)