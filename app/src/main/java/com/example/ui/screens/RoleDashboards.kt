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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.data.local.OrderEntity
import com.example.data.model.UserRole
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
fun RoleDashboardView(
    currentRole: UserRole,
    bookings: List<BookingEntity>,
    orders: List<OrderEntity>,
    onBackToCustomer: () -> Unit,
    onUpdateBookingStatus: (String, String) -> Unit,
    onUpdateOrderStatus: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("role_dashboard_${currentRole.name.lowercase()}"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "${currentRole.label} Portal",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                    Text(
                        text = "Guwahati Ecosystem Management",
                        style = MaterialTheme.typography.bodySmall.copy(color = SacredSaffronDark)
                    )
                }
                OutlinedButton(
                    onClick = onBackToCustomer,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, SacredSaffron)
                ) {
                    Text("Customer View", fontSize = 11.sp, color = SacredSaffron)
                }
            }
        }

        when (currentRole) {
            UserRole.PUJARI_PARTNER -> {
                item {
                    PujariStatsCard()
                }
                item {
                    Text(
                        text = "Assigned Puja Bookings in Guwahati",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                }
                items(bookings) { booking ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SandalwoodBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = booking.pujaName,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = "₹${booking.amount}",
                                    fontWeight = FontWeight.Bold,
                                    color = SacredSaffronDark
                                )
                            }
                            Text(
                                text = "Devotee: ${booking.contactName} • ${booking.phone}",
                                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
                            )
                            Text(
                                text = "Venue: ${booking.address}",
                                style = MaterialTheme.typography.bodySmall.copy(color = TempleCharcoal)
                            )
                            Text(
                                text = "Schedule: ${booking.date} at ${booking.timeSlot}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SacredVermillion,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SaffronContainer
                                ) {
                                    Text(
                                        text = "Status: ${booking.status}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SacredSaffronDark,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Button(
                                    onClick = {
                                        val next = if (booking.status == "COMPLETED") "CONFIRMED" else "COMPLETED"
                                        onUpdateBookingStatus(booking.id, next)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text(
                                        text = if (booking.status == "COMPLETED") "Reopen Puja" else "Complete Vidhi",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            UserRole.STORE_MERCHANT -> {
                item {
                    MerchantStatsCard()
                }
                item {
                    Text(
                        text = "Incoming Samagri Store Orders",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                }
                items(orders) { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SandalwoodBorder, RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = order.orderCode,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = "₹${order.totalAmount}",
                                    fontWeight = FontWeight.Bold,
                                    color = SacredSaffronDark
                                )
                            }
                            Text(
                                text = "Items: ${order.itemsSummary}",
                                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
                            )
                            Text(
                                text = "Deliver to: ${order.deliveryAddress}",
                                style = MaterialTheme.typography.bodySmall.copy(color = TempleCharcoal)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = SaffronContainer
                                ) {
                                    Text(
                                        text = "Status: ${order.status}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = SacredSaffronDark,
                                            fontWeight = FontWeight.Bold
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                                Button(
                                    onClick = {
                                        val next = when (order.status) {
                                            "ORDER_PLACED" -> "PACKED"
                                            "PACKED" -> "OUT_FOR_DELIVERY"
                                            "OUT_FOR_DELIVERY" -> "DELIVERED"
                                            else -> "ORDER_PLACED"
                                        }
                                        onUpdateOrderStatus(order.id, next)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(32.dp)
                                ) {
                                    Text("Advance Status", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            UserRole.ADMIN -> {
                item {
                    AdminMetricsCard()
                }
                item {
                    PlatformGovernanceCard()
                }
            }

            UserRole.CUSTOMER -> { /* Handled in normal tabs */ }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
fun PujariStatsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SacredGold.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SandalwoodWarmSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "₹18,450",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredSaffronDark
                    )
                )
                Text(
                    text = "This Month Dakshina",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "14",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredSaffronDark
                    )
                )
                Text(
                    text = "Pujas Performed",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "4.9 ★",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredMarigold
                    )
                )
                Text(
                    text = "Devotee Rating",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
fun MerchantStatsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SacredGold.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SandalwoodWarmSurface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "₹32,800",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredSaffronDark
                    )
                )
                Text(
                    text = "Samagri Revenue",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "42",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredSaffronDark
                    )
                )
                Text(
                    text = "Fulfilled Orders",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "98%",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = AuspiciousGreen
                    )
                )
                Text(
                    text = "On-time Dispatch",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
fun AdminMetricsCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SacredGold, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = SandalwoodWarmSurface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Guwahati Metro Platform Live Telemetry",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "48 Verified",
                        fontWeight = FontWeight.Bold,
                        color = SacredSaffronDark
                    )
                    Text("Guwahati Pandits", fontSize = 11.sp, color = TempleBrownMuted)
                }
                Column {
                    Text(
                        text = "26 Stores",
                        fontWeight = FontWeight.Bold,
                        color = SacredSaffronDark
                    )
                    Text("Active Merchants", fontSize = 11.sp, color = TempleBrownMuted)
                }
                Column {
                    Text(
                        text = "142 Active",
                        fontWeight = FontWeight.Bold,
                        color = AuspiciousGreen
                    )
                    Text("This Week Bookings", fontSize = 11.sp, color = TempleBrownMuted)
                }
            }
        }
    }
}

@Composable
fun PlatformGovernanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(14.dp)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Vedic Standards & Quality Audits",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "• All listed Pujaris verified via Vedic Gurukul credentials & local temple sangha verification in Guwahati.\n• Partner stores audited bi-weekly for pure cow ghee, unadulterated camphor, and fresh ritual flowers.\n• Zero cancellation guarantee for devotees on scheduled auspicious days.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}
