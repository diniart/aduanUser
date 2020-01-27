package com.example.thetwo_z

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.thetwo_z.model.User

class adapterProfil(val mCtx: Context, val layoutResId: Int, val list: List<User> )
    : ArrayAdapter<User>(mCtx,layoutResId,list){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId,null)

        val textNama = view.findViewById<TextView>(R.id.tv_user_profil)
       // val textStatus = view.findViewById<TextView>(R.id. textStatus)

        val user = list[position]

        textNama.text = user.username
        //textStatus.text = user.status

        return view

    }
}