package com.sihaloho.aplikasigithubuser.sqlite

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sihaloho.aplikasigithubuser.R
import kotlinx.android.synthetic.main.activity_detail_user.*
import kotlinx.android.synthetic.main.item_fav.view.*

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.CardViewViewHolder>() {

    private var onItemClickCallback : OnItemClickCallback? = null

    interface OnItemClickCallback{
        fun onItemClicked(data: Favorite)
    }

    var listFav = ArrayList<Favorite>()
        set(listfav) {
            if (listfav.size > 0) {
                this.listFav.clear()
            }
            this.listFav.addAll(listfav)
            notifyDataSetChanged()
        }

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favItem: Favorite) {
            with(itemView) {
                tv_nama.text = favItem.login
                tv_link.text = favItem.html_url
                Glide.with(context)
                    .load(favItem.avatar_url)
                    .apply(RequestOptions.circleCropTransform())
                    .into(civ_foto)


            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardViewViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_fav, viewGroup, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.listFav.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listFav[position])
    }
}