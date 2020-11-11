package com.sihaloho.aplikasigithubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel(){

    val listFollowing = MutableLiveData<ArrayList<UserModul>>()
    fun setFollowing(login: String) {
        val listItemsFollowing = ArrayList<UserModul>()
        RetrofitClient.instance.getFollow("https://api.github.com/users/$login/following").enqueue(object : Callback<ArrayList<UserModul>> {
            override fun onFailure(call: Call<ArrayList<UserModul>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<UserModul>>,
                response: Response<ArrayList<UserModul>>
            ) {
                response.body()?.let { listItemsFollowing.addAll(it) }
                listFollowing.postValue(listItemsFollowing)
            }

        })
    }

    fun getFollowing(): LiveData<ArrayList<UserModul>> {
        return listFollowing
    }
}