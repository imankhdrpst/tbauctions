package com.jetbrains.kmpapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)?
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        // title
        title?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 32.dp),
                text = title,
                maxLines = 1,
            )
        }

        // top bar
        onBack?.let {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBack() },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back arrow",
            )
        }
    }
}