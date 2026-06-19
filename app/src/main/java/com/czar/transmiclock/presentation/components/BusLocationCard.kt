package com.czar.transmiclock.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import com.czar.transmiclock.data.BusLocation

@Composable
fun BusLocationCard(
    busLocation: BusLocation,
    maxDistance: Int = 10000
) {
    val shape = RoundedCornerShape(24.dp)
    val progress = (1f - (busLocation.distanceMeters.toFloat() / maxDistance)).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(Color.Gray.darken(0.6f))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Top row — etiqueta + last update time
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = busLocation.etiqueta,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f)  // ← takes remaining space, pushes time right
            )
            Text(
                text = "Act. ${busLocation.lastTime}",  // ← shorter label
                style = MaterialTheme.typography.bodyExtraSmall,
                color = Color.White.copy(alpha = 0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Middle — ETA text
        Text(
            text = "A ${"%.1f".format(busLocation.distanceMeters / 1000f)} km · ${busLocation.minutesAway} min",
            style = MaterialTheme.typography.titleSmall,  // ← smaller than titleMedium
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
        )

        // Bottom — progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50))
                    .background(
                        when {
                            progress < 0.2f -> Color.Red
                            progress < 0.6f -> Color.Yellow
                            else -> Color.Green
                        }
                    )
            )
        }
    }
}

@Preview
@Composable
fun BusLocationCardPreview() {
    BusLocationCard(
        busLocation = BusLocation(
            vehicleId = 10049,
            etiqueta = "D0049",
            lastTime = "06:23:08 PM",
            minutesAway = 32,
            distanceMeters = 1200
        )
    )
}