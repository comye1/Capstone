package com.comye1.capstone.screens.player

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.comye1.capstone.R
import com.comye1.capstone.screens.mygoal.MyGoalViewModel
import com.comye1.capstone.ui.simpleVerticalScrollbar


@Composable
fun PlayerBottomSheetHandle(
    viewModel: MyGoalViewModel = hiltViewModel(),
    expandBottomSheet: () -> Unit
) {

    val (planCompleted, completePlan) = remember {
        mutableStateOf(false)
    }
    val (checked, checkOrNot) = remember {
        mutableStateOf(false)
    }
    Divider()
    Row(
        Modifier
            .fillMaxWidth()
            .height(96.dp)
            .background(MaterialTheme.colors.secondaryVariant)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.books),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            contentDescription = "image"
        )
        if (viewModel.currentGoal.goal_title == ""){
            Text(text = "선택된 계획이 없습니다.", fontSize = 18.sp)
        }
        else {
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "캡스톤 디자인 프로젝트", fontSize = 12.sp)
                Text(text = "발표자료 완성하기", fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            // 시작전 진행중 완료
            if (viewModel.currentPlan != viewModel.seekingPlan) {
                TextButton(
                    onClick = viewModel::startSeekingPlan,
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                ) {
                    Text(text = "시작")
                }
            } else {
                TextButton(
                    enabled = !viewModel.currentPlan.is_checked,
                    onClick = {
                        checkOrNot(true)
                        completePlan(true)
                    },
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                ) {
                    Text(text = "완료")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = { expandBottomSheet() },
                    border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                ) {
                    Text(text = "다른 목표")
                }
            }

        }
    }
    if (planCompleted)
        PlanCompleteDialog(
            onSave = { content ->
                completePlan(false)
                viewModel.completePlan(viewModel.currentPlan, content = content) {
                    viewModel.movePlan()
                }
            },
            onCancel = {
                checkOrNot(false)
                completePlan(false)
            }
        )
}

@Composable
fun PlanCompleteDialog(onSave: (String) -> Unit, onCancel: () -> Unit) {

    val content = remember {
        mutableStateOf("")
    }

    Dialog(onDismissRequest = { }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(
                Modifier
                    .height(72.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.books),
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = "image"
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "캡스톤 디자인 프로젝트")
                    Text(text = "발표자료 완성하기", fontSize = 24.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = content.value,
                onValueChange = { content.value = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { onCancel() }) {
                    Text(text = "취소하기")
                }
                Button(onClick = { onSave(content.value) }) {
                    Text(text = "저장하기")
                }
            }
        }
    }
}

@Composable
fun PlayerBottomSheetContent(bottomPadding: Dp) {
    Divider(color = Color.White)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(MaterialTheme.colors.secondaryVariant)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropDownGoalMenu()

        }
//        Text("다른 골 목록 어쩌구", modifier = Modifier.padding(16.dp))

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
            Modifier
                .fillMaxWidth()
                .clickable { setExpanded(!expanded) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = goalTitle)
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = if (expanded) "unfold menu" else "fold menu"
            )
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
//                items(10) { idx ->
//                    DropDownGoalItem(
//                        onClick = {
//                            setGoalIdx(idx)
//                            setGoalTitle("다른 목표 $idx")
//                            setExpanded(false)
//                        },
//                        clicked = goalIdx == idx,
//                        text = "다른 목표 $idx"
//                    )
//                    Divider()
//                }
                item {
                    DropDownGoalItem(
                        onClick = {
                            setGoalIdx(0)
                            setGoalTitle("토익스피킹 레벨7")
                            setExpanded(false)
                        },
                        clicked = goalIdx == 0,
                        text = "토익스피킹 레벨7"
                    )
                    Divider()
                }
                item {
                    DropDownGoalItem(
                        onClick = {
                            setGoalIdx(1)
                            setGoalTitle("다른 목표")
                            setExpanded(false)
                        },
                        clicked = goalIdx == 1,
                        text = "다른 목표"
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