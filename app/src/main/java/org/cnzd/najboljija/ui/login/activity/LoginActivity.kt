package org.cnzd.najboljija.ui.login.activity


import android.content.Intent
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseActivity
import org.cnzd.najboljija.common.utils.KEY_IS_LOGGED_IN
import org.cnzd.najboljija.ui.home.activity.HomeActivity
import org.cnzd.najboljija.ui.login.fragment.LoginFragment

class LoginActivity : BaseActivity() {

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_login
    }

    override fun setUpUi() {
        if(!sharedPrefs.contains(KEY_IS_LOGGED_IN)){
            sharedPrefs.edit().putBoolean(KEY_IS_LOGGED_IN, false).apply()
            supportFragmentManager.beginTransaction().replace(R.id.loginContentContainer, LoginFragment()).commit()
        }
        else{
            if(sharedPrefs.getBoolean(KEY_IS_LOGGED_IN, false)){
                startActivity(Intent(baseContext, HomeActivity::class.java))
                finish()
            }
            else{
                supportFragmentManager.beginTransaction().replace(R.id.loginContentContainer, LoginFragment()).commit()
            }
        }
    }


}
