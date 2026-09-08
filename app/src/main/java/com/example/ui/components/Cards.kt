package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.WorkspacePremium
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.OfferBanner
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.data.model.SamagriItem
import com.example.data.model.Store
import com.example.ui.theme.AuspiciousGreen
import com.example.ui.theme.AuspiciousGreenContainer
import com.example.ui.theme.SacredGold
import com.example.ui.theme.SacredMarigold
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SacredVermillion
import com.example.ui.theme.SacredVermillionContainer
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.SandalwoodWarmSurface
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@Composable
fun PujaCard(
    puja: PujaService,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(260.dp)
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp))
            .testTag("puja_card_${puja.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = SaffronContainer
                ) {
                    Text(
                        text = puja.category,
                        color = SacredSaffronDark,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
                RatingBar(rating = puja.rating, reviewCount = puja.reviewCount)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = puja.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal,
                    fontSize = 15.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = puja.assameseTitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = SacredVermillion,
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = TempleBrownMuted,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${puja.durationMinutes} mins • ${puja.deity}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = puja.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 12.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${puja.price}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = SacredSaffronDark,
                                fontSize = 16.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "₹${puja.originalPrice}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TempleBrownMuted,
                                textDecoration = TextDecoration.LineThrough,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Text(
                        text = if (puja.samagriIncluded) "Samagri Included" else "Dakshina Included",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AuspiciousGreen,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                    )
                }

                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .testTag("book_button_${puja.id}")
                ) {
                    Text(
                        text = "Book Now",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PackageCard(
    pkg: PujaService,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SacredGold.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .testTag("package_card_${pkg.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SacredGold.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "COMPLETE PUJA PACKAGE",
                                color = SacredSaffronDark,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        VerifiedBadge("All-Inclusive")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = pkg.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TempleCharcoal,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = pkg.assameseTitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = SacredVermillion,
                            fontSize = 12.sp
                        )
                    )
                }
                RatingBar(rating = pkg.rating, reviewCount = pkg.reviewCount)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pkg.description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 12.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Included highlights
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SandalwoodWarmSurface)
                    .padding(8.dp)
            ) {
                pkg.includedItems.take(3).forEach { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircleOutline,
                            contentDescription = null,
                            tint = SacredSaffron,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = item,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = TempleCharcoal
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "₹${pkg.price}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = SacredSaffronDark
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "₹${pkg.originalPrice}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TempleBrownMuted,
                                textDecoration = TextDecoration.LineThrough
                            )
                        )
                    }
                    Text(
                        text = "Samagri + Pujari + Home Delivery",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = AuspiciousGreen,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp
                        )
                    )
                }

                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffronDark),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("book_pkg_btn_${pkg.id}")
                ) {
                    Text("Book Package", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PujariCard(
    pujari: Pujari,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp))
            .testTag("pujari_card_${pujari.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_pujari_avatar),
                    contentDescription = pujari.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, SacredGold, CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = pujari.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TempleCharcoal,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        if (pujari.isVerified) {
                            VerifiedBadge("Vedic Verified")
                        }
                    }
                    Text(
                        text = pujari.title,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SacredSaffronDark,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                    Text(
                        text = "${pujari.experienceYears} Years Exp • ${pujari.locality}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleBrownMuted,
                            fontSize = 11.sp
                        )
                    )
                }

                RatingBar(rating = pujari.rating, reviewCount = pujari.reviewCount)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = pujari.bio,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 12.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                pujari.specialties.forEach { spec ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SaffronContainer
                    ) {
                        Text(
                            text = spec,
                            color = SacredSaffronDark,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
                pujari.languages.forEach { lang ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = SandalwoodWarmSurface
                    ) {
                        Text(
                            text = lang,
                            color = TempleBrownMuted,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Dakshina starts at",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                            color = TempleBrownMuted
                        )
                    )
                    Text(
                        text = "₹${pujari.dakshina}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = SacredSaffronDark
                        )
                    )
                }

                Button(
                    onClick = onBookClick,
                    colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("book_pujari_btn_${pujari.id}")
                ) {
                    Text("Book Pandit", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun StoreCard(
    store: Store,
    onShopClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(220.dp)
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp))
            .testTag("store_card_${store.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = SaffronContainer,
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = SacredSaffron,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                RatingBar(rating = store.rating)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = store.name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = store.address,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 11.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = AuspiciousGreenContainer
                ) {
                    Text(
                        text = "${store.deliveryTimeMin} min delivery",
                        color = AuspiciousGreen,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${store.distanceKm} km",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = onShopClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
                    .testTag("shop_store_btn_${store.id}"),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, SacredSaffron),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = SacredSaffron)
            ) {
                Text(
                    text = "View Samagri",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
fun SamagriCard(
    item: SamagriItem,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(14.dp))
            .testTag("samagri_card_${item.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_samagri_kit),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, SandalwoodBorder, RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TempleCharcoal
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = item.localName,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = SacredVermillion,
                        fontWeight = FontWeight.Medium,
                        fontSize = 11.sp
                    )
                )
                Text(
                    text = "${item.weightUnit} • ${item.storeName}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "₹${item.price}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = SacredSaffronDark
                    )
                )
            }

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = SacredSaffron),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .height(36.dp)
                    .testTag("add_to_cart_${item.id}")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Add",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun OfferCard(
    offer: OfferBanner,
    onApply: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(260.dp)
            .border(1.dp, SacredGold.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
            .clickable(onClick = onApply)
            .testTag("offer_card_${offer.code}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SaffronContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SacredSaffronDark),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocalOffer,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = SacredVermillionContainer
                ) {
                    Text(
                        text = offer.tag,
                        color = SacredVermillion,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = offer.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TempleCharcoal,
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = "Code: ${offer.code}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = SacredSaffronDark,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
fun TrustBadgesRow(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TrustItem(
            icon = Icons.Default.WorkspacePremium,
            title = "100% Shuddh",
            subtitle = "Authentic Samagri"
        )
        TrustItem(
            icon = Icons.Default.Security,
            title = "Verified",
            subtitle = "Vedic Pandits"
        )
        TrustItem(
            icon = Icons.Default.Bolt,
            title = "Punctual",
            subtitle = "Guwahati Fast"
        )
    }
}

@Composable
private fun TrustItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(SandalwoodWarmSurface)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SacredSaffron,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal,
                    fontSize = 11.sp
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TempleBrownMuted,
                    fontSize = 9.sp
                )
            )
        }
    }
}
