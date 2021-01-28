package com.task.ui.component.gallery

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.task.IMAGE_LIMIT
import com.task.PAGE_SIZE
import com.task.data.Resource
import com.task.data.dto.image.ImageItem
import com.task.data.remote.ImagePaginationDataSource
import com.task.data.remote.ServiceGenerator
import com.task.ui.base.BaseViewModel
import com.task.utils.NetworkConnectivity
import com.task.utils.SingleEvent
import javax.inject.Inject

class ImageListViewModel
@Inject constructor(
    private val serviceGenerator: ServiceGenerator,
    private val networkConnectivity: NetworkConnectivity
) : BaseViewModel() {
    var imageData: LiveData<PagedList<ImageItem>>
    var loadingStatus = MutableLiveData<Resource<Any>>()
    var mediatorLiveData = MediatorLiveData<Resource<Any>>()


    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    init {
        val pagingConfig = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setInitialLoadSizeHint(IMAGE_LIMIT)
            .setEnablePlaceholders(false)
            .build()
        imageData = initializedPagedListBuilder(pagingConfig).build()
        //loadingStatus.value = Resource.Loading(){}
    }

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, ImageItem> {
        var imageSource: ImagePaginationDataSource? = null

        val dataSourceFactory = object : DataSource.Factory<Int, ImageItem>() {
            override fun create(): DataSource<Int, ImageItem> {
                imageSource =  ImagePaginationDataSource(
                    viewModelScope,
                    serviceGenerator,
                    networkConnectivity
                )
                return imageSource!!
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }

}