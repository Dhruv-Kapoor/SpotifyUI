package com.example.spotifyui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import com.example.spotifyui.data.Album

class PlayerActivity : AppCompatActivity() {

    companion object{
        const val ALBUM_KEY = "album"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val album = intent.getSerializableExtra(ALBUM_KEY) as Album

        setContent {
            PlayerScreen(album = album)
        }
    }
}