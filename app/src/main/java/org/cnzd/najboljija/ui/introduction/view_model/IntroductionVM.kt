package org.cnzd.najboljija.ui.introduction.view_model

import androidx.lifecycle.MutableLiveData
import org.cnzd.najboljija.base.BaseViewModel
import org.cnzd.najboljija.common.interactor.Interactor
import org.cnzd.najboljija.common.utils.INTRODUCTION

class IntroductionVM(private val interactor: Interactor) : BaseViewModel() {

    val answersDataMentor = MutableLiveData<MutableList<String>>()
    val answersDataChild = MutableLiveData<MutableList<String>>()
    val mentorCompletedAnswersNumber = MutableLiveData<Int>()
    val childCompletedAnswersNumber = MutableLiveData<Int>()

    var challengeState: Boolean? = false
    var tokenId: String? = null
    var localId: String? = null

    init {
        answersDataMentor.value = null
        answersDataChild.value = null
    }

    fun getMentorAnswers() =
            interactor.getMentorAnswers(localId!!, tokenId!!)
                    .map { provideAnswersListFromData(it) }
                    .doOnNext { answersDataMentor.value = it; mentorCompletedAnswersNumber.value = countCompleted(it) }

    fun getChildAnswers() =
            interactor.getChildAnswers(localId!!, tokenId!!)
                    .map { provideAnswersListFromData(it) }
                    .doOnNext { answersDataChild.value = it; childCompletedAnswersNumber.value = countCompleted(it) }

    fun saveMentorAnswers() = interactor.saveMentorAnswers(localId!!, tokenId!!, provideAnswersDataFromList(answersDataMentor.value!!))

    fun saveChildAnswers() = interactor.saveChildAnswers(localId!!, tokenId!!, provideAnswersDataFromList(answersDataChild.value!!))

    fun updateMentorAnswers(position: Int, answer: String) {
        answersDataMentor.value!![position] = answer
    }

    fun updateChildAnswers(position: Int, answer: String) {
        answersDataChild.value!![position] = answer
    }

    fun setChallengeToCompleted() = interactor.setChallengeToCompleted(localId!!, INTRODUCTION, tokenId!!)

    fun getChallengeState() = interactor.getChallengeState(localId!!, INTRODUCTION, tokenId!!).doOnNext { challengeState = it }

    private fun countCompleted(it: List<String>): Int {
        return it.filter { it.isNotEmpty() }.count()
    }

}