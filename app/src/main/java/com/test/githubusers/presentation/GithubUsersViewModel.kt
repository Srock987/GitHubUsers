package com.test.githubusers.presentation

import androidx.lifecycle.*
import com.test.githubusers.data.entities.GithubUser
import com.test.githubusers.presentation.corutineUtil.DispatcherProvider
import kotlinx.coroutines.launch

class GithubUsersViewModel(
    private val model: GithubUsersModel,
    private val dispatcherProvider: DispatcherProvider = DispatcherProvider()
) : ViewModel(), GithubUsersContract.ViewModel {

    private val usersData: MediatorLiveData<List<GithubUser>> by lazy {
        MediatorLiveData<List<GithubUser>>().also {
            fetchUsers()
        }
    }

    private val error = MutableLiveData<String>()

    private val isLoading = MutableLiveData<Boolean>()

    fun getLoadingState(): LiveData<Boolean> = isLoading

    fun getUserData(): LiveData<List<GithubUser>> = usersData

    override fun fetchUsers() {
        viewModelScope.launch(dispatcherProvider.io) {
            isLoading.postValue(true)
            usersData.addSource(model.getGithubUsers().asLiveData(dispatcherProvider.io)) { users ->
                users.fold(
                    onSuccess = {
                        usersData.postValue(it)
                        isLoading.postValue(false)
                    },
                    onFailure = {
                        error.postValue(it.message)
                    }
                )
            }
        }
    }

}