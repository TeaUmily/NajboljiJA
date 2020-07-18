package org.cnzd.najboljija.firebase.authentication


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single
import java.lang.Exception


class FirebaseAuthenticationManager : FirebaseAuthenticationInterface {

    private val authentication = FirebaseAuth.getInstance()

    override fun getCurrentUser(): FirebaseUser? {
        return authentication.currentUser
    }



}