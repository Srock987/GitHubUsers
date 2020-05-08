package com.test.githubusers.data.dataSources

import com.test.githubusers.data.entities.GithubUser

interface RemoteGithubUsersDataSource {
    suspend fun fetchGithubUsers(): List<GithubUser>
}