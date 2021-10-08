package com.comye1.capstone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
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
import com.comye1.capstone.ui.theme.CapstoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CapstoneTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    PLDetailScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun PLDetailScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back")
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Share, contentDescription = "share")
                }
            })
    }) {
        Column(Modifier.padding(16.dp)) {
            //TitleSection
            Row(Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.toeic),
                    contentDescription = "img",
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "토익 900 달성",
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "소유자: 김예원님")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "21차시 과정")
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                Text("나의 리스트에 추가하기")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "영어의 기반이 생겼다고 느낄 때 기출문제집 공부를 시작했습니다. " +
                        "풀이법은 다른 사람이 가르쳐주는 것보다 본인에게 맞는 방법을 찾는 것이" +
                        "중요합니다."
            )
            Spacer(modifier = Modifier.height(16.dp))
            ListCard(index = 1, title = "PART5 2회분 풀기, 놓치고 있던 문법 정리하기", 100)
            Spacer(modifier = Modifier.height(8.dp))
            ListCard(index = 2, title = "PART5 2회분 풀기, 놓치고 있던 문법 정리하기", 100)
            Spacer(modifier = Modifier.height(8.dp))
            ListCard(index = 3, title = "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기", 120)
            Spacer(modifier = Modifier.height(8.dp))
            ListCard(index = 4, title = "PART1 5회분 풀기, 나만의 메모습관 만들기 \n문법 복습하기", 120)
            Spacer(modifier = Modifier.height(8.dp))
            ListCard(index = 5, title = "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복", 100)
            Spacer(modifier = Modifier.height(8.dp))
            ListCard(index = 6, title = "PART2 2회분 풀기, 의문사에 집중하기 \n들릴 때까지 반복", 100)
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