package com.example.thetwo_z

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.thetwo_z.model.User
import com.example.thetwo_z.model.pengaduan
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.item_home.view.*
import kotlinx.android.synthetic.main.item_riwayat.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class adapterRiwayat(
    private val context: Context,
    private val Aduans: ArrayList<pengaduan>
) : RecyclerView.Adapter<adapterRiwayat.ViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): adapterRiwayat.ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_riwayat, p0, false))

    override fun getItemCount(): Int = Aduans.size

    override fun onBindViewHolder(p0: adapterRiwayat.ViewHolder, p1: Int) =
        p0.bindItem(Aduans[p1])

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindItem(aduan: pengaduan){
            itemView.tv_judulRiwayatAduan.text = aduan.judul
            itemView.tv_riwayatAduan.text = aduan.text
            Glide.with(itemView.context).load(aduan.imageAduanUrl).into(itemView.iv_RiwayatAduan)
        //    Picasso.get().load(aduan.imageAduanUrl).into(itemView.iv_RiwayatAduan)
        }
    }
}