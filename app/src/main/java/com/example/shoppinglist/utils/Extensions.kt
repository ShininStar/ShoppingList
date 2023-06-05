package com.example.shoppinglist.utils

import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.*

//расширяем view model для того чтобы отображать текущее время
fun ViewModel.getCurrentTime() : String {
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val cv = Calendar.getInstance()
    return formatter.format(cv.time)
}