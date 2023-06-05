package com.example.shoppinglist.settings_screen

sealed class SettingsEvent{
    data class onItemSelected(val color: String): SettingsEvent()
}
