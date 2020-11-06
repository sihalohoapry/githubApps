package com.sihaloho.aplikasigithubuser

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sihaloho.aplikasigithubuser.adapter.AdapterUser
import com.sihaloho.aplikasigithubuser.menu.SettingActivity
import com.sihaloho.aplikasigithubuser.modul.UserDetailModul
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.retrofit.RetrofitClient
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract
import com.sihaloho.aplikasigithubuser.sqlite.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.sihaloho.aplikasigithubuser.sqlite.Favorite
import com.sihaloho.aplikasigithubuser.sqlite.FavoriteHelper
import kotlinx.android.synthetic.main.activity_detail_user.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {


    private val listFolowing = ArrayList<UserModul>()
    private val listFollowers = ArrayList<UserModul>()

    private lateinit var progressBar: ProgressDialog

    private var favorite: Favorite? = null
    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper
    private var isFav = false
    private lateinit var uriWithId: Uri

    companion object{
        const val RESULT_ADD = 101
        const val EXTRA_FAV = "extra_fav"
        const val EXTRA_POSITION_FAV = "extra_position_fav"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        progressBar = ProgressDialog(this)
        progressBar.setMessage("Mohon Tunggu")


        getDetailUSer()
        rv_follow.setHasFixedSize(true)
        rv_follow.layoutManager = LinearLayoutManager(this)
        getFollowers()


        cv_followers.setOnClickListener {
            cv_following.setCardBackgroundColor(Color.parseColor("#a1a1a1"))
            cv_followers.setCardBackgroundColor(Color.WHITE)
            getFollowers()
        }

        cv_following.setOnClickListener {
            cv_followers.setCardBackgroundColor(Color.parseColor("#a1a1a1"))
            cv_following.setCardBackgroundColor(Color.WHITE)
            getFollowing()
        }

        openSQL()

        fab_favorite.setOnClickListener {
            addanddelete()
        }


    }

    private fun openSQL() {
        favoriteHelper = FavoriteHelper.getInstance(this)
        favoriteHelper.open()
        favorite = intent?.getParcelableExtra(EXTRA_FAV)
        if (favorite != null) {
            position = intent.getIntExtra(EXTRA_FAV, 0)
            isFav = true

        } else {
            favorite = Favorite()
        }

    }

    private fun addanddelete() {
        val data = intent.getParcelableExtra<UserModul?>("data")
        val id = data?.id
        val login = data?.login
        val avatar = data?.avatar_url
        val follower = data?.followers_url
        val following = data?.following_url
        val html = data?.html_url

        val intent = Intent()
        intent.putExtra(EXTRA_FAV, favorite)
        intent.putExtra(EXTRA_POSITION_FAV, position)

        val values = ContentValues()
        values.put(DatabaseContract.FavoriteColumns.ID, id)
        values.put(DatabaseContract.FavoriteColumns.LOGIN, login)
        values.put(DatabaseContract.FavoriteColumns.AVATAR_URL, avatar)
        values.put(DatabaseContract.FavoriteColumns.FOLLOWERS_URL, follower)
        values.put(DatabaseContract.FavoriteColumns.FOLLOWING_URL, following)
        values.put(DatabaseContract.FavoriteColumns.HTML_URL, html)

        val result = favoriteHelper.insert(values)
        if (result > 0) {
//            favorite?.id = result.toInt()
//            setResult(RESULT_ADD, intent)
            contentResolver.insert(CONTENT_URI, values)
            Toast.makeText(this, "Berhasil menambahkan ke favorite", Toast.LENGTH_SHORT).show()
        } else {
//            favoriteHelper = FavoriteHelper.getInstance(this)
//            favoriteHelper.open()
//            favoriteHelper.deleteById(data?.id.toString())
            uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + data?.id)
            contentResolver.delete(uriWithId, null, null)
            Toast.makeText(this,"Berhasil Dihapus", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFollowing() {
        progressBar.show()
        val data = intent.getParcelableExtra<UserModul?>("data")
        val login = data?.login
        listFolowing.clear()
        RetrofitClient.instance.getFollow("https://api.github.com/users/$login/following").enqueue(object : Callback<ArrayList<UserModul>>{
            override fun onFailure(call: Call<ArrayList<UserModul>>, t: Throwable) {
                Toast.makeText(this@DetailUserActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<UserModul>>,
                response: Response<ArrayList<UserModul>>
            ) {
                progressBar.dismiss()
               response.body()?.let { listFolowing.addAll(it) }
                val adapter = AdapterUser(listFolowing){
                    if (listFolowing.isNotEmpty()){
                        val intent = Intent(baseContext, DetailUserActivity::class.java)
                            .putExtra("data", it)
                        startActivity(intent)
                        finishAffinity()
                    }
                }
                rv_follow.adapter = adapter
            }

        })

    }

    private fun getFollowers() {
        val data = intent.getParcelableExtra<UserModul?>("data")
        val url = data?.followers_url

        listFollowers.clear()
        RetrofitClient.instance.getFollow("$url").enqueue(object : Callback
        <ArrayList<UserModul>>{
            override fun onFailure(call: Call<ArrayList<UserModul>>, t: Throwable) {
                Toast.makeText(this@DetailUserActivity, "$t", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<ArrayList<UserModul>>,
                response: Response<ArrayList<UserModul>>
            ) {
                progressBar.dismiss()
                response.body()?.let { listFollowers.addAll(it) }
                val adapter = AdapterUser(listFollowers){
                    if (listFolowing.isNotEmpty()){
                        val intent = Intent(this@DetailUserActivity, DetailUserActivity::class.java)
                            .putExtra("data", it)
                        startActivity(intent)
                    }
                }
                rv_follow.adapter = adapter
            }

        })
    }


    private fun getDetailUSer() {

        listFollowers.clear()
        progressBar.show()
        val data = intent.getParcelableExtra<UserModul?>("data")
        val tvLogin = data?.login.toString()

        RetrofitClient.instance.getDetailUser("users/$tvLogin")
            .enqueue(object : Callback<UserDetailModul> {
                override fun onFailure(call: Call<UserDetailModul>, t: Throwable) {
                    Toast.makeText(this@DetailUserActivity, "$t", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<UserDetailModul>,
                    response: Response<UserDetailModul>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        tv_nama.text = data?.name
                        tv_nickname.text = data?.login
                        tv_alamat.text = data?.location
                        if (data?.location.isNullOrBlank()) {
                            tv_alamat.text = getString(R.string.nolocation)
                        }
                        tv_followers.text = data?.followers.toString()
                        tv_following.text = data?.following.toString()
                        Glide.with(baseContext)
                            .load(data?.avatar_url)
                            .apply(RequestOptions.circleCropTransform())
                            .into(iv_profile_detail)
                        progressBar.dismiss()

                    }


                }


            })
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


}