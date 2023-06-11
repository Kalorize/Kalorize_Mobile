package com.kalorize.kalorizeappmobile.util

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

private const val MAXIMAL_SIZE = 4000000

fun getPath(context: Context, uri: Uri): String? {
    val isKitKat: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
        if (isExternalStorageDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]
            return if ("primary".equals(type, ignoreCase = true)) {
                Environment.getExternalStorageDirectory().toString() + "/" + split[1]
            } else {
                var filePath = "non"
                val extenal = context.externalMediaDirs
                for (f in extenal) {
                    filePath = f.absolutePath
                    if (filePath.contains(type)) {
                        val endIndex = filePath.indexOf("Android")
                        filePath = filePath.substring(0, endIndex) + split[1]
                    }
                }
                filePath
            }
        } else if (isDownloadsDocument(uri)) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(
                Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)
            )
            return getDataColumn(context, contentUri, null, null)
        } else if (isMediaDocument(uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            val split = docId.split(":").toTypedArray()
            val type = split[0]
            var contentUri: Uri? = null
            if ("image" == type) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            } else if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val selection = "_id=?"
            val selectionArgs = arrayOf(
                split[1]
            )
            return getDataColumn(context, contentUri, selection, selectionArgs)
        }
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
        return getDataColumn(context, uri, null, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
        return uri.path
    }
    return null
}

private fun getDataColumn(
    context: Context, uri: Uri?, selection: String?,
    selectionArgs: Array<String>?
): String? {
    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(
        column
    )
    try {
        cursor = context.contentResolver.query(
            uri!!, projection, selection, selectionArgs,
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(column_index)
        }
    } catch (e: java.lang.Exception) {
    } finally {
        cursor?.close()
    }
    return null
}

private fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
}

private fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
}

private fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    Log.i("file path reduceFileImage", file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > MAXIMAL_SIZE)
    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun rotateFile(file: File, isBackCamera: Boolean = false) {
    val matrix = Matrix()
    val bitmap = BitmapFactory.decodeFile(file.path)
    val rotation = if (isBackCamera) 90f else -90f
    matrix.postRotate(rotation)
    if (!isBackCamera) {
        matrix.postScale(-1f, 1f, bitmap.width / 2f, bitmap.height / 2f)
    }
    val result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    result.compress(Bitmap.CompressFormat.JPEG, 100, FileOutputStream(file))
}

enum class BorderOrder {
    Start, Center, End
}

fun Modifier.drawSegmentedBorder(
    strokeWidth: Dp,
    color: Color,
    cornerPercent: Int,
    borderOrder: BorderOrder,
    drawDivider: Boolean = false
) = composed(
    factory = {

        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height
            val cornerRadius = height * cornerPercent / 100

            when (borderOrder) {
                BorderOrder.Start -> {

                    drawLine(
                        color = color,
                        start = Offset(x = width, y = 0f),
                        end = Offset(x = cornerRadius, y = 0f),
                        strokeWidth = strokeWidthPx
                    )

                    // Top left arc
                    drawArc(
                        color = color,
                        startAngle = 180f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset.Zero,
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = cornerRadius),
                        end = Offset(x = 0f, y = height - cornerRadius),
                        strokeWidth = strokeWidthPx
                    )
                    // Bottom left arc
                    drawArc(
                        color = color,
                        startAngle = 90f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(x = 0f, y = height - 2 * cornerRadius),
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = cornerRadius, y = height),
                        end = Offset(x = width, y = height),
                        strokeWidth = strokeWidthPx
                    )
                }
                BorderOrder.Center -> {
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = width, y = 0f),
                        strokeWidth = strokeWidthPx
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = height),
                        end = Offset(x = width, y = height),
                        strokeWidth = strokeWidthPx
                    )

                    if (drawDivider) {
                        drawLine(
                            color = color,
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = height),
                            strokeWidth = strokeWidthPx
                        )
                    }
                }
                else -> {

                    if (drawDivider) {
                        drawLine(
                            color = color,
                            start = Offset(x = 0f, y = 0f),
                            end = Offset(x = 0f, y = height),
                            strokeWidth = strokeWidthPx
                        )
                    }

                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = 0f),
                        end = Offset(x = width - cornerRadius, y = 0f),
                        strokeWidth = strokeWidthPx
                    )

                    // Top right arc
                    drawArc(
                        color = color,
                        startAngle = 270f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(x = width - cornerRadius * 2, y = 0f),
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = width, y = cornerRadius),
                        end = Offset(x = width, y = height - cornerRadius),
                        strokeWidth = strokeWidthPx
                    )
                    // Bottom right arc
                    drawArc(
                        color = color,
                        startAngle = 0f,
                        sweepAngle = 90f,
                        useCenter = false,
                        topLeft = Offset(
                            x = width - 2 * cornerRadius,
                            y = height - 2 * cornerRadius
                        ),
                        size = Size(cornerRadius * 2, cornerRadius * 2),
                        style = Stroke(width = strokeWidthPx)
                    )
                    drawLine(
                        color = color,
                        start = Offset(x = 0f, y = height),
                        end = Offset(x = width -cornerRadius, y = height),
                        strokeWidth = strokeWidthPx
                    )
                }
            }
        }
    }
)
