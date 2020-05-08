package com.test.githubusers.di

import com.google.gson.Gson
import com.test.githubusers.data.dataSources.LocalGithubUsersDataSource
import com.test.githubusers.data.dataSources.RemoteGithubUsersDataSource
import com.test.githubusers.data.repositories.GithubRepository
import com.test.githubusers.retrofit.dataSource.GithubUsersRetrofitDataSource
import com.test.githubusers.room.dataSource.GithubUserRoomDataSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val dataModule = Kodein.Module("DataModule"){
    bind<Gson>() with provider { Gson() }

    bind<RemoteGithubUsersDataSource>() with provider {
        GithubUsersRetrofitDataSource(
            mapper = instance(),
            client = instance()
        )
    }
    bind<LocalGithubUsersDataSource>() with provider {
        GithubUserRoomDataSource(
            userDao = instance(),
            repoDao = instance(),
            mapper = instance()
        )
    }
    bind<GithubRepository>() with provider {
        GithubRepository(
            local = instance(),
            remote = instance()
        )
    }
}