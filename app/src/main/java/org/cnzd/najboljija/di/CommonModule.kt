package org.cnzd.najboljija.di

import androidx.fragment.app.FragmentManager
import org.cnzd.najboljija.ui.home.viewpager.PagerAdapterHome
import org.cnzd.najboljija.ui.introduction.fragment.AnswersDataController
import org.cnzd.najboljija.ui.introduction.fragment.OnTickClick
import org.koin.dsl.module

val commonModule = module {
    factory { (fm: FragmentManager) -> PagerAdapterHome(fm) }
    factory { (onAnswerClick: OnTickClick) -> AnswersDataController( onAnswerClick) }
}