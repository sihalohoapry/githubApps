package com.sihaloho.aplikasigithubuser

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihaloho.aplikasigithubuser.adapter.AdapterUser
import com.sihaloho.aplikasigithubuser.menu.FavoriteActivity
import com.sihaloho.aplikasigithubuser.menu.SettingActivity
import com.sihaloho.aplikasigithubuser.modul.SearchModul
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<UserModul>()
    private val listQ = ArrayList<UserModul>()
    private lateinit var progressBar: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = ProgressDialog(this)
        progressBar.setMessage("Mohon Tunggu")

        rv_list_user.setHasFixedSize(true)
        rv_list_user.layoutManager = LinearLayoutManager(this)
        getUser()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getUserSearch(query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private fun getUserSearch(query: String) {
        progressBar.show()

        RetrofitClient.instance.getSearchUser("$query").enqueue(object : Callback<SearchModul> {
            override fun onFailure(call: Call<SearchModul>, t: Throwable) {
                Toast.makeText(this@MainActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<SearchModul>,
                response: Response<SearchModul>
            ) {
                response.body()?.items.let { it?.let { it1 -> listQ.addAll(it1) } }

                val adapter = AdapterUser(listQ) {
                    if (list.isNotEmpty()) {
                        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                            .putExtra("data", it)
                        startActivity(intent)
                    }
                }
                rv_list_user.adapter = adapter
                progressBar.dismiss()

            }


        })

    }


    private fun getUser() {
        list.clear()
        progressBar.show()
        RetrofitClient.instance.getUserGitHub().enqueue(object : Callback<ArrayList<UserModul>> {
            override fun onFailure(call: Call<ArrayList<UserModul>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<UserModul>>,
                response: Response<ArrayList<UserModul>>
            ) {
                progressBar.dismiss()
                response.body()?.let { list.addAll(it) }
                val adapter = AdapterUser(list) {
                    if (list.isNotEmpty()) {
                        val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                            .putExtra("data", it)
                        startActivity(intent)
                    }
                }
                rv_list_user.adapter = adapter
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_favorit -> {
                startActivity(
                    Intent(
                        this,
                        FavoriteActivity::class.java
                    )
                )

            }
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


}