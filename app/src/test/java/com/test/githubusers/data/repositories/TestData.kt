package com.test.githubusers.data.repositories

import com.test.githubusers.data.entities.GithubUser

val users = listOf(
    GithubUser(
        1,
        "login1",
        "url1",
        listOf("repo1","repo2")
    ),
    GithubUser(
        2,
        "login2",
        "url2",
        listOf("repo3","repo4")
    )
)