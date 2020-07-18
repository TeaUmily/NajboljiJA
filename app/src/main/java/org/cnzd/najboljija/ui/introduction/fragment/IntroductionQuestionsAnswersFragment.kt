package org.cnzd.najboljija.ui.introduction.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import kotlinx.android.synthetic.main.fragment_introduction_questions_answers.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.utils.CHILD
import org.cnzd.najboljija.common.utils.EXTRA_STRING_WHO
import org.cnzd.najboljija.common.utils.MENTOR
import org.cnzd.najboljija.common.utils.showToast
import org.cnzd.najboljija.ui.introduction.view_holder.introductionQA
import org.cnzd.najboljija.ui.introduction.view_model.IntroductionVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class IntroductionQuestionsAnswersFragment : BaseFragment<IntroductionVM>(), TickClick {

    private val controller by inject<AnswersDataController> { parametersOf(this) }
    override val viewModel: IntroductionVM by sharedViewModel()

    var selected: String = ""
    lateinit var questionsList: Array<String>
    var positionToScroll: Int = 0

    override fun getLayoutResources() = R.layout.fragment_introduction_questions_answers

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionsList = resources.getStringArray(R.array.introduction_questions)
        recyclerView.adapter = controller.adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setItemViewCacheSize(14)

        val bundle = this.arguments
        when (bundle?.getString(EXTRA_STRING_WHO)) {
            MENTOR -> {
                selected = MENTOR
                tv_who_label.text = sharedPrefs.getString(MENTOR, selected)
            }
            CHILD -> {
                selected = CHILD
                tv_who_label.text = sharedPrefs.getString(CHILD, selected)
            }
        }

        iniListeners()
        initObservers()
    }

    private fun iniListeners() {
        btnSave.setOnClickListener {
            saveAnswers()
        }
    }

    private fun initObservers() {
        viewModel.answersDataMentor.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                if (selected == MENTOR)
                    controller.setData(mergeQuestionsWithAnswers(questionsList, viewModel.answersDataMentor.value!!))
            }
        })

        viewModel.answersDataChild.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                if (selected == CHILD) {
                    controller.setData(mergeQuestionsWithAnswers(questionsList, viewModel.answersDataChild.value!!))
                }
            }
        })
    }

    private fun mergeQuestionsWithAnswers(keys: Array<String>, values: List<String>): Map<String, String> {
        val map = mutableMapOf<String, String>()
        keys.forEachIndexed { index, it ->
            map[it] = values[index]
        }
        return map
    }

    override fun onClick(position: Int, answer: String) {
        positionToScroll = position
        when (selected) {
            MENTOR -> viewModel.updateMentorAnswers(position, answer)
            CHILD -> viewModel.updateChildAnswers(position, answer)

        }
    }

    private fun saveAnswers() {
        when (selected) {
            MENTOR -> viewModel.saveMentorAnswers().doOnComplete { context!!.showToast(R.string.successfully_stored) }.longSubscribe()
            CHILD -> viewModel.saveChildAnswers().doOnComplete { context!!.showToast(R.string.successfully_stored) }.longSubscribe()
        }
    }
}

interface TickClick {
    fun onClick(position: Int, answer: String)
}

class AnswersDataController(private val listener: TickClick) : TypedEpoxyController<Map<String, String>>() {

    var i = 0
    override fun buildModels(data: Map<String, String>) {
        data.forEach {
            introductionQA {
                id(i++)
                questionLabel(it.key)
                answer(it.value)
                clickListener { _, parentView, clickedView, position ->
                    if (clickedView.id == R.id.ivSaveAnswer) {
                        listener.onClick(position, parentView.answerEditText.text.toString())
                    }
                }
            }
        }
    }
}