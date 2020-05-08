package com.test.githubusers.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.githubusers.room.entity.GithubUserRepoEntity

@Dao
interface GithubRepoDao {
    @Query("SELECT * FROM githubRepo WHERE userId=:userId")
    fun getUserRepos(userId: Int): List<GithubUserRepoEntity>
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    fun insertRepos(vararg repos: GithubUserRepoEntity)
}