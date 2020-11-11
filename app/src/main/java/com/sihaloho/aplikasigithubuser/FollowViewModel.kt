package com.sihaloho.aplikasigithubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel(){
    val listFollower = MutableLiveData<ArrayList<UserModul>>()
    fun setFollowers(urlFollowers: String) {
        val listItems = ArrayList<UserModul>()
        RetrofitClient.instance.getFollow("$urlFollowers").enqueue(object : Callback<ArrayList<UserModul>> {
            override fun onFailure(call: Call<ArrayList<UserModul>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<UserModul>>,
                response: Response<ArrayList<UserModul>>
            ) {
                response.body()?.let { listItems.addAll(it) }
                listFollower.postValue(listItems)
            }

        })
    }

    fun getFollowe(): LiveData<ArrayList<UserModul>> {
        return listFollower
    }

}