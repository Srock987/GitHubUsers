package com.test.githubusers.di

import com.test.githubusers.retrofit.GithubRetrofitClient
import com.test.githubusers.retrofit.dataSource.GithubRetrofitMapper
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val retrofitModule = Kodein.Module("RetrofitModule") {
    bind<GithubRetrofitClient>() with provider {
        GithubRetrofitClient()
    }
    bind<GithubRetrofitMapper>() with provider { GithubRetrofitMapper() }
}