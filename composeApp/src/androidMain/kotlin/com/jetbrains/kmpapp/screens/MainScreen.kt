package com.jetbrains.kmpapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jetbrains.kmpapp.component.AuctionRow
import com.jetbrains.kmpapp.component.TopBar
import com.jetbrains.kmpapp.viewmodel.MainScreenState
import com.jetbrains.kmpapp.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onAuctionClick: (Int) -> Unit,
) {
    val viewModel: MainViewModel = koinViewModel()
    val screenState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // top bar
        TopBar(
            title = "Auctions",
            onBack = null,
        )

        when (val state = screenState) {
            is MainScreenState.Loading -> {
                Text("Loading data ...")
            }

            is MainScreenState.Error -> {
                Text(state.message)
            }

            is MainScreenState.Content -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(state.auctions) { auction ->
                        AuctionRow(
                            modifier = Modifier
                                .padding(12.dp)
                                .animateItem(),
                            auction = auction,
                            onClick = { auctionId -> onAuctionClick(auctionId) },
                        )
                    }
                }
            }
        }
    }
}