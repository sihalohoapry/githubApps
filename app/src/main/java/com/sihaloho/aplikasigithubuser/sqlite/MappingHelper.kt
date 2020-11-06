package com.sihaloho.aplikasigithubuser.sqlite

import android.database.Cursor

object MappingHelper {
    fun mapCursorToArrayList(favCursor: Cursor): ArrayList<Favorite> {
        val favList = ArrayList<Favorite>()
        while (favCursor.moveToNext()){
            val id =
                favCursor.getInt(favCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.ID))
            val login =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOGIN))
            val avatar_url =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR_URL))
            val followers_url =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS_URL))
            val following_url =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING_URL))
            val html_url =
                favCursor.getString(favCursor.getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.HTML_URL))
            favList.add(
                Favorite(
                    id,
                    login,
                    avatar_url,
                    followers_url,
                    following_url,
                    html_url
                )
            )
        }
        return favList
    }
}