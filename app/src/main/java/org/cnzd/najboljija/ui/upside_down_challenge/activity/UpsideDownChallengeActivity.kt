package org.cnzd.najboljija.ui.upside_down_challenge.activity

import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseActivity
import org.cnzd.najboljija.ui.upside_down_challenge.fragment.UpsideDownChallengeLandingFragment


class UpsideDownChallengeActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_upside_down_challenge

    override fun setUpUi() {
        supportFragmentManager.beginTransaction().replace(R.id.contentContainer, UpsideDownChallengeLandingFragment()).commit()
    }

}