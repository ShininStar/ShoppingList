package com.example.shoppinglist.add_item_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.dialog.MainDialog
import com.example.shoppinglist.ui.theme.BlueLight
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.EmptyText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.utils.UiEvent
import kotlinx.coroutines.flow.collect

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val itemsList = viewModel.itemsList?.collectAsState(initial = emptyList())

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect{uiEvent ->
            when(uiEvent) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        uiEvent.message
                    )
                }
                else -> {}
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState, snackbarHost = {
            SnackbarHost(hostState = scaffoldState.snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    backgroundColor = BlueLight
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = viewModel.itemText.value,
                        onValueChange = { text ->
                            viewModel.onEvent(AddItemEvent.OnTextChange(text))
                        },
                        label = {
                            Text(
                                text = "New item",
                                fontSize = 12.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = BlueLight,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = DarkText
                        ),
                        singleLine = true
                    )
                    IconButton(onClick = {
                        viewModel.onEvent(AddItemEvent.OnItemSave)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Add"
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 5.dp,
                        end = 5.dp
                    )
            ) {
                if (itemsList != null) {
                    items(itemsList.value) { item ->
                        UiAddItem(item = item, onEvent = { event ->
                            viewModel.onEvent(event)
                        })
                    }
                }
            }
        }
        MainDialog(dialogController = viewModel)
        if (itemsList?.value?.isEmpty() == true) {
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
}