package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.BookingEntity
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.data.model.UserRole
import com.example.ui.components.BookingDialog
import com.example.ui.components.CartDialog
import com.example.ui.components.LocalityPickerDialog
import com.example.ui.components.PujaTopBar
import com.example.ui.components.RatingDialog
import com.example.ui.screens.BookingsScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RoleDashboardView
import com.example.ui.theme.PujaGharTheme
import com.example.ui.theme.SacredSaffron
import com.example.ui.theme.SacredSaffronDark
import com.example.ui.theme.SacredVermillion
import com.example.ui.theme.SaffronContainer
import com.example.ui.theme.SandalwoodBorder
import com.example.ui.theme.TempleBrownMuted
import com.example.ui.theme.TempleCharcoal
import com.example.ui.viewmodel.PujaViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PujaGharTheme {
                PujaGharApp()
            }
        }
    }
}

enum class BottomTab(val title: String, val testTag: String) {
    HOME("Home", "tab_home"),
    CATEGORIES("Categories", "tab_categories"),
    ORDERS("Orders", "tab_orders"),
    BOOKINGS("Bookings", "tab_bookings"),
    PROFILE("Profile", "tab_profile")
}

@Composable
fun PujaGharApp(
    viewModel: PujaViewModel = viewModel()
) {
    val selectedLocality by viewModel.selectedLocality.collectAsState()
    val currentRole by viewModel.currentRole.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedFilterTab by viewModel.selectedTab.collectAsState()

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val userName by viewModel.userName.collectAsState()
    val userPhone by viewModel.userPhone.collectAsState()
    val userEmail by viewModel.userEmail.collectAsState()

    val bookings by viewModel.bookings.collectAsState()
    val orders by viewModel.orders.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val cartTotal by viewModel.cartTotal.collectAsState()

    val filteredPujas by viewModel.filteredPujas.collectAsState()
    val filteredPackages by viewModel.filteredPackages.collectAsState()
    val filteredPujaris by viewModel.filteredPujaris.collectAsState()
    val filteredSamagri by viewModel.filteredSamagri.collectAsState()
    val filteredStores by viewModel.filteredStores.collectAsState()

    val snackbarMsg by viewModel.snackbarMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var currentTab by remember { mutableStateOf(BottomTab.HOME) }
    var categoryTabState by remember { mutableStateOf("All Pujas") }

    // If not authenticated in this session, show LoginScreen
    if (!isLoggedIn) {
        LoginScreen(
            onLoginSuccess = { phone, _, name ->
                viewModel.loginWithDemo(name, phone)
            },
            onSkip = {
                viewModel.skipLogin()
            }
        )
        return
    }

    // Dialog states
    var showLocalityPicker by remember { mutableStateOf(false) }
    var showCartDialog by remember { mutableStateOf(false) }
    var bookingTargetPuja by remember { mutableStateOf<PujaService?>(null) }
    var bookingTargetPujari by remember { mutableStateOf<Pujari?>(null) }
    var showBookingDialog by remember { mutableStateOf(false) }
    var ratingTargetBooking by remember { mutableStateOf<BookingEntity?>(null) }
    var showRatingDialog by remember { mutableStateOf(false) }

    // Show snackbar when message changes
    LaunchedEffect(snackbarMsg) {
        snackbarMsg?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearSnackbar()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            PujaTopBar(
                currentLocality = selectedLocality,
                cartItemCount = cartItems.sumOf { it.quantity },
                onLocalityClick = { showLocalityPicker = true },
                onCartClick = { showCartDialog = true }
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, SandalwoodBorder)
                    .navigationBarsPadding(),
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                // 1. Home
                NavigationBarItem(
                    selected = currentTab == BottomTab.HOME,
                    onClick = { currentTab = BottomTab.HOME },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Home",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SacredSaffronDark,
                        selectedTextColor = SacredSaffronDark,
                        indicatorColor = SaffronContainer,
                        unselectedIconColor = TempleBrownMuted,
                        unselectedTextColor = TempleBrownMuted
                    ),
                    modifier = Modifier.testTag(BottomTab.HOME.testTag)
                )

                // 2. Categories
                NavigationBarItem(
                    selected = currentTab == BottomTab.CATEGORIES,
                    onClick = { currentTab = BottomTab.CATEGORIES },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.GridView,
                            contentDescription = "Categories",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Categories",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SacredSaffronDark,
                        selectedTextColor = SacredSaffronDark,
                        indicatorColor = SaffronContainer,
                        unselectedIconColor = TempleBrownMuted,
                        unselectedTextColor = TempleBrownMuted
                    ),
                    modifier = Modifier.testTag(BottomTab.CATEGORIES.testTag)
                )

                // 3. Orders (with badge)
                NavigationBarItem(
                    selected = currentTab == BottomTab.ORDERS,
                    onClick = { currentTab = BottomTab.ORDERS },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (orders.isNotEmpty()) {
                                    Badge(
                                        containerColor = SacredSaffron,
                                        contentColor = Color.White
                                    ) {
                                        Text(text = orders.size.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ReceiptLong,
                                contentDescription = "Orders",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Orders",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SacredSaffronDark,
                        selectedTextColor = SacredSaffronDark,
                        indicatorColor = SaffronContainer,
                        unselectedIconColor = TempleBrownMuted,
                        unselectedTextColor = TempleBrownMuted
                    ),
                    modifier = Modifier.testTag(BottomTab.ORDERS.testTag)
                )

                // 4. Bookings (with badge)
                NavigationBarItem(
                    selected = currentTab == BottomTab.BOOKINGS,
                    onClick = { currentTab = BottomTab.BOOKINGS },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (bookings.isNotEmpty()) {
                                    Badge(
                                        containerColor = SacredVermillion,
                                        contentColor = Color.White
                                    ) {
                                        Text(text = bookings.size.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.VolunteerActivism,
                                contentDescription = "Bookings",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    label = {
                        Text(
                            text = "Bookings",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SacredSaffronDark,
                        selectedTextColor = SacredSaffronDark,
                        indicatorColor = SaffronContainer,
                        unselectedIconColor = TempleBrownMuted,
                        unselectedTextColor = TempleBrownMuted
                    ),
                    modifier = Modifier.testTag(BottomTab.BOOKINGS.testTag)
                )

                // 5. Profile
                NavigationBarItem(
                    selected = currentTab == BottomTab.PROFILE,
                    onClick = { currentTab = BottomTab.PROFILE },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Profile",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SacredSaffronDark,
                        selectedTextColor = SacredSaffronDark,
                        indicatorColor = SaffronContainer,
                        unselectedIconColor = TempleBrownMuted,
                        unselectedTextColor = TempleBrownMuted
                    ),
                    modifier = Modifier.testTag(BottomTab.PROFILE.testTag)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = TempleCharcoal,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Check if user is in a special Partner/Merchant/Admin mode
            if (currentRole != UserRole.CUSTOMER && currentTab == BottomTab.HOME) {
                RoleDashboardView(
                    currentRole = currentRole,
                    bookings = bookings,
                    orders = orders,
                    onBackToCustomer = { viewModel.selectRole(UserRole.CUSTOMER) },
                    onUpdateBookingStatus = { id, status -> viewModel.updateBookingStatus(id, status) },
                    onUpdateOrderStatus = { id, status -> viewModel.updateOrderStatus(id, status) }
                )
            } else {
                when (currentTab) {
                    BottomTab.HOME -> {
                        HomeScreen(
                            selectedLocality = selectedLocality,
                            searchQuery = searchQuery,
                            onSearchChange = { viewModel.onSearchQueryChanged(it) },
                            selectedFilterTab = selectedFilterTab,
                            onFilterTabSelect = { tab ->
                                viewModel.onTabChanged(tab)
                                when (tab) {
                                    "Pujas" -> categoryTabState = "All Pujas"
                                    "Pandits" -> categoryTabState = "Verified Pandits"
                                    "Samagri" -> categoryTabState = "Puja Samagri"
                                    "Packages" -> categoryTabState = "Complete Packages"
                                    "Stores" -> categoryTabState = "Partner Stores"
                                    else -> categoryTabState = tab
                                }
                                if (tab != "All") {
                                    currentTab = BottomTab.CATEGORIES
                                }
                            },
                            popularPujas = filteredPujas,
                            completePackages = filteredPackages,
                            featuredPujaris = filteredPujaris,
                            nearbyStores = filteredStores,
                            samagriItems = filteredSamagri,
                            activeOffers = viewModel.activeOffers,
                            onBookPuja = { puja ->
                                bookingTargetPuja = puja
                                bookingTargetPujari = null
                                showBookingDialog = true
                            },
                            onBookPujari = { p ->
                                bookingTargetPuja = null
                                bookingTargetPujari = p
                                showBookingDialog = true
                            },
                            onAddToCart = { item ->
                                viewModel.addToCart(item)
                            },
                            onNavigateToCategory = { categoryName ->
                                when (categoryName) {
                                    "Pujas" -> categoryTabState = "All Pujas"
                                    "Pandits" -> categoryTabState = "Verified Pandits"
                                    "Samagri" -> categoryTabState = "Puja Samagri"
                                    "Packages" -> categoryTabState = "Complete Packages"
                                    "Stores" -> categoryTabState = "Partner Stores"
                                    else -> categoryTabState = "All Pujas"
                                }
                                currentTab = BottomTab.CATEGORIES
                            }
                        )
                    }

                    BottomTab.CATEGORIES -> {
                        CategoriesScreen(
                            pujas = filteredPujas,
                            packages = filteredPackages,
                            pujaris = filteredPujaris,
                            stores = filteredStores,
                            samagriItems = filteredSamagri,
                            selectedCategoryTab = categoryTabState,
                            onCategoryTabSelect = { categoryTabState = it },
                            onBookPuja = { puja ->
                                bookingTargetPuja = puja
                                bookingTargetPujari = null
                                showBookingDialog = true
                            },
                            onBookPujari = { p ->
                                bookingTargetPuja = null
                                bookingTargetPujari = p
                                showBookingDialog = true
                            },
                            onAddToCart = { item ->
                                viewModel.addToCart(item)
                            }
                        )
                    }

                    BottomTab.ORDERS -> {
                        OrdersScreen(
                            orders = orders,
                            onExploreSamagri = {
                                categoryTabState = "Puja Samagri"
                                currentTab = BottomTab.CATEGORIES
                            },
                            onSimulateNextStatus = { id, status ->
                                viewModel.updateOrderStatus(id, status)
                            }
                        )
                    }

                    BottomTab.BOOKINGS -> {
                        BookingsScreen(
                            bookings = bookings,
                            onExplorePujas = { currentTab = BottomTab.HOME },
                            onRateBooking = { b ->
                                ratingTargetBooking = b
                                showRatingDialog = true
                            },
                            onSimulateStatus = { id, status ->
                                viewModel.updateBookingStatus(id, status)
                            }
                        )
                    }

                    BottomTab.PROFILE -> {
                        ProfileScreen(
                            currentRole = currentRole,
                            selectedLocality = selectedLocality,
                            userName = userName,
                            userPhone = userPhone,
                            userEmail = userEmail,
                            onUpdateProfile = { name, phone, email ->
                                viewModel.updateProfile(name, phone, email)
                            },
                            onRoleSelect = { role ->
                                viewModel.selectRole(role)
                            },
                            onLogout = {
                                viewModel.logout()
                            }
                        )
                    }
                }
            }
        }
    }

    // Dialog 1: Locality Picker
    if (showLocalityPicker) {
        LocalityPickerDialog(
            currentLocality = selectedLocality,
            onLocalitySelected = { locality ->
                viewModel.selectLocality(locality)
            },
            onDismiss = { showLocalityPicker = false }
        )
    }

    // Dialog 2: Samagri Cart & Checkout
    if (showCartDialog) {
        CartDialog(
            cartItems = cartItems,
            totalAmount = cartTotal,
            currentLocality = selectedLocality,
            onDismiss = { showCartDialog = false },
            onRemoveItem = { itemId -> viewModel.removeFromCart(itemId) },
            onUpdateQuantity = { itemId, qty -> viewModel.updateCartQuantity(itemId, qty) },
            onClearCart = { viewModel.clearCart() },
            onCheckout = { address, phone, paymentMethod ->
                viewModel.checkoutCart(address, phone, paymentMethod) {
                    showCartDialog = false
                    currentTab = BottomTab.ORDERS
                }
            }
        )
    }

    // Dialog 3: Book Puja / Pandit
    if (showBookingDialog) {
        BookingDialog(
            pujaService = bookingTargetPuja,
            pujari = bookingTargetPujari,
            currentLocality = selectedLocality,
            availablePujaris = viewModel.featuredPujaris,
            onDismiss = { showBookingDialog = false },
            onConfirm = { type, pujaName, pujariName, date, time, addr, name, ph, amt, payMethod ->
                viewModel.bookPuja(
                    bookingType = type,
                    pujaName = pujaName,
                    pujariName = pujariName,
                    date = date,
                    timeSlot = time,
                    address = addr,
                    contactName = name,
                    phone = ph,
                    amount = amt,
                    paymentMethod = payMethod
                ) {
                    showBookingDialog = false
                    currentTab = BottomTab.BOOKINGS
                }
            }
        )
    }

    // Dialog 4: Rate & Review
    if (showRatingDialog && ratingTargetBooking != null) {
        RatingDialog(
            booking = ratingTargetBooking!!,
            onDismiss = { showRatingDialog = false },
            onSubmit = { stars, comment ->
                viewModel.submitRating(ratingTargetBooking!!.id, stars, comment)
                showRatingDialog = false
            }
        )
    }
}
