package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AuspiciousGreen
import com.example.ui.theme.AuspiciousGreenContainer
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredMaroon
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@Composable
fun LoginScreen(
    onLoginSuccess: (phone: String, otp: String, name: String) -> Unit,
    onSkip: () -> Unit
) {
    var phoneInput by remember { mutableStateOf("98640 54321") }
    var nameInput by remember { mutableStateOf("Nitish Sarmah") }
    var otpInput by remember { mutableStateOf("") }
    var isOtpSent by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            SacredMaroon.copy(alpha = 0.06f),
            SandalwoodWarmSurface,
            Color.White
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Logo & Divine Emblem
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(SacredGold, SacredSaffron, SacredMaroon)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "PujaGhar Sacred Shield",
                    tint = Color.White,
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "PujaGhar",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = SacredMaroon,
                    letterSpacing = 1.sp
                )
            )

            Text(
                text = "পূজাঘৰ • গুৱাহাটী",
                style = MaterialTheme.typography.titleSmall.copy(
                    color = SacredSaffronDark,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = "Guwahati's Trusted Sacred Marketplace",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TempleBrownMuted,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Demo Notice Banner (Truthful & Explicit)
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SaffronContainer,
                border = BorderStroke(1.dp, SacredSaffron.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = SacredSaffronDark,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Customer MVP • Demo Mode",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = SacredMaroon
                            )
                        )
                        Text(
                            text = "No real SMS will be sent. Use Demo OTP 1234 or tap Quick Login below to explore.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TempleCharcoal,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Main Auth Card
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, SandalwoodBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = if (!isOtpSent) "Devotee Login / Signup" else "Enter Demo OTP",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal
                        )
                    )
                    Text(
                        text = if (!isOtpSent)
                            "Enter your mobile number to receive demo verification"
                        else
                            "Enter 4-digit code (Demo code: 1234)",
                        style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (!isOtpSent) {
                        // Devotee Name
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("Your Full Name") },
                            leadingIcon = {
                                Icon(Icons.Default.Person, contentDescription = null, tint = SacredSaffron)
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_name_input"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Phone Number Input
                        OutlinedTextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Mobile Number") },
                            leadingIcon = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(start = 12.dp, end = 6.dp)
                                ) {
                                    Icon(Icons.Default.Phone, contentDescription = null, tint = SacredSaffron, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("+91", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = TempleCharcoal))
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_phone_input"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Button(
                            onClick = {
                                if (phoneInput.isBlank()) {
                                    errorMessage = "Please enter a valid phone number"
                                } else {
                                    errorMessage = null
                                    isOtpSent = true
                                    otpInput = "1234" // Pre-filled for effortless testing
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("request_demo_otp_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron)
                        ) {
                            Text(
                                text = "Get Demo OTP",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, tint = Color.White)
                        }
                    } else {
                        // OTP Input State
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = AuspiciousGreenContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = AuspiciousGreen, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Demo code ready: 1234",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = AuspiciousGreen,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        OutlinedTextField(
                            value = otpInput,
                            onValueChange = { otpInput = it },
                            label = { Text("4-Digit Demo OTP") },
                            placeholder = { Text("1234") },
                            leadingIcon = {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = SacredSaffron)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("login_otp_input"),
                            shape = RoundedCornerShape(10.dp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { otpInput = "1234" }) {
                                Text("Auto-fill 1234", style = MaterialTheme.typography.labelSmall.copy(color = SacredSaffronDark, fontWeight = FontWeight.Bold))
                            }
                            TextButton(onClick = { isOtpSent = false }) {
                                Text("Change Phone", style = MaterialTheme.typography.labelSmall.copy(color = TempleBrownMuted))
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                if (otpInput.length < 4) {
                                    errorMessage = "Please enter 4 digits (Demo: 1234)"
                                } else {
                                    onLoginSuccess("+91 $phoneInput", otpInput, nameInput)
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("verify_demo_otp_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron)
                        ) {
                            Text(
                                text = "Verify & Enter PujaGhar",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }

                    AnimatedVisibility(visible = errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // One-tap quick demo login button
            OutlinedButton(
                onClick = {
                    onLoginSuccess("+91 98640 54321", "1234", "Nitish Sarmah")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("one_tap_demo_login_btn"),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, SacredSaffron)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = SacredSaffronDark,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "One-Tap Demo Login (Guwahati Devotee)",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SacredSaffronDark
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Skip / Browse as Guest
            TextButton(
                onClick = onSkip,
                modifier = Modifier.testTag("skip_login_btn")
            ) {
                Text(
                    text = "Skip for Now • Browse Catalog as Guest",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Guwahati coverage chips
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = SacredSaffronDark,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Serving Beltola, Ganeshguri, Kamakhya, Jalukbari, Uzan Bazar & Six Mile",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}
