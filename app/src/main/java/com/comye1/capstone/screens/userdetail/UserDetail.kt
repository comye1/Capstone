package com.comye1.capstone.screens.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comye1.capstone.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun UserDetailScreen(toBack: () -> Unit, toGoal: () -> Unit) {
    /*
     앱바 등을 어떻게 할지가 고민이다.

     */

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "{사용자 이름}") },
            navigationIcon = {
                IconButton(onClick = { toBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "navigate back"
                    )
                }
            }
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 16.dp, bottom = 96.dp, start = 16.dp, end = 16.dp)
        ) {
            UserProfile(userName = "사용자이름")
            Spacer(modifier = Modifier.height(16.dp))
            UserDetailPager()
        }
    }
}

@ExperimentalPagerApi
@Composable
fun UserDetailPager() {

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(
                    pagerState,
                    tabPositions
                )
            )
        },
        backgroundColor = Color.Transparent
    ) {
        Tab(
            selected = pagerState.currentPage == 0,
            onClick = { scope.launch { pagerState.scrollToPage(0) } },
            text = { Text(text = "Goals") },
            icon = {},
            selectedContentColor = MaterialTheme.colors.primary,
            unselectedContentColor = Color.LightGray,
        )

        Tab(
            selected = pagerState.currentPage == 1,
            onClick = { scope.launch { pagerState.scrollToPage(1) } },
            text = { Text(text = "Feed") },
            icon = {},
            selectedContentColor = MaterialTheme.colors.primary,
            unselectedContentColor = Color.LightGray,
        )
    }
    HorizontalPager(
        count = 2,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                GoalList() // goal_detail 이동
            }
            1 -> {
                FeedList()
            }
        }
    }
}

@Composable
fun GoalList() {
    LazyColumn(Modifier.fillMaxSize()) {
        repeat(30) {
            item {
                GoalListItem()
                Divider()
            }
        }
    }
}

@Composable
fun GoalListItem() {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.books),
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            contentDescription = "image"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "goal title", fontSize = 18.sp)
            Text(text = "작성자")
        }
    }
}

@Composable
fun FeedList() {
    LazyColumn(Modifier.fillMaxSize()) {
        repeat(30) {
            item {
                FeedListItem()
                Divider()
            }
        }
    }
}

@Composable
fun FeedListItem() {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "goal",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    //onGoalTitleClick()
                }
            )
            Text(
                text = "title",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.clickable {
//                    onPlanTitleClick()
                }
            )
            Text(text = "내용 내용 내용 내용입니다 아무거나 아무거나 적었습니다.")

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    //likeButtonClick()
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (true/*userLike*/) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "like this",
                                modifier = Modifier.size(20.dp)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "don't like this",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "3 ") //likeCount
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = { }) { //commentButtonClick()
                    Row {
                        Icon(
                            imageVector = Icons.Outlined.Comment,
                            contentDescription = "write a comment",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "5") //$commentCount
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "2022.4.11")
            }

        }
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            painter = painterResource(id = R.drawable.books),
            modifier = Modifier
                .height(64.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            contentDescription = "image"
        )
    }
}

@Composable
fun UserProfile(
    profileImage: Painter = painterResource(id = R.drawable.sample),
    userName: String,
    logIn: () -> Unit = {},
    logOut: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Bottom)
    ) {
        Column(
            Modifier
                .size(128.dp)
        ) {
            Image(
                painter = profileImage,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape),
                contentDescription = "profile image"
            )

            Text(text = userName, style = MaterialTheme.typography.h5)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .height(128.dp)
                .weight(1f)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                NumberTextColumn(number = 123, text = "팔로워") {
                    // modal dialog
                }
//                Spacer(modifier = Modifier.width(32.dp))
                NumberTextColumn(number = 1452, text = "팔로잉") {
                    // modal dialog
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {},
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White
                )
            ) {
                Text(text = "팔로우") // or 팔로잉
            }
        }
    }
}

@Composable
fun NumberTextColumn(number: Int, text: String, onClick: () -> Unit) {
    Column(
        Modifier.clickable { onClick() },
        horizontalAlignment = CenterHorizontally
    ) {
        Text(text = "%,d".format(number))
        Text(text = text)
    }
}