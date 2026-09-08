package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
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
import com.example.data.model.GuwahatiLocality
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.ui.theme.AuspiciousGreen
import com.example.ui.theme.AuspiciousGreenContainer
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SacredVermillion
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookingDialog(
    pujaService: PujaService? = null,
    pujari: Pujari? = null,
    currentLocality: GuwahatiLocality,
    availablePujaris: List<Pujari>,
    onDismiss: () -> Unit,
    onConfirm: (
        bookingType: String,
        pujaName: String,
        pujariName: String,
        date: String,
        timeSlot: String,
        address: String,
        contactName: String,
        phone: String,
        amount: Int,
        paymentMethod: String
    ) -> Unit
) {
    val initialPujaName = pujaService?.title ?: (pujari?.let { "Vedic Ceremony by ${it.name}" } ?: "Personalized Puja Ceremony")
    val initialPujariName = pujari?.name ?: (availablePujaris.firstOrNull()?.name ?: "Pandit Bhaskar Sarma")
    
    val tierOptions = if (pujari != null && pujaService == null) {
        listOf("Pujari Only")
    } else {
        listOf("Complete Package", "Samagri Only", "Pujari Only")
    }
    var selectedTier by remember { 
        mutableStateOf(if (pujari != null && pujaService == null) "Pujari Only" else "Complete Package") 
    }

    val computedAmount = when (selectedTier) {
        "Complete Package" -> pujaService?.packagePrice ?: pujaService?.price ?: 3199
        "Samagri Only" -> pujaService?.samagriOnlyPrice ?: 1299
        "Pujari Only" -> pujaService?.pujariOnlyPrice ?: pujari?.dakshina ?: 1899
        else -> pujaService?.price ?: 2100
    }

    val computedBookingType = when (selectedTier) {
        "Complete Package" -> "COMPLETE_PACKAGE"
        "Samagri Only" -> "SAMAGRI_ONLY"
        "Pujari Only" -> "PUJARI_ONLY"
        else -> "PUJA_CUSTOM"
    }

    val dateOptions = listOf(
        "Tomorrow (Auspicious)",
        "12 Sep (Ekadashi)",
        "15 Sep (Purnima)",
        "18 Sep (Shukla)",
        "21 Sep (Sunday)"
    )
    val timeSlots = listOf(
        "06:30 AM (Brahma Muhurta)",
        "09:00 AM (Pratah Kal)",
        "11:30 AM (Madhyanha)",
        "04:00 PM (Aparanha)",
        "06:00 PM (Sandhya Kal)"
    )
    val paymentOptions = listOf(
        "UPI (Google Pay / PhonePe / Paytm)",
        "Credit / Debit Card",
        "Net Banking (Assam Gramin / SBI)",
        "Cash on Completion (Dakshina)"
    )

    var selectedDate by remember { mutableStateOf(dateOptions[0]) }
    var selectedTimeSlot by remember { mutableStateOf(timeSlots[1]) }
    var selectedPujariName by remember { mutableStateOf(initialPujariName) }
    var addressText by remember { mutableStateOf("House No. 24, Near Mandir Path, ${currentLocality.name}, Guwahati") }
    var contactName by remember { mutableStateOf("Nitish Sarmah") }
    var contactPhone by remember { mutableStateOf("+91 98640 54321") }
    var selectedPaymentMethod by remember { mutableStateOf(paymentOptions[0]) }

    var isProcessing by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { if (!isProcessing) onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(vertical = 24.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, SandalwoodBorder, RoundedCornerShape(20.dp))
                .testTag("booking_dialog"),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Book Puja & Pandit",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = TempleCharcoal
                            )
                        )
                        Text(
                            text = "Guwahati Vedic Services",
                            style = MaterialTheme.typography.bodySmall.copy(color = SacredSaffronDark)
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        enabled = !isProcessing,
                        modifier = Modifier.testTag("close_booking_dialog")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TempleCharcoal)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Summary Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = SandalwoodWarmSurface,
                    border = BorderStroke(0.5.dp, SacredGold.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(SaffronContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = SacredSaffron,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = initialPujaName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TempleCharcoal,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = selectedTier,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = AuspiciousGreen,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        Text(
                            text = "₹$computedAmount",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = SacredSaffronDark
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Tier Selection (Samagri Only / Pujari Only / Complete Package)
                if (tierOptions.size > 1) {
                    Text(
                        text = "Choose Booking Plan",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        tierOptions.forEach { tier ->
                            val isSelected = selectedTier == tier
                            val priceForTier = when (tier) {
                                "Complete Package" -> pujaService?.packagePrice ?: 3199
                                "Samagri Only" -> pujaService?.samagriOnlyPrice ?: 1299
                                "Pujari Only" -> pujaService?.pujariOnlyPrice ?: 1899
                                else -> 2100
                            }
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (isSelected) SaffronContainer else MaterialTheme.colorScheme.surface,
                                border = BorderStroke(1.5.dp, if (isSelected) SacredSaffron else SandalwoodBorder),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedTier = tier }
                                    .testTag("tier_option_${tier.lowercase().replace(" ", "_")}")
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = tier,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) SacredMaroon else TempleCharcoal,
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                            fontSize = 11.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "₹$priceForTier",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = if (isSelected) SacredSaffronDark else TempleBrownMuted
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Section 1: Choose Date
                Text(
                    text = "1. Select Auspicious Date",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal)
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    dateOptions.forEach { date ->
                        val isSelected = selectedDate == date
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SacredSaffron else SandalwoodWarmSurface,
                            border = BorderStroke(1.dp, if (isSelected) SacredSaffron else SandalwoodBorder),
                            modifier = Modifier
                                .clickable { selectedDate = date }
                                .testTag("date_option_${date.take(5)}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarMonth,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else TempleBrownMuted,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = date,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (isSelected) Color.White else TempleCharcoal,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 2: Choose Time Slot
                Text(
                    text = "2. Select Puja Time Slot",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal)
                )
                Spacer(modifier = Modifier.height(6.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    timeSlots.forEach { slot ->
                        val isSelected = selectedTimeSlot == slot
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) SacredVermillion else SandalwoodWarmSurface,
                            border = BorderStroke(1.dp, if (isSelected) SacredVermillion else SandalwoodBorder),
                            modifier = Modifier
                                .clickable { selectedTimeSlot = slot }
                                .testTag("slot_option_${slot.take(4)}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else TempleBrownMuted,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = slot,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        color = if (isSelected) Color.White else TempleCharcoal,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 3: Select Pujari / Pandit
                Text(
                    text = "3. Assigned Vedic Pandit",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal)
                )
                Spacer(modifier = Modifier.height(6.dp))
                availablePujaris.forEach { p ->
                    val isSelected = selectedPujariName == p.name
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) SaffronContainer else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSelected) SacredSaffron else SandalwoodBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable { selectedPujariName = p.name }
                            .testTag("pujari_select_${p.id}")
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedPujariName = p.name },
                                colors = RadioButtonDefaults.colors(selectedColor = SacredSaffron)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = p.name,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = TempleCharcoal
                                    )
                                )
                                Text(
                                    text = "${p.experienceYears} Yrs • ${p.languages.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = TempleBrownMuted,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                            RatingBar(rating = p.rating)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 4: Address & Contact Details
                Text(
                    text = "4. Guwahati Venue Address & Contact",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal)
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = addressText,
                    onValueChange = { addressText = it },
                    label = { Text("Puja Location Address") },
                    leadingIcon = { Icon(Icons.Default.Home, contentDescription = null, tint = SacredSaffron) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_address"),
                    shape = RoundedCornerShape(10.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = contactName,
                        onValueChange = { contactName = it },
                        label = { Text("Name") },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = SacredSaffron) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_name"),
                        shape = RoundedCornerShape(10.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = contactPhone,
                        onValueChange = { contactPhone = it },
                        label = { Text("Phone") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = SacredSaffron) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("input_phone"),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Section 5: Online Payment & COD
                Text(
                    text = "5. Payment Method",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal)
                )
                Spacer(modifier = Modifier.height(6.dp))

                paymentOptions.forEach { method ->
                    val isSelected = selectedPaymentMethod == method
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (isSelected) SandalwoodWarmSurface else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSelected) SacredSaffron else SandalwoodBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable { selectedPaymentMethod = method }
                            .testTag("payment_method_${method.take(3)}")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { selectedPaymentMethod = method },
                                colors = RadioButtonDefaults.colors(selectedColor = SacredSaffron)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.CreditCard,
                                contentDescription = null,
                                tint = SacredSaffronDark,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = method,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = TempleCharcoal
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Submit button
                Button(
                    onClick = {
                        isProcessing = true
                        onConfirm(
                            computedBookingType,
                            initialPujaName,
                            selectedPujariName,
                            selectedDate,
                            selectedTimeSlot,
                            addressText,
                            contactName,
                            contactPhone,
                            computedAmount,
                            selectedPaymentMethod
                        )
                    },
                    enabled = !isProcessing && addressText.isNotBlank() && contactPhone.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("confirm_booking_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron)
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Verifying Pandit & Booking...", color = Color.White)
                    } else {
                        Text(
                            text = "Confirm & Pay ₹$computedAmount",
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
