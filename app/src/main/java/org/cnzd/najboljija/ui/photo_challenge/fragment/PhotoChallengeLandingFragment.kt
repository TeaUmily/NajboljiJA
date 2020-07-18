package org.cnzd.najboljija.ui.photo_challenge.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.TypedEpoxyController
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_introduction_questions_answers.*
import kotlinx.android.synthetic.main.fragment_photo_challenge_landing.*
import kotlinx.android.synthetic.main.layout_loader.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.fragment_dialog.ChallengeCompletedFragmentDialog
import org.cnzd.najboljija.common.utils.FRAGMENT_INTRODUCTION_QUIZ
import org.cnzd.najboljija.common.utils.KEY_LOCAL_ID
import org.cnzd.najboljija.common.utils.KEY_TOKEN_ID
import org.cnzd.najboljija.ui.photo_challenge.view_holder.photoChallengeTaskHolder
import org.cnzd.najboljija.ui.photo_challenge.view_model.PhotoChallengeTask
import org.cnzd.najboljija.ui.photo_challenge.view_model.PhotoChallengeVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class PhotoChallengeLandingFragment : BaseFragment<PhotoChallengeVM>(), TaskClick {

    private val controller by inject<PhotoTasksController> { parametersOf(this) }
    override val viewModel by sharedViewModel<PhotoChallengeVM>()
    override fun getLayoutResources() = R.layout.fragment_photo_challenge_landing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewPhotoChallenge.adapter = controller.adapter
        recyclerViewPhotoChallenge.layoutManager = LinearLayoutManager(context)
        viewModel.localId = sharedPrefs.getString(KEY_LOCAL_ID, " ")
        viewModel.tokenId = sharedPrefs.getString(KEY_TOKEN_ID, " ")
        cofigureObservers()

    }

    private fun cofigureObservers() {
        val tasksList = resources.getStringArray(R.array.photo_challenge_tasks)
        viewModel.photoChallengeData.observe(viewLifecycleOwner, Observer {
            it.apply {
                it[0].enabled = true
                it.forEachIndexed { index, task ->
                    task.taskText = tasksList[index]
                    if (task.completed) {
                        task.enabled = true
                        if(index <= 7){
                            it[index + 1].enabled = true
                        }
                    }
                }

                val completedNumber = it.count { item -> item.completed }
                photoChallengeProgressBar.progress = completedNumber
                tvPhotoChallengeProgress.text = "$completedNumber/9"
                 if(completedNumber == 9 && !viewModel.challengeState!! ){
                      viewModel.setChallengeToCompleted().doOnComplete { showChallengeCompletedDialog() }.longSubscribe()
                  }
                controller.setData(it)
            }

        })
        val sub1 = viewModel.getChallengeState()
        val sub2 = viewModel.getPhotoChallengeData()
        Observable.concat(sub1, sub2).longSubscribe()
    }

    override fun onTaskClick(position: Int) {
        val fragment = PhotoChallengeAddPhotoFragment().newInstance(position)
        fragmentManager!!.beginTransaction().replace(R.id.contentContainer, fragment).addToBackStack(FRAGMENT_INTRODUCTION_QUIZ).commit()

    }

    private fun showChallengeCompletedDialog() {
        val dialog = ChallengeCompletedFragmentDialog()
        dialog.setChallengeNameAndMessage(getString(R.string.photo_challenge_title), getString(R.string.photo_challenge_completed_message))
        dialog.show(childFragmentManager, dialog.tag)
    }

}

interface TaskClick {
    fun onTaskClick(position: Int)
}

class PhotoTasksController(private val listener: TaskClick) : TypedEpoxyController<List<PhotoChallengeTask>>() {

    override fun buildModels(data: List<PhotoChallengeTask>) {
        data.forEachIndexed { index, s ->
            photoChallengeTaskHolder {
                id(index)
                task(s.taskText!!)
                enabled(s.enabled)
                completed(s.completed)
                clickListener { model, parentView, clickedView, position ->
                    if (clickedView.id == R.id.clContainer) {
                        listener.onTaskClick(position)
                    }
                }
            }
        }
    }

}
