package com.example.shoppinglist.main_screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppinglist.ui.theme.BlueLight
import com.example.shoppinglist.ui.theme.GrayLight
import com.example.shoppinglist.utils.Routes

//создаем bottomNav и заполняем его и названиями экранов
@Composable
fun BottomNav(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val listItem = listOf(
        BottomNavItem.ListItem,
        BottomNavItem.NoteItem,
        BottomNavItem.AboutItem,
        BottomNavItem.SettingsItem
    )

    BottomNavigation(backgroundColor = Color.White) {
        listItem.forEach { bottomNavItem ->

            BottomNavigationItem(
                selected = currentRoute == bottomNavItem.route,
                onClick = {
                    onNavigate(bottomNavItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = bottomNavItem.iconId),
                        contentDescription = "icon"
                    )
                },
                label = {
                    Text(text = bottomNavItem.title)
                },
                selectedContentColor = BlueLight,
                unselectedContentColor = GrayLight,
                alwaysShowLabel = false
            )
        }
    }
}