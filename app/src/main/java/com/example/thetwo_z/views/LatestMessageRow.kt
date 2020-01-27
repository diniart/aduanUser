package com.example.thetwo_z.views

import com.example.thetwo_z.R
import com.example.thetwo_z.model.Admin
import com.example.thetwo_z.model.User
import com.example.thetwo_z.model.chatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow (val ChatMessage: chatMessage): Item<GroupieViewHolder>(){

var chatPartnerUser: Admin?=null

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_latest_message_row.text=ChatMessage.text
      // viewHolder.itemView.tv_username_latestmessage_row.text="blablaqq"


        val chatPartnerId:String
        if(ChatMessage.fromId== FirebaseAuth.getInstance().uid){
            chatPartnerId=ChatMessage.toId
        }else{
            chatPartnerId=ChatMessage.fromId
        }
        val ref = FirebaseDatabase.getInstance().getReference("/Admin/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val chatPartnerUser = p0.getValue(Admin::class.java)

                viewHolder.itemView.tv_username_latestmessage_row.text=chatPartnerUser?.adminname
                val targetImageView = viewHolder.itemView.iv_latestMessage_row
                Picasso.get().load(chatPartnerUser?.profileImageUrl).into(targetImageView)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }
    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}


