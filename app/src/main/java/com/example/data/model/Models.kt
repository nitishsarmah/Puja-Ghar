package com.example.data.model

enum class UserRole(val label: String, val description: String) {
    CUSTOMER("Devotee / Customer", "Book pujas, pandits & buy samagri"),
    PUJARI_PARTNER("Pujari / Pandit", "Manage bookings, schedule & dakshina"),
    STORE_MERCHANT("Store Merchant", "Manage samagri store & customer orders"),
    ADMIN("Guwahati Admin", "Platform overview, verifications & analytics")
}

data class GuwahatiLocality(
    val id: String,
    val name: String,
    val landmark: String,
    val pinCode: String
)

val GuwahatiLocalities = listOf(
    GuwahatiLocality("beltola", "Beltola", "Near Survey & Jayanagar", "781028"),
    GuwahatiLocality("ganeshguri", "Ganeshguri", "Near Ganesh Mandir / GS Road", "781006"),
    GuwahatiLocality("uzanbazar", "Uzan Bazar", "Brahmaputra Riverfront & Latasil", "781001"),
    GuwahatiLocality("jalukbari", "Jalukbari", "Near Gauhati University & Saraighat", "781014"),
    GuwahatiLocality("paltan", "Paltan Bazaar", "Near Guwahati Railway Station", "781008"),
    GuwahatiLocality("sixmile", "Six Mile", "VIP Road & Khanapara", "781022"),
    GuwahatiLocality("chandmari", "Chandmari", "Near AEI Field & Nabin Nagar", "781003"),
    GuwahatiLocality("kamakhya", "Kamakhya / Nilachal", "Temple Foothills & Maligaon", "781010")
)

data class PujaService(
    val id: String,
    val title: String,
    val assameseTitle: String,
    val category: String,
    val price: Int,
    val originalPrice: Int,
    val durationMinutes: Int,
    val description: String,
    val samagriIncluded: Boolean,
    val pujariIncluded: Boolean,
    val includedItems: List<String>,
    val benefits: List<String>,
    val deity: String,
    val rating: Float,
    val reviewCount: Int,
    val isPopular: Boolean = false,
    val isPackage: Boolean = false,
    val samagriOnlyPrice: Int = 999,
    val pujariOnlyPrice: Int = 1499,
    val packagePrice: Int = 2299
)

data class Pujari(
    val id: String,
    val name: String,
    val title: String,
    val experienceYears: Int,
    val languages: List<String>,
    val rating: Float,
    val reviewCount: Int,
    val locality: String,
    val isVerified: Boolean,
    val dakshina: Int,
    val specialties: List<String>,
    val bio: String,
    val availableSlots: List<String> = listOf("07:00 AM", "09:30 AM", "11:30 AM", "03:30 PM", "05:30 PM"),
    val phone: String = "+91 98640 12345"
)

data class Store(
    val id: String,
    val name: String,
    val locality: String,
    val address: String,
    val rating: Float,
    val distanceKm: Double,
    val deliveryTimeMin: Int,
    val isVerified: Boolean,
    val phone: String
)

data class SamagriItem(
    val id: String,
    val name: String,
    val localName: String,
    val category: String,
    val price: Int,
    val weightUnit: String,
    val storeId: String,
    val storeName: String,
    val description: String,
    val inStock: Boolean = true
)

data class OfferBanner(
    val id: String,
    val title: String,
    val subtitle: String,
    val code: String,
    val discountPercent: Int,
    val tag: String
)
