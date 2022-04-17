package com.comye1.capstone.screens.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comye1.capstone.R

data class FeedItemDataClass(
    val userName: String,
    val date: String,
    val goal: String,
    val title: String,
    val imgId: Int? = null,
    val description: String
)

object FeedItems {
    val list = listOf(
        FeedItemDataClass(
            userName = "예원",
            date = "5분 전",
            goal = "쇼팽 친해지기",
            title = "영웅 폴로네이즈",
            description = "'영웅' 이라는 이름이 붙은 이유가 재밌다. 조성진의 연주 짱이다",
        ),
        FeedItemDataClass(
            userName = "정인",
            date = "49분 전",
            goal = "자전거 국토종주를 향해",
            title = "한강에서 10km 타기",
            description = "지난 주보다 숨이 덜 찼다. 가을 날씨 선선하다 ^^",
        ),
        FeedItemDataClass(
            userName = "희서",
            date = "2시간 전",
            goal = "고전영화 섭렵하기",
            title = "바람과 함께 사라지다",
            imgId = R.drawable.gone_with_wind,
            description = "내일은 내일의 태양이 뜬다.",
        ),
        FeedItemDataClass(
            userName = "혜성",
            date = "어제",
            goal = "토익 만점을 향해",
            title = "오답을 고른 이유 분석하기",
            description = "자꾸만 터무니 없는 실수를 한다. " +
                    "왜 오답을 고르게 되는지 되짚어 보았다.",
        ),
        FeedItemDataClass(
            userName = "혜성",
            date = "어제",
            goal = "토익 만점을 향해",
            title = "오답을 고른 이유 분석하기",
            description = "자꾸만 터무니 없는 실수를 한다. " +
                    "왜 오답을 고르게 되는지 되짚어 보았다.",
        ),
        FeedItemDataClass(
            userName = "혜성",
            date = "어제",
            goal = "토익 만점을 향해",
            title = "오답을 고른 이유 분석하기",
            description = "자꾸만 터무니 없는 실수를 한다. " +
                    "왜 오답을 고르게 되는지 되짚어 보았다.",
        ),
    )

    //    val likeList = mutableListOf(
//        Pair(0, false),
//        Pair(2, true),
//        Pair(11, true),
//        Pair(3, false),
//        Pair(1, false),
//        Pair(5, true)
//    )
    val commentList = listOf(
        2,
        0,
        5,
        1,
        6,
        3
    )
}

@ExperimentalMaterialApi
@Composable
fun FeedScreen(
    toGoalDetail: () -> Unit,
    toUserDetail: () -> Unit
) {

    val likeList = remember {
        mutableStateListOf(
            Pair(0, false),
            Pair(2, true),
            Pair(11, true),
            Pair(3, false),
            Pair(1, false),
            Pair(5, true)
        )
    }
    Column(modifier = Modifier.padding(bottom = 96.dp)) {
        TopAppBar(
            title = { Text("Feed") },
        )
        LazyColumn(
            Modifier
                .fillMaxSize()
//                .padding(paddingValues = it)
        ) {
            itemsIndexed(FeedItems.list) { index, item ->
                Divider(Modifier.height(8.dp))
                FeedItem(
                    item,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    likeCount = likeList[index].first,
                    userLike = likeList[index].second,
                    likeButtonClick = {
                        if (likeList[index].second) { // 하트 해제
                            likeList[index] =
                                Pair(likeList[index].first - 1, !likeList[index].second)
                        } else {
                            likeList[index] =
                                Pair(likeList[index].first + 1, !likeList[index].second)
                        }
                    },
                    commentCount = FeedItems.commentList[index],
                    commentButtonClick = { },
                    onGoalTitleClick = { toGoalDetail() },
                    onPlanTitleClick = { toGoalDetail() },
                    onUserNameClick = { toUserDetail() }
                )
            }
            item {
                Divider(Modifier.height(8.dp))
            }
        }

    }
}

@Composable
fun FeedItem(
    item: FeedItemDataClass,
    onGoalTitleClick: () -> Unit,
    onPlanTitleClick: () -> Unit,
    likeCount: Int,
    userLike: Boolean,
    onUserNameClick: () -> Unit,
    likeButtonClick: () -> Unit,
    commentCount: Int,
    commentButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = item.userName,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(shape = CircleShape, width = 1.dp, color = Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = item.userName, modifier = Modifier.clickable { onUserNameClick() })
//                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.goal,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onGoalTitleClick() }
                    )
                }

            }
            Row {
                Text(text = item.date, color = Color.Gray, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.clickable { onPlanTitleClick() })
        if (item.imgId != null) {
            Image(
                painter = painterResource(id = item.imgId),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = item.description, style = MaterialTheme.typography.subtitle1)
        Row {
            IconButton(onClick = { likeButtonClick() }) {
                Row {
                    if (userLike) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "like this")
                    } else {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "don't like this"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "$likeCount")
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { commentButtonClick() }) {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.Comment,
                        contentDescription = "write a comment"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "$commentCount")
                }
            }
        }
    }
}