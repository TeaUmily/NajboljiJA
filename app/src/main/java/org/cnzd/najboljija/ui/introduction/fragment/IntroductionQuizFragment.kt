package org.cnzd.najboljija.ui.introduction.fragment

import android.os.Bundle
import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_introduction_quiz.*
import kotlinx.android.synthetic.main.layout_on_quiz_fnished.*
import kotlinx.android.synthetic.main.layout_quiz_instructions.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.interactor.addSchedulers
import org.cnzd.najboljija.common.utils.toInvisible
import org.cnzd.najboljija.common.utils.toVisible
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.concurrent.TimeUnit

class IntroductionQuizFragment : BaseFragment<IntroductionVM>() {

    override val viewModel by sharedViewModel<IntroductionVM>()
    override fun getLayoutResources() = R.layout.fragment_introduction_quiz

    lateinit var questionsList: Array<String>
    var questionIndex = 0
    var correct = 0
    var wrong = 0
    var repeatTimes = 14L
    var subscription: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionsList = resources.getStringArray(R.array.introduction_questions)

        setupQuestionAndTimer()
        initListener()
    }

    private fun initListener() {
        startQuizBtn.setOnClickListener {
            layoutInstructions.toInvisible()
            subscription = getObservable().subscribe({}, { it.printStackTrace() })
        }

        btnCorrect.setOnClickListener {
            correct++
            subscription?.dispose()
            if (questionIndex == 14) {
                onQuizFinished()
            } else {
                repeatTimes = 14L - questionIndex
                setupQuestionAndTimer()
                tvCorrect.text = correct.toString()
                subscription = getObservable().subscribe()
            }
        }

        btnWrong.setOnClickListener {
            wrong++
            subscription?.dispose()
            if (questionIndex == 14) {
                onQuizFinished()
            } else {
                repeatTimes = 14L - questionIndex
                setupQuestionAndTimer()
                tvWrong.text = wrong.toString()
                subscription = getObservable().subscribe()
            }
        }

        btnBack.setOnClickListener {
            activity!!.onBackPressed()
        }
    }


    private fun getObservable(): Observable<Long> {
        return Observable.interval(1, TimeUnit.SECONDS)
                .addSchedulers()
                .take(9)
                .map { 7 - it }
                .doOnNext { tvTimer.text = it.toString() }
                .doOnComplete {
                    wrong++
                    if (questionIndex == 13) {
                        onQuizFinished()
                    } else {
                        questionIndex++
                        setupQuestionAndTimer()
                        tvWrong.text = wrong.toString()
                    }
                }
                .doOnDispose { questionIndex++ }
                .repeat(repeatTimes)
    }

    private fun setupQuestionAndTimer() {
        tvTimer.text = "8"
        tvQuestion.text = questionsList[questionIndex]
    }

    private fun onQuizFinished() {
        tvWrongFinalCount.text = wrong.toString()
        tvCorrectFinalCount.text = correct.toString()
        layoutQuizFinished.toVisible()
    }

    override fun onDestroy() {
        if (subscription != null) {
            if (!subscription?.isDisposed!!) {
                subscription?.dispose()
            }
        }
        super.onDestroy()
    }
}