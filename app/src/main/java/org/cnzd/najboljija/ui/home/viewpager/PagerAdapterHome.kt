package org.cnzd.najboljija.ui.home.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.cnzd.najboljija.ui.home.fragment.KnowEachOtherBetterFragment
import org.cnzd.najboljija.ui.home.fragment.PhotoChallengeFragment
import org.cnzd.najboljija.ui.home.fragment.UpsideDownLookFragment

class PagerAdapterHome ( fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment {
        return when (position) {
            0 -> KnowEachOtherBetterFragment()
            1 -> PhotoChallengeFragment()
            2-> UpsideDownLookFragment()
            else -> PhotoChallengeFragment()
        }
    }
}