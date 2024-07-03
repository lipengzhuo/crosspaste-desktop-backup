package com.crosspaste.ui.clip.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.crosspaste.LocalKoinApplication
import com.crosspaste.clip.item.ClipHtml
import com.crosspaste.dao.clip.ClipData
import com.crosspaste.dao.clip.ClipItem
import com.crosspaste.i18n.GlobalCopywriter
import com.crosspaste.ui.base.AsyncView
import com.crosspaste.ui.base.LoadImageData
import com.crosspaste.ui.base.LoadingStateData
import com.crosspaste.ui.base.UISupport
import com.crosspaste.ui.base.loadImageData
import com.crosspaste.utils.getDateUtils
import com.crosspaste.utils.getFileUtils
import kotlinx.coroutines.delay

@Composable
fun HtmlToImageDetailView(
    clipData: ClipData,
    clipHtml: ClipHtml,
) {
    val current = LocalKoinApplication.current
    val density = LocalDensity.current
    val copywriter = current.koin.get<GlobalCopywriter>()
    val uiSupport = current.koin.get<UISupport>()
    val clipItem = clipHtml as ClipItem

    val dateUtils = getDateUtils()
    val fileUtils = getFileUtils()

    val filePath by remember(clipData.id) { mutableStateOf(clipHtml.getHtmlImagePath()) }

    var existFile by remember(clipData.id) { mutableStateOf(filePath.toFile().exists()) }

    ClipDetailView(
        detailView = {
            AsyncView(
                key = clipData.id,
                defaultValue = if (existFile) loadImageData(filePath, density) else LoadingStateData,
                load = {
                    while (!filePath.toFile().exists()) {
                        delay(200)
                    }
                    existFile = true
                    loadImageData(filePath, density)
                },
                loadFor = { loadImageView ->
                    when (loadImageView) {
                        is LoadImageData -> {
                            val horizontalScrollState = rememberScrollState()
                            val verticalScrollState = rememberScrollState()

                            BoxWithConstraints(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .horizontalScroll(horizontalScrollState)
                                        .verticalScroll(verticalScrollState)
                                        .clickable {
                                            uiSupport.openHtml(clipHtml.html)
                                        },
                            ) {
                                Image(
                                    painter = loadImageView.toPainterImage.toPainter(),
                                    contentDescription = "Html 2 Image",
                                    modifier = Modifier.wrapContentSize(),
                                )
                            }
                        }

                        else -> {
                            Text(
                                text = clipHtml.getText(),
                                fontFamily = FontFamily.SansSerif,
                                maxLines = 4,
                                softWrap = true,
                                overflow = TextOverflow.Ellipsis,
                                style =
                                    TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colors.onBackground,
                                        fontSize = 14.sp,
                                    ),
                            )
                        }
                    }
                },
            )
        },
        detailInfoView = {
            ClipDetailInfoView(
                clipData = clipData,
                items =
                    listOf(
                        ClipDetailInfoItem("Type", copywriter.getText("Html")),
                        ClipDetailInfoItem("Size", fileUtils.formatBytes(clipItem.size)),
                        ClipDetailInfoItem("Remote", copywriter.getText(if (clipData.remote) "Yes" else "No")),
                        ClipDetailInfoItem(
                            "Date",
                            copywriter.getDate(
                                dateUtils.convertRealmInstantToLocalDateTime(clipData.createTime),
                                true,
                            ),
                        ),
                    ),
            )
        },
    )
}