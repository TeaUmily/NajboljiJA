package org.cnzd.najboljija.ui.photo_challenge.view_model

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.cnzd.najboljija.base.BaseViewModel
import org.cnzd.najboljija.common.utils.INTRODUCTION
import org.cnzd.najboljija.common.utils.PHOTO_CHALLENGE
import org.cnzd.najboljija.firebase.database.FirebaseStorageInterface
import org.cnzd.najboljija.networking.interactor.Interactor
import org.cnzd.najboljija.networking.interactor.addSchedulers

class PhotoChallengeVM(private val interactor: Interactor, private val storage: FirebaseStorageInterface
                       ): BaseViewModel() {

    val photoChallengeData = MutableLiveData<List<PhotoChallengeTask>>()

    var challengeState: Boolean? = false
    var tokenId: String? = null
    var localId: String? = null


    fun getPhotoChallengeData() =  interactor.getPhotoChallengeData(localId!!, tokenId!!)
            .map { providePhotoChallengeUI(it) }
            .doOnNext { photoChallengeData.value = it }


    fun savePhoto(filepath: Uri, path: String, position: Int)= storage.addPhoto(filepath, path, photoChallengeData.value!![position].taskText!!).flatMap { interactor.updatePhotoChallengeTask(localId!!, tokenId!!, photoChallengeData.value!![position].text) }.addSchedulers()

    fun getPhoto(path: String, position: Int) = storage.getPhoto(path, photoChallengeData.value!![position].taskText!!)!!.addSchedulers()

    fun getChallengeState() = interactor.getChallengeState(localId!!, PHOTO_CHALLENGE, tokenId!!).doOnNext { challengeState = it }

    fun setChallengeToCompleted() = interactor.setChallengeToCompleted(localId!!, PHOTO_CHALLENGE, tokenId!!)

}