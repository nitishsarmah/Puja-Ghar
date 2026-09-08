package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.BookingEntity
import com.example.data.local.CartItemEntity
import com.example.data.local.OrderEntity
import com.example.data.local.PujaDatabase
import com.example.data.model.GuwahatiLocality
import com.example.data.model.OfferBanner
import com.example.data.model.PujaService
import com.example.data.model.Pujari
import com.example.data.model.SamagriItem
import com.example.data.model.Store
import com.example.data.model.UserRole
import com.example.data.repository.PujaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PujaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = PujaDatabase.getDatabase(application)
    val repository = PujaRepository(
        bookingDao = db.bookingDao(),
        orderDao = db.orderDao(),
        cartDao = db.cartDao()
    )

    // Login & Profile Authentication State (Customer MVP with transparent Demo OTP)
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _userName = MutableStateFlow("Nitish Sarmah")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _userPhone = MutableStateFlow("+91 98640 54321")
    val userPhone: StateFlow<String> = _userPhone.asStateFlow()

    private val _userEmail = MutableStateFlow("nitish.guwahati@example.com")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    // Current Locality
    val selectedLocality: StateFlow<GuwahatiLocality> = repository.selectedLocality

    // User Role (Customer, Pujari Partner, Store Merchant, Admin)
    val currentRole: StateFlow<UserRole> = repository.currentRole

    // Search & Category Filters
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTab = MutableStateFlow("All")
    val selectedTab: StateFlow<String> = _selectedTab.asStateFlow()

    // Room DB Flows
    val bookings: StateFlow<List<BookingEntity>> = repository.allBookings.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val orders: StateFlow<List<OrderEntity>> = repository.allOrders.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val cartSubtotal: StateFlow<Int> = cartItems.map { list ->
        list.sumOf { it.price * it.quantity }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val deliveryFee: StateFlow<Int> = cartSubtotal.map { subtotal ->
        if (subtotal == 0) 0
        else if (subtotal >= 300) 0
        else 40
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    val grandTotal: StateFlow<Int> = combine(cartSubtotal, deliveryFee) { subtotal, fee ->
        subtotal + fee
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    // Legacy alias for cartTotal
    val cartTotal: StateFlow<Int> = cartSubtotal

    // Static catalog from repository
    val popularPujas: List<PujaService> = repository.popularPujas
    val completePackages: List<PujaService> = repository.completePackages
    val featuredPujaris: List<Pujari> = repository.featuredPujaris
    val nearbyStores: List<Store> = repository.nearbyStores
    val samagriItems: List<SamagriItem> = repository.samagriItems
    val activeOffers: List<OfferBanner> = repository.activeOffers

    // Dynamic Filtered Lists
    val filteredPujas: StateFlow<List<PujaService>> = combine(_searchQuery, _selectedTab) { query, tab ->
        val list = popularPujas
        if (query.isBlank()) list
        else list.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.assameseTitle.contains(query, ignoreCase = true) ||
            it.deity.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), popularPujas)

    val filteredPackages: StateFlow<List<PujaService>> = combine(_searchQuery, _selectedTab) { query, _ ->
        val list = completePackages
        if (query.isBlank()) list
        else list.filter {
            it.title.contains(query, ignoreCase = true) ||
            it.assameseTitle.contains(query, ignoreCase = true) ||
            it.description.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), completePackages)

    val filteredPujaris: StateFlow<List<Pujari>> = combine(_searchQuery, _selectedTab) { query, _ ->
        val list = featuredPujaris
        if (query.isBlank()) list
        else list.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.specialties.any { s -> s.contains(query, ignoreCase = true) } ||
            it.languages.any { l -> l.contains(query, ignoreCase = true) } ||
            it.locality.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), featuredPujaris)

    val filteredSamagri: StateFlow<List<SamagriItem>> = combine(_searchQuery, _selectedTab) { query, _ ->
        val list = samagriItems
        if (query.isBlank()) list
        else list.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.localName.contains(query, ignoreCase = true) ||
            it.category.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), samagriItems)

    val filteredStores: StateFlow<List<Store>> = combine(_searchQuery, _selectedTab) { query, _ ->
        val list = nearbyStores
        if (query.isBlank()) list
        else list.filter {
            it.name.contains(query, ignoreCase = true) ||
            it.locality.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), nearbyStores)

    // UI state notifications
    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage.asStateFlow()

    init {
        seedSampleDataIfEmpty()
    }

    private fun seedSampleDataIfEmpty() {
        viewModelScope.launch {
            val currentBookings = db.bookingDao().getAllBookings().first()
            if (currentBookings.isEmpty()) {
                repository.createBooking(
                    bookingType = "COMPLETE_PACKAGE",
                    pujaName = "Shri Satyanarayan Puja & Katha",
                    pujariName = "Pandit Bhaskar Sarma",
                    date = "12 Sep 2026",
                    timeSlot = "09:30 AM",
                    locality = "Beltola, Guwahati",
                    address = "House 18, Survey Path, Beltola, Guwahati",
                    contactName = "Nitish Sarmah",
                    phone = "+91 98640 54321",
                    amount = 3199,
                    paymentMethod = "UPI (Google Pay)"
                )
            }
            val currentOrders = db.orderDao().getAllOrders().first()
            if (currentOrders.isEmpty()) {
                repository.createOrder(
                    storeName = "Kamakhya Pavitra Samagri Bhandar",
                    itemsSummary = "Pure Desi Cow Ghee (500ml), Bhimseni Camphor (100g), Gamusa Pair",
                    itemCount = 3,
                    totalAmount = 790,
                    locality = "Beltola, Guwahati",
                    deliveryAddress = "House 18, Survey Path, Beltola, Guwahati",
                    phone = "+91 98640 54321",
                    paymentMethod = "Cash on Delivery"
                )
            }
        }
    }

    // Demo Auth Flow
    fun loginWithDemo(phone: String, otp: String, name: String = "Nitish Sarmah") {
        _userPhone.value = phone.ifBlank { "+91 98640 54321" }
        _userName.value = name.ifBlank { "Devotee" }
        _isLoggedIn.value = true
        _snackbarMessage.value = "Welcome to PujaGhar, ${_userName.value}!"
    }

    fun skipLogin() {
        _isLoggedIn.value = true
        _snackbarMessage.value = "Browsing PujaGhar as Devotee"
    }

    fun logout() {
        _isLoggedIn.value = false
        _snackbarMessage.value = "Logged out successfully"
    }

    fun updateProfile(name: String, phone: String, email: String) {
        _userName.value = name
        _userPhone.value = phone
        _userEmail.value = email
        _snackbarMessage.value = "Profile updated successfully"
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onTabChanged(tab: String) {
        _selectedTab.value = tab
    }

    fun selectLocality(locality: GuwahatiLocality) {
        repository.setSelectedLocality(locality)
        _snackbarMessage.value = "Delivery location updated to ${locality.name}, Guwahati"
    }

    fun selectRole(role: UserRole) {
        repository.setRole(role)
        _snackbarMessage.value = "Switched to ${role.label} mode"
    }

    fun addToCart(item: SamagriItem, quantity: Int = 1) {
        viewModelScope.launch {
            repository.addToCart(item, quantity)
            _snackbarMessage.value = "Added '${item.name}' to Samagri Cart"
        }
    }

    fun updateCartQuantity(itemId: String, quantity: Int) {
        viewModelScope.launch {
            repository.updateCartQuantity(itemId, quantity)
        }
    }

    fun removeFromCart(itemId: String) {
        viewModelScope.launch {
            repository.removeFromCart(itemId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun clearSnackbar() {
        _snackbarMessage.value = null
    }

    fun bookPuja(
        bookingType: String,
        pujaName: String,
        pujariName: String,
        date: String,
        timeSlot: String,
        address: String,
        contactName: String,
        phone: String,
        amount: Int,
        paymentMethod: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val code = repository.createBooking(
                bookingType = bookingType,
                pujaName = pujaName,
                pujariName = pujariName,
                date = date,
                timeSlot = timeSlot,
                locality = selectedLocality.value.name,
                address = address,
                contactName = contactName,
                phone = phone,
                amount = amount,
                paymentMethod = paymentMethod
            )
            _snackbarMessage.value = "Puja booked successfully! Code: $code"
            onSuccess(code)
        }
    }

    fun checkoutCart(
        address: String,
        phone: String,
        paymentMethod: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch {
            val items = cartItems.value
            if (items.isEmpty()) return@launch
            val summary = items.joinToString(", ") { "${it.name} (x${it.quantity})" }
            val total = cartTotal.value
            val store = items.firstOrNull()?.storeName ?: "PujaGhar Partner Store"
            val orderCode = repository.createOrder(
                storeName = store,
                itemsSummary = summary,
                itemCount = items.size,
                totalAmount = total,
                locality = selectedLocality.value.name,
                deliveryAddress = address,
                phone = phone,
                paymentMethod = paymentMethod
            )
            _snackbarMessage.value = "Order placed! Order ID: $orderCode"
            onSuccess(orderCode)
        }
    }

    fun submitRating(bookingId: String, rating: Int, comment: String) {
        viewModelScope.launch {
            repository.updateBookingRating(bookingId, rating, comment)
            _snackbarMessage.value = "Thank you! Your rating has been shared."
        }
    }

    // Role actions for partner/merchant/admin demo
    fun updateBookingStatus(bookingId: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateBookingStatus(bookingId, newStatus)
            _snackbarMessage.value = "Booking status updated to $newStatus"
        }
    }

    fun updateOrderStatus(orderId: String, newStatus: String) {
        viewModelScope.launch {
            repository.updateOrderStatus(orderId, newStatus)
            _snackbarMessage.value = "Order status updated to $newStatus"
        }
    }
}
