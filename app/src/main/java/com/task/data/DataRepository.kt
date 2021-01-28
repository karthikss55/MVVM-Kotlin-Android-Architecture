package com.task.data

import com.task.data.dto.image.ImageList
import com.task.data.local.LocalData
import com.task.data.remote.RemoteData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by Karthik S S
 */

class DataRepository @Inject
constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext)
    : DataRepositorySource {

    @ExperimentalCoroutinesApi
    override suspend fun requestImages(): Flow<Resource<ImageList>> {
        return flow {
            emit(remoteRepository.requestImages())
        }.flowOn(ioDispatcher)
    }
}
