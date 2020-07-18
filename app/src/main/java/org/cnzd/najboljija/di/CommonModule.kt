package org.cnzd.najboljija.di

import androidx.fragment.app.FragmentManager
import org.cnzd.najboljija.firebase.authentication.FirebaseAuthenticationInterface
import org.cnzd.najboljija.firebase.authentication.FirebaseAuthenticationManager
import org.cnzd.najboljija.firebase.database.FirebaseStorageInterface
import org.cnzd.najboljija.firebase.database.FirebaseStorageManager
import org.cnzd.najboljija.ui.home.adapter.PagerAdapterHome
import org.cnzd.najboljija.ui.introduction.fragment.AnswersDataController
import org.cnzd.najboljija.ui.introduction.fragment.TickClick
import org.cnzd.najboljija.ui.photo_challenge.fragment.PhotoTasksController
import org.cnzd.najboljija.ui.photo_challenge.fragment.TaskClick
import org.cnzd.najboljija.ui.upside_down_challenge.adapter.ViewPagerAdapterUpsideDownChallenge
import org.koin.dsl.module

val commonModule = module {
    factory { (fm: FragmentManager) -> PagerAdapterHome(fm) }
    factory { (fm: FragmentManager) -> ViewPagerAdapterUpsideDownChallenge(fm) }
    factory { (onTickClick: TickClick) -> AnswersDataController( onTickClick) }
    factory { (onTaskClick: TaskClick) -> PhotoTasksController(onTaskClick) }
    factory<FirebaseAuthenticationInterface> { FirebaseAuthenticationManager() }
    factory<FirebaseStorageInterface> { FirebaseStorageManager() }
}