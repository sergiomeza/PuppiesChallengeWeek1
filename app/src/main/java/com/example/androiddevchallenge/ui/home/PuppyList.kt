package com.example.androiddevchallenge.ui.home

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.model.Filter
import com.example.androiddevchallenge.data.model.Puppy
import com.example.androiddevchallenge.data.model.filters
import com.example.androiddevchallenge.data.viewmodel.FirestoreViewModel
import com.example.androiddevchallenge.ui.theme.customFontFamily
import com.example.androiddevchallenge.ui.theme.purple200
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.statusBarsHeight
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.util.*

@ExperimentalStdlibApi
@Composable
fun HomeScreen(navController: NavController,
               firestoreViewModel: FirestoreViewModel) {
    firestoreViewModel.getPuppies()
    val items: List<Puppy> by firestoreViewModel.puppyItems.observeAsState(listOf())
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { PuppyListTopLayout(navController = navController) },
    ) {
        MainContent(puppies = items, navController = navController)
    }
}

@ExperimentalStdlibApi
@Composable
fun MainContent(puppies: List<Puppy>, navController: NavController) {
    Box(modifier = Modifier.padding(bottom = 70.dp)) {
        val listState = rememberLazyListState()
        LazyColumn(content = {
            item {
                FilterBar(filters)
            }
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
fun FilterBar(filters: List<Filter>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        modifier = Modifier.heightIn(min = 56.dp)
    ) {
        items(filters) { filter ->
            FilterChip(filter)
        }
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
                 navController: NavController,
                 scroll: Int = 0) {
    var paddingHorizontal = 16
    var paddingVertical = 16
    var aligment = Alignment.BottomStart
    Box(modifier = modifier) {
        CoilImage(
            data = puppy.image ?: "",
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
        if(isDetail && scroll < 130){
            paddingHorizontal = 45
            paddingVertical = 4
            aligment = Alignment.Center
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
            Column(modifier = Modifier
                .padding(vertical = paddingVertical.dp,
                    horizontal = paddingHorizontal.dp)
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
                        text = puppy.location ?: "",
                        fontWeight = FontWeight.Medium,
                        fontFamily = customFontFamily,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun FilterChip(
    filter: Filter,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small
) {
    val (selected, setSelected) = filter.enabled
    val border = Modifier.fadeInDiagonalGradientBorder(
        showBorder = false,
        colors = listOf(Color.Gray, Color.DarkGray),
        shape = shape
    )
    val backgroundColor by animateColorAsState(
        if (selected) purple200 else Color.White
    )
    val textColor by animateColorAsState(
        if (selected) purple200 else Color.Gray
    )

    Surface(
        modifier = modifier
            .background(
                color = backgroundColor
            )
            .height(28.dp)
            .then(border),
        contentColor = textColor,
        shape = shape,
        elevation = 2.dp
    ) {
        Box (
            modifier = Modifier.toggleable(
                value = selected,
                onValueChange = setSelected
            )
        ){
            Text(
                text = filter.name,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 6.dp
                )
            )
        }
    }
}

fun Modifier.fadeInDiagonalGradientBorder(
    showBorder: Boolean,
    colors: List<Color>,
    borderSize: Dp = 2.dp,
    shape: Shape
) = composed {
    val animatedColors = List(colors.size) { i ->
        animateColorAsState(if (showBorder) colors[i] else colors[i].copy(alpha = 0f)).value
    }
    diagonalGradientBorder(
        colors = animatedColors,
        borderSize = borderSize,
        shape = shape
    )
}

fun Modifier.diagonalGradientBorder(
    colors: List<Color>,
    borderSize: Dp = 2.dp,
    shape: Shape
) = border(
    width = borderSize,
    brush = Brush.linearGradient(colors),
    shape = shape
)