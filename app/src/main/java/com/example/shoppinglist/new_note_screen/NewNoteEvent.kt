package com.example.shoppinglist.new_note_screen

//события экрана NewNote - изменение заголовка, изменения описания, сохранение
sealed class NewNoteEvent{
    data class OnTitleChange(val title: String): NewNoteEvent()
    data class OnDescriptionChange(val description: String): NewNoteEvent()
    object OnSave: NewNoteEvent()

}
