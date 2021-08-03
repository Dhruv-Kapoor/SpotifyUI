package com.example.spotifyui.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color

enum class NavItems {
    HOME, SEARCH, LIBRARY
}

val lightBlack = Color(0xFF141414)

class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainScreen()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val bottomNavState = rememberSaveable { mutableStateOf(NavItems.HOME) }

    Scaffold(
        bottomBar = { BottomNavigationContent(bottomNavState) }
    ) {
        Crossfade(targetState = bottomNavState) { type ->
            when (type.value) {
                NavItems.HOME -> HomeScreen()
                NavItems.SEARCH -> SearchScreen()
                NavItems.LIBRARY -> LibraryScreen()
            }

        }
    }

}

@Composable
fun BottomNavigationContent(
    bottomNavState: MutableState<NavItems> = rememberSaveable {
        mutableStateOf(
            NavItems.HOME
        )
    }
) {
    BottomNavigation(
        backgroundColor = lightBlack
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null,
                    tint = if (bottomNavState.value == NavItems.HOME) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }, selected = bottomNavState.value == NavItems.HOME,
            onClick = { bottomNavState.value = NavItems.HOME },
            label = {
                Text(
                    "Home", color = if (bottomNavState.value == NavItems.HOME) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = if (bottomNavState.value == NavItems.SEARCH) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            },
            selected = bottomNavState.value == NavItems.SEARCH,
            onClick = { bottomNavState.value = NavItems.SEARCH },
            label = {
                Text(
                    "Search",
                    color = if (bottomNavState.value == NavItems.HOME) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Outlined.LibraryMusic,
                    contentDescription = null,
                    tint = if (bottomNavState.value == NavItems.LIBRARY) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }, selected = bottomNavState.value == NavItems.LIBRARY,
            onClick = { bottomNavState.value = NavItems.LIBRARY },
            label = {
                Text(
                    "Library",
                    color = if (bottomNavState.value == NavItems.HOME) {
                        Color.White
                    } else {
                        Color.Gray
                    }
                )
            }
        )
    }
}