package org.cnzd.najboljija.ui.home.view_model

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import org.cnzd.najboljija.base.BaseViewModel
import org.cnzd.najboljija.common.interactor.Interactor


class HomeVM(private val interactor: Interactor) : BaseViewModel() {

    val namesData = MutableLiveData<String>()
    val challengesState = MutableLiveData<Map<String, Boolean>>()

    var tokenId:String? = null
    var localId: String? = null

    init {
        namesData.value = null
        challengesState.value = null
    }

    fun getHomeData(): Observable<Any> = Observable.concat(getNamesData(), getChallengesStateData())

    fun getNamesData() = interactor.getNames(localId!!, tokenId!!)
            .map { it.getValue("dijete") + " i " + it.getValue("mentor") }
            .doOnNext { namesData.value = it }

    fun getChallengesStateData() = interactor.getChallengesState(localId!!, tokenId!!)
            .doOnNext { challengesState.value = it }
}