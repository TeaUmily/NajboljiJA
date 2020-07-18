package org.cnzd.najboljija.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_introduction_home.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.ui.home.view_model.HomeVM
import org.cnzd.najboljija.ui.introduction.activity.IntroductionActivity
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ActiveListenerFragment :  Fragment() {

    private val viewModel by sharedViewModel<HomeVM>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_active_listener_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnStartChallenge.setOnClickListener { Toast.makeText(context, "Aktivni slušač", Toast.LENGTH_SHORT).show()  }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}