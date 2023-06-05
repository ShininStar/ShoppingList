package com.example.shoppinglist.utils

//здесь считаем полоску прогресса, когда отмечакем покупки
object ProgressHelper {
    fun getProgress(allItemsCount: Int, selectedItemsCount: Int) : Float {
        return if (selectedItemsCount == 0) 0.0f
        else selectedItemsCount.toFloat() / allItemsCount.toFloat()
    }
}