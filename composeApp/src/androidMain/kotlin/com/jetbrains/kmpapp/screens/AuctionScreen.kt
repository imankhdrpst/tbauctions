package com.jetbrains.kmpapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.jetbrains.kmpapp.component.TopBar
import com.jetbrains.kmpapp.viewmodel.AuctionScreenState
import com.jetbrains.kmpapp.viewmodel.AuctionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AuctionScreen(
    auctionId: Int,
    onBack: () -> Unit,
) {
    val viewModel: AuctionViewModel = koinViewModel()
    val screenState by viewModel.state.collectAsStateWithLifecycle()

    var bidDialogVisible by remember { mutableStateOf(false) }
    var bidInput by remember { mutableStateOf("") }

    LaunchedEffect(auctionId) {
        viewModel.initialize(auctionId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // content
        when (val state = screenState) {
            is AuctionScreenState.Loading -> {
                Text("Loading data ...")
            }

            is AuctionScreenState.Error -> {
                Text(state.message)
            }

            is AuctionScreenState.Details -> {
                val auction = state.auction
                // top bar
                TopBar(
                    title = auction.product.name,
                    onBack = onBack
                )
                if (bidDialogVisible) {
                    AlertDialog(
                        onDismissRequest = { bidDialogVisible = false },
                        title = { Text("Enter your bid") },
                        text = {
                            Column {
                                Text("Enter a higher bid than the current:")
                                TextField(
                                    value = bidInput,
                                    onValueChange = { bidInput = it },
                                    singleLine = true
                                )
                            }
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                val bid = bidInput.toIntOrNull()
                                if (bid != null && bid > auction.product.currentBid) {
                                    // save in memory
                                    viewModel.placeNewBid(bid)
                                    bidDialogVisible = false
                                }
                            }) {
                                Text("Submit")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { bidDialogVisible = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }

                // image
                ProductLargeImage(
                    modifier = Modifier.weight(1f),
                    url = auction.product.mainImage?.imageUrlLarge
                )

                // name
                Text(
                    text = auction.product.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                )

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

                // place bid button
                Button(
                    onClick = { bidDialogVisible = true },
                    colors = ButtonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Red,
                        disabledContentColor = Color.White,
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Place bid")
                }
            }
        }
    }
}

@Composable
fun ProductLargeImage(
    modifier: Modifier = Modifier,
    url: String?,
) {
    if (url != null) {
        Image(
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            painter = rememberAsyncImagePainter(model = url),
            contentDescription = "product thumbnail",
            contentScale = ContentScale.Crop,
        )
    } else {
        Image(
            imageVector = Icons.Default.Lock,
            contentDescription = "product placeholder",
            modifier = modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}