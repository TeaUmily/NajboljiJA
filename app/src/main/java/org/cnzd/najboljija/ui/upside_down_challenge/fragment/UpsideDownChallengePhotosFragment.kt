package org.cnzd.najboljija.ui.upside_down_challenge.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_upside_down_challenge_photos.*
import kotlinx.android.synthetic.main.fragment_upside_down_look_home.*
import org.cnzd.najboljija.R
import org.cnzd.najboljija.base.BaseFragment
import org.cnzd.najboljija.common.utils.ILLUSION_DUCK_RABBIT
import org.cnzd.najboljija.common.utils.ILLUSION_ELEPHANT
import org.cnzd.najboljija.common.utils.ILLUSION_PLAYER_WOMENA
import org.cnzd.najboljija.common.utils.ILLUSION_VASE_FACE
import org.cnzd.najboljija.ui.upside_down_challenge.adapter.ViewPagerAdapterUpsideDownChallenge
import org.cnzd.najboljija.ui.upside_down_challenge.view_model.UpsideDownVM
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf

class UpsideDownChallengePhotosFragment : BaseFragment<UpsideDownVM>() {

    override val viewModel by sharedViewModel<UpsideDownVM>()
    override fun getLayoutResources() = R.layout.fragment_upside_down_challenge_photos
    private val pagerAdapter by inject<ViewPagerAdapterUpsideDownChallenge> { parametersOf(childFragmentManager) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager.adapter = pagerAdapter
        viewPager.currentItem = 0

        initListeners()
        configureObservers()

    }

    private fun configureObservers() {
        viewModel.selectedIllusion.observe(viewLifecycleOwner, Observer {
            it.apply {
                viewPager.currentItem = it
            }
        })
    }

    private fun deselectLastSelected() {
        when (viewModel.selectedIllusion.value) {
            0 -> tvFirstIllusion.textSize = 14F
            1 -> tvSecondIllusion.textSize = 14F
            2 -> tvThirdIllusion.textSize = 14F
            3 -> tvFourthIllusion.textSize = 14F
        }
    }

    private fun initListeners() {
        tvFirstIllusion.setOnClickListener {
            if (viewModel.selectedIllusion.value != 0) {
                deselectLastSelected()
                tvFirstIllusion.textSize = 20F
                viewModel.selectedIllusion.value = 0
            }
        }
        tvSecondIllusion.setOnClickListener {
            if (viewModel.selectedIllusion.value != 1) {
                deselectLastSelected()
                tvSecondIllusion.textSize = 20F
                viewModel.selectedIllusion.value = 1
            }
        }
        tvThirdIllusion.setOnClickListener {
            if (viewModel.selectedIllusion.value != 2) {
                deselectLastSelected()
                tvThirdIllusion.textSize = 20F
                viewModel.selectedIllusion.value = 2
            }
        }
        tvFourthIllusion.setOnClickListener {
            if (viewModel.selectedIllusion.value != 3) {
                deselectLastSelected()
                tvFourthIllusion.textSize = 20F
                viewModel.selectedIllusion.value = 3
            }
        }
    }

}