package com.example.justdance

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.lates_messages.view.*

class LatestMessageRow(val chatMessage: ChatMessage): Item<GroupieViewHolder>() {
    var charPartnerUser:User? = null

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.message_textview_latest_message.text = chatMessage.text

        val chatParnetId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatParnetId = chatMessage.toId
        }else{
            chatParnetId =chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatParnetId")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                charPartnerUser = p0.getValue(User::class.java)
                viewHolder.itemView.username_textview_latest_message.text = charPartnerUser?.username

                val targetImageView = viewHolder.itemView.imageView4
                Picasso.get().load(R.drawable.login).into(targetImageView)
            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }

    override fun getLayout(): Int {
        return R.layout.lates_messages
    }
}