package org.cnzd.najboljija.ui.upside_down_challenge.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.cnzd.najboljija.ui.upside_down_challenge.fragment.UpsideDownFirstIllussionFragment
import org.cnzd.najboljija.ui.upside_down_challenge.fragment.UpsideDownFourthIllusionFragment
import org.cnzd.najboljija.ui.upside_down_challenge.fragment.UpsideDownSecondIllusionFragment
import org.cnzd.najboljija.ui.upside_down_challenge.fragment.UpsideDownThirdIllusionFragment

class ViewPagerAdapterUpsideDownChallenge( fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment{
        return  when(position){
            0 -> UpsideDownFirstIllussionFragment()
            1 -> UpsideDownSecondIllusionFragment()
            2 -> UpsideDownThirdIllusionFragment()
            3 -> UpsideDownFourthIllusionFragment()
            else -> UpsideDownFirstIllussionFragment()
        }
    }

}