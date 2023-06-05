package com.example.shoppinglist.dialog

import androidx.compose.runtime.MutableState

//DialogController реализуем во view models, где этот диалог непобходим
interface DialogController {
    val dialogTitle: MutableState<String>
    val editableText: MutableState<String>
    val openDialog: MutableState<Boolean>
    val showEditableText: MutableState<Boolean>
    fun onDialogEvent(event: DialogEvent)
}