package com.clipevery.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp


@Composable
fun warning(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "Warning", defaultWidth = 44.0.dp, defaultHeight = 44.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2c3e50)),
                strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.0f, 9.0f)
                verticalLineToRelative(4.0f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2c3e50)),
                strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.363f, 3.591f)
                lineToRelative(-8.106f, 13.534f)
                arcToRelative(1.914f, 1.914f, 0.0f, false, false, 1.636f, 2.871f)
                horizontalLineToRelative(16.214f)
                arcToRelative(1.914f, 1.914f, 0.0f, false, false, 1.636f, -2.87f)
                lineToRelative(-8.106f, -13.536f)
                arcToRelative(1.914f, 1.914f, 0.0f, false, false, -3.274f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2c3e50)),
                strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.0f, 16.0f)
                horizontalLineToRelative(0.01f)
            }
        }
            .build()
    }
}

@Composable
fun question(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "Question", defaultWidth = 44.0.dp, defaultHeight = 44.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2c3e50)),
                strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(9.103f, 2.0f)
                horizontalLineToRelative(5.794f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 2.122f, 0.879f)
                lineToRelative(4.101f, 4.1f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 0.88f, 2.125f)
                verticalLineToRelative(5.794f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, -0.879f, 2.122f)
                lineToRelative(-4.1f, 4.101f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, -2.123f, 0.88f)
                horizontalLineToRelative(-5.795f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, -2.122f, -0.88f)
                lineToRelative(-4.101f, -4.1f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, -0.88f, -2.124f)
                verticalLineToRelative(-5.794f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 0.879f, -2.122f)
                lineToRelative(4.1f, -4.101f)
                arcToRelative(3.0f, 3.0f, 0.0f, false, true, 2.125f, -0.88f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2c3e50)),
                strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.0f, 9.0f)
                horizontalLineToRelative(0.01f)
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2c3e50)),
                strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(11.0f, 12.0f)
                horizontalLineToRelative(1.0f)
                verticalLineToRelative(4.0f)
                horizontalLineToRelative(1.0f)
            }
        }
            .build()
    }
}

@Composable
fun arrowLeft(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "arrowLeft", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.41f, 7.41f)
                lineTo(14.0f, 6.0f)
                lineToRelative(-6.0f, 6.0f)
                lineToRelative(6.0f, 6.0f)
                lineToRelative(1.41f, -1.41f)
                lineTo(10.83f, 12.0f)
                lineToRelative(4.58f, -4.59f)
                close()
            }
        }
            .build()
    }
}

@Composable
fun arrowRight(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "arrowRight", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.59f, 16.59f)
                lineTo(10.0f, 18.0f)
                lineToRelative(6.0f, -6.0f)
                lineToRelative(-6.0f, -6.0f)
                lineToRelative(-1.41f, 1.41f)
                lineTo(13.17f, 12.0f)
                lineToRelative(-4.58f, 4.59f)
                close()
            }
        }
            .build()
    }
}

@Composable
fun arrowUp(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "ExpandLessBlack24dp", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.0f, 8.0f)
                lineToRelative(-6.0f, 6.0f)
                lineToRelative(1.41f, 1.41f)
                lineTo(12.0f, 10.83f)
                lineToRelative(4.59f, 4.58f)
                lineTo(18.0f, 14.0f)
                lineToRelative(-6.0f, -6.0f)
                close()
            }
        }
            .build()
    }
}

@Composable
fun arrowDown(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "ExpandMoreBlack24dp", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.59f, 8.59f)
                lineTo(12.0f, 13.17f)
                lineTo(7.41f, 8.59f)
                lineTo(6.0f, 10.0f)
                lineToRelative(6.0f, 6.0f)
                lineToRelative(6.0f, -6.0f)
                lineToRelative(-1.41f, -1.41f)
                close()
            }
        }
            .build()
    }
}

@Preview
@Composable
fun showIcon() {
    Icon(arrowLeft(), contentDescription = "arrowLeft")
    Icon(arrowRight(), contentDescription = "arrowRight")
    Icon(arrowUp(), contentDescription = "arrowUp")
    Icon(arrowDown(), contentDescription = "arrowDown")
}