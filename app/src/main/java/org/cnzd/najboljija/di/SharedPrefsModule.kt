package org.cnzd.najboljija.di

import android.content.Context
import android.preference.PreferenceManager
import org.koin.dsl.module

val sharedPrefsModule = module {
    single { (context: Context) -> PreferenceManager.getDefaultSharedPreferences(context) }
}
