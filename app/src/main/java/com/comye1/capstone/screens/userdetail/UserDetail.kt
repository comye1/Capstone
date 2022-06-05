package com.comye1.capstone.screens.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.comye1.capstone.R
import com.comye1.capstone.network.models.FollowData
import com.comye1.capstone.network.models.PlanData
import com.comye1.capstone.network.models.SignUpData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun UserDetailScreen(
    userId: Int,
    toBack: () -> Unit,
    toGoal: (Int) -> Unit,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    /*
     앱바 등을 어떻게 할지가 고민이다.
     */
    val user = produceState(
        initialValue = SignUpData(-1, "", "", "")
    ) {
        value = viewModel.getUserInfo(userId)
    }.value

    val followerList = produceState<List<FollowData>>(initialValue = listOf()) {
        value = viewModel.getFollowerList(userId)
    }.value

    val followingList = produceState<List<FollowData>>(initialValue = listOf()) {
        value = viewModel.getFollowingList(userId)
    }.value


    val planList = produceState<List<PlanData>>(initialValue = listOf()) {
        value = viewModel.getUserPlans(userId)
    }.value


    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = user.nickname) },
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
            UserProfile(
                isUserOwn = user == viewModel.thisUser,
                userName = "사용자이름",
                isFollowing = followingList.any { it.User == viewModel.thisUser },
                followerNum = followerList.size,
                followingNum = followingList.size,
                follow = { viewModel.followUser(userId) },
                unFollow = { viewModel.unfollowUser(userId) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            UserDetailPager(userPlanList = planList, toGoalDetail = toGoal)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun UserDetailPager(userPlanList: List<PlanData>, toGoalDetail: (Int) -> Unit) {

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
                LazyColumn(Modifier.fillMaxSize()) {
                    repeat(30) {
                        item {
                            GoalListItem()
                            Divider()
                        }
                    }
                }
            }
            1 -> {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(userPlanList, key = { item -> item.id }) {
                        FeedListItem(
                            item = it,
                            toGoalDetail = {
                                toGoalDetail(it.goal_id)
                            }
                        )
                        Divider()
                    }
                }
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
fun FeedListItem(item: PlanData, toGoalDetail: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "goal title",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = toGoalDetail)
            )
            Text(
                text = item.plan_title,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.clickable {
//                    onPlanTitleClick()
                }
            )
            Text(text = item.content)
            Text(text = item.createdAt)

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
    isUserOwn: Boolean,
    profileImage: Painter = painterResource(id = R.drawable.sample),
    userName: String,
    followerNum: Int,
    followingNum: Int,
    isFollowing: Boolean,
    follow: () -> Unit,
    unFollow: () -> Unit,
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
                    .weight(1f)
                    .aspectRatio(1f)
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
                NumberTextColumn(number = followerNum, text = "팔로워") {
                    // modal dialog
                }
//                Spacer(modifier = Modifier.width(32.dp))
                NumberTextColumn(number = followingNum, text = "팔로잉") {
                    // modal dialog
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (!isUserOwn) {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = if (isFollowing) unFollow else follow,
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = if (isFollowing) "언팔로우" else "팔로우") // or 팔로잉
                }
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