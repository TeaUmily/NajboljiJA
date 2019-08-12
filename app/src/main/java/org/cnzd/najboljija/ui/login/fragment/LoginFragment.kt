package org.cnzd.najboljija.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_login.*
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.ui.login.model.LoginResponse
import org.cnzd.najboljija.ui.home.activity.HomeActivity
import org.cnzd.najboljija.ui.login.view_model.LoginVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class LoginFragment : BaseFragment<LoginVM>() {

    override val viewModel by sharedViewModel<LoginVM>()

    override fun getLayoutResources() = org.cnzd.najboljija.R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        loginBtn.setOnClickListener {
            if (emailInput.text.isNullOrEmpty() && passwordInput.text.isNullOrEmpty()) {
                context!!.showToast(getString(org.cnzd.najboljija.R.string.empty_fields))
            } else if (emailInput.text.isNullOrEmpty()) {
                context!!.showToast(getString(org.cnzd.najboljija.R.string.empty_email_field))
            } else if (passwordInput.text.isNullOrEmpty()) {
                context!!.showToast(getString(org.cnzd.najboljija.R.string.empty_password_field))
            } else {
                if (hasInternet(context)) {
                    viewModel.login()
                            .doOnNext {
                                sharedPrefs.putBoolean(KEY_IS_LOGGED_IN, true)
                                sharedPrefs.putString(KEY_TOKEN_ID, it.idToken)
                                sharedPrefs.putString(KEY_LOCAL_ID, it.localId)
                                val intent = Intent(activity!!.baseContext, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent) }
                            .onErrorReturn {
                                context!!.showToast(getString(org.cnzd.najboljija.R.string.error_occured))
                                LoginResponse()
                            }
                            .longSubscribe()
                } else {
                    context!!.showToast(getString(org.cnzd.najboljija.R.string.no_internet_connection))
                }
            }
        }

        emailInput.onTextChanged { viewModel.email = emailInput.text.toString() }
        passwordInput.onTextChanged { viewModel.password = passwordInput.text.toString() }
    }

}