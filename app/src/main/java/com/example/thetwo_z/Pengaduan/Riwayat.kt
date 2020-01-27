package com.example.thetwo_z.Pengaduan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thetwo_z.R
import com.example.thetwo_z.adapterRiwayat
import com.example.thetwo_z.model.pengaduan
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_riwayat_aduan2.*
import kotlinx.android.synthetic.main.bar_layout.*

class Riwayat : AppCompatActivity() {

    //lateinit var ref: DatabaseReference
   // lateinit var list: MutableList<pengaduan>
   // lateinit var listView: ListView
    //  var adapter: adapterProfil? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_aduan2)

        /*  btn_logout.setOnClickListener {
              FirebaseAuth.getInstance().signOut()
              val intent = Intent(this, login::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
              startActivity(intent)
          }

         */

       // setSupportActionBar(toolbar)

        var toAdu: pengaduan? = null
        val idpengaduan = toAdu?.idPengaduan
        val databaseRef = FirebaseDatabase.getInstance().getReference("Pengaduan/$idpengaduan" )

        val Aduans: ArrayList<pengaduan> = ArrayList()

       recycleRiwayat.layoutManager = GridLayoutManager(this, 1)

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println("Info: ${p0.message}")
            }

            override fun onDataChange(p0: DataSnapshot) {
              //  Aduans.clear()
                for (dataSnapshot in p0.children){
                    val getValue = dataSnapshot.getValue(pengaduan::class.java)
                    getValue?.let { Aduans.add(it) }
                }

                recycleRiwayat.adapter = adapterRiwayat(applicationContext, Aduans)
            }
        })
    }
}
