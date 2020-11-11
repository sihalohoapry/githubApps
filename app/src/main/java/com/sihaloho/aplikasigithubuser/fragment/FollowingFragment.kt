package com.sihaloho.aplikasigithubuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sihaloho.aplikasigithubuser.R
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private var login: String? = null

    companion object {
        const val ARG_PARAMS = "PARAMS"
        fun newInstance(login: String): FollowingFragment {
            val followingFragment = FollowingFragment()
            val bundle = Bundle().apply {
                putString(ARG_PARAMS, login)
            }
            followingFragment.arguments = bundle
            return followingFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        login = arguments?.getString(ARG_PARAMS)
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        section_label.text = login.toString()

//        if (arguments != null) {
//            val username = arguments?.getString(DetailUserActivity.username)
//            section_label.text = username.toString()
//
//        }
    }

}