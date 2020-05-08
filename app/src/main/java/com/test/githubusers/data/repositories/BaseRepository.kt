package com.test.githubusers.data.repositories

import kotlinx.coroutines.flow.flow
import java.lang.Exception

abstract class BaseRepository {
    suspend fun <T> getBoundResource(
        getLocal: suspend () -> T?,
        getRemote: suspend () -> T?,
        casheRemoteData: suspend (T) -> Unit,
        isValidData: (T?) -> Boolean = { true }
    ) = flow {
        try {
            val local = getLocal()
            if (isValidData(local) && local != null) {
                emit(Result.success(local))
            }
            val remote = getRemote()
            if (isValidData(remote) && remote != null) {
                emit(Result.success(remote))
                casheRemoteData(remote)
            } else {
                emit(Result.failure(NetworkException()))
            }
        } catch (exception: Exception){
            emit(Result.failure(exception))
        }
    }
}

class NetworkException: Exception("Couldn't connect to server")