package com.example.shoppinglist.main_screen

//события главного экрана - сохранение списка покупко, переход на экраны NewNote/AddItem, переход между экранами ShoppingList/NoteList/About/Settings,
//нажатие на floatiing button
sealed class MainScreenEvent{
    object OnItemSave : MainScreenEvent()
    data class Navigate(val route: String) : MainScreenEvent()
    data class NavigateMain(val route: String) : MainScreenEvent()
    data class OnNewItemClick(val route: String) : MainScreenEvent()
}
