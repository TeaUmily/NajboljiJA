package org.cnzd.najboljija.ui.photo_challenge.activity

import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseActivity
import org.cnzd.najboljija.ui.photo_challenge.fragment.PhotoChallengeLandingFragment

class PhotoChallengeActivity: BaseActivity() {

    override fun getLayoutResourceId() = R.layout.activity_photo_challenge

    override fun setUpUi() {
        supportFragmentManager.beginTransaction().replace(R.id.contentContainer, PhotoChallengeLandingFragment()).commit()
    }

}