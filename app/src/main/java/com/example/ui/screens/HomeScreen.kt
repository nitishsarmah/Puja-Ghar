package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.GuwahatiLocality
import com.example.data.model.OfferBanner
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.data.model.SamagriItem
import com.example.data.model.Store
import com.example.ui.components.OfferCard
import com.example.ui.components.PackageCard
import com.example.ui.components.PujaCard
import com.example.ui.components.PujariCard
import com.example.ui.components.SamagriCard
import com.example.ui.components.SearchBarView
import com.example.ui.components.SectionHeader
import com.example.ui.components.StoreCard
import com.example.ui.components.TrustBadgesRow
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
fun HomeScreen(
    selectedLocality: GuwahatiLocality,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedFilterTab: String,
    onFilterTabSelect: (String) -> Unit,
    popularPujas: List<PujaService>,
    completePackages: List<PujaService>,
    featuredPujaris: List<Pujari>,
    nearbyStores: List<Store>,
    samagriItems: List<SamagriItem>,
    activeOffers: List<OfferBanner>,
    onBookPuja: (PujaService) -> Unit,
    onBookPujari: (Pujari) -> Unit,
    onAddToCart: (SamagriItem) -> Unit,
    onNavigateToCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_scroll"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Search Bar with Quick Filter Tabs
        item {
            SearchBarView(
                searchQuery = searchQuery,
                onQueryChange = onSearchChange,
                selectedTab = selectedFilterTab,
                onTabSelect = onFilterTabSelect
            )
        }

        // 2. Devotional Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .testTag("hero_banner_card"),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_hero_puja),
                        contentDescription = "Guwahati Sacred Puja",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    // Gradient overlay for readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.85f),
                                        Color.Black.copy(alpha = 0.45f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SacredGold
                        ) {
                            Text(
                                text = "GUWAHATI VEDIC SEVA",
                                color = TempleCharcoal,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 10.sp
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Authentic Pujas &\nVerified Pandits",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,
                                lineHeight = 26.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Delivered to your doorstep in ${selectedLocality.name}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFFFE0B2),
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }

        // 3. Four Core Action Buttons (Requested in Prompt: Book a Puja, Book a Pujari, Puja Samagri, Complete Puja Packages)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "Quick Booking & Services",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TempleCharcoal
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Book a Puja",
                        subtitle = "Vedic Vidhi",
                        icon = Icons.Default.VolunteerActivism,
                        accentColor = SacredSaffron,
                        modifier = Modifier.weight(1f),
                        onClick = { onFilterTabSelect("Pujas") }
                    )
                    QuickActionCard(
                        title = "Book a Pujari",
                        subtitle = "Verified Pandits",
                        icon = Icons.Default.PersonSearch,
                        accentColor = SacredVermillion,
                        modifier = Modifier.weight(1f),
                        onClick = { onFilterTabSelect("Pandits") }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickActionCard(
                        title = "Puja Samagri",
                        subtitle = "Nearby Stores",
                        icon = Icons.Default.ShoppingBag,
                        accentColor = SacredMarigold,
                        modifier = Modifier.weight(1f),
                        onClick = { onFilterTabSelect("Samagri") }
                    )
                    QuickActionCard(
                        title = "Complete Packages",
                        subtitle = "All-In-One Kit",
                        icon = Icons.Default.Inventory2,
                        accentColor = SacredSaffronDark,
                        modifier = Modifier.weight(1f),
                        onClick = { onFilterTabSelect("Packages") }
                    )
                }
            }
        }

        // 4. Trust Badges
        item {
            TrustBadgesRow()
        }

        // 5. Special Festive Offers (Carousel)
        item {
            Column {
                SectionHeader(
                    title = "Festive Deals & Offers",
                    subtitle = "Special discounts for Guwahati devotees"
                )
                LazyRow(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(activeOffers) { offer ->
                        OfferCard(
                            offer = offer,
                            onApply = { onFilterTabSelect("Packages") }
                        )
                    }
                }
            }
        }

        // 6. Complete Puja Packages (Requested: “Complete Puja Packages”)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(
                    title = "Complete Puja Packages",
                    subtitle = "Pandit + 100% Shuddh Samagri Kit + Doorstep Delivery",
                    actionText = "View All",
                    onActionClick = { onFilterTabSelect("Packages") },
                    modifier = Modifier.padding(horizontal = 0.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                completePackages.take(2).forEach { pkg ->
                    PackageCard(
                        pkg = pkg,
                        onBookClick = { onBookPuja(pkg) },
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }

        // 7. Popular Pujas (Requested in Prompt)
        item {
            Column {
                SectionHeader(
                    title = "Popular Pujas in Guwahati",
                    subtitle = "Satyanarayan, Griha Pravesh, Kamakhya Chandi Path",
                    actionText = "See All",
                    onActionClick = { onFilterTabSelect("Pujas") }
                )
                LazyRow(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(popularPujas) { puja ->
                        PujaCard(
                            puja = puja,
                            onBookClick = { onBookPuja(puja) }
                        )
                    }
                }
            }
        }

        // 8. Featured Verified Pujaris (Requested in Prompt)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(
                    title = "Featured Pujaris & Pandits",
                    subtitle = "Verified Vedic scholars fluent in Assamese & Sanskrit",
                    actionText = "All Pandits",
                    onActionClick = { onFilterTabSelect("Pandits") },
                    modifier = Modifier.padding(horizontal = 0.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                featuredPujaris.take(3).forEach { pujari ->
                    PujariCard(
                        pujari = pujari,
                        onBookClick = { onBookPujari(pujari) },
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                }
            }
        }

        // 9. Nearby Puja Stores (Requested in Prompt)
        item {
            Column {
                SectionHeader(
                    title = "Nearby Puja Stores in Guwahati",
                    subtitle = "Partner stores delivering within 30 minutes",
                    actionText = "Explore",
                    onActionClick = { onFilterTabSelect("Stores") }
                )
                LazyRow(
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(nearbyStores) { store ->
                        StoreCard(
                            store = store,
                            onShopClick = { onFilterTabSelect("Samagri") }
                        )
                    }
                }
            }
        }

        // 10. Trending Puja Samagri
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(
                    title = "Essential Puja Samagri",
                    subtitle = "Pure Cow Ghee, Muga Gamusa, Sandalwood & Camphor",
                    actionText = "Shop Samagri",
                    onActionClick = { onFilterTabSelect("Samagri") },
                    modifier = Modifier.padding(horizontal = 0.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                samagriItems.take(3).forEach { samagri ->
                    SamagriCard(
                        item = samagri,
                        onAddToCart = { onAddToCart(samagri) },
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        // 11. Devotional Footer Card
        item {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, SandalwoodBorder, RoundedCornerShape(16.dp)),
                color = SandalwoodWarmSurface
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ওঁ সৰ্বমঙ্গল মাঙ্গল্যে শিৱে সৰ্বাৰ্থ সাধিকে",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = SacredVermillion
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "PujaGhar • Guwahati's Trusted Sacred Marketplace\nServing Nilachal, Beltola, Ganeshguri, Uzan Bazar & entire Guwahati.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TempleBrownMuted,
                            fontSize = 11.sp
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        }

        // Spacer for bottom navigation
        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(82.dp)
            .border(1.dp, SandalwoodBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("quick_action_${title.lowercase().replace(" ", "_")}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TempleCharcoal,
                        fontSize = 13.sp
                    ),
                    maxLines = 1
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TempleBrownMuted,
                        fontSize = 11.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}
