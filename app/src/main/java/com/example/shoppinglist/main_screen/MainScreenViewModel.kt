package com.example.shoppinglist.main_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.utils.Routes
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//view model для MainScreen
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf("List name:")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(true)
        private set

    var showFloatingButton = mutableStateOf(true)
        private set

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            //сохраняем новый списко покупок
            is MainScreenEvent.OnItemSave -> {
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            null,
                            editableText.value,
                            getCurrentTime(),
                            0,
                            0
                        )
                    )
                }
            }
            //слушаем нажатие на floatting button, если это экран ShoppingList, то открываем диалог
            //если экран NoteList, то переходим в экран NewNote
            is MainScreenEvent.OnNewItemClick -> {
                if (event.route == Routes.SHOPPING_LIST) openDialog.value = true
                else sendUiEvent(UiEvent.NavigateMain(Routes.NEW_NOTE + "/-1"))
            }
            //убираем floatting button на экранах About и Settings
            is MainScreenEvent.Navigate -> {
                sendUiEvent(UiEvent.Navigate(event.route))
                showFloatingButton.value = if (event.route == Routes.ABOUT || event.route == Routes.SETTINGS) {
                    false
                } else {
                    true
                }
            }
            is MainScreenEvent.NavigateMain -> {
                sendUiEvent(UiEvent.NavigateMain(event.route))
            }
        }
    }

    //обрабатываем нажатия на кнопки диалога
    override fun onDialogEvent(event: DialogEvent) {
        when (event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnConfirm -> {
                onEvent(MainScreenEvent.OnItemSave)
                openDialog.value = false
                editableText.value = ""
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }

        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}