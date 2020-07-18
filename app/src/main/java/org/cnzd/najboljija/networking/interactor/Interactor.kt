package org.cnzd.najboljija.networking.interactor

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.cnzd.najboljija.networking.rest_interface.RestInterface
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionAnswersResponse
import org.cnzd.najboljija.ui.login.model.LoginRequest

class Interactor(private val restInterface: RestInterface) {

    fun login(apiKey: String, loginRequest: LoginRequest) = restInterface.logIn(apiKey, loginRequest).addSchedulers()
    fun getNames(localId: String, tokenId: String) = restInterface.getNames(localId, tokenId).addSchedulers()
    fun getChallengesState(localId: String, tokenId: String) = restInterface.getChallengesState(localId, tokenId).addSchedulers()
    fun getMentorAnswers(localId: String, tokenId: String) = restInterface.getMentorAnswers(localId, tokenId).addSchedulers()
    fun getChildAnswers(localId: String, tokenId: String) = restInterface.getChildAnswers(localId, tokenId).addSchedulers()
    fun saveMentorAnswers(localId: String, tokenId: String, data: IntroductionAnswersResponse) = restInterface.saveMentorData(localId, tokenId, data).addSchedulers()
    fun saveChildAnswers(localId: String, tokenId: String, data: IntroductionAnswersResponse) = restInterface.saveChildData(localId, tokenId, data).addSchedulers()
    fun setChallengeToCompleted(localId: String, challenge: String, tokenId: String, value: Boolean = true) = restInterface.setChallengeToCompleted(localId, challenge, tokenId, value).addSchedulers()
    fun getChallengeState(localId: String, challenge: String, tokenId: String) = restInterface.getChallengeState(localId, challenge, tokenId).addSchedulers()
    fun getPhotoChallengeData(localId: String, tokenId: String) = restInterface.getPhotoChallengeData(localId, tokenId).addSchedulers()
    fun updatePhotoChallengeTask(localId: String, tokenId: String, task: String) = restInterface.updateTask(localId, task, tokenId, true).addSchedulers()
    fun getIllusionAnswer(localId: String, tokenId: String, illusion: String) = restInterface.getIllusionAnswers(localId, illusion, tokenId).addSchedulers()
    fun saveMentorAnswer(localId: String, tokenId: String, illusion: String, answer: String) = restInterface.saveMentorAnswer(localId, illusion, tokenId, answer).addSchedulers()
    fun saveChildAnswer(localId: String, tokenId: String, illusion: String, answer: String) = restInterface.saveChildAnswer(localId, illusion, tokenId, answer).addSchedulers()
    fun saveIllusionTaskState(localId: String, tokenId: String, illusion: String, state: String) = restInterface.saveIllusionTaskState(localId, illusion, tokenId, state).addSchedulers()
}

fun <T> Observable<T>.addSchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.addSchedulers(): Single<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}
