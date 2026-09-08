package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class BookingEntity(
    @PrimaryKey
    val id: String,
    val bookingCode: String,
    val bookingType: String, // COMPLETE_PACKAGE, PUJARI_ONLY, PUJA_CUSTOM
    val pujaName: String,
    val pujariName: String,
    val date: String,
    val timeSlot: String,
    val locality: String,
    val address: String,
    val contactName: String,
    val phone: String,
    val amount: Int,
    val paymentMethod: String,
    val paymentStatus: String,
    val status: String, // CONFIRMED, PUJARI_ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
    val rating: Int = 0,
    val reviewComment: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val orderCode: String,
    val storeName: String,
    val itemsSummary: String,
    val itemCount: Int,
    val totalAmount: Int,
    val locality: String,
    val deliveryAddress: String,
    val phone: String,
    val paymentMethod: String,
    val paymentStatus: String,
    val status: String, // ORDER_PLACED, PACKED, OUT_FOR_DELIVERY, DELIVERED
    val estimatedTime: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val itemId: String,
    val name: String,
    val category: String,
    val price: Int,
    val weightUnit: String,
    val storeName: String,
    val quantity: Int
)
