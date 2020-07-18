package org.cnzd.najboljija.ui.upside_down_challenge.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_upside_down_first_illusion.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.utils.*
import org.cnzd.najboljija.ui.upside_down_challenge.view_model.UpsideDownVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UpsideDownFirstIllussionFragment : BaseFragment<UpsideDownVM>() {

    override val viewModel by sharedViewModel<UpsideDownVM>()
    override fun getLayoutResources() = R.layout.fragment_upside_down_first_illusion


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvChildName.text = sharedPrefs.getString(CHILD, getString(R.string.child))!!.split(" ")[0]
        tvMentorName.text = sharedPrefs.getString(MENTOR, getString(R.string.mentor))!!.split(" ")[0]

        initListeners()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configureObservers()
    }

    private fun configureObservers() {
        viewModel.firstIllusionAnswers.observe(viewLifecycleOwner, Observer {
            it.apply {
                    val childAnswer = it.getValue(resources.getString(R.string.child_answers))
                    val mentorAnswer = it.getValue(resources.getString(R.string.mentor_answer))
                    if (childAnswer.isNotEmpty() && mentorAnswer.isNotEmpty()) {
                        tvAnswer.toVisible()
                        btnSaveAnswers.toInvisible()
                        etChildAnswer.apply { isEnabled = false; setText(childAnswer) }
                        etMentorAnswer.apply { isEnabled = false; setText(mentorAnswer) }
                    }
            }
        })
    }

    private fun initListeners() {
        btnSaveAnswers.setOnClickListener {
            if (etChildAnswer.text.isEmpty() || etMentorAnswer.text.isEmpty()) {
                activity!!.showToast(resources.getString(R.string.fill_both_fields))
            } else {
                viewModel.saveAnswers(ILLUSION_ELEPHANT, etChildAnswer.text.toString(), etMentorAnswer.text.toString())
                        .doOnComplete {
                            btnSaveAnswers.toInvisible()
                            btnShowAnswer.toVisible()
                            etChildAnswer.isEnabled = false
                            etMentorAnswer.isEnabled = false
                        }.longSubscribe()
            }
        }

        btnShowAnswer.setOnClickListener {
            tvAnswer.toVisible()
            btnShowAnswer.toInvisible()
        }
    }

}