package com.example.shoppinglist.new_note_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglist.R
import com.example.shoppinglist.ui.theme.BlueLight
import com.example.shoppinglist.ui.theme.DarkText
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.ui.theme.Red
import com.example.shoppinglist.utils.UiEvent

//экран новой заметки
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewNoteScreen(
    viewModel: NewNoteViewModel = hiltViewModel(),
    onPopBackStack: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    //слушаем события, кнопка возвращения на предыдущий экран или показ snackbar
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
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
                    backgroundColor = BlueLight,
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = GrayLight)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        //здесь слушаем изменение текста в заголовке
                        TextField(
                            modifier = Modifier.weight(1f),
                            value = viewModel.title,
                            onValueChange = { text ->
                                viewModel.onEvent(NewNoteEvent.OnTitleChange(text))
                            },
                            label = {
                                Text(
                                    text = "Title...",
                                    fontSize = 14.sp
                                )
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.White,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = BlueLight
                            ),
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(android.graphics.Color.parseColor(viewModel.titleColor.value))
                            )
                        )
                        //кнопка сохранения
                        IconButton(onClick = {
                            viewModel.onEvent(NewNoteEvent.OnSave)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.save_icon),
                                contentDescription = "Save",
                                tint = BlueLight
                            )
                        }
                    }
                    //здесь слушаем изменения текста в Description
                    TextField(
                        value = viewModel.description,
                        onValueChange = { text ->
                            viewModel.onEvent(NewNoteEvent.OnDescriptionChange(text))
                        },
                        label = {
                            Text(
                                text = "Description",
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = DarkText
                        )
                    )
                }
            }
        }
    }
}