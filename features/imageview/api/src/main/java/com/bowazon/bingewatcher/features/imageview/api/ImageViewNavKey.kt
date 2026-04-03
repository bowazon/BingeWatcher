package com.bowazon.bingewatcher.features.imageview.api

import androidx.navigation3.runtime.NavKey
import com.bowazon.bingewatcher.core.navigation.Navigator
import kotlinx.serialization.Serializable

@Serializable
data class ImageViewNavKey(
    val imageId: Int
) : NavKey

fun Navigator.navigateToImageView(
    imageId: Int,
) {
    navigate(ImageViewNavKey(imageId))
}