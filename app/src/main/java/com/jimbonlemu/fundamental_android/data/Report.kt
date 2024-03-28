package com.jimbonlemu.fundamental_android.data

sealed class Report <out R>private constructor(){
    data class Success<out T>(val data: T) : Report<T>()
    data class Error(val error: String) : Report<Nothing>()
    data object Loading : Report<Nothing>()
}