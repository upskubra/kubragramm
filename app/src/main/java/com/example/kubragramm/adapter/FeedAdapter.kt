package com.example.kubragramm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kubragramm.R
import com.example.kubragramm.model.Post
import kotlinx.android.synthetic.main.recycler_row.view.*

class FeedAdapter(val postList: ArrayList<Post>): RecyclerView.Adapter<FeedAdapter.PostVH>(){
    class PostVH(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostVH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return PostVH(view)

    }

    override fun onBindViewHolder(holder: PostVH, position: Int) {
        holder.itemView.recycler_email.text = postList[position].userEmail
        holder.itemView.recycler_comment.text = postList[position].userComment

    }

    override fun getItemCount(): Int {
        return postList.size
    }
}