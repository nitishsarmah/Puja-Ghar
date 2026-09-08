package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.SwitchAccount
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GuwahatiLocality
import com.example.data.model.UserRole
import com.example.ui.theme.AuspiciousGreen
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

@Composable
fun ProfileScreen(
    currentRole: UserRole,
    selectedLocality: GuwahatiLocality,
    userName: String = "Nitish Sarmah",
    userPhone: String = "+91 98640 54321",
    userEmail: String = "nitish.guwahati@example.com",
    onUpdateProfile: (name: String, phone: String, email: String) -> Unit = { _, _, _ -> },
    onRoleSelect: (UserRole) -> Unit,
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showEditProfileDialog by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(userName) }
    var editPhone by remember { mutableStateOf(userPhone) }
    var editEmail by remember { mutableStateOf(userEmail) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .testTag("profile_screen"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "My Profile & Settings",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = "PujaGhar Guwahati Account & Preferences",
                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
            )
        }

        // Transparent Demo Environment Notice (Requested in prompt)
        item {
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = SaffronContainer,
                border = BorderStroke(1.dp, SacredSaffron.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth().testTag("demo_environment_notice")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = SacredSaffronDark,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Transparent Demo Environment",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = SacredMaroon
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "This customer-facing MVP is running in demonstration mode for Guwahati, Assam. Real payment gateways, SMS gateways, and live delivery GPS are not active; all bookings, orders, cart updates, and ratings are safely handled through local database state.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleCharcoal,
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    )
                }
            }
        }

        // Profile Card with Edit Action
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_pujari_avatar),
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .border(2.dp, SacredGold, CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TempleCharcoal
                            )
                        )
                        Text(
                            text = "$userPhone • $userEmail",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TempleBrownMuted,
                                fontSize = 11.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "${selectedLocality.name}, Guwahati (Assam)",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = SacredSaffronDark,
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        )
                    }

                    IconButton(
                        onClick = {
                            editName = userName
                            editPhone = userPhone
                            editEmail = userEmail
                            showEditProfileDialog = true
                        },
                        modifier = Modifier.testTag("edit_profile_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = SacredSaffronDark
                        )
                    }
                }
            }
        }

        // Role Switcher Mode (For Extensibility: Customer, Merchant, Pujari, Admin)
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, SacredGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .testTag("role_switcher_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SandalwoodWarmSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwitchAccount,
                            contentDescription = null,
                            tint = SacredSaffron,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Role View (Customer, Partner, Merchant, Admin)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TempleCharcoal
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Designed for complete ecosystem participation. Switch roles to view respective modules:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleBrownMuted,
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        UserRole.values().forEach { role ->
                            val isSelected = currentRole == role
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) SacredSaffron else MaterialTheme.colorScheme.surface,
                                border = BorderStroke(
                                    1.dp,
                                    if (isSelected) SacredSaffron else SandalwoodBorder
                                ),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onRoleSelect(role) }
                                    .testTag("role_btn_${role.name.lowercase()}")
                            ) {
                                Text(
                                    text = role.label,
                                    color = if (isSelected) Color.White else TempleCharcoal,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 10.sp
                                    ),
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Saved Addresses
        item {
            SectionContainer(title = "Saved Guwahati Addresses") {
                AddressRow(
                    label = "Home",
                    address = "House 18, Survey Path, Beltola, Guwahati - 781028"
                )
                Divider(color = SandalwoodBorder)
                AddressRow(
                    label = "Ancestral Home",
                    address = "Brahmaputra View Path, Uzan Bazar, Guwahati - 781001"
                )
            }
        }

        // Trust & Authenticity
        item {
            SectionContainer(title = "Devotional Authenticity & Trust") {
                SettingRow(
                    icon = Icons.Default.Security,
                    title = "Vedic Authenticity Pledge",
                    subtitle = "100% Shuddh Vidhi & Verified Purohits"
                )
                Divider(color = SandalwoodBorder)
                SettingRow(
                    icon = Icons.Default.Language,
                    title = "App Language",
                    subtitle = "অসমীয়া (Assamese) / English"
                )
                Divider(color = SandalwoodBorder)
                SettingRow(
                    icon = Icons.Default.Call,
                    title = "Guwahati Devotee Support",
                    subtitle = "+91 98640 99999 • GS Road & Kamakhya Office"
                )
                Divider(color = SandalwoodBorder)
                SettingRow(
                    icon = Icons.Default.Policy,
                    title = "Privacy & Vedic Marketplace Terms",
                    subtitle = "Safe Online UPI Payments & Verified Samagri"
                )
            }
        }

        // Logout Action
        item {
            OutlinedButton(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("profile_logout_btn"),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign Out from PujaGhar",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(90.dp))
        }
    }

    if (showEditProfileDialog) {
        AlertDialog(
            onDismissRequest = { showEditProfileDialog = false },
            title = {
                Text(
                    text = "Edit Profile Details",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Full Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("edit_profile_name_input")
                    )
                    OutlinedTextField(
                        value = editPhone,
                        onValueChange = { editPhone = it },
                        label = { Text("Mobile Number") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("edit_profile_phone_input")
                    )
                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Email Address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("edit_profile_email_input")
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdateProfile(editName, editPhone, editEmail)
                        showEditProfileDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                    modifier = Modifier.testTag("save_profile_btn")
                ) {
                    Text("Save Changes", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditProfileDialog = false }) {
                    Text("Cancel", color = TempleBrownMuted)
                }
            }
        )
    }
}

@Composable
fun SectionContainer(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}

@Composable
fun AddressRow(
    label: String,
    address: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(SaffronContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = SacredSaffron,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = address,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 11.sp
                )
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TempleBrownMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun SettingRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(SandalwoodWarmSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = SacredSaffronDark,
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 11.sp
                )
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TempleBrownMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}
