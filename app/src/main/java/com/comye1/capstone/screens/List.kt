package com.comye1.capstone.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListScreen() {

    val (filterIndex, setFilterIndex) = remember {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("List")
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.NoteAdd, contentDescription = "add")
                    }
                }
            )
        },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            FilterSection(selectedFilterIndex = filterIndex, setIndex = setFilterIndex)
            Spacer(modifier = Modifier.height(16.dp))
            MyGoalItem(
                goal = "매일매일 코어운동",
                prevTitle = "플랭크 40초 스쿼트 40회 런지 40회",
                nextTitle = "플랭크 45초 스쿼트 45회 런지 45회"
            )
            Spacer(modifier = Modifier.height(16.dp))
            MyGoalItem(goal = "안드로이드 기본지식", prevTitle = "View Lifecycle", nextTitle = "ViewModel")
            Spacer(modifier = Modifier.height(16.dp))
            MyGoalItem(goal = "쇼팽 친해지기", prevTitle = "영웅 폴로네이즈", nextTitle = "환상 폴로네이즈")
            Spacer(modifier = Modifier.height(16.dp))
            MyGoalItem(
                goal = "정보처리기사 필기",
                prevTitle = "소프트웨어 설계 - 요구사항 확인",
                nextTitle = "아직 작성되지 않았습니다."
            )
        }
    }
}

@Preview
@Composable
fun NewPlayLog() {

    var text by remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("기록하기") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "complete")
            }
        },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField(value = "환상 폴로네이즈", onValueChange = {}, modifier = Modifier.fillMaxWidth())

            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun MyGoalItem(goal: String, prevTitle: String, nextTitle: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
    ) {
        Row(Modifier.padding(8.dp)) {
            Text(text = goal, style = MaterialTheme.typography.h6, fontWeight = FontWeight.Bold)
        }
        Divider()
        Column(Modifier.padding(8.dp)) {
            MyGoalItemPrev(text = prevTitle)
            Spacer(modifier = Modifier.height(4.dp))
            MyGoalItemNext(text = nextTitle)
        }
    }
}

@Composable
fun FilterSection(selectedFilterIndex: Int, setIndex: (Int) -> Unit) {
    Row {
        FilterText("전체", selectedFilterIndex == 0) { setIndex(0) }
        Spacer(modifier = Modifier.width(8.dp))
        FilterText("개발자 되기", selectedFilterIndex == 1) { setIndex(1) }
        Spacer(modifier = Modifier.width(8.dp))
        FilterText("건강", selectedFilterIndex == 2) { setIndex(2) }
        Spacer(modifier = Modifier.width(8.dp))
        FilterText("클래식", selectedFilterIndex == 3) { setIndex(3) }
    }
}

@Composable
fun FilterText(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(CircleShape)
            .clickable(enabled = !selected, onClick = onClick)
            .background(color = if (selected) Color.LightGray else Color.Transparent)
            .padding(horizontal = 20.dp, vertical = 2.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.body1, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun MyGoalItemPrev(text: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(text = "이전", color = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, overflow = TextOverflow.Ellipsis, maxLines = 1)
    }
}

@Composable
fun MyGoalItemNext(text: String) {
    Row(Modifier.fillMaxWidth()) {
        Text(text = "다음", color = Color.Gray)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, overflow = TextOverflow.Ellipsis, maxLines = 1)
    }
}