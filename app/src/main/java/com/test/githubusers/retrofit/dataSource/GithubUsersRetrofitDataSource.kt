package com.test.githubusers.retrofit.dataSource

import com.test.githubusers.data.dataSources.RemoteGithubUsersDataSource
import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.retrofit.GithubRetrofitClient

class GithubUsersRetrofitDataSource(
    private val mapper: GithubRetrofitMapper,
    client: GithubRetrofitClient
) : RemoteGithubUsersDataSource {

    val service = client.getService()

    override suspend fun fetchGithubUsers(): List<GithubUser> {
        return service.getUsers(PAGE_LIMIT,USER_DATA_LIMIT)
            .mapNotNull(mapper::mapUser)
            .map { user ->
            user.copy(userRepositories = service.getUserRepos(user.login, PAGE_LIMIT, REPO_DATA_LIMIT)
                .mapNotNull(mapper::mapRepos))
        }
    }

    companion object {
        const val USER_DATA_LIMIT = 30
        const val REPO_DATA_LIMIT = 3
        const val PAGE_LIMIT = 1
    }
}