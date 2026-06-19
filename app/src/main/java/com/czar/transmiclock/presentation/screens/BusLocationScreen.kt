package com.czar.transmiclock.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import com.czar.transmiclock.data.Bus
import com.czar.transmiclock.presentation.components.BusLocationCard
import com.czar.transmiclock.presentation.components.LoadingBox
import com.czar.transmiclock.presentation.theme.TransmiClockTheme
import com.czar.transmiclock.presentation.viewmodels.BusViewModel

@Composable
fun BusLocationScreen(
    selectedBus: Bus,
    viewModel: BusViewModel
) {
    LaunchedEffect(selectedBus.id) {
        viewModel.fetchBusLocation()
    }

    TransmiClockTheme {
        AppScaffold {
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()
            val pullToRefreshState = rememberPullToRefreshState()

            val busLocations by viewModel.busLocations
            val busStopName by viewModel.busStop
            val isLoading by viewModel.isLoading

            ScreenScaffold { contentPadding ->
                PullToRefreshBox(
                    state = pullToRefreshState,
                    isRefreshing = isLoading,
                    onRefresh = { viewModel.fetchBusLocation() },
                    modifier = Modifier.fillMaxSize(),
                    indicator = {
                        if (!isLoading) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.TopCenter
                            ) {
                                PullToRefreshDefaults.Indicator(
                                    state = pullToRefreshState,
                                    isRefreshing = false,
                                )
                            }
                        }
                    }
                ) {
                    TransformingLazyColumn(contentPadding = contentPadding, state = listState) {
                        item {
                            ListHeader(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .transformedHeight(this, transformationSpec),
                                transformation = SurfaceTransformation(transformationSpec),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "${selectedBus.codigo} ${selectedBus.nombre}",
                                        style = MaterialTheme.typography.titleMedium,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Text(
                                        text = busStopName?.nombre ?: "",
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }

                        if (isLoading) {
                            item {
                                LoadingBox()
                            }
                        } else if (busLocations.isEmpty()) {
                            item {
                                Text(
                                    text = "No hay buses",
                                    style = MaterialTheme.typography.bodySmall,
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        items(busLocations) { busLocation ->
                            BusLocationCard(
                                busLocation
                            )
                        }
                    }

                }
            }
        }
    }
}