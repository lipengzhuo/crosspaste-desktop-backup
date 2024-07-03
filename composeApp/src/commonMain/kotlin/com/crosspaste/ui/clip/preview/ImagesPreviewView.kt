package com.crosspaste.ui.clip.preview

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.crosspaste.clip.item.ClipFiles
import com.crosspaste.dao.clip.ClipData

@Composable
fun ImagesPreviewView(clipData: ClipData) {
    clipData.getClipItem()?.let {
        val clipFiles = it as ClipFiles

        ClipSpecificPreviewContentView(
            clipMainContent = {
                val imagePaths = clipFiles.getFilePaths()
                LazyRow(modifier = Modifier.fillMaxSize()) {
                    items(imagePaths.size) { index ->
                        SingleImagePreviewView(imagePaths[index])
                        if (index != imagePaths.size - 1) {
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }
            },
            clipRightInfo = { toShow ->
                ClipMenuView(clipData = clipData, toShow = toShow)
            },
        )
    }
}