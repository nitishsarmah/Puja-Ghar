package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.BookingEntity
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredMarigold
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RatingDialog(
    booking: BookingEntity,
    onDismiss: () -> Unit,
    onSubmit: (rating: Int, comment: String) -> Unit
) {
    var selectedStars by remember { mutableStateOf(5) }
    var reviewComment by remember { mutableStateOf("") }
    val tags = listOf("Punctual Arrival", "Pure Sanskrit Chanting", "Shuddh Vidhi", "Courteous & Humble", "Complete Samagri Care")
    var selectedTags by remember { mutableStateOf(setOf<String>()) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, SandalwoodBorder, RoundedCornerShape(20.dp))
                .testTag("rating_dialog"),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Rate & Review Service",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = booking.pujaName,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TempleCharcoal
                    )
                )
                Text(
                    text = "Performed by ${booking.pujariName}",
                    style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Star Picker
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        IconButton(
                            onClick = { selectedStars = i },
                            modifier = Modifier
                                .size(44.dp)
                                .testTag("star_$i")
                        ) {
                            Icon(
                                imageVector = if (i <= selectedStars) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = "$i Stars",
                                tint = if (i <= selectedStars) SacredMarigold else Color.LightGray,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                Text(
                    text = when (selectedStars) {
                        5 -> "Divine & Soulful Experience (5/5)"
                        4 -> "Very Good & Authentic (4/5)"
                        3 -> "Satisfactory (3/5)"
                        else -> "Needs Improvement"
                    },
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = SacredSaffron,
                        fontWeight = FontWeight.Bold
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Quick tags
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    tags.forEach { tag ->
                        val isChosen = selectedTags.contains(tag)
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isChosen) SacredSaffron else SandalwoodWarmSurface,
                            border = BorderStroke(1.dp, if (isChosen) SacredSaffron else SandalwoodBorder),
                            modifier = Modifier
                                .clickable {
                                    selectedTags = if (isChosen) selectedTags - tag else selectedTags + tag
                                }
                        ) {
                            Text(
                                text = tag,
                                color = if (isChosen) Color.White else TempleCharcoal,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Normal
                                ),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedTextField(
                    value = reviewComment,
                    onValueChange = { reviewComment = it },
                    label = { Text("Write your devotional feedback...") },
                    placeholder = { Text("How was the Pandit's chanting and ritual guidance?") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .testTag("review_comment_input"),
                    shape = RoundedCornerShape(10.dp),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        val fullFeedback = if (selectedTags.isNotEmpty()) {
                            val tagString = selectedTags.joinToString(", ")
                            if (reviewComment.isNotBlank()) "$reviewComment (Highlights: $tagString)" else "Highlights: $tagString"
                        } else reviewComment
                        onSubmit(selectedStars, fullFeedback)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("submit_review_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron)
                ) {
                    Text("Submit Review & Rating", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
