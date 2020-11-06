package com.sihaloho.aplikasigithubuser.modul

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetailModul(
    var id : Int,
    var login : String,
    var name : String,
    var avatar_url : String,
    var followers_url : String,
    var following_url : String,
    var location : String,
    var followers : Int,
    var following : Int
) : Parcelable