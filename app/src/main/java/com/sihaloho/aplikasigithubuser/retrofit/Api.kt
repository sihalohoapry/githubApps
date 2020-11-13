package com.sihaloho.aplikasigithubuser.retrofit

import com.sihaloho.aplikasigithubuser.modul.SearchModul
import com.sihaloho.aplikasigithubuser.modul.UserDetailModul
import com.sihaloho.aplikasigithubuser.modul.UserModul
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface Api {
    @GET("users")
    @Headers("Authorization: Token 4092804f73496360affddeb05df2dd7d5ecf5383")
    fun getUserGitHub(): Call<ArrayList<UserModul>>

    @GET("search/users")
    @Headers("Authorization: Token 4092804f73496360affddeb05df2dd7d5ecf5383")
    fun getSearchUser(@Query("q") q : String): Call<SearchModul>

    @GET
    @Headers("Authorization: Token 4092804f73496360affddeb05df2dd7d5ecf5383")
    fun getDetailUser(@Url url: String): Call<UserDetailModul>

    @GET
    @Headers("Authorization: Token 4092804f73496360affddeb05df2dd7d5ecf5383")
    fun getFollow(@Url follow: String): Call<ArrayList<UserModul>>


}