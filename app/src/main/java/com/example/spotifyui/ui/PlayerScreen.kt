package com.example.spotifyui.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.example.spotifyui.data.Album
import com.example.spotifyui.data.AlbumsDataProvider

@Preview
@Composable
fun PlayerScreen(album: Album = AlbumsDataProvider.album) {

    val scrollState = rememberScrollState()

    val image = ImageBitmap.imageResource(id = album.imageId).asAndroidBitmap()
    val dominantColor = image.generateDominantColorState()
    val gradientColors = listOf(
        Color(dominantColor.rgb).copy(
            alpha = (1f - scrollState.value / 1000F).coerceIn(0f, 1f)
        ), Color.Black
    )

    Box(
        modifier = Modifier
            .background(
                brush = Brush.verticalGradient(
                    gradientColors,
                    startY = 0F,
                    endY = 1080.dp.value,
                    tileMode = TileMode.Clamp
                )
            )
    ) {
        SongDetail(scrollState = scrollState, album = album)
        BottomList(scrollState = scrollState)
        Toolbar(scrollState = scrollState, name = album.song)
    }
}

@Composable
fun BottomList(scrollState: ScrollState) {
    val albums = AlbumsDataProvider.albums

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.size(480.dp))
        albums.forEach {
            SongListItemView(album = it)
        }
        Spacer(modifier = Modifier.size(50.dp))
    }
}

@Composable
fun SongListItemView(album: Album = AlbumsDataProvider.album) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(56.dp)
        )

        Column(
            modifier = Modifier
                .padding(4.dp)
                .weight(1F)
        ) {
            Text(
                text = album.song,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography().h6.copy(fontSize = 16.sp)
            )
            Text(
                text = "${album.artist}, ${album.descriptions}",
                color = Color.LightGray,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = Typography().subtitle2
            )
        }

        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
            tint = Color.LightGray,
            modifier = Modifier.padding(4.dp)
        )

    }
}

@Composable
fun SongDetail(scrollState: ScrollState, album: Album) {

    val alpha = (1f-scrollState.value/1000F).coerceIn(0f,1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(100.dp))

        val imageSize = max(10.dp, 250.dp - (scrollState.value / 5F).dp)

        Image(
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize)
        )

        Text(
            text = album.song,
            color = Color.White,
            style = Typography().h5.copy(fontWeight = FontWeight.ExtraBold),
            modifier = Modifier
                .padding(8.dp)
        )

    }
}

@Composable
fun Toolbar(scrollState: ScrollState, name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (scrollState.value.toFloat() < 1080.dp.value) Color.Transparent
                else Color.Black
            )
            .padding(start = 8.dp, end = 8.dp, top = 30.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            tint = Color.White
        )
        Text(
            text = name,
            color = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .alpha(((scrollState.value + 0.001f) / 1000).coerceIn(0f, 1f))
        )
        Icon(
            imageVector = Icons.Outlined.MoreVert,
            contentDescription = null,
            tint = Color.White
        )
    }
}