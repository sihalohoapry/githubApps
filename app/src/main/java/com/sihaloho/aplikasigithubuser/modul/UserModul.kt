package com.sihaloho.aplikasigithubuser.modul

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModul(
    var id : Int,
    var login : String,
    var avatar_url : String,
    var followers_url : String,
    var following_url : String,
    var html_url : String
) : Parcelable