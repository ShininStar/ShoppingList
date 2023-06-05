package com.example.shoppinglist.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglist.about_screen.AboutScreen
import com.example.shoppinglist.note_list_screen.NoteListScreen
import com.example.shoppinglist.settings_screen.SettingsScreen
import com.example.shoppinglist.shopping_list_screen.ShoppingListScreen
import com.example.shoppinglist.utils.Routes

//навигация между экранами, которые находятся на MainScreen
@Composable
fun NavigationGraph(navController: NavHostController, onNavigate: (String) -> Unit) {

    NavHost(navController = navController, startDestination = Routes.SHOPPING_LIST) {
        composable(Routes.SHOPPING_LIST) {
            ShoppingListScreen() { route ->
                onNavigate(route)
            }
        }
        composable(Routes.NOTE_LIST) {
            NoteListScreen() { route ->
                onNavigate(route)
            }
        }
        composable(Routes.ABOUT) {
            AboutScreen()
        }
        composable(Routes.SETTINGS) {
            SettingsScreen()
        }
    }
}