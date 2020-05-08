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

    private val filterData = MutableLiveData<String>("")

    private val error = MutableLiveData<String>()

    private val isLoading = MutableLiveData<Boolean>()

    fun getLoadingState(): LiveData<Boolean> = isLoading

    fun getFilteredData(): LiveData<List<GithubUser>> {
        return Transformations.switchMap(filterData) { filter ->
            val users = when {
                filter.isEmpty() -> usersData
                else -> {
                    Transformations.switchMap(usersData) { userList ->
                        val filteredUsers = MutableLiveData<List<GithubUser>>()
                        val filteredList = userList.filter { user ->
                            user.login.contains(filter) || user.userRepositories.any {
                                it.contains(filter)
                            }
                        }
                        filteredUsers.value = filteredList
                        filteredUsers
                    }
                }
            }
            users
        }
    }

    fun filterData(filter: String) {
        filterData.postValue(filter)
    }

    override fun fetchUsers() {
        viewModelScope.launch(dispatcherProvider.main) {
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