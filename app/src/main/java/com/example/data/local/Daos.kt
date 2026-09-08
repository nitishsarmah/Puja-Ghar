package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY timestamp DESC")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Query("SELECT * FROM bookings WHERE id = :id")
    suspend fun getBookingById(id: String): BookingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: BookingEntity)

    @Update
    suspend fun updateBooking(booking: BookingEntity)

    @Query("UPDATE bookings SET rating = :rating, reviewComment = :comment WHERE id = :id")
    suspend fun updateRating(id: String, rating: Int, comment: String)

    @Query("UPDATE bookings SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: String, status: String)

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteBooking(id: String)
}

@Dao
interface OrderDao {
    @Query("SELECT * FROM orders ORDER BY timestamp DESC")
    fun getAllOrders(): Flow<List<OrderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("UPDATE orders SET status = :status WHERE id = :id")
    suspend fun updateOrderStatus(id: String, status: String)

    @Query("DELETE FROM orders WHERE id = :id")
    suspend fun deleteOrder(id: String)
}

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE itemId = :itemId")
    suspend fun deleteItem(itemId: String)

    @Query("UPDATE cart_items SET quantity = :quantity WHERE itemId = :itemId")
    suspend fun updateQuantity(itemId: String, quantity: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
