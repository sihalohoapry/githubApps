package com.sihaloho.aplikasigithubuser

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sihaloho.aplikasigithubuser.fragment.SectionsPagerAdapter
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

    private lateinit var progressBar: ProgressDialog

    private var favorite: Favorite? = null
    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper
    private var isFav = false
    private lateinit var uriWithId: Uri

    companion object {
        const val EXTRA_USER = "extra_user"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_FAV = "extra_fav"
        const val EXTRA_POSITION_FAV = "extra_position_fav"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
        progressBar = ProgressDialog(this)
        progressBar.setMessage("Mohon Tunggu")
        getDetailUSer()

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
        val data = intent.getParcelableExtra<UserModul?>(EXTRA_USER)
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
            contentResolver.insert(CONTENT_URI, values)
            Toast.makeText(this, "Berhasil menambahkan ke favorite", Toast.LENGTH_SHORT).show()
        } else {
            uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + data?.id)
            contentResolver.delete(uriWithId, null, null)
            Toast.makeText(this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getDetailUSer() {

        progressBar.show()
        val data = intent.getParcelableExtra<UserModul?>(EXTRA_USER)

        if (data != null) {
            val data = intent.getParcelableExtra<UserModul?>(EXTRA_USER)

            val tvLogin = data?.login.toString()

            RetrofitClient.instance.getDetailUser("https://api.github.com/users/$tvLogin")
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
                            tvNama.text = data?.name
                            tvNickName.text = data?.login
                            tvAlamat.text = data?.location
                            if (data?.location.isNullOrBlank()) {
                                tvAlamat.text = getString(R.string.nolocation)
                            }
                            tvFollowers.text = data?.followers.toString()
                            tvFollowing.text = data?.following.toString()
                            Glide.with(baseContext)
                                .load(data?.avatar_url)
                                .apply(RequestOptions.circleCropTransform())
                                .into(iv_profile_detail)
                            progressBar.dismiss()

                        }


                    }


                })
        }

        if (data == null) {
            fab_favorite.visibility = View.GONE
            fab_delete.visibility = View.VISIBLE
            val login = intent.getStringExtra(EXTRA_NAME)
            fab_delete.setOnClickListener {
                val id = intent.getStringExtra(EXTRA_ID)
                uriWithId = Uri.parse("$CONTENT_URI/$id")
                contentResolver.delete(uriWithId, null, null)
                Toast.makeText(this, "Berhasil Dihapus", Toast.LENGTH_SHORT).show()
            }
            RetrofitClient.instance.getDetailUser("https://api.github.com/users/$login")
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
                            tvNama.text = data?.name
                            tvNickName.text = data?.login
                            tvAlamat.text = data?.location
                            if (data?.location.isNullOrBlank()) {
                                tvAlamat.text = getString(R.string.nolocation)
                            }
                            tvFollowers.text = data?.followers.toString()
                            tvFollowing.text = data?.following.toString()
                            Glide.with(baseContext)
                                .load(data?.avatar_url)
                                .apply(RequestOptions.circleCropTransform())
                                .into(iv_profile_detail)
                            progressBar.dismiss()

                        }


                    }


                })
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


}