package com.jetbrains.kmpapp.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.jetbrains.kmpapp.data.Auction

@Composable
fun AuctionRow(
    modifier: Modifier = Modifier,
    auction: Auction,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier.clickable { onClick(auction.product.id) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            // image and the name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // thumbnail image
                ProductThumbnail(url = auction.product.mainImage?.imageUrlThumb)

                Box(
                    modifier = Modifier.height(100.dp).weight(1f)
                ) {

                    // name
                    Text(
                        modifier = Modifier.align(Alignment.TopStart),
                        text = auction.product.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                    )
                }
            }

            // description
            Text(
                text = auction.product.description ?: "",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
            )

            // municipality name
            Text(
                text = auction.product.municipalityName,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
            )

            // current bid
            Text(
                text = "Current bid: ${auction.product.currentBid}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
            )

            // categories
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                // category 1
                auction.category1?.let {
                    Text(
                        text = it.headline,
                        color = Color.Blue,
                    )
                }

                // category 2
                auction.category2?.let {
                    Text(
                        text = it.headline,
                        color = Color.Blue,
                    )
                }

                // category 3
                auction.category3?.let {
                    Text(
                        text = it.headline,
                        color = Color.Blue,
                    )
                }
            }

            // end date
            Text(
                text = auction.product.endDate,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun ProductThumbnail(url: String?) {
    if (url != null) {
        Image(
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
            painter = rememberAsyncImagePainter(model = url),
            contentDescription = "product thumbnail",
            contentScale = ContentScale.Crop
        )
    } else {
        Image(
            imageVector = Icons.Default.Lock,
            contentDescription = "product placeholder",
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}