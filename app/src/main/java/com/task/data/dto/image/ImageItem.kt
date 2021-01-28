package com.task.data.dto.image

import com.squareup.moshi.Json

data class ImageItem(
    @Json(name = "id")
    val id: String = "",
    @Json(name = "author")
    val author: String = "",
    @Json(name = "width")
    val width: Int = 0,
    @Json(name = "height")
    val height: Int = 0,
    @Json(name = "url")
    val url: String = "",
    @Json(name = "download_url")
    val downloadUrl: String = ""
)