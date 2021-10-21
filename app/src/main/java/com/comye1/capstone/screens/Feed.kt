package com.comye1.capstone.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comye1.capstone.R

@Preview
@Composable
fun FeedScreen() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Feed") },
        )
    }) {
        Column( // Todo : LazyColumn으로 변경
            Modifier
                .fillMaxSize()
        ) {
            Divider(Modifier.height(8.dp))
            FeedItem(
                id = "예원",
                date = "5분 전",
                goal = "쇼팽 친해지기",
                title = "영웅 폴로네이즈",
                description = "'영웅' 이라는 이름이 붙은 이유가 재밌다. 조성진의 연주 짱이다",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Divider(Modifier.height(8.dp))
            FeedItem(
                id = "정인",
                date = "49분 전",
                goal = "자전거 국토종주를 향해",
                title = "한강에서 10km 타기",
                description = "지난 주보다 숨이 덜 찼다. 가을 날씨 선선하다 ^^",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Divider(Modifier.height(8.dp))
            FeedItem(
                id = "희서",
                date = "2시간 전",
                goal = "고전영화 섭렵하기",
                title = "바람과 함께 사라지다",
                img = painterResource(id = R.drawable.gone_with_wind),
                description = "내일은 내일의 태양이 뜬다.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Divider(Modifier.height(8.dp))
            FeedItem(
                id = "혜성",
                date = "어제",
                goal = "토익 만점을 향해",
                title = "오답을 고른 이유 분석하기",
                description = "자꾸만 터무니 없는 실수를 한다. " +
                        "왜 오답을 고르게 되는지 되짚어 보았다.",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Divider(Modifier.height(8.dp))
        }

    }

}

@Composable
fun FeedItem(
    id: String,
    date: String,
    goal: String,
    title: String,
    description: String,
    img: Painter? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "person",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .border(shape = CircleShape, width = 1.dp, color = Color.LightGray)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = id)
//                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = goal, fontWeight = FontWeight.Bold)
                }

            }
            Row {
                Text(text = date, color = Color.Gray, fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, style = MaterialTheme.typography.h6)
        if (img != null) {
            Image(
                painter = img,
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = description, style = MaterialTheme.typography.subtitle1)
    }
}