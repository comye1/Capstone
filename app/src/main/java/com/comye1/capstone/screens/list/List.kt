package com.comye1.capstone.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ListScreen(
    paddingValues: PaddingValues,
    viewModel: ListViewModel = hiltViewModel(),
    toMyGoal: (Int) -> Unit,
    toCreateScreen: () -> Unit
) {
    val categoryList = listOf(
        "전체",
        "건강",
        "취미",
        "학습",
        "어학",
        "IT",
        "기타"
    )
    val (filter, setFilter) = remember {
        mutableStateOf(categoryList[0])
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("List")
                },
                actions = {
                    IconButton(onClick = {
                        toCreateScreen()
                    }) {
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
            FilterSection(categoryList, selectedFilter = filter, setFilter = setFilter)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn (
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)){
                if (filter == "전체") {
                    items(viewModel.userGoals){
                        MyGoalItem(
                            goal = it.goal_title,
                            prevTitle = it.Plan.last { plan -> plan.is_checked }.plan_title ?: "",
                            nextTitle = it.Plan.first { plan -> !plan.is_checked }.plan_title ?: "",
                            onClick = {
                                toMyGoal(it.id)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
                else {
                    items(viewModel.userGoals.filter { it.board_category == filter }){
                        MyGoalItem(
                            goal = it.goal_title,
                            prevTitle = it.Plan.last { plan -> plan.is_checked }.plan_title ?: "",
                            nextTitle = it.Plan.first { plan -> !plan.is_checked }.plan_title ?: "",
                            onClick = {
                                toMyGoal(it.id)
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                    }
                }
            }

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
fun MyGoalItem(goal: String, prevTitle: String, nextTitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(5.dp))
            .clickable { onClick() }
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
fun FilterSection(list: List<String>, selectedFilter: String, setFilter: (String) -> Unit) {
    LazyRow {
        items(list) {
            FilterText(it, selectedFilter == it) { setFilter(it) }
            Spacer(modifier = Modifier.width(8.dp))
        }
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