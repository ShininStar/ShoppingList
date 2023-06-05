package com.example.shoppinglist.utils

//события для перехода на другие экраны или показа snackbar
sealed class UiEvent{
    object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
    data class NavigateMain(val route: String): UiEvent()
    data class ShowSnackBar(val message: String): UiEvent()
}