package com.example.thetwo_z

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.thetwo_z.model.pengaduan
import com.google.firebase.database.*

class Riwayat5 : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    lateinit var list: MutableList<pengaduan>
    lateinit var listView: ListView
  //  var adapter: adapterRiwayat3? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.riwayat_aduan3)

        /*  btn_logout.setOnClickListener {
              FirebaseAuth.getInstance().signOut()
              val intent = Intent(this, login::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
              startActivity(intent)
          }

         */

        ref = FirebaseDatabase.getInstance().getReference("Pengaduan/Akademik")
        list = mutableListOf()
        listView = findViewById(R.id.listRiwayat)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {

                    for (h in p0.children) {
                        val aduan = h.getValue(pengaduan::class.java)
                        list.add(aduan!!)
                    }
                    val adapter =
                        adapterRiwayat3(applicationContext, R.layout.riwayat_aduan3, list)
                    listView.adapter = adapter
                }
            }
        })
    }

}
