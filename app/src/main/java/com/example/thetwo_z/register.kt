package com.example.thetwo_z

//import com.example.mychattzzke2.models.User

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thetwo_z.chat.latestMessage
import com.example.thetwo_z.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.io.ByteArrayOutputStream
import java.util.*


class register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.title = "Rgister"
        btnRegister.setOnClickListener {

            performRegister()
        }
        haveAccount.setOnClickListener{
            val intent = Intent(this, login::class.java)
            startActivity(intent )
        }

        fotoregister.setOnClickListener {
            Log.d("register", "memilih foto")

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


          // val  compressedImageFile = Compressor(this).compressToFile(actualImageFile);
           /* val fotoini= openFileInput()

            val compressedImage = Compressor(this)
                .setMaxWidth(640)
                .setMaxHeight(480)
                .setQuality(75)
                .setCompressFormat(Bitmap.CompressFormat.WEBP)
                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                   Environment.DIRECTORY_PICTURES).getAbsolutePath())
                //.compressToBitmap(Bitmap)*/

            /*   try {
                   selectPhotoUri?.let {
                       if(Build.VERSION.SDK_INT > 28) {
                           val bitmap = MediaStore.Images.Media.getBitmap(
                               this.contentResolver,
                               selectPhotoUri
                           )
                           circleImageView.setImageBitmap(bitmap)
                       } else {
                           val source = ImageDecoder.createSource(this.contentResolver,
                               selectPhotoUri!!
                           )
                           val bitmap = ImageDecoder.decodeBitmap(source)
                           circleImageView.setImageBitmap(bitmap)
                       }
                   }
               } catch (e: Exception) {
                   e.printStackTrace()
               }*/
            circleImageView.setImageBitmap(bitmap)
            fotoregister.alpha=0f
            //val bitmapDrawable = BitmapDrawable(this.resources,bitmap)
            //fotoregister.setBackgroundDrawable(bitmapDrawable)

        }
    }

    private  fun performRegister(){
        val email = etEmailRegister.text.toString()
        val password = etPasswordRegister.text.toString()

        Log.d("registe",  "Email is "+email)
        Log.d("register", "password $password")

        if (email.isEmpty()||password.isEmpty()){
            Toast.makeText(this,"masukkan email dan password",Toast.LENGTH_LONG).show()
            return
        }


        //firebase Authentication

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener {
            if (!it.isSuccessful)return@addOnCompleteListener

            Log.d("register", "sukses membuat akun : ${it.result?.user?.uid}")
            Toast.makeText(this," Register Berhasil ",Toast.LENGTH_LONG).show()

            uploadImageToFirebaseStorage()
        }
            .addOnFailureListener {

                Log.d("regiter","gagal membuat akun: ${it.message}")
                Toast.makeText(this," Register Gagal ",Toast.LENGTH_LONG).show()
            }
    }

    private fun uploadImageToFirebaseStorage(){

        if (selectPhotoUri==null)return
        val filename = UUID.randomUUID().toString()
        val ref =FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoUri!!)
            .addOnSuccessListener {
                Log.d("register", "sukses upload foto: ${it.metadata?.path} ")

                ref.downloadUrl.addOnSuccessListener {


                    Log.d("register","lokasi file: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {

            }

    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid=FirebaseAuth.getInstance().uid ?:""
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user= User(
            uid,
            etUserRegister.text.toString(),
            profileImageUrl
        )

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("register","user tersimpan dalam database")

                val intent = Intent(this, latestMessage::class.java)
                intent.flags =Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("register", "gagal menyimpan ke database: ${it.message}")
            }
    }
}
