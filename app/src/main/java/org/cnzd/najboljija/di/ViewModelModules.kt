package org.cnzd.najboljija.di


import org.cnzd.najboljija.ui.home.view_model.HomeVM
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionVM
import org.cnzd.najboljija.ui.login.view_model.LoginVM
import org.cnzd.najboljija.ui.photo_challenge.view_model.PhotoChallengeVM
import org.cnzd.najboljija.ui.upside_down_challenge.view_model.UpsideDownVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModels = module {
    viewModel { LoginVM(get()) }
    viewModel { HomeVM(get()) }
    viewModel { IntroductionVM(get()) }
    viewModel { PhotoChallengeVM(get(), get()) }
    viewModel { UpsideDownVM(get()) }
}



