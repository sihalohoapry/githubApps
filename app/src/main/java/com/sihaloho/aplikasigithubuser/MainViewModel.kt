package com.sihaloho.aplikasigithubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sihaloho.aplikasigithubuser.modul.SearchModul
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserModul>>()
    fun setUserSearch(query: String) {
        val listItems = ArrayList<UserModul>()
        listItems.clear()
        RetrofitClient.instance.getSearchUser("$query").enqueue(object : Callback<SearchModul> {
            override fun onFailure(call: Call<SearchModul>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<SearchModul>,
                response: Response<SearchModul>
            ) {
                response.body()?.items.let { it?.let { it1 -> listItems.addAll(it1) } }
                listUser.postValue(listItems)

            }

        })
    }

    fun getUserSearch(): LiveData<ArrayList<UserModul>> {
        return listUser
    }


}