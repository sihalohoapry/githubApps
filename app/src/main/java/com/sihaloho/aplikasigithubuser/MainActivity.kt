package com.sihaloho.aplikasigithubuser

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihaloho.aplikasigithubuser.adapter.AdapterUser
import com.sihaloho.aplikasigithubuser.menu.FavoriteActivity
import com.sihaloho.aplikasigithubuser.menu.SettingActivity
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<UserModul>()
    private lateinit var  progressBar : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = ProgressDialog(this)
        progressBar.setMessage("Mohon Tunggu")

        rv_list_user.setHasFixedSize(true)
        rv_list_user.layoutManager = LinearLayoutManager(this)
        getUser()


    }


    private fun getUser() {
        list.clear()
        progressBar.show()
        RetrofitClient.instance.getUserGitHub().enqueue(object : Callback<ArrayList<UserModul>>{
            override fun onFailure(call: Call<ArrayList<UserModul>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<UserModul>>,
                response: Response<ArrayList<UserModul>>
            ) {
                progressBar.dismiss()
                response.body()?.let { list.addAll(it) }
                val adapter = AdapterUser(list){
                    if (list.isNotEmpty()){
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
        inflater.inflate(R.menu.main_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_favorit->{
                startActivity(Intent(this,
                    FavoriteActivity::class.java))

            }
            R.id.menu_setting->{
                startActivity(Intent(this,
                    SettingActivity::class.java))

            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }

        return true
    }


}