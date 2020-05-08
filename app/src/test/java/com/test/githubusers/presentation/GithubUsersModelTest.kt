package com.test.githubusers.presentation

import com.test.githubusers.BlockingTest
import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.data.repositories.GithubRepository
import com.test.githubusers.data.repositories.NetworkException
import com.test.githubusers.data.repositories.users
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GithubUsersModelTest: BlockingTest(){

    private lateinit var subject: GithubUsersModel

    @RelaxedMockK
    lateinit var repository: GithubRepository

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        subject = GithubUsersModel(repository)
    }

    @Test
    fun `Given valid response from repository, When getGithubUsers is called, Then the same response is returned`() = runSuspendTestBlock{
        val flow = flowOf(Result.success(users), Result.success(users))
        coEvery { repository.getGithubUsers() } coAnswers { flow }
        val result = subject.getGithubUsers()
        coVerify(exactly = 1) { repository.getGithubUsers() }
        confirmVerified( repository )
        result shouldEqual flow
    }

    @Test
    fun `Given invalid response from repository, When getGithubUsers is called, Then the same response is returned`() = runSuspendTestBlock{
        val flow = flowOf<Result<List<GithubUser>>>(Result.failure(NetworkException()))
        coEvery { repository.getGithubUsers() } coAnswers { flow }
        val result = subject.getGithubUsers()
        coVerify(exactly = 1) { repository.getGithubUsers() }
        confirmVerified( repository )
        result shouldEqual flow
    }

}