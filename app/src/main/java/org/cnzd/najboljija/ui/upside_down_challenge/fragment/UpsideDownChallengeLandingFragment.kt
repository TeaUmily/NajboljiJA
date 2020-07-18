package org.cnzd.najboljija.ui.upside_down_challenge.fragment

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_introduction_quiz.*
import kotlinx.android.synthetic.main.fragment_upside_down_challenge_landing.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.fragment_dialog.ChallengeCompletedFragmentDialog
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.ui.upside_down_challenge.view_model.UpsideDownVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UpsideDownChallengeLandingFragment : BaseFragment<UpsideDownVM>() {

    override val viewModel by sharedViewModel<UpsideDownVM>()
    override fun getLayoutResources() = R.layout.fragment_upside_down_challenge_landing


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.localId = sharedPrefs.getString(KEY_LOCAL_ID, " ")
        viewModel.tokenId = sharedPrefs.getString(KEY_TOKEN_ID, " ")

        val obs1 = viewModel.getIllusionAnswers(ILLUSION_ELEPHANT)
        val obs2 = viewModel.getIllusionAnswers(ILLUSION_VASE_FACE)
        val obs3 = viewModel.getIllusionAnswers(ILLUSION_PLAYER_WOMENA)
        val obs4 = viewModel.getIllusionAnswers(ILLUSION_DUCK_RABBIT)
        Observable.concat(obs1, obs2, obs3, obs4)
                .doOnComplete {
                    if (viewModel.illusionTasksCompletedNumber == 4) {
                        ivPhotosCompletedTick.toVisible()
                        rlQuestions.apply {
                            isEnabled = true
                            alpha = 1F
                        }
                    }
                }.longSubscribe()


        rlPhotos.setOnClickListener { activity!!.supportFragmentManager.beginTransaction().replace(R.id.contentContainer, UpsideDownChallengePhotosFragment()).addToBackStack("bla").commit() }
        rlQuestions.setOnClickListener { activity!!.supportFragmentManager.beginTransaction().replace(R.id.contentContainer, UpsideDownQuestionsFragment()).addToBackStack("bla").commit() }
        rlTask.setOnClickListener { showChallengeCompletedDialog()}
    }

    private fun showChallengeCompletedDialog() {
        val dialog = ChallengeCompletedFragmentDialog()
        dialog.setChallengeNameAndMessage(getString(R.string.look_upside_down_title), getString(R.string.upside_down_challenge_completed_message))
        dialog.show(childFragmentManager, dialog.tag)
    }
}