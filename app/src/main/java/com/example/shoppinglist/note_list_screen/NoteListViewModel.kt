package com.example.shoppinglist.note_list_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.data.NoteRepository
import com.example.shoppinglist.dialog.DialogController
import com.example.shoppinglist.dialog.DialogEvent
import com.example.shoppinglist.note_list_screen.NoteListEvent
import com.example.shoppinglist.shopping_list_screen.ShoppingListEvent
import com.example.shoppinglist.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//view model по аналогии с ShoppingListViewModel
@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
): ViewModel(), DialogController {

    val noteList = repository.getAllItems()

    private var noteItem: NoteItem? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    override var dialogTitle = mutableStateOf("Delete this note?")
        private set
    override var editableText = mutableStateOf("")
        private set
    override var openDialog = mutableStateOf(false)
        private set
    override var showEditableText = mutableStateOf(false)
        private set

    fun onEvent(event: NoteListEvent) {
        when(event) {
            is NoteListEvent.OnShowDeleteDialog -> {
                openDialog.value = true
                noteItem = event.item
            }
            is NoteListEvent.OnItemClick -> {
                sendUiEvent(UiEvent.Navigate(event.route))
            }
            is NoteListEvent.UnDoneDeleteItem -> {
                viewModelScope.launch {
                    repository.insertItem(noteItem!!)
                }
            }
        }
    }

    override fun onDialogEvent(event: DialogEvent) {
        when(event) {
            is DialogEvent.OnCancel -> {
                openDialog.value = false
            }
            is DialogEvent.OnConfirm -> {
                viewModelScope.launch {
                    repository.deleteItem(noteItem!!)
                    sendUiEvent(UiEvent.ShowSnackBar("Undone delete item?"))
                }
                openDialog.value = false
            }
            else -> {}
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}