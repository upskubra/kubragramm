package com.example.kubragramm.view

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kubragramm.model.Post
import com.example.kubragramm.R
import com.example.kubragramm.adapter.FeedAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private lateinit var recyclerViewAdapter: FeedAdapter

    var postList = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)


        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
            getData()

        val layoutManager = LinearLayoutManager(this )
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = FeedAdapter(postList)
        recyclerView.adapter = recyclerViewAdapter
    }

    fun getData(){
       database.collection("Post").orderBy("date", Query.Direction.DESCENDING)

           .addSnapshotListener { snapshot, exception ->
           if (exception != null){
               Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
           } else if (snapshot != null){
               if (!snapshot.isEmpty){
                  val documents =  snapshot.documents
                   postList.clear()
                   for (document in documents){
                       val userEmail = document.get("useremail") as String
                       val userComment = document.get("usercomment") as String
                       val photoUrl = document.get("photourl") as String
                            var downloadPost = Post(userEmail, userComment, photoUrl)
                            postList.add(downloadPost)
                   }
                   recyclerViewAdapter.notifyDataSetChanged()
               }
           }
       }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share_photo) {
            val intent = Intent(this, SharePhoto::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.sign_out) {

            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }

        return super.onOptionsItemSelected(item)
    }
}