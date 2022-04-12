package com.comye1.capstone.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ExitDialog(onDismiss: () -> Unit, onYes: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("정말 종료하시겠습니까?", style = MaterialTheme.typography.h6)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "아니오",
                    modifier = Modifier
                        .clickable {
                            onDismiss()
                        }
                        .padding(8.dp),
                    color = Color.Red, style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.width(32.dp))
                Text(
                    text = "네",
                    modifier = Modifier
                        .clickable {
                            onYes()
                        }
                        .padding(8.dp),
                    color = Color.Red, style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}
@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp = 8.dp
): Modifier {

    return drawWithContent {
        drawContent()

        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        if (firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / (state.layoutInfo.totalItemsCount + 1)
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight =
                (state.layoutInfo.visibleItemsInfo.size + 1 ) * elementHeight

            drawRect(
                color = Color.Black,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = 0.4f
            )
        }
    }
}