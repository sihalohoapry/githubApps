package com.sihaloho.aplikasigithubuser.remainder

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemsRemainder (
    var reminder: Boolean = false
): Parcelable