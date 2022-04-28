package com.comye1.capstone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.comye1.capstone.R
import com.comye1.capstone.screens.goaldetail.ListCard
import com.comye1.capstone.screens.goaldetail.PlayItem
import com.comye1.capstone.screens.goaldetail.PlayList

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
    val createListCardDialog = remember { mutableStateOf(false) }
    val (planText, setPlanText) = remember {
        mutableStateOf("")
    }
    if (createListCardDialog.value) {
        Dialog(onDismissRequest = { createListCardDialog.value = false }) {
            Column(

                Modifier
                    .clip(RoundedCornerShape(size = 12.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(text = "${playList.playItems.size + 1} 차시 계획 작성하기")
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = planText,
                    onValueChange = setPlanText,
                    placeholder = { Text("계획 내용") },
                    modifier = Modifier.height(300.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = { createListCardDialog.value = false }) {
                        Text("취소")
                    }
                    Button(onClick = { createListCardDialog.value = false }) {
                        Text("저장")
                    }
                }
            }
        }
    }

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
                    CreateListCard(
                        index = playList.playItems.size,
                        onClick = { createListCardDialog.value = true })
                }
                item {
                    Spacer(modifier = Modifier.height(56.dp))
                }
            }
        }
    }
}

@Composable
fun CreateListCard(index: Int, onClick: () -> Unit) {
    Card(
        elevation = 5.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
            Column {
                Text(text = "추가하기", style = MaterialTheme.typography.h6)
            }
        }
    }
}
