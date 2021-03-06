package com.sihaloho.aplikasigithubuser.fragment

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sihaloho.aplikasigithubuser.R

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
//    var login = "test"
//
//    companion object{
//        const val EXTRA_USER = "extra_user"
//    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_text_1, R.string.tab_text_2)


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
//                val bundle = Bundle()
//                bundle.putString(EXTRA_USER, getData())
//                fragment.arguments = bundle
            }
            1 -> fragment = FollowingFragment()
        }
        return fragment as Fragment
    }

    override fun getCount(): Int {
        return 2
    }
    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }
//    private fun getData(): String {
//        return login
//    }
}