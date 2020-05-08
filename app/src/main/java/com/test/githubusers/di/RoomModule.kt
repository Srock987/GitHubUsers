package com.test.githubusers.di

import com.test.githubusers.room.GithubDatabase
import com.test.githubusers.room.dao.GithubRepoDao
import com.test.githubusers.room.dao.GithubUserDao
import com.test.githubusers.room.mapper.GithubRoomMapper
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val roomModule = Kodein.Module("RoomModule") {
    bind<GithubDatabase>() with provider { GithubDatabase.getDatabase(instance()) }

    // DAOs
    bind<GithubUserDao>() with provider { instance<GithubDatabase>().usersDao() }
    bind<GithubRepoDao>() with provider { instance<GithubDatabase>().repoDao() }

    //Mapper
    bind<GithubRoomMapper>() with provider { GithubRoomMapper() }
}