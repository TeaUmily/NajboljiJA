package org.cnzd.najboljija.base

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun <T> Observable<T>.subscribe(){
        addDisposable(this.subscribe({},{it.printStackTrace()}))
    }

    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(disposable)
    }

}