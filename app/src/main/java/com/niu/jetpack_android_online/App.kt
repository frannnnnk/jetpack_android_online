package com.niu.jetpack_android_online

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class App : Application(){

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        const val TOKEN = "DYGaPoizeOjASILY"//彩云天气token
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}