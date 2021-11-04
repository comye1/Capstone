package com.comye1.capstone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.comye1.capstone.R

enum class ExploreState {
    Main,
    Detail
}

@Composable
fun ExploreScreen() {

    val (screenState, setScreenState) = remember {
        mutableStateOf(ExploreState.Main)
    }

    val (clickedPlayList, setClickedPlayList) = remember {
        mutableStateOf(PlayList(null, "", ""))
    }

    when (screenState) {
        ExploreState.Main -> {
            ExploreMainScreen { playList ->
                setClickedPlayList(playList)
                setScreenState(ExploreState.Detail)
            }
        }
        ExploreState.Detail -> {
//            ExploreDetailScreen()
            PLDetailScreen{setScreenState(ExploreState.Main)}
        }
    }

}


@Composable
fun ExploreMainScreen(onPlayListClicked: (PlayList) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
                    }
                }
            )
        }
    ) {
        LazyColumn() {
            item {
                PLList(
                    title = "인기 플레이 리스트",
                    list = listOf(
                        PlayList(
                            painterResource(id = R.drawable.toeic),
                            title = "토익 900 달성",
                            author = "김예원"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                    ),
                    onPlayListClicked
                )

            }
            item {
                PLList(
                    title = "관심 주제 : 개발",
                    list = listOf(
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                    ),
                    onPlayListClicked
                )
            }
            item {
                PLList(
                    title = "관심 주제 : 음악",
                    list = listOf(
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                        PlayList(
                            painterResource(id = R.drawable.sample),
                            title = "제목",
                            author = "작성자"
                        ),
                    ),
                    onPlayListClicked
                )
            }
            item {
                Column(Modifier.height(50.dp)) {

                }
            }
        }
    }
}

@Composable
fun PLList(
    title: String,
    list: List<PlayList>,
    onItemClick: (PlayList) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            for (item in list) {
                item {
                    PLItem(playList = item) { onItemClick(item) }
                }
            }
        }
    }
}

//@Preview
@Composable
fun PLItemPreview() {
    PLItem(
        playList = PlayList(
            painterResource(id = R.drawable.toeic),
            title = "토익 900 달성",
            author = "김예원"
        ),
        onItemClick = {}
    )
}

@Composable
fun PLItem(playList: PlayList, onItemClick: () -> Unit) {
    Column(
        Modifier
            .width(150.dp)
            .padding(bottom = 8.dp, end = 16.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onItemClick)
    ) {
        Image(
            painter = playList.img ?: painterResource(id = R.drawable.sample),
            contentDescription = "playlist image",
            modifier = Modifier
                .width(150.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = playList.title, fontWeight = FontWeight.Bold)
        Text(text = playList.author)
    }
}

data class PlayList(
    val img: Painter?,
    val title: String,
    val author: String,
)
