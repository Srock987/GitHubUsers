package com.test.githubusers.room.mapper

import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.room.entity.GithubUserEntity
import com.test.githubusers.room.entity.GithubUserRepoEntity

class GithubRoomMapper {
    fun mapToData(entity: GithubUserEntity) = with(entity) {
        GithubUser(
            id,
            login,
            avatarUrl,
            emptyList()
        )
    }

    fun mapToEntity(data: GithubUser) = with(data) {
        GithubUserEntity(
            id,
            login,
            avatarUrl
        )
    }

    fun mapRepoToData(entity: GithubUserRepoEntity) = with(entity) {
        repoName
    }

    fun mapUserToRepos(data: GithubUser) = with(data) {
        userRepositories.map {
            GithubUserRepoEntity(
                this.id,
                it
            )
        }
    }
}