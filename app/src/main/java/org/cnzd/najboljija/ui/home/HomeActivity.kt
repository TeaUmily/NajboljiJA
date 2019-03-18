package org.cnzd.najboljija.ui.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_home.*
import org.cnzd.najboljija.R


class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        val pagerAdapter = MyAdapter(dpToPixels(2, this), supportFragmentManager)
        viewPager.adapter = pagerAdapter
        viewPager.offscreenPageLimit = 9
        viewPager.currentItem = 0
        viewPager.pageMargin = dpToPixels(10, this).toInt()
    }


    fun dpToPixels(dp: Int, context: Context): Float {
        return dp * context.resources.displayMetrics.density
    }


    class MyAdapter(private var maxFactor: Float, fm: FragmentManager) : FragmentPagerAdapter(fm) {


        public fun getFactor(): Float {
            return maxFactor
        }


        override fun getCount(): Int {
            return 6
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> PhotoChallengeFragment()
                else -> PhotoChallengeFragment()
            }
        }
    }

}


