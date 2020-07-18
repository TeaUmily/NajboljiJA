package org.cnzd.najboljija.ui.upside_down_challenge.view_model

import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import org.cnzd.najboljija.base.BaseViewModel
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.networking.interactor.Interactor

class UpsideDownVM(val interactor: Interactor) : BaseViewModel() {

    val selectedIllusion = MutableLiveData<Int>()
    val firstIllusionAnswers = MutableLiveData<Map<String, String>>()
    val secondIllusionAnswers = MutableLiveData<Map<String, String>>()
    val thirdIllusioAnser = MutableLiveData<Map<String, String>>()
    val fourthIllusionAnswer = MutableLiveData<Map<String, String>>()


    var challengeState: Boolean? = false
    var tokenId: String? = null
    var localId: String? = null
    var illusionTasksCompletedNumber: Int = 0

    init {
        selectedIllusion.value = 0
    }


    fun getIllusionAnswers(illusion: String) = interactor.getIllusionAnswer(localId!!, tokenId!!, illusion)
            .doOnNext {
                when (illusion) {
                    ILLUSION_ELEPHANT -> {
                        firstIllusionAnswers.value = it; if (it.getValue(COMPLETED) == TRUE) {
                            illusionTasksCompletedNumber++
                        }
                    }
                    ILLUSION_VASE_FACE -> {
                        secondIllusionAnswers.value = it; if (it.getValue(COMPLETED) == TRUE) {
                            illusionTasksCompletedNumber++
                        }
                    }
                    ILLUSION_PLAYER_WOMENA -> {
                        thirdIllusioAnser.value = it; if (it.getValue(COMPLETED) == TRUE) {
                            illusionTasksCompletedNumber++
                        }
                    }
                    ILLUSION_DUCK_RABBIT -> {
                        fourthIllusionAnswer.value = it; if (it.getValue(COMPLETED) == TRUE) {
                            illusionTasksCompletedNumber++
                        }
                    }
                }
            }

    fun saveAnswers(illusion: String, childAnswer: String, mentorAnswer: String): Observable<Unit> {
        val obs1 = interactor.saveChildAnswer(localId!!, tokenId!!, illusion, childAnswer)
        val obs2 = interactor.saveMentorAnswer(localId!!, tokenId!!, illusion, mentorAnswer)
        val obs3 = interactor.saveIllusionTaskState(localId!!, tokenId!!, illusion, TRUE)
        return Observable.concat(obs1, obs2, obs3)
    }
}