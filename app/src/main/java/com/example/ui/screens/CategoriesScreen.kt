package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.data.model.SamagriItem
import com.example.data.model.Store
import com.example.ui.components.PackageCard
import com.example.ui.components.PujaCard
import com.example.ui.components.PujariCard
import com.example.ui.components.SamagriCard
import com.example.ui.components.StoreCard
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal

@Composable
fun CategoriesScreen(
    pujas: List<PujaService>,
    packages: List<PujaService>,
    pujaris: List<Pujari>,
    stores: List<Store>,
    samagriItems: List<SamagriItem>,
    onBookPuja: (PujaService) -> Unit,
    onBookPujari: (Pujari) -> Unit,
    onAddToCart: (SamagriItem) -> Unit,
    selectedCategoryTab: String = "All Pujas",
    onCategoryTabSelect: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val categoryTabs = listOf("All Pujas", "Complete Packages", "Puja Samagri", "Verified Pandits", "Partner Stores")
    
    // Normalize external tab name
    val normalizedTab = when (selectedCategoryTab) {
        "Pujas" -> "All Pujas"
        "Packages" -> "Complete Packages"
        "Samagri" -> "Puja Samagri"
        "Pandits" -> "Verified Pandits"
        "Stores" -> "Partner Stores"
        in categoryTabs -> selectedCategoryTab
        else -> "All Pujas"
    }

    var internalSelectedTab by remember(normalizedTab) { mutableStateOf(normalizedTab) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("categories_screen")
    ) {
        // Top Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "Devotional Categories",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = TempleCharcoal
                )
            )
            Text(
                text = "Explore complete Vedic services & authentic items in Guwahati",
                style = MaterialTheme.typography.bodySmall.copy(color = TempleBrownMuted)
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Sub-category pill tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                categoryTabs.forEach { tab ->
                    val isSelected = internalSelectedTab == tab
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) SacredSaffron else MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, if (isSelected) SacredSaffron else SandalwoodBorder),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable {
                                internalSelectedTab = tab
                                onCategoryTabSelect(tab)
                            }
                            .testTag("category_tab_${tab.take(6).lowercase()}")
                    ) {
                        Text(
                            text = tab,
                            color = if (isSelected) Color.White else TempleCharcoal,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }
        }

        // Content by Selected Tab
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            when (internalSelectedTab) {
                "All Pujas" -> {
                    items(pujas) { puja ->
                        PujaCard(
                            puja = puja,
                            onBookClick = { onBookPuja(puja) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                "Complete Packages" -> {
                    items(packages) { pkg ->
                        PackageCard(
                            pkg = pkg,
                            onBookClick = { onBookPuja(pkg) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                "Puja Samagri" -> {
                    items(samagriItems) { item ->
                        SamagriCard(
                            item = item,
                            onAddToCart = { onAddToCart(item) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                "Verified Pandits" -> {
                    items(pujaris) { pujari ->
                        PujariCard(
                            pujari = pujari,
                            onBookClick = { onBookPujari(pujari) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                "Partner Stores" -> {
                    items(stores) { store ->
                        StoreCard(
                            store = store,
                            onShopClick = {
                                internalSelectedTab = "Puja Samagri"
                                onCategoryTabSelect("Puja Samagri")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(90.dp))
            }
        }
    }
}
