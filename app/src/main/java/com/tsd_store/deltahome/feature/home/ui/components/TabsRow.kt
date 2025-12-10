package com.tsd_store.deltahome.feature.home.ui.components

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import com.tsd_store.deltahome.domain.old_domain.model.Room
import kotlin.math.roundToInt

@Composable
 fun TabsRow(
    rooms: List<Room>,
    selectedRoomId: String?,
    onSelected: (String?) -> Unit,
    onDeleteRoom: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            val selected = selectedRoomId == null

            AssistChip(
                onClick = { onSelected(null) },
                label = { Text("Favorites") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selected)
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    else
                        MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(bottom = 25.dp)
            )
        }

        items(rooms, key = { it.id }) { room ->
            var offsetY by remember { mutableStateOf(0f) }

            val maxOffset = 140f
            val deleteThreshold = 80f
            val progress = (offsetY / deleteThreshold)
                .coerceIn(0f, 1f)

            Box(
                modifier = Modifier
                    .height(40.dp + 32.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error.copy(alpha = progress),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                )

                Box(
                    modifier = Modifier
                        .padding()
                        .align(Alignment.TopCenter)
                        .offset { IntOffset(0, offsetY.roundToInt()) }
                        .pointerInput(room.id) {
                            detectVerticalDragGestures(
                                onVerticalDrag = { change, dragAmount ->
                                    change.consume()

                                    val newOffset = offsetY + dragAmount
                                    offsetY = newOffset
                                        .coerceIn(0f, maxOffset)
                                },
                                onDragEnd = {
                                    if (offsetY >= deleteThreshold) {

                                        onDeleteRoom(room.id)
                                    } else {

                                        offsetY = 0f
                                    }
                                },
                                onDragCancel = {
                                    offsetY = 0f
                                }
                            )
                        }
                ) {
                    val selected = selectedRoomId == room.id
                    AssistChip(
                        onClick = { onSelected(room.id) },
                        label = { Text(room.name) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (selected)
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            else
                                MaterialTheme.colorScheme.surface,
                            labelColor = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}
