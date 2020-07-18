package org.cnzd.najboljija.ui.home.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.cnzd.najboljija.ui.home.fragment.*

class PagerAdapterHome ( fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getCount(): Int {
        return 9
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return when (position) {
            0 -> IntroductionFragment()
            1 -> PhotoChallengeFragment()
            2-> UpsideDownLookFragment()
            3-> ExpressYourselfFragment()
            4 -> ActiveListenerFragment()
            5 -> WithoutShameFragment()
            6 -> StandOutForYourSelf()
            7 -> MakeRightDecision()
            8 -> DealWithFeelingsFragment()
            else -> PhotoChallengeFragment()
        }
    }
}