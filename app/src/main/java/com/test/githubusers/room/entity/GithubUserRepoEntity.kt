package com.test.githubusers.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "githubRepo", primaryKeys = ["userId","repoName"])
data class GithubUserRepoEntity(
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "repoName")
    val repoName: String
)