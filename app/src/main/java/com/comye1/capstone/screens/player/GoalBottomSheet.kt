package com.comye1.capstone.screens.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comye1.capstone.R
import com.comye1.capstone.ui.simpleVerticalScrollbar


@Composable
fun PlayerBottomSheetHandle() {
    Divider()
    Row(
        Modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
//                                contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.books),
            contentDescription = "image"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "goal title")
            Text(text = "plan title", fontSize = 24.sp)
        }
        val (checked, checkOrNot) = remember {
            mutableStateOf(false)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Checkbox(checked = checked, onCheckedChange = checkOrNot)
    }
}

@Composable
fun PlayerBottomSheetContent(bottomPadding: Dp) {
    Divider()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropDownGoalMenu()

        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("다른 골 목록 어쩌구")

        Spacer(modifier = Modifier.height(bottomPadding))
    }
}

@Composable
fun DropDownGoalMenu() {
    val (expanded, setExpanded) = remember {
        mutableStateOf(false)
    }

    val lazyColumnState = rememberLazyListState()

    val (goalIdx, setGoalIdx) = remember {
        mutableStateOf(0)
    }

    val (goalTitle, setGoalTitle) = remember {
        mutableStateOf("현재 목표")
    }

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = goalTitle)
            IconButton(onClick = { setExpanded(!expanded) }) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (expanded) "unfold menu" else "fold menu"
                )
            }
        }
        if (expanded) {
            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeightIn(max = 150.dp)
                    .border((1.5).dp, color = Color.LightGray)
                    .simpleVerticalScrollbar(lazyColumnState)
            ) {
                items(10) { idx ->
                    DropDownGoalItem(
                        onClick = {
                            setGoalIdx(idx)
                            setGoalTitle("다른 목표 $idx")
                            setExpanded(false)
                        },
                        clicked = goalIdx == idx,
                        text = "다른 목표 $idx"
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun DropDownGoalItem(onClick: () -> Unit, clicked: Boolean, text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(enabled = !clicked, onClick = onClick)
            .background(if (clicked) Color.LightGray else Color.White)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text)
        if (clicked)
            Icon(imageVector = Icons.Default.Check, contentDescription = "selected")
    }
}