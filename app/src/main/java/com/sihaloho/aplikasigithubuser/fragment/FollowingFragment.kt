package com.sihaloho.aplikasigithubuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihaloho.aplikasigithubuser.DetailUserActivity
import com.sihaloho.aplikasigithubuser.DetailUserActivity.Companion.EXTRA_NAME
import com.sihaloho.aplikasigithubuser.FollowingViewModel
import com.sihaloho.aplikasigithubuser.R
import com.sihaloho.aplikasigithubuser.adapter.AdapterUser
import com.sihaloho.aplikasigithubuser.modul.UserModul
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    private lateinit var adapter: AdapterUser


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AdapterUser()
        adapter.notifyDataSetChanged()
        rv_following.setHasFixedSize(true)
        rv_following.layoutManager = LinearLayoutManager(context)
        rv_following.adapter = adapter
        getFollowing()

    }

    private fun getFollowing() {
        val user = activity?.intent?.getParcelableExtra<UserModul>(DetailUserActivity.EXTRA_USER)
        if (user != null){
            val user = activity?.intent?.getParcelableExtra<UserModul>(DetailUserActivity.EXTRA_USER)
            val login = user?.login.toString()
            val followingViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    FollowingViewModel::class.java
                )
            followingViewModel.setFollowing(login)
            followingViewModel.getFollowing().observe(this, Observer { userItemsFollowing ->
                if (userItemsFollowing != null) {
                    adapter.setData(userItemsFollowing)
                }
            })
        }
        if (user == null){
            val login =activity?.intent?.getStringExtra(EXTRA_NAME)
            val followingViewModel =
                ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                    FollowingViewModel::class.java
                )
            followingViewModel.setFollowing(login!!)
            followingViewModel.getFollowing().observe(this, Observer { userItemsFollowing ->
                if (userItemsFollowing != null) {
                    adapter.setData(userItemsFollowing)
                }
            })
        }


    }

}