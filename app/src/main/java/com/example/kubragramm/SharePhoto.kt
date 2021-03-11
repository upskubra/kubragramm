package com.example.kubragramm

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class SharePhoto : AppCompatActivity() {
    lateinit var shareButton: Button
    lateinit var commentText: TextView
    lateinit var addPhotoImageView: ImageView
    var selectedPhoto : Uri? = null
    var selectedBitmap : Bitmap? = null
    private lateinit var storage : FirebaseStorage
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_photo)

        shareButton = findViewById(R.id.shareButton)
        commentText = findViewById(R.id.commentText)
        addPhotoImageView = findViewById(R.id.addPhotoImageView)

        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        shareButton.setOnClickListener {
            sharePhoto()
        }

        addPhotoImageView.setOnClickListener {

            addPhoto()

        }
    }

    fun addPhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, 2)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray) {
        if (requestCode == 1){
           if (grantResults.size> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)   {
                val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
               println("asdfghjkl")
                startActivityForResult(galleryIntent, 2)

        }

        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println(requestCode)
        println(resultCode)
        println(data)
        if (requestCode == 2  && resultCode == Activity.RESULT_OK&& data != null) {

            selectedPhoto = data.data
            println(selectedPhoto)
            if (selectedPhoto != null){
                if(Build.VERSION.SDK_INT >= 28){
                    val source = ImageDecoder.createSource(this.contentResolver, selectedPhoto!!)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    addPhotoImageView.setImageBitmap(selectedBitmap)



                }else {
                    selectedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedPhoto)
                    addPhotoImageView.setImageBitmap(selectedBitmap)

                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun sharePhoto(){

        //depo işlemleri

        val reference = storage.reference
        val photoReference = reference.child("images").child("selectedphoto.jpg")
        if (selectedPhoto != null){
            photoReference.putFile(selectedPhoto!!).addOnSuccessListener { TaskSnapshot ->
                println("yüklendi")
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()

            }
        }


    }
}