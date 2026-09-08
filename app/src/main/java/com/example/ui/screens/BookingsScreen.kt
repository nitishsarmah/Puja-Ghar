package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BookingEntity
import com.example.ui.theme.AuspiciousGreen
import com.example.ui.theme.AuspiciousGreenContainer
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredMarigold
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SacredVermillion
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@Composable
fun BookingsScreen(
    bookings: List<BookingEntity>,
    onExplorePujas: () -> Unit,
    onRateBooking: (BookingEntity) -> Unit,
    onSimulateStatus: (bookingId: String, nextStatus: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("bookings_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "My Puja Bookings",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = "Manage your scheduled Vedic pujas & assigned pandits",
                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
            )
        }

        if (bookings.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(SaffronContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolunteerActivism,
                            contentDescription = null,
                            tint = SacredSaffron,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Bookings Found",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Book a certified Vedic pandit or complete puja package for your home or office in Guwahati.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleBrownMuted,
                            fontSize = 12.sp
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Button(
                        onClick = onExplorePujas,
                        colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Explore Pujas & Packages")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(bookings) { booking ->
                    BookingItemCard(
                        booking = booking,
                        onRateClick = { onRateBooking(booking) },
                        onSimulateStatus = {
                            val next = when (booking.status) {
                                "CONFIRMED" -> "PUJARI_ASSIGNED"
                                "PUJARI_ASSIGNED" -> "IN_PROGRESS"
                                "IN_PROGRESS" -> "COMPLETED"
                                else -> "CONFIRMED"
                            }
                            onSimulateStatus(booking.id, next)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: BookingEntity,
    onRateClick: () -> Unit,
    onSimulateStatus: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp))
            .testTag("booking_item_${booking.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Booking Code & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = booking.pujaName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "Booking ID: ${booking.bookingCode}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleBrownMuted,
                            fontSize = 11.sp
                        )
                    )
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (booking.status) {
                        "COMPLETED" -> AuspiciousGreenContainer
                        "IN_PROGRESS" -> SaffronContainer
                        else -> SandalwoodWarmSurface
                    }
                ) {
                    Text(
                        text = booking.status.replace("_", " "),
                        color = when (booking.status) {
                            "COMPLETED" -> AuspiciousGreen
                            "IN_PROGRESS" -> SacredSaffronDark
                            else -> TempleCharcoal
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Date & Time
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SandalwoodWarmSurface)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = SacredSaffron,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = booking.date,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TempleCharcoal
                        )
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = null,
                        tint = SacredSaffron,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = booking.timeSlot,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TempleCharcoal
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Assigned Pandit Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SaffronContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = SacredSaffronDark,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Assigned Pandit",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                            color = TempleBrownMuted
                        )
                    )
                    Text(
                        text = booking.pujariName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SandalwoodWarmSurface,
                    border = BorderStroke(0.5.dp, SandalwoodBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Contact",
                            tint = AuspiciousGreen,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = booking.phone,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = TempleCharcoal,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Address
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = TempleBrownMuted,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = booking.address,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    ),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = SandalwoodBorder)
            Spacer(modifier = Modifier.height(10.dp))

            // If already rated:
            if (booking.rating > 0) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFF8E1),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            for (i in 1..booking.rating) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = null,
                                    tint = SacredMarigold,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Your Review (${booking.rating}/5)",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TempleCharcoal
                                )
                            )
                        }
                        if (booking.reviewComment.isNotBlank()) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "\"${booking.reviewComment}\"",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TempleCharcoal,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Bottom row: Dakshina amount, Rate button, and Status progression
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "₹${booking.amount}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = SacredSaffronDark
                        )
                    )
                    Text(
                        text = booking.paymentMethod,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                            color = TempleBrownMuted
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Rate & Review Button
                    if (booking.rating == 0) {
                        Button(
                            onClick = onRateClick,
                            colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .height(34.dp)
                                .testTag("rate_booking_btn_${booking.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Rate & Review",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Next Status action button (enables testing the ceremony lifecycle)
                    OutlinedButton(
                        onClick = onSimulateStatus,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, SandalwoodBorder),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text(
                            text = if (booking.status == "COMPLETED") "Reopen" else "Next Step",
                            style = MaterialTheme.typography.labelSmall.copy(color = TempleBrownMuted)
                        )
                    }
                }
            }
        }
    }
}
