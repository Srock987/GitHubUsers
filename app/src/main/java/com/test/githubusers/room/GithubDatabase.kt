package com.test.githubusers.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.githubusers.room.dao.GithubRepoDao
import com.test.githubusers.room.dao.GithubUserDao
import com.test.githubusers.room.entity.GithubUserEntity
import com.test.githubusers.room.entity.GithubUserRepoEntity

@Database(
    entities = [
    GithubUserEntity::class,
    GithubUserRepoEntity::class
    ],
    version = 1
)
abstract class GithubDatabase: RoomDatabase() {

    abstract fun usersDao(): GithubUserDao
    abstract fun repoDao(): GithubRepoDao

    companion object {
        @Volatile
        private var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java,
                    "aafp_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}