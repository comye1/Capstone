package com.comye1.capstone.screens.goaldetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comye1.capstone.R

data class PlayList(
    val imgId: Int? = R.drawable.sample,
    val title: String,
    val author: String,
    val description: String,
    val playItems: List<PlayItem>
)

data class PlayItem(
    val title: String,
    val timeInMin: Int
)

@Preview(showBackground = true)
@Composable
fun PLDetailPreview() {
    GoalDetailScreen(playList = samplePlayList) {

    }
}

val samplePlayList = PlayList(
    imgId = R.drawable.toeic,
    title = "토익 900 달성",
    author = "김예원",
    playItems = listOf(
        PlayItem(
            title = "PART5 2회분 풀기, 놓치고 있던 문법 정리하기", 100
        ),
        PlayItem(
            title = "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기", 120
        ),
        PlayItem(
            "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기", 120
        ),
        PlayItem(
            "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복", 100
        ),
        PlayItem(
            "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복", 100
        ),
        PlayItem(
            "PART3 2회분 풀기, 필요한 정보 파악 & 메모하기", 110
        ),
        PlayItem(
            "PART3 2회분 풀기, 필요한 정보 파악 & 메모하기", 110
        )

    ),
    description = "영어의 기반이 생겼다고 느낄 때 기출문제집 공부를 시작했습니다. " +
            "풀이법은 다른 사람이 가르쳐주는 것보다 본인에게 맞는 방법을 찾는 것이 " +
            "중요합니다."
)

@Composable
fun GoalDetailScreen(playList: PlayList = samplePlayList, toBack: () -> Unit) {
    Column(modifier = Modifier.padding(bottom = 96.dp)) {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = toBack) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Share, contentDescription = "share")
                }
            })
        Column(Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            //TitleSection
            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = playList.imgId!!),//R.drawable.toeic
                    contentDescription = "img",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = playList.title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "소유자: ${playList.author}님", modifier = Modifier.clickable { })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${playList.playItems.size}차시 과정")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text("나의 리스트에 추가하기")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = playList.description
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                itemsIndexed(playList.playItems) { index, item ->
                    ListCard(index = index + 1, title = item.title, min = item.timeInMin)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ListCard(index: Int, title: String, min: Int) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "$index 차시",
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Divider(
                modifier = Modifier
                    .height(50.dp)
                    .width(1.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column() {
                Text(text = title)
                Text(text = "소요 시간 : $min 분")
            }
        }
    }
}