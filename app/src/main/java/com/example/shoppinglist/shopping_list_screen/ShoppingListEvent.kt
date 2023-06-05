package com.example.shoppinglist.shopping_list_screen

import com.example.shoppinglist.data.ShoppingListItem

//события для экрана со списками покупок (нажатие на списко, редактирование, удаление, сохранение)
sealed class ShoppingListEvent{
    data class OnShowDeleteDialog(val item: ShoppingListItem) : ShoppingListEvent()
    data class OnShowEditDialog(val item: ShoppingListItem) : ShoppingListEvent()
    data class OnItemClick(val route: String) : ShoppingListEvent()
    object OnItemSave : ShoppingListEvent()
}
