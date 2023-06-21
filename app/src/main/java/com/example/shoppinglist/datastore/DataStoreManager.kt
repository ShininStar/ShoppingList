package com.example.shoppinglist.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

//добавляем в контекст переменную dataStore
const val DATA_STORE_NAME = "preference_storage_name"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

//здесь будем сохранять выбранный цвет, чтобы менять цвет шрифта
class DataStoreManager(val context: Context) {

    suspend fun saveStringPreference(value: String, key: String) {
        context.dataStore.edit { pref ->
            pref[stringPreferencesKey(key)] = value
        }
    }

    fun getStringPreference(key: String, defValue: String) = context
        .dataStore.data.map { pref ->
            pref[stringPreferencesKey(key)] ?: defValue
    }

    companion object {
        const val TITLE_COLOR = "title_color"
    }
}