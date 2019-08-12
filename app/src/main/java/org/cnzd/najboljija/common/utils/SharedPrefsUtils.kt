package org.cnzd.najboljija.common.utils

import android.content.SharedPreferences

fun SharedPreferences.putString(key:String, value: String){
    this.edit().putString(key,value).apply()
}

fun SharedPreferences.putBoolean(key:String, value: Boolean){
    this.edit().putBoolean(key, value).apply()
}
