package org.cnzd.najboljija.ui.introduction.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_introduction_landing.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.fragment_dialog.ChallengeCompletedFragmentDialog
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class IntroductionLandingFragment : BaseFragment<IntroductionVM>() {

    override val viewModel by sharedViewModel<IntroductionVM>()
    override fun getLayoutResources() = org.cnzd.najboljija.R.layout.fragment_introduction_landing
    private var isCompletedDialogShown = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.localId = sharedPrefs.getString(KEY_LOCAL_ID, " ")
        viewModel.tokenId = sharedPrefs.getString(KEY_TOKEN_ID, " ")

        val sub1 = viewModel.getChallengeState().doOnNext { if (it) enableQuizButtons() }
        val sub2 = viewModel.getChildAnswers()
        val sub3 = viewModel.getMentorAnswers()
        Observable.concat(sub1, sub2, sub3).longSubscribe()

        tvChildName.text = sharedPrefs.getString(CHILD, getString(R.string.child))!!.split(" ")[0]
        tvMentorName.text = sharedPrefs.getString(MENTOR, getString(R.string.mentor))!!.split(" ")[0]

        initListeners()
        initObservers()
        disableQuizButtons()
    }

    private fun disableQuizButtons() {
        startMentorQuizBtn.apply { ;alpha = 0.3f;isEnabled = false }
        startChildQuizBtn.apply { alpha = 0.3f;isEnabled = false }
    }

    private fun initListeners() {
        editChildBtn.setOnClickListener { showIntroductionQAFragment(CHILD) }
        editMentorBtn.setOnClickListener { showIntroductionQAFragment(MENTOR) }
        startChildQuizBtn.setOnClickListener { showIntroductionQuizFragment(CHILD) }
        startMentorQuizBtn.setOnClickListener { showIntroductionQuizFragment(MENTOR) }
    }

    private fun showIntroductionQAFragment(whoIs: String) {
        val fragment = IntroductionQuestionsAnswersFragment()
        var data = Bundle().apply { putString(EXTRA_STRING_WHO, whoIs) }
        fragment.arguments = data
        fragmentManager!!.beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(FRAGMENT_INTRODUCTION_QA).commit()
    }

    private fun showIntroductionQuizFragment(whoIs: String) {
        if (childProgressBar.progress == 14) {
            val fragment = IntroductionQuizFragment()
            fragmentManager!!.beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(FRAGMENT_INTRODUCTION_QUIZ).commit()
        }
    }

    private fun initObservers() {
        viewModel.childCompletedAnswersNumber.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                childProgressBar.progress = it
                val progressState = "$it/14"
                tvChildProgressbarState.text = progressState
                if (it == 14 && viewModel.mentorCompletedAnswersNumber.value == 14 && !viewModel.challengeState!!) {
                    onChallengeCompleted()
                }
            }
        })

        viewModel.mentorCompletedAnswersNumber.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                mentorProgressBar.progress = it
                val progressState = "$it/14"
                tvMentorProgressbarState.text = progressState
                if (it == 14 && viewModel.childCompletedAnswersNumber.value == 14 && !viewModel.challengeState!!) {
                    onChallengeCompleted()
                }
            }
        })
    }

    private fun onChallengeCompleted() {
        if (!isCompletedDialogShown) {
            viewModel.setChallengeToCompleted().doOnComplete {
                showChallengeCompletedDialog()
                enableQuizButtons()
            }.longSubscribe()
        }
        isCompletedDialogShown = true
    }

    private fun enableQuizButtons() {
        startChildQuizBtn.apply { alpha = 1f;isEnabled = true }
        startMentorQuizBtn.apply { alpha = 1f;isEnabled = true }
    }

    private fun showChallengeCompletedDialog() {
        val dialog = ChallengeCompletedFragmentDialog()
        dialog.setChallengeNameAndMessage(getString(R.string.introduction_title), getString(R.string.introduction_challenge_completed_message))
        dialog.show(childFragmentManager, dialog.tag)
    }
}