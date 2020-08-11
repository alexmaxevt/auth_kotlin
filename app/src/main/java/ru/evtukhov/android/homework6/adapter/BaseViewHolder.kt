package ru.evtukhov.android.homework6.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.post_card.view.*
import ru.evtukhov.android.homework6.R
import ru.evtukhov.android.homework6.posts.Post
import ru.evtukhov.android.homework6.posts.PostType

@Suppress("IMPLICIT_CAST_TO_ANY")
abstract class BaseViewHolder(val adapter: PostAdapter, view: View): RecyclerView.ViewHolder(view) {
    
    init {
        this.viewClick()
    }

    fun viewClick () {
        with(itemView) {
            closeButton.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    adapter.removeItem(adapterPosition)
                    adapter.notifyItemRemoved(adapterPosition)
                }
            }
            likeBtn.setOnClickListener { 
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val itemPost = adapter.posts[adapterPosition]
                    itemPost.likedByMe = !itemPost.likedByMe
                    if (itemPost.likedByMe) {
                        likeBtn.setImageResource(R.drawable.ic_favorite_active_24dp)
                        itemPost.likedCount += 1
                        likeCount.text = itemPost.likedCount.toString()
                        likeCount.setTextColor(ContextCompat.getColor(this.context, R.color.colorActive))
                    }
                    else {
                        likeBtn.setImageResource(R.drawable.ic_favorite_inactive_24dp)
                        itemPost.likedCount -= 1
                        likeCount.text = (
                                if (itemPost.likedCount == 0) {
                                    ""
                                }
                                else {
                                    itemPost.likedCount
                                }).toString()
                        likeCount.setTextColor(ContextCompat.getColor(this.context, R.color.colorInActive))
                    }
                }
            }
            commentsBtn.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION) {
                    val itemPost = adapter.posts[adapterPosition]
                    itemPost.commentsByMe = !itemPost.commentsByMe
                    if (itemPost.commentsByMe) {
                        commentsBtn.setImageResource(R.drawable.ic_chat_active_24dp)
                        itemPost.commentsCount += 1
                        commentsCount.text = itemPost.commentsCount.toString()
                        commentsCount.setTextColor(ContextCompat.getColor(this.context, R.color.colorActive))
                    }
                    else {
                        commentsBtn.setImageResource(R.drawable.ic_chat_inactive_24dp)
                        itemPost.commentsCount -= 1
                        commentsCount.text = (
                                if (itemPost.commentsCount == 0) {
                                    ""
                                }
                                else {
                                    itemPost.commentsCount
                                }).toString()
                        commentsCount.setTextColor(ContextCompat.getColor(this.context, R.color.colorInActive))
                    }
                }
            }
            shareBtn.setOnClickListener {
                if(adapterPosition != RecyclerView.NO_POSITION) {
                    val itemPost = adapter.posts[adapterPosition]
                    itemPost.sharedByMe = !itemPost.sharedByMe
                    if (itemPost.sharedByMe) {
                        val intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT, """
                                ${itemPost.author} (${itemPost.dateText})
                
                                 ${itemPost.content}
                                """.trimIndent())
                            type = "text/plain"
                        }
                        itemView.context.startActivity(intent)
                        itemPost.sharedCount += 1
                        shareCount.text = itemPost.sharedCount.toString()
                        shareBtn.setImageResource(R.drawable.ic_share_active_24dp)
                        shareCount.setTextColor(ContextCompat.getColor(this.context, R.color.colorActive))
                    }
                    else {
                        shareBtn.setImageResource(R.drawable.ic_share_inactive_24dp)
                        itemPost.sharedCount -= 1
                        shareCount.text = (
                                if (itemPost.sharedCount == 0) {
                                    ""
                                }
                                else {
                                    itemPost.sharedCount
                                }).toString()
                        shareCount.setTextColor(ContextCompat.getColor(this.context, R.color.colorInActive))
                    }
                }
            }
            location.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val itemPost = adapter.posts[adapterPosition]
                    val lat = itemPost.lat
                    val lng = itemPost.lng
                    val intent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse("geo:$lat,$lng")
                    }
                    itemView.context.startActivity(intent)
                }
            }
            buttonLink.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val itemPost = adapter.posts[adapterPosition]
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(itemPost.intentLink)
                    }
                    itemView.context.startActivity(intent)
                }
            }
            video.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    val itemPost = adapter.posts[adapterPosition]
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(itemPost.videoLink)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

   open fun bind (post: Post) {
       with(itemView) {
           avatar.setImageResource(R.drawable.ic_android_24dp)
           textViewDate.text = post.dateText
           author.text = post.author
           content.text = post.content
           eventTitle.text = when(post.postType) {
               PostType.POST -> "POST"
               PostType.REPOST -> "REPOST"
               PostType.REPLAY -> "REPLAY"
               PostType.EVENT -> "EVENT"
               PostType.ADVERTISING -> "COMMERC"
               PostType.VIDEO -> "VIDEO"
           }
           likeCount.text = (
                   if (post.likedCount == 0) {
                       ""
                   }
                   else {
                       post.likedCount
                   }
                   ).toString()
           commentsCount.text = (
                   if (post.commentsCount == 0) {
                       ""
                   }
                   else {
                       post.commentsCount
                   }
                   ).toString()
           shareCount.text = (
                   if(post.sharedCount == 0) {
                       ""
                   }
                   else {
                       post.sharedCount
                   }
                   ).toString()
           if (post.lat == null && post.lng == null && post.address == null) location.visibility = View.INVISIBLE else location.visibility = View.VISIBLE
           if (post.videoLink != null) video.visibility = View.VISIBLE
           if (post.intentLink != null) buttonLink.visibility = View.VISIBLE
       }
   }
}