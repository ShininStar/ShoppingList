package com.example.shoppinglist.dialog

//события диалого - изменение текста, отмена, подтверждение
sealed class DialogEvent{
    data class OnTextChange(val text: String): DialogEvent()
    object OnCancel: DialogEvent()
    object OnConfirm: DialogEvent()
}
