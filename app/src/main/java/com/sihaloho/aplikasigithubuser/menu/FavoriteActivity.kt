package com.sihaloho.aplikasigithubuser.menu

import android.content.Intent
import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihaloho.aplikasigithubuser.R
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.sihaloho.aplikasigithubuser.sqlite.Favorite
import com.sihaloho.aplikasigithubuser.sqlite.FavoriteAdapter
import com.sihaloho.aplikasigithubuser.sqlite.FavoriteHelper
import com.sihaloho.aplikasigithubuser.sqlite.MappingHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        rv_favorite.setHasFixedSize(true)
        rv_favorite.layoutManager = LinearLayoutManager(this)
        favoriteAdapter = FavoriteAdapter()
        rv_favorite.adapter = favoriteAdapter
//        getFav()

        favoriteHelper = FavoriteHelper.getInstance(this)
        favoriteHelper.open()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadFavAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)


        if (savedInstanceState == null) {
            loadFavAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (list != null) {
                favoriteAdapter.listFav = list
            }
        }

    }


    private fun loadFavAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredFav = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                MappingHelper.mapCursorToArrayList(cursor!!)
            }
            val favorite = deferredFav.await()
            if (favorite.size > 0) {
                favoriteAdapter.listFav = favorite
            } else {
                favoriteAdapter.listFav = ArrayList()
            }
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_setting -> {
                startActivity(
                    Intent(
                        this,
                        SettingActivity::class.java
                    )
                )

            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.listFav)
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}