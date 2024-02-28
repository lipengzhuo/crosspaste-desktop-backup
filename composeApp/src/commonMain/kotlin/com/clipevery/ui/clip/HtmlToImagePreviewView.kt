package com.clipevery.ui.clip

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clipevery.LocalKoinApplication
import com.clipevery.clip.ChromeService
import com.clipevery.clip.item.ClipHtml
import com.clipevery.dao.clip.ClipData
import com.clipevery.i18n.GlobalCopywriter
import com.clipevery.presist.FilePersist
import com.clipevery.ui.base.html

@Composable
fun HtmlToImagePreviewView(clipData: ClipData) {
    clipData.getClipItem()?.let {
        val current = LocalKoinApplication.current
        val copywriter = current.koin.get<GlobalCopywriter>()
        val filePersist = current.koin.get<FilePersist>()

        val clipHtml = it as ClipHtml

        val imageBitmap: ImageBitmap? = remember(clipHtml) {
            clipHtml.getHtmlImage()
        }

        if (imageBitmap == null) {
            val chromeService = current.koin.get<ChromeService>()
            LaunchedEffect(Unit) {
                chromeService.html2Image(clipHtml.html)?.let { bytes ->
                    filePersist.createOneFilePersist(it.getHtmlImagePath())
                        .saveBytes(bytes)
                }
            }
        }

        ClipSpecificPreviewContentView(it, {
            Row {
                imageBitmap?.let { bitmap ->
                    val horizontalScrollState = rememberScrollState()
                    val verticalScrollState = rememberScrollState()

                    BoxWithConstraints(
                        modifier = Modifier
                            .height(120.dp)
                            .width(350.dp)
                            .horizontalScroll(horizontalScrollState)
                            .verticalScroll(verticalScrollState)
                    ) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = "Html 2 Image",
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                } ?: run {
                    Text(
                        text = clipHtml.html,
                        fontFamily = FontFamily.SansSerif,
                        maxLines = 4,
                        softWrap = true,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colors.onBackground,
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }, {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    html(),
                    contentDescription = "Html",
                    modifier = Modifier.padding(3.dp).size(14.dp),
                    tint = MaterialTheme.colors.onBackground
                )
                Spacer(modifier = Modifier.size(3.dp))
                Text(
                    text = copywriter.getText("Html"),
                    fontFamily = FontFamily.SansSerif,
                    style = TextStyle(
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colors.onBackground,
                        fontSize = 10.sp
                    )
                )
            }
        })
    }
}