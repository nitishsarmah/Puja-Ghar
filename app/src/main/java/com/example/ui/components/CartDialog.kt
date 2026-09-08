package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.example.data.local.CartItemEntity
import com.example.data.model.GuwahatiLocality
import com.example.ui.theme.AuspiciousGreen
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@Composable
fun CartDialog(
    cartItems: List<CartItemEntity>,
    totalAmount: Int,
    currentLocality: GuwahatiLocality,
    onDismiss: () -> Unit,
    onRemoveItem: (String) -> Unit,
    onUpdateQuantity: (String, Int) -> Unit,
    onClearCart: () -> Unit,
    onCheckout: (address: String, phone: String, paymentMethod: String) -> Unit
) {
    var deliveryAddress by remember { mutableStateOf("Survey Path, Near Market, ${currentLocality.name}, Guwahati") }
    var contactName by remember { mutableStateOf("Nitish Sarmah") }
    var phone by remember { mutableStateOf("+91 98640 54321") }
    val paymentOptions = listOf("UPI (Google Pay / PhonePe)", "Credit / Debit Card", "Cash on Delivery (COD)")
    var selectedPayment by remember { mutableStateOf(paymentOptions[0]) }
    var isPlacing by remember { mutableStateOf(false) }

    val subtotal = cartItems.sumOf { it.price * it.quantity }
    val deliveryFee = if (subtotal == 0 || subtotal >= 300) 0 else 40
    val grandTotal = subtotal + deliveryFee

    Dialog(
        onDismissRequest = { if (!isPlacing) onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 24.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, SandalwoodBorder, RoundedCornerShape(20.dp))
                .testTag("cart_dialog"),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(SacredSaffron.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                tint = SacredSaffron,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Puja Samagri Cart",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TempleCharcoal
                                )
                            )
                            Text(
                                text = "${cartItems.sumOf { it.quantity }} items from partner stores",
                                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (cartItems.isNotEmpty()) {
                            IconButton(
                                onClick = onClearCart,
                                enabled = !isPlacing,
                                modifier = Modifier.testTag("clear_cart_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Clear Cart",
                                    tint = Color.Red.copy(alpha = 0.7f),
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                        IconButton(onClick = onDismiss, enabled = !isPlacing) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Your samagri cart is empty.\nExplore nearby partner stores to add pure puja items.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = TempleBrownMuted),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                } else {
                    // Items List
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f, fill = false)
                            .height(180.dp)
                    ) {
                        items(cartItems) { item ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SandalwoodWarmSurface)
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TempleCharcoal
                                        ),
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "${item.weightUnit} • ₹${item.price} each",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = TempleBrownMuted,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                                
                                // Quantity Controller with Minus, Count, Plus
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.surface)
                                        .border(0.5.dp, SandalwoodBorder, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    IconButton(
                                        onClick = { onUpdateQuantity(item.itemId, item.quantity - 1) },
                                        modifier = Modifier.size(26.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = "Decrease",
                                            tint = SacredSaffronDark,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }

                                    Text(
                                        text = "${item.quantity}",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TempleCharcoal
                                        ),
                                        modifier = Modifier.padding(horizontal = 6.dp)
                                    )

                                    IconButton(
                                        onClick = { onUpdateQuantity(item.itemId, item.quantity + 1) },
                                        modifier = Modifier.size(26.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Increase",
                                            tint = SacredSaffronDark,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                IconButton(
                                    onClick = { onRemoveItem(item.itemId) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = "Remove",
                                        tint = Color.Red.copy(alpha = 0.8f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(color = SandalwoodBorder)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Address and Contact Details
                    OutlinedTextField(
                        value = deliveryAddress,
                        onValueChange = { deliveryAddress = it },
                        label = { Text("Guwahati Delivery Address") },
                        leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = SacredSaffron) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("cart_address_input"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Contact Phone") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SacredSaffron) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("cart_phone_input"),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Payment
                    Text(
                        text = "Payment Method",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    paymentOptions.forEach { method ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedPayment = method }
                                .padding(vertical = 2.dp)
                        ) {
                            RadioButton(
                                selected = selectedPayment == method,
                                onClick = { selectedPayment = method },
                                colors = RadioButtonDefaults.colors(selectedColor = SacredSaffron)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = method,
                                style = MaterialTheme.typography.bodyMedium.copy(color = TempleCharcoal)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Item Subtotal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Item Subtotal",
                            style = MaterialTheme.typography.bodyMedium.copy(color = TempleBrownMuted)
                        )
                        Text(
                            text = "₹$subtotal",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TempleCharcoal,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    // Delivery fee breakdown
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Delivery to ${currentLocality.name}",
                            style = MaterialTheme.typography.bodyMedium.copy(color = TempleBrownMuted)
                        )
                        Text(
                            text = if (deliveryFee == 0) "FREE (Promo)" else "₹$deliveryFee",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = if (deliveryFee == 0) AuspiciousGreen else TempleCharcoal,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    // Grand Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Grand Total",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TempleCharcoal
                            )
                        )
                        Text(
                            text = "₹$grandTotal",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = SacredSaffronDark
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            isPlacing = true
                            onCheckout(deliveryAddress, phone, selectedPayment)
                        },
                        enabled = !isPlacing && deliveryAddress.isNotBlank() && phone.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("checkout_order_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron)
                    ) {
                        if (isPlacing) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Confirming Order...")
                        } else {
                            Text(
                                text = "Place Order • ₹$grandTotal",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
