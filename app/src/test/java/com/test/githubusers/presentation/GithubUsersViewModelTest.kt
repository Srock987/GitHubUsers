package com.test.githubusers.presentation

import androidx.lifecycle.Observer
import com.test.githubusers.BlockingTest
import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.data.repositories.users
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.intellij.lang.annotations.Flow
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GithubUsersViewModelTest: BlockingTest(){

    private lateinit var subject: GithubUsersViewModel

    @RelaxedMockK
    private lateinit var model: GithubUsersModel

    @RelaxedMockK
    private lateinit var userObserver: Observer<List<GithubUser>>

    @RelaxedMockK
    private lateinit var isLoadingObserver: Observer<Boolean>

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        subject = GithubUsersViewModel(model, dispatcherProvider)
    }

    @Test
    fun `Given valid data from model, When subscribing to userData, Then the data is fetched `(){
        val userList = users
        val result = Result.success(userList)
        coEvery { model.getGithubUsers() } coAnswers { flowOf(result) }
        subject.getLoadingState().observeForever(isLoadingObserver)
        subject.getUserData().observeForever(userObserver)
        coVerify { model.getGithubUsers() }
        coVerify { isLoadingObserver.onChanged(true) }
        confirmVerified(isLoadingObserver, model)
    }

}