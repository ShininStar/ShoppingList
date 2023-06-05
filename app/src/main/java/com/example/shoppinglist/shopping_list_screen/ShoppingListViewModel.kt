package com.example.shoppinglist.shopping_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShoppingListItem
import com.example.shoppinglist.data.ShoppingListRepository
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//во view model обрабатываем события сохранения, нажатия, удаления и тд
//используем hilt и репозиторий для обращения к базе данных
//здесь же события диалога и события для UI
@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val repository: ShoppingListRepository
) : ViewModel(), DialogController {

    val list = repository.getAllItems()

    //канал UI событий
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var listItem: ShoppingListItem? = null

    override var dialogTitle = mutableStateOf("")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(false)
        private set

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            is ShoppingListEvent.OnItemSave -> {
                if (editableText.value.isEmpty()) return
                viewModelScope.launch {
                    repository.insertItem(
                        ShoppingListItem(
                            listItem?.id,
                            editableText.value,
                            listItem?.time ?: getCurrentTime(),
                            listItem?.allItemsCount ?: 0,
                            listItem?.alSelectedItemCount ?: 0
                        )
                    )
                }
            }
            is ShoppingListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }
            is ShoppingListEvent.OnShowEditDialog -> {
                listItem = event.item
                openDialog.value = true
                editableText.value = listItem?.name ?: ""
                dialogTitle.value = "List name:"
                showEditableText.value = true
            }
            is ShoppingListEvent.OnShowDeleteDialog -> {
                listItem = event.item
                openDialog.value = true
                dialogTitle.value = "Delete this item"
                showEditableText.value = false
            }
        }
    }

    //события для диалога (подтверждения, отмена, редактирование)
    override fun onDialogEvent(event: DialogEvent) {
        when(event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                if (showEditableText.value) {
                    onEvent(ShoppingListEvent.OnItemSave)
                }
                else {
                    viewModelScope.launch {
                        listItem?.let { repository.deleteItem(it) }
                    }
                }
                openDialog.value = false
            }
            is DialogEvent.OnTextChange -> {
                editableText.value = event.text
            }

        }
    }

    //функция для отправки UI событий
    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}