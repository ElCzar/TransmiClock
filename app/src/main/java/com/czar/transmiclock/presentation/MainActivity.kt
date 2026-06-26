package com.czar.transmiclock.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.czar.transmiclock.presentation.screens.BusLocationScreen
import com.czar.transmiclock.presentation.screens.BusStopDetailScreen
import com.czar.transmiclock.presentation.screens.BusStopSearchScreen
import com.czar.transmiclock.presentation.screens.TransmiClockScreen
import com.czar.transmiclock.presentation.viewmodels.BusStopViewModel
import com.czar.transmiclock.presentation.viewmodels.BusViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberSwipeDismissableNavController()
            val busStopViewModel: BusStopViewModel = viewModel()
            val busViewModel: BusViewModel = viewModel()

            SwipeDismissableNavHost(
                navController = navController,
                startDestination = Routes.HOME
            ) {
                composable(Routes.HOME) {
                    TransmiClockScreen(
                        onSearch = {
                            navController.navigate(Routes.BUS_STOP_SEARCH)
                        },
                        onBusStopClick = {
                            navController.navigate(Routes.BUS_STOP_DETAIL)
                        },
                        viewModel = busStopViewModel
                    )
                }
                composable(Routes.BUS_STOP_SEARCH) {
                    BusStopSearchScreen(
                        onBusStopClick = {
                            navController.navigate(Routes.BUS_STOP_DETAIL)
                        },
                        viewModel = busStopViewModel
                    )
                }
                composable(Routes.BUS_STOP_DETAIL) {
                    busStopViewModel.selectedBusStop.value?.let { busStop ->
                        BusStopDetailScreen(
                            busStop = busStop,
                            viewModel = busViewModel,
                            onBusClick = {
                                navController.navigate(Routes.BUS_LOCATION)
                            }
                        )
                    }
                }
                composable(Routes.BUS_LOCATION) {
                    busViewModel.selectedBus.value?.let { bus ->
                        BusLocationScreen(
                            selectedBus = bus,
                            viewModel = busViewModel
                        )
                    }
                }
            }
        }
    }
}

object Routes {
    const val HOME = "home"
    const val BUS_STOP_SEARCH = "bus_stop_search"
    const val BUS_STOP_DETAIL = "bus_stop_detail"
    const val BUS_LOCATION = "bus_location"
}
