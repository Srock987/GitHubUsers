package com.test.githubusers.data.dataSources

import com.test.githubusers.data.entities.GithubUser

interface LocalGithubUsersDataSource {
    suspend fun getGithubUsers(): List<GithubUser>
    suspend fun saveUsers(users: List<GithubUser>)
}