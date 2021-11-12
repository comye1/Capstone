package com.comye1.capstone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.comye1.capstone.R

enum class ExploreState {
    Main,
    Detail,
    Search
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
            ExploreMainScreen(
                toSearchScreen = { setScreenState(ExploreState.Search) },
                onPlayListClicked = { playList ->
                    setClickedPlayList(playList)
                    setScreenState(ExploreState.Detail)
                }
            )
        }
        ExploreState.Detail -> {
//            ExploreDetailScreen()
            PLDetailScreen { setScreenState(ExploreState.Main) }
        }
        ExploreState.Search -> {
            ExploreSearchScreen {
                setScreenState(ExploreState.Main)
            }
        }
    }

}

@Composable
fun ExploreSearchScreen(toMainScreen: () -> Unit) {

    val (queryString, setQueryString) = remember {
        mutableStateOf("")
    }

    val (queryResult, setQueryResult) = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                placeHolderText = "주제, 사용자, 플레이 리스트 이름 등 검색",
                queryString = queryString,
                setQueryString = setQueryString,
                onBackButtonClick = toMainScreen
            ) {
                // onSearchKey
                setQueryResult(queryString)
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = buildAnnotatedString {
                    val boldStyle = SpanStyle( // 부분적으로 적용할 Style 정의
                        color = MaterialTheme.colors.primary,
                        fontWeight = FontWeight.Bold
                    )
                    pushStyle(boldStyle)
                    append(queryResult)
                    pop()
                    append(" 검색 결과")
                }
            )
        }
    }

}


@Composable
fun ExploreMainScreen(onPlayListClicked: (PlayList) -> Unit, toSearchScreen: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explore") },
                actions = {
                    IconButton(onClick = toSearchScreen) {
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
fun SearchTopBar(
    placeHolderText: String,
    queryString: String,
    setQueryString: (String) -> Unit,
    onBackButtonClick: () -> Unit,
    onSearchKey: () -> Unit
) {
    TopAppBar(
        elevation = 0.dp,
        backgroundColor = Color.White,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = "navigate back"
                )
            }
        },
        title = {
            TextField(
                value = queryString,
                onValueChange = setQueryString,
                placeholder = {
                    Text(
                        text = placeHolderText,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent

                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { onSearchKey() }
                ),
            )
        },
        actions = {
            if (queryString.isNotBlank()) {
                IconButton(onClick = { setQueryString("") }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "delete")
                }
            }
        }
    )
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
