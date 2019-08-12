package org.cnzd.najboljija.common.fragment_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_dialog_challenge_completed.*
import org.cnzd.najboljija.R

class ChallengeCompletedFragmentDialog : DialogFragment() {

    private var message = ""
    private var challengeName  = ""

    fun setChallengeNameAndMessage(name: String, message: String){
        this.challengeName = name
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.FragmentDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dialog_challenge_completed, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvChallengeCompleted.text = challengeName
        tvChallengeCompletedMessage.text = message
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    companion object {
        fun newInstance() = ChallengeCompletedFragmentDialog()
    }

}