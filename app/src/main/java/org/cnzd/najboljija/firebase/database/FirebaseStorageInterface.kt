package org.cnzd.najboljija.firebase.database

import android.graphics.Bitmap
import android.net.Uri
import io.reactivex.Observable
import io.reactivex.Single


interface FirebaseStorageInterface {

    fun addPhoto(uri: Uri, coupleIdentifier: String, task: String): Single<Boolean>
    fun getPhoto(coupleIdentifier: String, task: String): Observable<Bitmap>?
}