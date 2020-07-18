package org.cnzd.najboljija.firebase.database

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import java.lang.Exception
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import io.reactivex.Observable


private const val KEY_USER = "foto_izazov/"

class FirebaseStorageManager : FirebaseStorageInterface {

    private val firebaseStorageRef = FirebaseStorage.getInstance().reference


    override fun addPhoto(uri: Uri, coupleIdentifier: String, task: String): Single<Boolean> {
        return Single.create {emitter ->
            firebaseStorageRef.child("$KEY_USER/$coupleIdentifier/$task.jpg")
                    .putFile(uri)
                    .addOnCompleteListener { task ->
                if (task.isComplete && task.isSuccessful) {
                    emitter.onSuccess(true)
                }
                else{
                    emitter.onError(Exception(task.exception))
                }
            }
        }
    }



    override fun getPhoto(coupleIdentifier: String, task: String) =
        Observable.create<Bitmap> { emitter->
           firebaseStorageRef.child("$KEY_USER/$coupleIdentifier/$task.jpg").getBytes(Long.MAX_VALUE).addOnSuccessListener {
              emitter.onNext(BitmapFactory.decodeByteArray(it, 0, it.size))
           }.addOnFailureListener {
               emitter.onError(it)
           }
       }

}