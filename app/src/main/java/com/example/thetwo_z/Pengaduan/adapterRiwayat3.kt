package com.example.thetwo_z

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.thetwo_z.model.User
import com.example.thetwo_z.model.pengaduan
import kotlinx.android.synthetic.main.item_riwayat.view.*

class adapterRiwayat3(val mCtx: Context, val layoutResId: Int, val list: List<pengaduan> )
    : ArrayAdapter<pengaduan>(mCtx,layoutResId,list){



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val judul = view.findViewById<TextView>(R.id.tv_judulRiwayatAduan)
        val text = view.findViewById<TextView>(R.id.tv_riwayatAduan)
        // val textStatus = view.findViewById<TextView>(R.id. textStatus)
       // val gambar = view.findViewById<ImageView>(R.id.iv_RiwayatAduan)

        val aduan = list[position]

        judul.text = aduan.judul
        text.text = aduan.text
        //textStatus.text = user.status
       // Glide.with(gambar).load(aduan.imageAduanUrl).into(view.iv_RiwayatAduan)
        //    Picasso.get().load(aduan.imageAduanUrl).into(itemView.iv_RiwayatAduan)
        return view

    }
}