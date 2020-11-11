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
import com.sihaloho.aplikasigithubuser.FollowViewModel
import com.sihaloho.aplikasigithubuser.R
import com.sihaloho.aplikasigithubuser.adapter.AdapterUser
import com.sihaloho.aplikasigithubuser.modul.UserModul
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_following.*


class FollowersFragment : Fragment() {

    private lateinit var adapter: AdapterUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = AdapterUser()
        adapter.notifyDataSetChanged()
        rv_followers.setHasFixedSize(true)
        rv_followers.layoutManager = LinearLayoutManager(context)
        rv_followers.adapter = adapter
        getFollowers()

    }

    private fun getFollowers() {
        val data = activity?.intent?.getParcelableExtra<UserModul?>(DetailUserActivity.EXTRA_USER)
        val login = data?.login.toString()
        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowViewModel::class.java)
        followViewModel.setFollowers(login)
        followViewModel.getFollowe().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
            }
        })
    }

}