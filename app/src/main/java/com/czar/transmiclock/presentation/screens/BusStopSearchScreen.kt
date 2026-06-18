package com.czar.transmiclock.presentation.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.ListHeader
import androidx.wear.compose.material3.ScreenScaffold
import androidx.wear.compose.material3.SurfaceTransformation
import androidx.wear.compose.material3.lazy.rememberTransformationSpec
import androidx.wear.compose.material3.lazy.transformedHeight
import com.czar.transmiclock.presentation.components.BusStopCard
import com.czar.transmiclock.presentation.components.LoadingBox
import com.czar.transmiclock.presentation.components.SearchHeaderContent
import com.czar.transmiclock.presentation.theme.TransmiClockTheme
import com.czar.transmiclock.presentation.viewmodels.BusStopViewModel

@Composable
fun BusStopSearchScreen(
    onBusStopClick: () -> Unit,
    viewModel: BusStopViewModel = viewModel()
) {
    TransmiClockTheme {
        AppScaffold {
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()
            var searchQuery by remember { mutableStateOf("") }

            val busStops by viewModel.searchResults
            val isLoading by viewModel.isLoading

            ScreenScaffold(scrollState = listState) { contentPadding ->
                TransformingLazyColumn(contentPadding = contentPadding, state = listState) {
                    item {
                        ListHeader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            SearchHeaderContent(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                onSearch = { query ->
                                    viewModel.search(query)
                                },
                            )
                        }
                    }

                    if (isLoading) {
                        item {
                            LoadingBox()
                        }
                    }

                    items(busStops) { busStop ->
                        BusStopCard(
                            busStop = busStop,
                            onClick = {
                                viewModel.selectBusStop(busStop)
                                onBusStopClick()
                            }
                        )
                    }
                }
            }
        }
    }
}