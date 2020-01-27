package com.example.thetwo_z.Pengaduan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thetwo_z.Homee
import com.example.thetwo_z.R
import com.example.thetwo_z.chat.*
import com.example.thetwo_z.login
import com.example.thetwo_z.model.Admin
import com.example.thetwo_z.model.User
import com.example.thetwo_z.model.chatMessage
import com.example.thetwo_z.model.pengaduan
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_aduan_keuangan.*
import kotlinx.android.synthetic.main.activity_aduan_keuangan.*
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.activity_register.*

import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.imagechat_from_row.view.*
import kotlinx.android.synthetic.main.item_riwayat.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import java.io.ByteArrayOutputStream
import java.util.*

class aduanDosen : AppCompatActivity() {
    companion object{
        //  var currentUser:User?=null
        val TAG = "aduanAkademik"
        //  val ADUAN_KEY ="ADUAN_KEY"
    }
    // val adapter= GroupAdapter<GroupieViewHolder>()
    //var toAdmin : Admin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aduan_keuangan)



//import com.example.mychattzzke2.models.User


        btn_lapor.setOnClickListener {

            performAduan()

        }

        btn_gambar_aduan.setOnClickListener {
            Log.d(TAG, "memilih foto")

            val intent= Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png"))
            startActivityForResult(intent,0)
        }

    }
    var selectPhotoUri: Uri?=null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==0 && resultCode==Activity.RESULT_OK && data!= null){
            //proses dan cek foto yg dipilih
            Log.d("register","foto sudah dipilih")

            selectPhotoUri= data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectPhotoUri)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos)
            bitmap.compress(Bitmap.CompressFormat.PNG, 25, baos)
            val data: ByteArray = baos.toByteArray()



            iv_foto_aduan.setImageBitmap(bitmap)


        }
    }


    private  fun  performAduan(){
        uploadImageToFirebaseStorage()
    }

//


    private fun uploadImageToFirebaseStorage(){


        if (selectPhotoUri==null)return
        val filename = UUID.randomUUID().toString()
        val ref =FirebaseStorage.getInstance().getReference("/images/Pangaduan/$filename")

        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d("register", "sukses upload foto: ${it.metadata?.path} ")

                ref.downloadUrl.addOnSuccessListener {


                    Log.d("register","lokasi file: $it")

                    saveAduanToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {

            }

    }

    private fun saveAduanToFirebaseDatabase(imageAduanUrl: String){
        val fromId=FirebaseAuth.getInstance().uid ?:""
        val toId =""
        val ref=FirebaseDatabase.getInstance().getReference("/Pengaduan/Dosen/$fromId").push()
        val Aduan= pengaduan(
            ref.key!! ,
            fromId,toId,
            et_judul_aduan.text.toString(),
            et_pengaduan.text.toString(),
            imageAduanUrl,System.currentTimeMillis()/1000
        )

        ref.setValue(Aduan)
            .addOnSuccessListener {
                Log.d("register"," tersimpan dalam database")

                val intent = Intent(this, Riwayat::class.java)
                intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("register", "gagal menyimpan ke database: ${it.message}")
            }
    }
}
