package org.cnzd.najboljija.base

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_introduction_landing.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.common.utils.toInvisible
import org.cnzd.najboljija.common.utils.toVisible
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity : AppCompatActivity() {

    protected val sharedPrefs by inject<SharedPreferences> { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
        setUpUi()
    }

    abstract fun getLayoutResourceId(): Int
    abstract fun setUpUi()

}