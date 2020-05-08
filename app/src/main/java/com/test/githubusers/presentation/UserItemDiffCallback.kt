package com.test.githubusers.presentation

import androidx.recyclerview.widget.DiffUtil
import com.test.githubusers.data.entities.GithubUser

class UserItemDiffCallback: DiffUtil.Callback() {
    private lateinit var oldList: List<GithubUser>
    private lateinit var newList: List<GithubUser>

    fun setItems(oldList: List<GithubUser>, newList: List<GithubUser>){
        this.oldList = oldList
        this.newList = newList
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id


    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.id == newItem.id && oldItem.login == newItem.login && oldItem.avatarUrl == newItem.avatarUrl && oldItem.userRepositories.containsAll(newItem.userRepositories)
    }
}