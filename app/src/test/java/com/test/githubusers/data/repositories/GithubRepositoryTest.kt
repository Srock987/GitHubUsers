package com.test.githubusers.data.repositories

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.test.githubusers.BlockingTest
import com.test.githubusers.data.dataSources.LocalGithubUsersDataSource
import com.test.githubusers.data.dataSources.RemoteGithubUsersDataSource
import com.test.githubusers.data.entities.GithubUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GithubRepositoryTest: BlockingTest(){

    @RelaxedMockK
    lateinit var local: LocalGithubUsersDataSource
    @RelaxedMockK
    lateinit var remote: RemoteGithubUsersDataSource
    @RelaxedMockK
    lateinit var userObserver: Observer<Result<List<GithubUser>>>

    private lateinit var subject: GithubRepository

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        subject = GithubRepository(local, remote)
    }

    @Test
    fun `Given users from local and remote, When getGithubUsers called, Then both users list emitted`() = runSuspendTestBlock {
        coEvery { local.getGithubUsers() } coAnswers { users }
        coEvery { remote.fetchGithubUsers() } coAnswers { users }
        subject.getGithubUsers().asLiveData(testDispatcher).observeForever(userObserver)
        coVerify(exactly = 2) { userObserver.onChanged(Result.success(users)) }
        confirmVerified( userObserver )
    }

    @Test
    fun `Given no users from local and vaid data from remote, When getGithubUsers called, Then users list once emitted`() = runSuspendTestBlock {
        coEvery { local.getGithubUsers() } coAnswers { emptyList() }
        coEvery { remote.fetchGithubUsers() } coAnswers { users }
        subject.getGithubUsers().asLiveData(testDispatcher).observeForever(userObserver)
        coVerify(exactly = 1) { userObserver.onChanged(Result.success(users)) }
        confirmVerified( userObserver )
    }

}