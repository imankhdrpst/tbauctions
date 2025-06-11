package com.jetbrains.kmpapp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetbrains.kmpapp.screens.AuctionScreen
import com.jetbrains.kmpapp.screens.MainScreen

@Composable
fun App() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
    ) {
        Surface {
            val navController = rememberNavController()

            Scaffold { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "main",
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable("main") {
                        MainScreen(
                            onAuctionClick = { auctionId ->
                                navController.navigate("auction/${auctionId}")
                            },
                        )
                    }

                    composable(
                        route = "auction/{auctionId}",
                        arguments = listOf(navArgument("auctionId") {
                            type = NavType.IntType
                        })
                    ) { navBackStackEntry ->
                        navBackStackEntry.arguments?.getInt("auctionId")?.let { auctionId ->

                            AuctionScreen(
                                auctionId = auctionId,
                                onBack = { navController.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
