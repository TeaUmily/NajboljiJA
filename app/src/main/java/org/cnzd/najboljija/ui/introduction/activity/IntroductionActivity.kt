package org.cnzd.najboljija.ui.introduction.activity

import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseActivity
import org.cnzd.najboljija.ui.introduction.fragment.IntroductionLandingFragment

class IntroductionActivity : BaseActivity() {

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_introduction
    }

    override fun setUpUi() {
        supportFragmentManager.beginTransaction().replace(R.id.contentContainer, IntroductionLandingFragment()).commit()
    }

}

