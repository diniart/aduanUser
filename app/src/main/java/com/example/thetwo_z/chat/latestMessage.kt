package com.example.thetwo_z.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.thetwo_z.R
import com.example.thetwo_z.login
import com.example.thetwo_z.model.User
import com.example.thetwo_z.model.chatMessage
import com.example.thetwo_z.register
import com.example.thetwo_z.views.LatestMessageRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_latest_message.*

class latestMessage : AppCompatActivity() {

    companion object{
        var currentUser:User?=null
        val TAG = "latestMessage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_message)
        recyclerview_latestMessage.adapter=adapter
        recyclerview_latestMessage.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        
        adapter.setOnItemClickListener { item, view ->
            Log.d("latestMessage", "123")
            val intent = Intent(this, chatLogActivity::class.java )

            val row = item as LatestMessageRow

           intent.putExtra(NewMessageActivity.USER_KEY,row.chatPartnerUser)
            startActivity(intent)
        }
        //s/"etUpDummyRows()
        listenForLatestMessage()

        fetchCurrentUser()

        verifyUserIsLoggedIn()
    }

    val latestMessageMap = HashMap<String, chatMessage>()

    private fun refreshRecycleViewMessage(){
       adapter.clear()
        latestMessageMap.values.forEach {
            adapter.add(LatestMessageRow(it))
        }
    }

    private fun listenForLatestMessage(){
        val fromId = FirebaseAuth.getInstance().uid
        val ref =FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(chatMessage::class.java) ?:return
                latestMessageMap[p0.key!!]=chatMessage
                refreshRecycleViewMessage()



               // adapter.add(LatestMessageRow(chatMessage))

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage= p0.getValue(chatMessage::class.java)?:return
                adapter.add(
                    LatestMessageRow(
                        chatMessage
                    )
                )
                latestMessageMap[p0.key!!] = chatMessage
                refreshRecycleViewMessage()
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildRemoved(p0: DataSnapshot) {}

            override fun onCancelled(p0: DatabaseError) {}
        })


    }
    val adapter =GroupAdapter<GroupieViewHolder>()


    private  fun fetchCurrentUser(){
        val uid =FirebaseAuth.getInstance().uid
        val ref= FirebaseDatabase.getInstance().getReference("/users/$uid")
        ref.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
               currentUser =p0.getValue(User::class.java)
                Log.d("latestMessage","Current user ${currentUser?.profileImageUrl}")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
    private fun verifyUserIsLoggedIn(){
        val uid = FirebaseAuth.getInstance().uid
        if(uid==null){
            val intent = Intent (this, register::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.newMessagemenu ->{

                val intent =Intent(this,
                    NewMessageActivity::class.java)
                startActivity(intent)

            }
            R.id.signOutmenu ->{
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, login::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)

    }
}
