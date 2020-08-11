package ru.evtukhov.android.homework6.adapter

import android.view.View
import kotlinx.android.synthetic.main.post_card.view.*
import ru.evtukhov.android.homework6.R
import ru.evtukhov.android.homework6.posts.Post

class AdvertisingViewHolder (adapter: PostAdapter, view: View): BaseViewHolder(adapter, view) {
    override fun bind(post: Post) {
        super.bind(post)
        with(itemView) {
            if (post.imageLink != null) {
                image.visibility = View.VISIBLE
                post.imageLink?.let { image.setImageResource(it) }
            }
            else {
                image.visibility = View.VISIBLE
                image.setImageResource(R.drawable.ic_logo_full_color_black)
            }
        }
    }
}