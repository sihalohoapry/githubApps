package com.sihaloho.aplikasigithubuser.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "dbfavorite"
        private const val DATABASE_VERSION = 2
        private val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavoriteColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavoriteColumns.LOGIN} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.AVATAR_URL} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWERS_URL} TEXT NOT NULL,"+
                " ${DatabaseContract.FavoriteColumns.FOLLOWING_URL} TEXT NOT NULL,"+
                " ${DatabaseContract.FavoriteColumns.HTML_URL} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}