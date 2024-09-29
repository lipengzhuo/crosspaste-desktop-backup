package com.crosspaste.image

import androidx.compose.ui.graphics.asSkiaBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import okio.Path
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.Image
import org.jetbrains.skia.Rect
import org.jetbrains.skia.Surface
import kotlin.math.min

class DesktopImageCreator : ImageCreator {
    override fun createBitmap(path: Path): Bitmap {
        return path.toFile().inputStream().buffered().use {
            it.use(::loadImageBitmap).asSkiaBitmap()
        }
    }

    override fun createBitmap(
        path: Path,
        width: Int,
        height: Int,
    ): Bitmap {
        return path.toFile().inputStream().buffered().use { stream ->
            val originalImage = Image.makeFromEncoded(stream.readBytes())

            // Determine the actual crop dimensions (in case the image is smaller than the requested crop size)
            val actualCropWidth = min(width, originalImage.width)
            val actualCropHeight = min(height, originalImage.height)

            // Create a surface for the cropped image
            val surface = Surface.makeRasterN32Premul(actualCropWidth, actualCropHeight)

            // Draw only the top-left portion of the original image onto the new surface
            surface.canvas.drawImageRect(
                originalImage,
                Rect.makeXYWH(0f, 0f, actualCropWidth.toFloat(), actualCropHeight.toFloat()),
                Rect.makeWH(actualCropWidth.toFloat(), actualCropHeight.toFloat()),
            )

            // Convert the cropped image to a Compose ImageBitmap
            surface.makeImageSnapshot().toComposeImageBitmap()
        }.asSkiaBitmap()
    }
}
