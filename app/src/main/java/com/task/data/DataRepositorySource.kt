package com.task.data

import com.task.data.dto.image.ImageList
import kotlinx.coroutines.flow.Flow

/**
 * Created by Karthik S S
 */
interface DataRepositorySource {
    suspend fun requestImages(): Flow<Resource<ImageList>>
}
