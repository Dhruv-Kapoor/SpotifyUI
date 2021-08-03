package com.example.spotifyui.ui

import android.graphics.Bitmap
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.palette.graphics.Palette
import com.example.spotifyui.data.Album
import com.example.spotifyui.data.AlbumsDataProvider

@Preview
@ExperimentalFoundationApi
@Composable
fun SearchScreen() {
    val scrollState = rememberScrollState()
    val searchItems = AlbumsDataProvider.albums

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)) {
        Spacer(modifier = Modifier.size(50.dp))
        LazyColumn(
        ) {
            item {
                SearchTitle()
                Spacer(modifier = Modifier.size(12.dp))
            }
            stickyHeader(1) {
                SearchBar()
                Spacer(modifier = Modifier.size(12.dp))
            }
            items((searchItems.size + 1) / 2) { index ->
                Row {
                    SearchGridView(modifier = Modifier.fillMaxWidth(0.5f), album = searchItems[2 * index])
                    if (2 * index + 1 < searchItems.size) {
                        SearchGridView(
                            modifier = Modifier.fillMaxWidth(),
                            album = searchItems[2*index+1]
                        )
                    }
                }
            }
            item{
                Spacer(modifier = Modifier.size(100.dp))
            }
        }
    }
}

@Composable
fun SearchTitle() {
    Text(
        text = "Search",
        color = Color.White,
        style = Typography().h4.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(12.dp)
    )
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        leadingIcon = { Icon(imageVector = Icons.Outlined.Search, contentDescription = null) },
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        label = { Text(text = "Artists, songs or podcasts", color = Color.LightGray) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = lightBlack,
            leadingIconColor = Color.White,
            textColor = Color.White,
            cursorColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

//@ExperimentalFoundationApi
//@Composable
//fun SearchGrid() {
//    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
//        items(searchItems){
//            SearchGridView(it)
//        }
//    }
//}

@Composable
fun SearchGridView(modifier:Modifier = Modifier,album: Album = AlbumsDataProvider.album) {
    val imageBitmap = ImageBitmap.imageResource(album.imageId).asAndroidBitmap()
    val dominantColor = remember(album.id) {
        imageBitmap.generateDominantColorState()
    }
    val gradientColors = remember {
        listOf(Color(dominantColor.rgb), Color(dominantColor.rgb).copy(alpha = 0.6F))
    }

    Row(
        modifier = modifier
            .padding(8.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                brush = Brush.horizontalGradient(gradientColors)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = album.song,
            style = Typography().h6.copy(fontSize = 14.sp),
            modifier = Modifier.padding(8.dp),
            color = Color.White
        )
        Image(
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Bottom)
                .graphicsLayer(rotationZ = 32F, shadowElevation = 16F, translationX = 40F)

        )
    }

}

fun Bitmap.generateDominantColorState(): Palette.Swatch = Palette.Builder(this)
    .resizeBitmapArea(0)
    .maximumColorCount(16)
    .generate()
    .swatches
    .maxByOrNull { swatch -> swatch.population }!!
