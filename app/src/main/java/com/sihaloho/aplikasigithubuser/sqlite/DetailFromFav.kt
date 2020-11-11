package com.sihaloho.aplikasigithubuser.sqlite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sihaloho.aplikasigithubuser.R

class DetailFromFav : AppCompatActivity() {

    companion object {
        const val EXTRA_NAME = "extra_name"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_from_fav)

        val login =intent.getStringExtra(EXTRA_NAME)
//        getUser(login)

    }
}