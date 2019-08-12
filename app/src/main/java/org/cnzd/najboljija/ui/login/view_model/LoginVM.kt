package org.cnzd.najboljija.ui.login.view_model

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import org.cnzd.najboljija.base.BaseViewModel
import org.cnzd.najboljija.common.interactor.Interactor
import org.cnzd.najboljija.common.utils.WEB_API_KEY
import org.cnzd.najboljija.ui.login.model.LoginRequest
import org.cnzd.najboljija.ui.login.model.LoginResponse


class LoginVM(val interactor: Interactor
) : BaseViewModel(){

    private val loginLiveData = MutableLiveData<LoginRequest>()

    var email = ""
    var password = ""

    init {
        loginLiveData.value = null
    }

    fun login(): Observable<LoginResponse> {
        loginLiveData.value = LoginRequest(email, password)
        return interactor.logIn(WEB_API_KEY,loginLiveData.value!!)
    }
}

