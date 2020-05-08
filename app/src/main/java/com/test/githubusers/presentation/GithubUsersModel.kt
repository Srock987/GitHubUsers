package com.test.githubusers.presentation

import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.data.repositories.GithubRepository
import kotlinx.coroutines.flow.Flow

class GithubUsersModel(
    private val repository: GithubRepository
): GithubUsersContract.Model {
    override suspend fun getGithubUsers(): Flow<Result<List<GithubUser>>> {
        return repository.getGithubUsers()
    }
}