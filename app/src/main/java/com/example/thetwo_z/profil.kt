package com.example.thetwo_z

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListAdapter
import android.widget.ListView
import com.example.thetwo_z.chat.chatLogActivity
import com.example.thetwo_z.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_profil.*
import kotlinx.android.synthetic.main.activity_profil.view.*


        class profil : AppCompatActivity() {

            lateinit var ref: DatabaseReference
            lateinit var list: MutableList<User>
            lateinit var listView: ListView
          //  var adapter: adapterProfil? = null

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.profil2)

              /*  btn_logout.setOnClickListener {
                    FirebaseAuth.getInstance().signOut()
                    val intent = Intent(this, login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }

               */

                ref = FirebaseDatabase.getInstance().getReference("users")
                list = mutableListOf()
                listView = findViewById(R.id.listView)

                ref.addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.exists()) {

                            for (h in p0.children) {
                                val user = h.getValue(User::class.java)
                                list.add(user!!)
                            }
                           val adapter =
                                adapterProfil(applicationContext, R.layout.activity_profil, list)
                            listView.adapter = adapter
                        }
                    }
                })
            }
        }
