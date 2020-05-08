package com.test.githubusers.presentation

import androidx.lifecycle.LiveData
import com.test.githubusers.data.entities.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUsersContract {
    interface ViewModel{
        fun fetchUsers()
    }
    interface Model{
        suspend fun getGithubUsers(): Flow<Result<List<GithubUser>>>
    }
}