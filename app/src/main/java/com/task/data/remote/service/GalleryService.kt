package com.task.data.remote.service

import com.task.data.dto.image.ImageItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryService {
    @GET("list?")
    suspend fun fetchImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<List<ImageItem>>

    @GET("list")
    suspend fun fetchImages(
    ): Response<List<ImageItem>>
}