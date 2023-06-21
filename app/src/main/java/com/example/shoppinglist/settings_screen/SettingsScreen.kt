package com.example.shoppinglist.settings_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

//экран настроек для изменения цвета текста заголовка NoteItem
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val list = viewModel.colorItemListState.value

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "Title color",
            fontSize = 16.sp
        )
        Text(
            text = "Select a title color:",
            fontSize = 12.sp,
            color = Color.Gray
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            items(list) {item ->
                UiColorItem(item = item) { event ->
                    viewModel.onEvent(event)
                }
            }
        }
    }
}