package com.task.data.remote

import com.task.data.Resource
import com.task.data.dto.image.ImageList

/**
 * Created by Karthik S S
 */

internal interface RemoteDataSource {
    suspend fun requestImages(): Resource<ImageList>
}
