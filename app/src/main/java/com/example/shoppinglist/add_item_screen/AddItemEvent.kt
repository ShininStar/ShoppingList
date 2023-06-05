package com.example.shoppinglist.add_item_screen

import com.example.shoppinglist.data.AddItem

//события списка покупок - удаление, показать диалого редактирования, редактирование, отмечена покупка или нет, сохранение
sealed class AddItemEvent{
    data class OnDelete(val item: AddItem): AddItemEvent()
    data class OnShowEditDialog(val item: AddItem): AddItemEvent()
    data class OnTextChange(val text: String): AddItemEvent()
    data class OnCheckedChange(val item: AddItem): AddItemEvent()
    object OnItemSave: AddItemEvent()
}
