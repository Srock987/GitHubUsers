package com.test.githubusers.retrofit.dataSource

import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldNotBe
import org.junit.Before
import org.junit.Test

class GithubRetrofitMapperTest{

    private lateinit var subject: GithubRetrofitMapper

    @Before
    fun setUp(){
        subject = GithubRetrofitMapper()
    }

    @Test
    fun `Given valid userDto, When mapUser called, Then valid GithubUser data object returned`(){
        val dto = validUserDto
        val userData = subject.mapUser(dto)
        userData shouldNotBe null
        userData!!.id shouldEqual validUserDto.id
        userData.login shouldEqual validUserDto.login
        userData.avatarUrl shouldEqual validUserDto.avatarUrl
    }

    @Test
    fun `Given invalid userDto, When mapUser called, Then null object returned`(){
        val dto = validUserDto.copy(login = null)
        val userData = subject.mapUser(dto)
        userData shouldBe  null
    }

}