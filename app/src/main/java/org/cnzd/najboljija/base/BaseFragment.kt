package org.cnzd.najboljija.base

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_no_internet.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.ui.login.activity.LoginActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    protected val sharedPrefs by inject<SharedPreferences> { parametersOf(this) }

    abstract val viewModel: T
    var loaderLayout: View? = null
    var noInternetLayout: View? = null
    var retryInternet: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(getLayoutResources(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loaderLayout = view.findViewById(R.id.loaderLayout)
        noInternetLayout = view.findViewById(R.id.noInternetLayout)
        retryInternet = view.findViewById(R.id.btnRetry)
    }

    abstract fun getLayoutResources(): Int

    protected fun <T> Observable<T>.subscribe() {
        addDisposable(this.subscribe({}, { it.printStackTrace() }))
    }

    protected fun <T> Observable<T>.longSubscribe() {
        val observable =
                this.doOnSubscribe { loaderLayout?.toVisible() }
                        .doOnError { loaderLayout?.toInvisible(); handleError(it) }
                        .doOnComplete { loaderLayout?.toInvisible(); noInternetLayout?.toInvisible() }
                        .doOnNext { loaderLayout?.toInvisible(); noInternetLayout?.toInvisible() }
                        .retryWhen {
                            it.flatMap {
                                btnRetry?.let { view -> RxView.clicks(view) }
                            }
                        }

        addDisposable(observable.subscribe({}, { it.printStackTrace() }))
    }

    private fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed)
            compositeDisposable = CompositeDisposable()
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun handleError(throwable: Throwable) {
        when (throwable.message) {
            UNABLE_TO_RESOLVE_HOST -> noInternetLayout?.toVisible()
            HTTP_401 -> logOut()
            else -> context!!.showToast(getString(org.cnzd.najboljija.R.string.error_occured))
        }
    }

    private fun logOut() {
        sharedPrefs.putBoolean(KEY_IS_LOGGED_IN, false)
        startActivity(Intent(activity!!.baseContext, LoginActivity::class.java))
        activity!!.finish()
    }
}