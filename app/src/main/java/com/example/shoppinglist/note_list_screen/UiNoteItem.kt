package com.example.shoppinglist.note_list_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.ui.theme.BlueLight
import com.example.shoppinglist.ui.theme.LightText
import com.example.shoppinglist.ui.theme.Red
import com.example.shoppinglist.utils.Routes

//карточка заметки
@Composable
fun UiNoteItem(
    item: NoteItem,
    onEvent: (NoteListEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 3.dp,
                start = 3.dp,
                end = 3.dp
            )
                //по нажатию на запись переходим в экрна записи для редактирования
            .clickable {
                onEvent(NoteListEvent.OnItemClick(
                    Routes.NEW_NOTE + "/${item.id}"
                ))
            }
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            start = 10.dp
                        )
                        .weight(1f),
                    text = item.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(
                        top = 10.dp,
                        end = 10.dp
                    ),
                    text = item.time,
                    color = BlueLight,
                    fontSize = 12.sp
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .padding(
                            top = 5.dp,
                            start = 10.dp,
                            bottom = 10.dp
                        )
                        .weight(1f),
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = LightText
                )
                //кнопка удаления, перед удалением спрашиваем подтверждение
                IconButton(
                    onClick = {
                        onEvent(NoteListEvent.OnShowDeleteDialog(item))
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Red
                    )
                }
            }
        }

    }
}