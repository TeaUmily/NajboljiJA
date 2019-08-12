package org.cnzd.najboljija.ui.home.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_loader.*
import kotlinx.android.synthetic.main.layout_no_internet.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseActivity
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.ui.home.view_model.HomeVM
import org.cnzd.najboljija.ui.home.viewpager.PagerAdapterHome
import org.cnzd.najboljija.ui.login.activity.LoginActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val viewModel by viewModel<HomeVM>()
    private val pagerAdapter by inject<PagerAdapterHome> { parametersOf(supportFragmentManager) }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_home
    }

    override fun setUpUi() {

        setUpViewPager()
        setUpActionBar()

        viewModel.localId = sharedPrefs.getString(KEY_LOCAL_ID, " ")
        viewModel.tokenId = sharedPrefs.getString(KEY_TOKEN_ID, " ")

        initListeners()
        configureObservers()
        viewModel.getHomeData().homeSubscribe()
    }

    private fun initListeners() {
        btnLogout.setOnClickListener {
            logOut()
        }
    }

    private fun configureObservers() {
        viewModel.namesData.observe(this, Observer { data ->
            data?.let {
                tv_MentorChildName.text = it
                sharedPrefs.edit().putString(CHILD, it.split(" ")[0]).apply()
                sharedPrefs.edit().putString(MENTOR, it.split(" ")[2]).apply()
            }
        })
    }

    private fun setUpActionBar() {
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setUpViewPager() {
        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0
        viewPager.pageMargin = dpToPixels(12, this).toInt()

        viewPager.setPageTransformer(false) { page, _ ->
            val pageWidth = viewPager.measuredWidth - viewPager.paddingLeft - viewPager.paddingRight
            val pageHeight = viewPager.height
            val paddingLeft = viewPager.paddingLeft
            val transformPos = (page.left - (viewPager.scrollX + paddingLeft)).toFloat() / pageWidth
            val normalizedPosition = Math.abs(Math.abs(transformPos) - 1)
            page.alpha = normalizedPosition + 0.5f
            val max = -pageHeight / 10
            when {
                transformPos < -1 -> // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.translationY = 0F
                transformPos <= 1 -> // [-1,1]
                    page.translationY = max * (1 - Math.abs(transformPos))
                else -> // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.translationY = 0F
            }
        }
    }


    private fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun <T> Observable<T>.homeSubscribe() {
        val observable =
                this.doOnSubscribe { loaderLayout.toVisible() }
                        .doOnComplete { loaderLayout.toInvisible(); noInternetLayout.toInvisible() }
                        .doOnError { loaderLayout.toInvisible(); handleError(it) }
                        .doOnNext { loaderLayout.toInvisible(); noInternetLayout.toInvisible() }
                        .retryWhen {
                            it.flatMap {
                                btnRetry?.let { view -> RxView.clicks(view) }
                            }
                        }

        addDisposable(observable.subscribe())
    }

    private fun handleError(error: Throwable) {
        when (error.message) {
            UNABLE_TO_RESOLVE_HOST -> noInternetLayout.toVisible()
            HTTP_401 -> logOut()
        }
    }

    private fun logOut() {
        sharedPrefs.putBoolean(KEY_IS_LOGGED_IN, false)
        startActivity(Intent(baseContext, LoginActivity::class.java))
        finish()
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

    override fun onResume() {
        viewModel.getChallengesStateData().homeSubscribe()
        super.onResume()
    }
}


