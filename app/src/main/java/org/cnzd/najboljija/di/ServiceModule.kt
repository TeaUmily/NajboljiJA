package org.cnzd.najboljija.di

import org.cnzd.najboljija.common.interactor.Interactor
import org.cnzd.najboljija.common.rest_interface.RestInterface
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single { provideRestInterface(get()) }
    single { Interactor(get()) }
}

private fun provideRestInterface(retrofit: Retrofit): RestInterface =
        retrofit.create(RestInterface::class.java)

