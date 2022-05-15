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
import androidx.hilt.navigation.compose.hiltViewModel
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
)

@Preview(showBackground = true)
@Composable
fun PLDetailPreview() {
    GoalDetailScreen {

    }
}

@Composable
fun GoalDetailScreen(
    viewModel: GoalDetailViewModel = hiltViewModel(),
    toBack: () -> Unit
) {
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
                    painter = painterResource(id = viewModel.playList.imgId!!),//R.drawable.toeic
                    contentDescription = "img",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = viewModel.playList.title,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "소유자: ${viewModel.playList.author}님", modifier = Modifier.clickable { })
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${viewModel.playList.playItems.size}차시 과정")
                }

            }
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
                    Button(onClick = { viewModel.deleteFromMyList() }) {
                        Text(text = "삭제하기")
                    }
                }

            }
            else {
                Button(
                    onClick = { viewModel.addToMyList() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("나의 리스트에 추가하기")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = viewModel.playList.description
            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                itemsIndexed(viewModel.playList.playItems) { index, item ->
                    ListCard(index = index + 1, title = item.title)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun ListCard(index: Int, title: String) {
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
            Text(text = title)
        }
    }
}