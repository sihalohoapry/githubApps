package com.sihaloho.aplikasigithubuser.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sihaloho.aplikasigithubuser.R
import com.sihaloho.aplikasigithubuser.modul.UserModul
import com.sihaloho.aplikasigithubuser.sqlite.Favorite

class AdapterUser(
    private val list: ArrayList<UserModul>,
    private val listener : (UserModul)-> Unit
) : RecyclerView.Adapter<AdapterUser.Holder>(){

    lateinit var ContextAdapter : Context

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

        private var nama : TextView = view.findViewById(R.id.tv_nama)
        private var link : TextView = view.findViewById(R.id.tv_link)
        private var civprovile : ImageView = view.findViewById(R.id.civ_foto)

        fun onBind(data : UserModul, listener: (UserModul) -> Unit, context: Context, position: Int){

            nama.text = data.login
            link.text = data.html_url

            Glide.with(context)
                .load(data.avatar_url)
                .apply(RequestOptions.circleCropTransform())
                .into(civprovile)

            itemView.setOnClickListener{
                listener(data)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        ContextAdapter = parent.context
        val view = LayoutInflater.from(ContextAdapter).inflate(R.layout.item_list_user, parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position],listener,ContextAdapter,position)
    }

}