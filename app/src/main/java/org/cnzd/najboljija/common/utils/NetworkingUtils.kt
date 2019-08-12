package org.cnzd.najboljija.common.utils

import android.content.Context
import android.net.ConnectivityManager

fun hasInternet(context: Context?): Boolean = context?.let {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeInfo = manager.activeNetworkInfo

    activeInfo != null
} ?: false