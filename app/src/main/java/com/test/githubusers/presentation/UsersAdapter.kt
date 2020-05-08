package com.test.githubusers.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.githubusers.R
import com.test.githubusers.data.entities.GithubUser
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.user_list_item.view.*

class UsersAdapter: RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private var users: List<GithubUser> = emptyList()
    private val diffCallback = UserItemDiffCallback()

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), LayoutContainer, Binder{
        override val containerView: View? = itemView
        override fun bind(user: GithubUser) {
            with(user){
                itemView.loginTextView.text = login
                itemView.userRepositoriesTextView.text = userRepositories.joinToString(", ")
                Glide.with(itemView.context).load(avatarUrl).into(itemView.userAvatarImageView)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false))
    }

    fun setItems(users: List<GithubUser>){
        diffCallback.setItems(this.users, users)
        val result= DiffUtil.calculateDiff(diffCallback)
        this.users = users
        result.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    internal interface Binder{
        fun bind(user: GithubUser)
    }
}