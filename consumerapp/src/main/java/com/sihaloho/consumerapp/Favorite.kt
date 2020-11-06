package com.sihaloho.consumerapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id : Int = 0,
    var login : String? = null,
    var avatar_url : String? = null,
    var followers_url : String? = null,
    var following_url : String? = null,
    var html_url : String? = null
) : Parcelable
