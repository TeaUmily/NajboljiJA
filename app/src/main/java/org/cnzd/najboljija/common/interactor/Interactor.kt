package org.cnzd.najboljija.common.interactor

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.cnzd.najboljija.common.rest_interface.RestInterface
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionAnswersdata
import org.cnzd.najboljija.ui.login.model.LoginRequest

class Interactor(private val restInterface: RestInterface) {

    fun logIn(apiKey: String, loginRequest: LoginRequest) = restInterface.logIn(apiKey, loginRequest).addSchedulers()
    fun getNames(localId: String, tokenId: String) = restInterface.getNames(localId, tokenId).addSchedulers()
    fun getChallengesState(localId: String, tokenId: String) = restInterface.getChallengesState(localId, tokenId).addSchedulers()
    fun getMentorAnswers(localId: String, tokenId: String) = restInterface.getMentorAnswers(localId, tokenId).addSchedulers()
    fun getChildAnswers(localId: String, tokenId: String) = restInterface.getChildAnswers(localId, tokenId).addSchedulers()
    fun saveMentorAnswers(localId: String, tokenId: String, data: IntroductionAnswersdata) = restInterface.saveMentorData(localId,tokenId, data).addSchedulers()
    fun saveChildAnswers(localId: String, tokenId: String, data: IntroductionAnswersdata) = restInterface.saveChildData(localId, tokenId, data).addSchedulers()
    fun setChallengeToCompleted(localId: String, challenge: String, tokenId: String, value:Boolean = true) = restInterface.setChallengeToCompleted(localId,challenge,tokenId,value).addSchedulers()
    fun getChallengeState(localId: String, challenge: String, tokenId: String) = restInterface.getChallengeState(localId,challenge,tokenId).addSchedulers()
}

fun <T> Observable<T>.addSchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}