package com.comye1.capstone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.comye1.capstone.R

@Composable
fun CreateScreen(
    playList: PlayList = PlayList(
        title = "달성 중인 목표 샘플",
        author = "윤희서",
        description = "책을 꾸준히 읽자",
        imgId = R.drawable.books,
        playItems = listOf(
            PlayItem("11월 1일 ~ 모비딕 제 12장 간추린 생애", 150),
            PlayItem("11월 8일 ~ 모비딕 제 35장 돛대 꼭대기", 160),
            PlayItem("11월 15일 ~ 모비딕 제 49장 하이에나", 130),
        )
    ),
    toBack: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = toBack) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.MoreVert, contentDescription = "share")
                }
            })
    }) {
        Column(Modifier.padding(16.dp)) {
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
                    Text(text = "소유자: ${playList.author}님")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${playList.playItems.size}차시 과정")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
//                Text("나의 리스트에 추가하기")
//            }
//            Spacer(modifier = Modifier.height(16.dp))
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
                item {
                    CreateListCard(index = playList.playItems.size)
                }
                item {
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}

@Composable
fun CreateListCard(index: Int) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.primary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${index + 1} 차시",
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
                Text(text = "추가하기", style = MaterialTheme.typography.h6)
            }
        }
    }
}
