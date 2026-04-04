package com.bowazon.bingewatcher.features.imageview.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ImageViewNavKey(
    val imageIndex: Int,
    val urlList: List<String>
) : NavKey

fun Navigator.navigateToImageView(
    imageIndex: Int,
    urlList: List<String>
) {
    navigate(ImageViewNavKey(imageIndex, urlList))
}