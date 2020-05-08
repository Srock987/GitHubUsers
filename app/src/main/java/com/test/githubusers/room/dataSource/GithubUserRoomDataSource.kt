package com.test.githubusers.room.dataSource

import com.test.githubusers.data.dataSources.LocalGithubUsersDataSource
import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.room.dao.GithubRepoDao
import com.test.githubusers.room.dao.GithubUserDao
import com.test.githubusers.room.mapper.GithubRoomMapper

class GithubUserRoomDataSource(
    private val userDao: GithubUserDao,
    private val repoDao: GithubRepoDao,
    private val mapper: GithubRoomMapper
) : LocalGithubUsersDataSource {

    override suspend fun getGithubUsers(): List<GithubUser> {
        return userDao.getUsers()
            .map(mapper::mapToData)
            .map { user ->
                user.copy(
                    userRepositories = repoDao.getUserRepos(user.id)
                        .map(mapper::mapRepoToData)
                )
            }
    }

    override suspend fun saveUsers(users: List<GithubUser>) {
        userDao.insertUsers(*users.map(mapper::mapToEntity).toTypedArray())
        repoDao.insertRepos(*users.map(mapper::mapUserToRepos).flatten().toTypedArray())
    }

}