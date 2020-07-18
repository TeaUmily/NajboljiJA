package org.cnzd.najboljija.ui.upside_down_challenge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.ui.upside_down_challenge.view_model.UpsideDownVM
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UpsideDownQuestionsFragment: BaseFragment<UpsideDownVM>(){

    override val viewModel by sharedViewModel<UpsideDownVM>()
    override fun getLayoutResources() = R.layout.fragment_upside_down_questions


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return super.onCreateView(inflater, container, savedInstanceState)


    }

}