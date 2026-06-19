package com.czar.transmiclock.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.czar.transmiclock.data.BusStop
import com.czar.transmiclock.presentation.components.BusCard
import com.czar.transmiclock.presentation.components.LoadingBox
import com.czar.transmiclock.presentation.theme.TransmiClockTheme
import com.czar.transmiclock.presentation.viewmodels.BusViewModel

@Composable
fun BusStopDetailScreen(
    busStop: BusStop,
    viewModel: BusViewModel,
    onBusClick: () -> Unit
) {
    LaunchedEffect(busStop.id) {
        viewModel.busStop.value = busStop
        viewModel.fetchBuses()
    }

    TransmiClockTheme {
        AppScaffold {
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()

            val buses by viewModel.buses
            val isLoading by viewModel.isLoading

            ScreenScaffold { contentPadding ->
                TransformingLazyColumn(contentPadding = contentPadding, state = listState) {
                    item {
                        ListHeader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text(
                                text = busStop.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (isLoading) {
                        item {
                            LoadingBox()
                        }
                    }

                    items(buses) { bus ->
                        BusCard(
                            bus = bus,
                            onClick = {
                                viewModel.selectedBus.value = bus
                                onBusClick()
                            }
                        )
                    }
                }
            }
        }
    }
}
