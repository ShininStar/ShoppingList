package com.example.shoppinglist.shopping_list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.dialog.MainDialog
import com.example.shoppinglist.ui.theme.EmptyText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.utils.UiEvent

//экран со списками покупок
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {
    val itemsList = viewModel.list.collectAsState(initial = emptyList())
    //слушаем событие нажатия на карточку и передаем его в NavigationGraph
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    onNavigate(uiEvent.route)
                }
                else -> {}
            }
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayLight),
        contentPadding = PaddingValues(bottom = 100.dp)
    ) {
        //заполняем карточки со списками покупок, которые получаем из ShoppingListViewModel
        items(itemsList.value) { item ->
            UiShoppingListItem(item) { event ->
                viewModel.onEvent(event)
            }
        }
    }
    //если списко пустокй, пишем в центре экрана Empty
    MainDialog(dialogController = viewModel)
    if (itemsList.value.isEmpty()) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            text = "Empty",
            fontSize = 25.sp,
            textAlign = TextAlign.Center,
            color = EmptyText
        )
    }
}