package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
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
import com.example.data.local.OrderEntity
import com.example.ui.theme.AuspiciousGreen
import com.example.ui.theme.AuspiciousGreenContainer
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@Composable
fun OrdersScreen(
    orders: List<OrderEntity>,
    onExploreSamagri: () -> Unit,
    onSimulateNextStatus: (orderId: String, currentStatus: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("orders_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Samagri Orders",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = "Track your pure puja items & partner store deliveries",
                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
            )
        }

        if (orders.isEmpty()) {
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
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = SacredSaffron,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Samagri Orders Yet",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Order 100% shuddh puja samagri from top Guwahati stores like Kamakhya Bhandar or Ganeshguri Vedic.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleBrownMuted,
                            fontSize = 12.sp
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Button(
                        onClick = onExploreSamagri,
                        colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Browse Samagri Items")
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
                items(orders) { order ->
                    OrderItemCard(
                        order = order,
                        onSimulateNextStatus = {
                            val next = when (order.status) {
                                "ORDER_PLACED" -> "PACKED"
                                "PACKED" -> "OUT_FOR_DELIVERY"
                                "OUT_FOR_DELIVERY" -> "DELIVERED"
                                else -> "ORDER_PLACED"
                            }
                            onSimulateNextStatus(order.id, next)
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
fun OrderItemCard(
    order: OrderEntity,
    onSimulateNextStatus: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp))
            .testTag("order_item_${order.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header: Store & Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = null,
                        tint = SacredSaffron,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = order.storeName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                }
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (order.status) {
                        "DELIVERED" -> AuspiciousGreenContainer
                        "OUT_FOR_DELIVERY" -> SaffronContainer
                        else -> SandalwoodWarmSurface
                    }
                ) {
                    Text(
                        text = order.status.replace("_", " "),
                        color = when (order.status) {
                            "DELIVERED" -> AuspiciousGreen
                            "OUT_FOR_DELIVERY" -> SacredSaffronDark
                            else -> TempleCharcoal
                        },
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Order ID: ${order.orderCode}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 11.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = SandalwoodBorder)
            Spacer(modifier = Modifier.height(8.dp))

            // Items Summary
            Text(
                text = "Items (${order.itemCount}):",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = order.itemsSummary,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Address & Payment
            Text(
                text = "Delivery to: ${order.deliveryAddress}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleCharcoal,
                    fontSize = 11.sp
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Step Progress Tracker
            OrderTrackerProgress(currentStatus = order.status)

            Spacer(modifier = Modifier.height(10.dp))

            // Footer Total & Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Paid: ₹${order.totalAmount}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SacredSaffronDark
                        )
                    )
                    Text(
                        text = order.paymentStatus,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                            color = TempleBrownMuted
                        )
                    )
                }

                // Status simulator button (helpful for testing step progress)
                OutlinedButton(
                    onClick = onSimulateNextStatus,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, SacredSaffron),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = if (order.status == "DELIVERED") "Reopen Step" else "Update Status",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SacredSaffron
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun OrderTrackerProgress(currentStatus: String) {
    val steps = listOf("Placed", "Packed", "On The Way", "Delivered")
    val currentStepIndex = when (currentStatus) {
        "ORDER_PLACED" -> 0
        "PACKED" -> 1
        "OUT_FOR_DELIVERY" -> 2
        "DELIVERED" -> 3
        else -> 0
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SandalwoodWarmSurface)
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, stepName ->
            val isCompleted = index <= currentStepIndex
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(if (isCompleted) SacredSaffron else Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(11.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stepName,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isCompleted) FontWeight.Bold else FontWeight.Normal,
                        color = if (isCompleted) TempleCharcoal else TempleBrownMuted,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}
