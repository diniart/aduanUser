package com.example.thetwo_z.chat

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.thetwo_z.R
import com.example.thetwo_z.model.Admin
import com.example.thetwo_z.model.User
import com.example.thetwo_z.model.chatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.iv_chat_from_row
import kotlinx.android.synthetic.main.chat_to_row.view.iv_chat_to_row
import kotlinx.android.synthetic.main.imagechat_from_row.view.*
import kotlinx.android.synthetic.main.imagechat_to_row.view.*
import java.io.ByteArrayOutputStream
import java.util.*

class chatLogActivity : AppCompatActivity() {

    companion object{
        val TAG ="chatlog"
    }
    val adapter= GroupAdapter<GroupieViewHolder>()
    var toAdmin : Admin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recycler_chat_log.adapter=adapter


       // val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
         toAdmin = intent.getParcelableExtra<Admin>(NewMessageActivity.USER_KEY)
        supportActionBar?.title =toAdmin?.adminname

        listenForMessage()

        setupDummyData()

        btn_lampir_chat_log.setOnClickListener {
            Log.d(TAG, "pilih foto")
            val intent= Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            intent.action= Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            startActivityForResult(intent,0)
        }


        btn_send_chat_log.setOnClickListener{
        Log.d(TAG,"attempt to send message..")
            performSendMessage()
        }

    }

    var selectPhotoUri: Uri?=null

    private fun listenForMessage(){
        val fromId = FirebaseAuth.getInstance().uid

        val toId=toAdmin?.aid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val ChatMessage = p0.getValue(chatMessage::class.java)
                if(ChatMessage!=null) {
                    Log.d(TAG, ChatMessage.text)
                    if(ChatMessage.fromId==FirebaseAuth.getInstance().uid){
                        val currentUser =
                            latestMessage.currentUser
                                ?:return

                        if(ChatMessage.text=="")
                            adapter.add(
                                chatMessageFromItem(
                                    ChatMessage.imageMessageUrl,
                                    currentUser
                                )
                            )
                        else
                            adapter.add(
                                chatFromItem(
                                    ChatMessage.text,
                                    currentUser
                                )
                            )
                    }else{
                        if(ChatMessage.text=="")
                            adapter.add(
                                chatMessageToItem(
                                    ChatMessage.imageMessageUrl,
                                    toAdmin!!
                                )
                            )
                        else
                            adapter.add(
                                chatToItem(
                                    ChatMessage.text,
                                    toAdmin!!
                                )
                            )
                    }

                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {


            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {

            }
        })
    }






    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //proses dan cek foto yg dipilih
            Log.d("register", "foto sudah dipilih")

            selectPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoUri)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos)
            bitmap.compress(Bitmap.CompressFormat.PNG, 25, baos)
            val data: ByteArray = baos.toByteArray()
            btn_lampir_chat_log.alpha = 0f
            val bitmapDrawable = BitmapDrawable(this.resources,bitmap)
            btn_lampir_chat_log.setBackgroundDrawable(bitmapDrawable)
            uploadImageToFirebaseStorage()
        }
    }

    private fun performSendMessage(){
        //how to  actually send to firebase..
    val text= et_chat_log.text.toString()
        //val reference = FirebaseDatabase.getInstance().getReference("/message").push()


        val fromId = FirebaseAuth.getInstance().uid
        val admin = intent.getParcelableExtra<Admin>(NewMessageActivity.USER_KEY)
        val toId=admin.aid
        val imageMessageUrl= "";//FirebaseDatabase.getInstance()
        if (fromId==null)return
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatMessage = chatMessage(reference.key!!,text,fromId, toId,imageMessageUrl,System.currentTimeMillis()/1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG," simpan pesan: ${reference.key}")
                et_chat_log.text.clear()
                recycler_chat_log.scrollToPosition(adapter.itemCount-1)
            }
        toReference.setValue(chatMessage)
           val latestMessageRef =FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)
    }

    private fun uploadImageToFirebaseStorage() {

        if (selectPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d("register", "sukses upload foto: ${it.metadata?.path} ")

                ref.downloadUrl.addOnSuccessListener {


                    Log.d("register", "lokasi file: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {

            }

    }
    private fun saveUserToFirebaseDatabase(imageMessageUrl: String){
        val text= ""

        val fromId = FirebaseAuth.getInstance().uid
        val admin = intent.getParcelableExtra<Admin>(NewMessageActivity.USER_KEY)
        val toId=admin.aid
        val uid=FirebaseAuth.getInstance().uid ?:""
        if (fromId==null)return
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatMessage = chatMessage(reference.key!!,text,fromId, toId,imageMessageUrl,System.currentTimeMillis()/1000)

        val ref=FirebaseDatabase.getInstance().getReference("/user-messages/$uid")

//        val ChatMessage= chatMessage(
//            uid,
//            et_chat_log.text.toString(),
//            imageMessageUrl
//        )

        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG," simpan pesan: ${reference.key}")
                et_chat_log.text.clear()
                recycler_chat_log.scrollToPosition(adapter.itemCount-1)
            }
        toReference.setValue(chatMessage)
        val latestMessageRef =FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRef.setValue(chatMessage)
    }

    private fun setupDummyData(){
        /*  val adapter = GroupAdapter<GroupieViewHolder>()

      adapter.add(chatFromItem())
      adapter.add(chatToItem())

      recycler_chat_log.adapter=adapter
      */

    }

}


class  chatFromItem(val text:String,val user: User): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tv_from_row.text=text

        //load pic to user chat
        val uri= user.profileImageUrl
        val targetImageView = viewHolder.itemView.iv_chat_from_row
        Picasso.get().load(uri).into(targetImageView)


    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row

    }
}
class  chatToItem(val text : String,val admin: Admin): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_to_row.text=text

        //load pic to user chat
        val uri= admin.profileImageUrl
        val targetImageView = viewHolder.itemView.iv_chat_to_row
        Picasso.get().load(uri).into(targetImageView)
    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}

//
class  chatMessageFromItem(val imageMessageUrl:String, val user: User): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        //viewHolder.itemView.tv_from_row.text=text
        Picasso.get().load(imageMessageUrl).into(viewHolder.itemView.iv_message_from_row)
        //load pic to user chat
        val uri= user.profileImageUrl
        val targetImageView = viewHolder.itemView.iv_chat_from_row
        Picasso.get().load(uri).into(targetImageView)
    }
    override fun getLayout(): Int {
        return R.layout.imagechat_from_row

    }
}
class  chatMessageToItem(val imageMessageUrl: String, val admin: Admin): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        viewHolder.itemView.tv_to_row.text=text
        Picasso.get().load(imageMessageUrl).into(viewHolder.itemView.iv_message_to_row)
        //load pic to user chat
        val uri= admin.profileImageUrl
        val targetImageView = viewHolder.itemView.iv_chat_to_row
        Picasso.get().load(uri).into(targetImageView)


    }
    override fun getLayout(): Int {
        return R.layout.imagechat_to_row

    }
}
