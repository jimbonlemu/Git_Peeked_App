package com.jimbonlemu.fundamental_android.utils

open class Event<out T>(private val content:T) {
    var isHandled = false
        private set
    fun getContentIfUnhandled():T?{
        return  if(isHandled){
            null
        }else{
            isHandled = true
            content
        }
    }
}
