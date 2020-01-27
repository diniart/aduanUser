package com.example.thetwo_z.Pengaduan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thetwo_z.R
import com.example.thetwo_z.chat.chatLogActivity
import com.example.thetwo_z.model.pengaduan
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_riwayat_aduan2.*
import kotlinx.android.synthetic.main.item_riwayat.view.*


class Riwayat2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_aduan2)
        supportActionBar?.title = "Riwayat Pengaduan"

        /*val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(UserItem(user))


        recyclerview_newmessage.adapter = adapter

         */

        fetchAduan()


    }

    companion object {
        val ADUAN_KEY = "ADUAN_KEY"
        val USER_KEY = "USER_KEY"
        val TAG = "Riwayat2"
    }

    var toAdu: pengaduan? = null
    val adapter = GroupAdapter<GroupieViewHolder>()

    private fun fetchAduan() {
        val idPengaduan = toAdu?.idPengaduan
        val ref = FirebaseDatabase.getInstance().getReference("/Pengaduan/$idPengaduan")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {

                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach {
                    Log.d(TAG, it.toString())

                    val aduan = it.getValue(pengaduan::class.java)
                    //val user =it.getValue(User::class.java)
                    if (aduan != null) {
                        adapter.add(AduanItem(aduan))
                    }
                }
                adapter.setOnItemClickListener { item, view ->

                    // val userItem =item as AduanItem
                    val AduanItem = item as AduanItem
                    val intent = Intent(
                        view.context,
                        chatLogActivity::class.java
                    )
                    //     intent.putExtra(USER_KEY, userItem.user.username)
                    intent.putExtra(ADUAN_KEY, AduanItem.aduan.fromId)
                    startActivity(intent)

                    finish()
                }

                recycleRiwayat.adapter = adapter



                //

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })


    }
}



class  AduanItem(val aduan: pengaduan): Item<GroupieViewHolder>(){
    //(val user: User): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //will be called in our list each user object elater on..

        viewHolder.itemView.tv_judulRiwayatAduan.text=aduan.judul
        viewHolder.itemView.tv_riwayatAduan.text=aduan.text
        viewHolder.itemView.tv_usernameRiwayatAduan.text=aduan.fromId
       // Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.iv_profilRiwayatAduan)
        Picasso.get().load(aduan.imageAduanUrl).into(viewHolder.itemView.iv_RiwayatAduan)

    }

    override fun getLayout(): Int {
        return R.layout.item_riwayat
    }

}



