package com.test.githubusers.retrofit

import androidx.lifecycle.LiveData
import com.test.githubusers.retrofit.dto.GithubRepoDto
import com.test.githubusers.retrofit.dto.GithubUserDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET(usersPath)
    suspend fun getUsers(@Query(pageParam) page: Int, @Query(perPageParam) perPage: Int): List<GithubUserDto>

    @GET(usersRepoPath)
    suspend fun getUserRepos(@Path(userLoginPath) userLogin: String, @Query(pageParam) page: Int, @Query(perPageParam) perPage: Int): List<GithubRepoDto>

    companion object{
        const val usersPath = "users"
        const val userLoginPath = "user_login"
        const val usersRepoPath = "$usersPath/{$userLoginPath}/repos"
        const val pageParam = "page"
        const val perPageParam = "per_page"
    }
}