package com.test.githubusers.data.entities

data class GithubUser(
    val id: Int,
    val login: String,
    val avatarUrl: String?,
    val userRepositories: List<String>
)