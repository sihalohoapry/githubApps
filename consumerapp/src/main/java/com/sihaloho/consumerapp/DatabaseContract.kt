package com.sihaloho.consumerapp

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.sihaloho.aplikasigithubuser"
    const val SCHEME = "content"
    internal class FavoriteColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "favorite"
            const val ID = "_id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
            const val FOLLOWERS_URL = "followers_url"
            const val FOLLOWING_URL = "following_url"
            const val HTML_URL = "html_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()

        }
    }



}