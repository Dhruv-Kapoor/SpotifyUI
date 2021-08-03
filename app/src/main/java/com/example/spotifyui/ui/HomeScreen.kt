package com.example.spotifyui.ui

import android.content.Intent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spotifyui.data.Album
import com.example.spotifyui.data.AlbumsDataProvider

@Preview
@Composable
fun HomeScreen() {
    val scrollState: ScrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(
                brush = Brush.radialGradient(
                    center = Offset(0F, -700F - scrollState.value),
                    radius = 1500.dp.value,
                    colors = listOf(Color.Red, Color.Black),
                    tileMode = TileMode.Clamp
                )
            )
    ) {
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 50.dp, end = 12.dp)
                .alpha(animateFloatAsState(targetValue = 1F - scrollState.value / 200F).value),
            imageVector = Icons.Outlined.Settings,
            contentDescription = null,
            tint = Color.White
        )

        Column(
            modifier = Modifier.verticalScroll(state = scrollState)
        ) {
            Spacer(Modifier.height(50.dp))
            val categories = AlbumsDataProvider.listOfSpotifyHomeLanes
            categories.forEach {
                Title(it)
                Lane()
            }
            Spacer(modifier = Modifier.height(100.dp))
        }

        PlayerBar(modifier = Modifier.align(Alignment.BottomCenter))
    }

}

@Composable
fun PlayerBar(modifier: Modifier = Modifier, album: Album = AlbumsDataProvider.album) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 57.dp)
            .background(color = lightBlack),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            modifier = Modifier.size(65.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = album.song,
            color = Color.White,
            modifier = Modifier
                .padding(8.dp)
                .weight(1F)
        )
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.padding(8.dp)

        )
    }
}

@Composable
fun Title(title: String = "Hello") {
    Text(
        text = title,
        style = Typography().h5.copy(fontWeight = FontWeight.ExtraBold),
        modifier = Modifier.padding(top = 24.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
        color = Color.White
    )
}

@Composable
fun Lane() {
    val t = AlbumsDataProvider.albums
    val albums = ArrayList(t)
    albums.shuffle()
    LazyRow {
        items(albums) {
            AlbumView(it)
        }
    }
}

@Composable
fun AlbumView(album: Album = AlbumsDataProvider.album) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .clickable {
                context.startActivity(
                    Intent(
                        context,
                        PlayerActivity::class.java
                    ).putExtra(
                        PlayerActivity.ALBUM_KEY,
                        album
                    )
                )
            }
    ) {
        Image(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            painter = painterResource(id = album.imageId),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = "${album.song}: ${album.descriptions}",
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.White,
            style = Typography().body2
        )
    }
}