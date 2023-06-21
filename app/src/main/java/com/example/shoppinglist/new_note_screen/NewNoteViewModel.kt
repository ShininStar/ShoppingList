package com.example.shoppinglist.new_note_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.NoteItem
import com.example.shoppinglist.data.NoteRepository
import com.example.shoppinglist.datastore.DataStoreManager
import com.example.shoppinglist.utils.UiEvent
import com.example.shoppinglist.utils.getCurrentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

//view model для новой заметки, изпользуем hilt и репозиторий для обращения к базе данных
@HiltViewModel
class NewNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var noteId = -1
    private var noteItem: NoteItem? = null

    var titleColor = mutableStateOf("#487242")

    var title by mutableStateOf("")
        private set
    var description by mutableStateOf("")
        private set

    //проверям значение noteId, если не равно -1, то эта запись существует, находим ее по id и открываем
    init {
        noteId = savedStateHandle.get<String>("noteId")?.toInt() ?: -1
        if (noteId != -1) {
            viewModelScope.launch {
                repository.getNoteItemById(noteId).let {noteItem ->
                    title = noteItem.title
                    description = noteItem.description
                    this@NewNoteViewModel.noteItem = noteItem
                }
                dataStoreManager.getStringPreference(
                    DataStoreManager.TITLE_COLOR,
                    "#487242"
                ).collect {color ->
                    titleColor.value = color
                }
            }
        }
    }

    //функция событий
    fun onEvent(event: NewNoteEvent) {
        when(event) {
            //нажимем на сохранение, если поле пустое, то появляется snackbar с надписью
            is NewNoteEvent.OnSave -> {
                viewModelScope.launch{
                    if (title.isEmpty()) {
                        sendUiEvent(UiEvent.ShowSnackBar(
                            "Title can not be empty!"
                        ))
                        return@launch
                    }
                    //если не пустое, то сохраняем
                    repository.insertItem(
                        NoteItem(
                            noteItem?.id,
                            title,
                            description,
                            noteItem?.time ?: getCurrentTime()
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
                }
            }
            //меняем заголовок
            is NewNoteEvent.OnTitleChange -> {
                title = event.title
            }
            //меняем описание
            is NewNoteEvent.OnDescriptionChange -> {
                description = event.description
            }
        }

    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}