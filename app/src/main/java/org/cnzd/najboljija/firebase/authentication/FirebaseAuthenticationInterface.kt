package org.cnzd.najboljija.firebase.authentication

import com.google.firebase.auth.FirebaseUser
import io.reactivex.Single


interface FirebaseAuthenticationInterface {
  fun getCurrentUser(): FirebaseUser?
}