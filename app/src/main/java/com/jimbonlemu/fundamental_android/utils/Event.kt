package com.jimbonlemu.fundamental_android.utils

open class Event<out T>(private val content:T) {
    private var isHandled = false
    fun getContentIfUnhandled():T?{
        return  if(isHandled){
            null
        }else{
            isHandled = true
            content
        }
    }
}
