package com.example.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.about_screen.AboutScreen
import com.example.shoppinglist.add_item_screen.AddItemScreen
import com.example.shoppinglist.main_screen.MainScreen
import com.example.shoppinglist.new_note_screen.NewNoteScreen
import com.example.shoppinglist.note_list_screen.NoteListScreen
import com.example.shoppinglist.settings_screen.SettingsScreen
import com.example.shoppinglist.shopping_list_screen.ShoppingListScreen
import com.example.shoppinglist.utils.Routes

//NavGraph для открытия экрана Main с меню внизу и кнопкой floating button
//и открытия двух кэранов добавления элемента в спискок покупок и добавления заметки без меню внизу и floating button
@Composable
fun MainNavigationGraph() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MAIN_SCREEN) {
        composable(Routes.ADD_ITEM + "/{listId}") {
            AddItemScreen()
        }
        composable(Routes.NEW_NOTE + "/{noteId}") {
            NewNoteScreen() {
                navController.popBackStack()
            }
        }
        composable(Routes.MAIN_SCREEN) {
            MainScreen(navController)
        }
    }
}