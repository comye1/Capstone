package com.comye1.capstone.screens.goaldetail

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.comye1.capstone.R
import com.comye1.capstone.network.Resource
import com.comye1.capstone.network.models.GoalData
import kotlinx.coroutines.flow.collect

data class PlayList(
    val imgId: Int? = R.drawable.sample,
    val title: String,
    val author: String,
    val playItems: MutableList<PlayItem>
)

data class PlayItem(
    val title: String,
    val comment: String
)

@Preview(showBackground = true)
@Composable
fun PLDetailPreview() {
    GoalDetailScreen(0, toBack = {}) {

    }
}

@Composable
fun GoalDetailScreen(
    goalId: Int,
    viewModel: GoalDetailViewModel = hiltViewModel(),
    toBack: () -> Unit,
    toMyGoal: (String) -> Unit
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
                    Text(text = "소유자: ${goal.data.user_id}님", modifier = Modifier.clickable { })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${goal.data.Plan.size}차시 과정")

                    Spacer(modifier = Modifier.height(16.dp))
                    if (viewModel.added) {
                        Row(Modifier.fillMaxWidth()) {
                            Button(
                                enabled = false,
                                onClick = { },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "추가됨 √")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { viewModel.navigateToMyList() }) {
                                Text(text = "내 리스트로")
                            }
                        }

                    } else {
                        Button(
                            onClick = {
                                viewModel.addToMyList(goal.data)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("나의 리스트에 추가하기")
                        }

                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn {
                        item {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                        itemsIndexed(viewModel.playList.playItems) { index, item ->
                            ListCard(index = index + 1, title = item.title, item.comment) {

                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

            }
        }
    }

    LaunchedEffect(true) {
        viewModel.messageFlow.collect { message ->
            when (message) {
                is GoalDetailMessage.NavigateToMyList -> {
                    toMyGoal(message.route)
                }
            }
        }
    }
}

@Composable
fun ListCard(index: Int, title: String, comment: String, onClick: () -> Unit) {
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
                    .clickable(onClick = onClick)
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
            Text(
                text = comment,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.LightGray)
                    .padding(8.dp)
            )
        }
    }
}