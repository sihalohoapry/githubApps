package com.sihaloho.aplikasigithubuser.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sihaloho.aplikasigithubuser.DetailUserActivity
import com.sihaloho.aplikasigithubuser.R
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.sqlite.Favorite

class AdapterUser : RecyclerView.Adapter<AdapterUser.Holder>(){

    lateinit var ContextAdapter : Context
    private val mData = ArrayList<UserModul>()
    fun setData(items: ArrayList<UserModul>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }
    var listFav = ArrayList<Favorite>()
        set(listFav) {
            if (listFav.size > 0) {
                this.listFav.clear()
            }
            this.listFav.addAll(listFav)
            Log.d("CHECK", "listFav: $listFav")
            notifyDataSetChanged()
        }

    inner class Holder(view : View) : RecyclerView.ViewHolder(view){

        private var nama : TextView = view.findViewById(R.id.tvNama)
        private var link : TextView = view.findViewById(R.id.tv_link)
        private var civprovile : ImageView = view.findViewById(R.id.civ_foto)

        fun onBind(data : UserModul, context: Context, position: Int){
            nama.text = data.login
            link.text = data.html_url

            Glide.with(context)
                .load(data.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .into(civprovile)



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        ContextAdapter = parent.context
        val view = LayoutInflater.from(ContextAdapter).inflate(R.layout.item_list_user, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = mData.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(mData[position],ContextAdapter,position)
        holder.itemView.setOnClickListener {
            val intent = Intent(ContextAdapter, DetailUserActivity::class.java)
            intent.putExtra(DetailUserActivity.EXTRA_USER, mData[position])
            ContextAdapter.startActivity(intent)
        }
    }

}