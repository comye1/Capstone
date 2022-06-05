package com.comye1.capstone.screens.mygoal

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.GoalData
import com.comye1.capstone.screens.player.PlanCompleteDialog

@Composable
fun MyGoalScreen(
    goalId: Int,
    viewModel: MyGoalViewModel = hiltViewModel(),
    toBack: () -> Unit
) {

    var goal = produceState<Resource<GoalData>>(initialValue = Resource.Loading<GoalData>()) {
        value = viewModel.getGoalById(goalId)
    }.value

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
        when (goal) {
            is Resource.Success -> {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    //TitleSection
                    Text(
                        text = goal.data.goal_title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${goal.data.Plan.size}차시 과정")

                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        itemsIndexed(goal.data.Plan) { index, item ->
                            MyGoalListCard(
                                index = index + 1,
                                title = item.plan_title,
                                comment = item.content,
                                isChecked = item.is_checked,
                                onComplete = { newContent ->
                                    viewModel.completePlan(item, newContent) {
                                        goal.data.Plan[index].apply {
                                            is_checked = true
                                            content = newContent
                                        }
                                    }
                                },
                                currentPlan = item == viewModel.currentPlan
                            ) {
                                viewModel.startPlan(goal.data, index)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

    }
}

@Preview("mygoalList")
@Composable
fun MyGoalListCardPreview() {
    Column {
        MyGoalListCard(index = 0, title = "아무제목", comment = "아무내용", isChecked = false, currentPlan = true) {

        }
        MyGoalListCard(index = 0, title = "아무제목", comment = "아무내용", isChecked = true, currentPlan = false) {

        }
    }
}

@Composable
fun MyGoalListCard(
    index: Int,
    isChecked: Boolean,
    title: String,
    comment: String,
    currentPlan: Boolean,
    onComplete: (String) -> Unit = {},
    onClick: () -> Unit
) {
    val (planCompleted, completePlan) = remember {
        mutableStateOf(false)
    }
    Column() {
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
                Text(text = title, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            if (currentPlan) {
                TextButton(onClick = { completePlan(true) }) {
                    Text(
                        text = "완료하고 소감 작성하기",
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }
            }
            else if (isChecked) {
                TextButton(onClick = { /*반응 안함*/ }) {
                    Text(
                        text = comment,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }
            } else {
                TextButton(onClick = onClick) {
                    Text(
                        text = "시작하기",
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
    if (planCompleted)
        PlanCompleteDialog(
            onSave = { content ->
                completePlan(false)
                onComplete(content)
            },
            onCancel = {
                completePlan(false)
            }
        )
}