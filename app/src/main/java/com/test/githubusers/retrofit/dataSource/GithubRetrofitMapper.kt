package com.test.githubusers.retrofit.dataSource

import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.retrofit.dto.GithubRepoDto
import com.test.githubusers.retrofit.dto.GithubUserDto

class GithubRetrofitMapper {
    fun mapUser(dto: GithubUserDto): GithubUser? = with(dto){
        if (login == null || id == null) return@with null
        GithubUser(
            id,
            login,
            avatarUrl,
            emptyList()
        )
    }

    fun mapRepos(dto: GithubRepoDto): String? = with(dto){
        name
    }
}
