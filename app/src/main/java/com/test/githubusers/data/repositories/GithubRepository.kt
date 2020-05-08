package com.test.githubusers.data.repositories

import com.test.githubusers.data.dataSources.LocalGithubUsersDataSource
import com.test.githubusers.data.dataSources.RemoteGithubUsersDataSource
import com.test.githubusers.data.entities.GithubUser
import kotlinx.coroutines.flow.Flow

class GithubRepository(
    private val local: LocalGithubUsersDataSource,
    private val remote: RemoteGithubUsersDataSource
) : BaseRepository() {

    suspend fun getGithubUsers(): Flow<Result<List<GithubUser>>> {
        return getBoundResource(
            getLocal = { local.getGithubUsers() },
            getRemote = { remote.fetchGithubUsers() },
            casheRemoteData = { local.saveUsers(it) },
            isValidData = { data -> data != null && data.isNotEmpty() }
        )
    }
}