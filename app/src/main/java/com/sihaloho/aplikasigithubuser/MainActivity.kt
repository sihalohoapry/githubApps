package com.sihaloho.aplikasigithubuser

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sihaloho.aplikasigithubuser.adapter.AdapterUser
import com.sihaloho.aplikasigithubuser.menu.FavoriteActivity
import com.sihaloho.aplikasigithubuser.menu.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var progressBar: ProgressDialog

    private lateinit var adapter: AdapterUser
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        adapter = AdapterUser()
        adapter.notifyDataSetChanged()

        progressBar = ProgressDialog(this)
        progressBar.setMessage("Mohon Tunggu")

        rv_list_user.setHasFixedSize(true)
        rv_list_user.layoutManager = LinearLayoutManager(this)
        rv_list_user.adapter = adapter


        et_search.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
                if (cs.toString().toLowerCase()!=null){
                    textView.visibility = View.GONE
                    mainViewModel.setUserSearch(cs.toString().toLowerCase())
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })




        mainViewModel.getUserSearch().observe(this, Observer { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
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