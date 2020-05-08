package com.test.githubusers.di

import androidx.lifecycle.ViewModelProvider
import com.test.githubusers.presentation.GithubUsersModel
import com.test.githubusers.presentation.GithubUsersViewModel
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

val presentationModule = Kodein.Module("PresentationModule") {
    bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }

    bind<GithubUsersModel>() with provider {
        GithubUsersModel(
            repository = instance()
        )
    }
    bindViewModel<GithubUsersViewModel>() with provider {
        GithubUsersViewModel(
            model = instance()
        )
    }
}