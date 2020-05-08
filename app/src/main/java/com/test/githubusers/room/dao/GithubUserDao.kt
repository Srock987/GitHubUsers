package com.test.githubusers.room.dao

import androidx.room.*
import com.test.githubusers.room.entity.GithubUserEntity

@Dao
interface GithubUserDao {
    @Query("SELECT * FROM githubUser")
    fun getUsers(): List<GithubUserEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: GithubUserEntity)
}