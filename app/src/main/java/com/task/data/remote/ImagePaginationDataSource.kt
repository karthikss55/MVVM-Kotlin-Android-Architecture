package com.task.data.remote

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.task.data.Resource
import com.task.data.dto.image.ImageItem
import com.task.data.error.NETWORK_ERROR
import com.task.data.error.NO_INTERNET_CONNECTION
import com.task.data.remote.service.GalleryService
import com.task.utils.NetworkConnectivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Karthik S S
 */
class ImagePaginationDataSource
@Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : PageKeyedDataSource<Int, ImageItem>() {

    private var imageService: GalleryService = serviceGenerator.createService(GalleryService::class.java)
    var loadingStatus = MutableLiveData<Resource<Any>>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageItem>
    ) {
        coroutineScope.launch {
            val response =
                processCall(1, params.requestedLoadSize, imageService::fetchImages)
            when (response) {
                is List<*> -> {
                    callback.onResult(response as ArrayList<ImageItem>, null, 2)
                }
                else -> {
                    loadingStatus.value = Resource.DataError(response as Int)
                }
            }
        }
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        coroutineScope.launch {
            val response =
                processCall(params.key, params.requestedLoadSize, imageService::fetchImages)
            when (response) {
                is List<*> -> {
                    callback.onResult(response as ArrayList<ImageItem>, params.key + 1)
                }
                else -> {
                    loadingStatus.value = Resource.DataError(response as Int)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageItem>) {
        TODO("Not yet implemented")
    }

    private suspend fun processCall(
        page: Int,
        limit: Int,
        responseCall: suspend (Int, Int) -> Response<*>
    ): Any? {
        if (!networkConnectivity.isConnected()) {
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke(page, limit)
            val responseCode = response.code()
            if (response.isSuccessful) {
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}