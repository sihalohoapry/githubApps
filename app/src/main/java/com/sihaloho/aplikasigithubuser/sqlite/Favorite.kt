package com.sihaloho.aplikasigithubuser.sqlite

import android.database.Cursor
import android.os.Parcelable
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract.FavoriteColumns.Companion.ID
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract.getColumnInt
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract.getColumnString
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var id : Int = 0,
    var login : String? = null,
    var avatar_url : String? = null,
    var followers_url : String? = null,
    var following_url : String? = null,
    var html_url : String? = null
) : Parcelable{
    constructor(cursor: Cursor) : this(){
        this.id = getColumnInt(cursor, ID)
        this.login = getColumnString(cursor, DatabaseContract.FavoriteColumns.LOGIN)
        this.avatar_url = getColumnString(cursor, DatabaseContract.FavoriteColumns.AVATAR_URL)
        this.followers_url = getColumnString(cursor, DatabaseContract.FavoriteColumns.FOLLOWERS_URL)
        this.following_url = getColumnString(cursor, DatabaseContract.FavoriteColumns.FOLLOWING_URL)
        this.html_url = getColumnString(cursor, DatabaseContract.FavoriteColumns.HTML_URL)
    }
}
