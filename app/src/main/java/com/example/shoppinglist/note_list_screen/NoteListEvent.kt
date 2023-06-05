package com.example.shoppinglist.note_list_screen

import com.example.shoppinglist.data.NoteItem

//события для списка заметок (показываем диалог удаления, нажатие на заметку, отмена удаления)
sealed class NoteListEvent{
    data class OnShowDeleteDialog(val item: NoteItem): NoteListEvent()
    data class OnItemClick(val route: String): NoteListEvent()
    object UnDoneDeleteItem: NoteListEvent()
}