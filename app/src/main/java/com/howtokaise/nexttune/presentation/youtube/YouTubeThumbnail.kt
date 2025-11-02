package com.howtokaise.nexttune.presentation.youtube

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun YouTubeThumbnail(
    imageId: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val thumbnailUrl = "https://img.youtube.com/vi/$imageId/hqdefault.jpg"

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
//            .clickable {
//                // open YouTube app or browser
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://www.youtube.com/watch?v=$imageId")
//                )
//                context.startActivity(intent)
//            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(thumbnailUrl),
            contentDescription = "YouTube Thumbnail",
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
    }
}
