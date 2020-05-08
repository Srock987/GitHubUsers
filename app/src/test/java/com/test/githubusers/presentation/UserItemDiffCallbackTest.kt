package com.test.githubusers.presentation

import androidx.recyclerview.widget.DiffUtil
import com.test.githubusers.data.repositories.users
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotEqual
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserItemDiffCallbackTest{

    private lateinit var subject: UserItemDiffCallback

    @Before
    fun setUp(){
        subject = UserItemDiffCallback()
    }

    @Test
    fun `Given the same users, When comparing lists, Then no differences found`(){
        val usersList = users
        subject.setItems(usersList, usersList)
        subject.newListSize shouldEqual subject.oldListSize
        for (x in usersList.indices){
            subject.areItemsTheSame(x,x) shouldEqual true
            subject.areContentsTheSame(x,x) shouldEqual true
        }
    }

    @Test
    fun `Given changed user list, When comparing lists, Then size difference found`(){
        val usersList = users
        subject.setItems(usersList, usersList.take(usersList.size - 1))
        subject.newListSize shouldNotEqual  subject.oldListSize
    }

    @Test
    fun `Given changed users list, When comparing lists content, Then content differences found`(){
        val usersList = users
        subject.setItems(usersList, usersList.map { it.copy(login = it.login.plus("a")) })
        subject.newListSize shouldEqual subject.oldListSize
        for (x in usersList.indices){
            subject.areItemsTheSame(x,x) shouldEqual true
            subject.areContentsTheSame(x,x) shouldEqual false
        }
    }

    @Test
    fun `Given changed users list, When comparing lists items equality, Then differences found`(){
        val usersList = users
        subject.setItems(usersList, usersList.map { it.copy(id = it.id+1) })
        subject.newListSize shouldEqual subject.oldListSize
        for (x in usersList.indices){
            subject.areItemsTheSame(x,x) shouldEqual false
            subject.areContentsTheSame(x,x) shouldEqual false
        }
    }

}