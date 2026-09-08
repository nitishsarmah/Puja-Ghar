package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.BookingEntity
import com.example.data.local.PujaDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read app name string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("PujaGhar", appName)
    }

    @Test
    fun `test booking and rating in database`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = PujaDatabase.getDatabase(context)
        val bookingDao = db.bookingDao()

        val booking = BookingEntity(
            id = "test-bkg-1",
            bookingCode = "PG-BKG-TEST",
            bookingType = "COMPLETE_PACKAGE",
            pujaName = "Satyanarayan Complete Family Package",
            pujariName = "Pandit Bhaskar Sarma",
            date = "15 Sep 2026",
            timeSlot = "09:30 AM",
            locality = "Beltola, Guwahati",
            address = "House 18, Survey Path, Beltola, Guwahati",
            contactName = "Nitish Sarmah",
            phone = "+91 98640 54321",
            amount = 3199,
            paymentMethod = "UPI",
            paymentStatus = "PAID_ONLINE",
            status = "CONFIRMED"
        )
        bookingDao.insertBooking(booking)

        val list = bookingDao.getAllBookings().first()
        assertTrue(list.any { it.id == "test-bkg-1" })

        // Update rating
        bookingDao.updateRating("test-bkg-1", 5, "Soulful and authentic chanting")
        val updated = bookingDao.getAllBookings().first().first { it.id == "test-bkg-1" }
        assertEquals(5, updated.rating)
        assertEquals("Soulful and authentic chanting", updated.reviewComment)
    }

    @Test
    fun `test order creation and status progression in database`() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = PujaDatabase.getDatabase(context)
        val orderDao = db.orderDao()

        val order = com.example.data.local.OrderEntity(
            id = "test-ord-1",
            orderCode = "PG-ORD-9999",
            storeName = "Kamakhya Devotional Store",
            itemsSummary = "Pure Cow Ghee (x2), Camphor Tablets (x1)",
            itemCount = 3,
            totalAmount = 680,
            locality = "Ulubari, Guwahati",
            deliveryAddress = "Flat 4B, Ulubari Chariali, Guwahati",
            phone = "+91 98640 12345",
            paymentMethod = "COD",
            paymentStatus = "PENDING_CASH",
            status = "ORDER_PLACED",
            estimatedTime = "45 mins"
        )
        orderDao.insertOrder(order)

        val orders = orderDao.getAllOrders().first()
        assertTrue(orders.any { it.id == "test-ord-1" })

        orderDao.updateOrderStatus("test-ord-1", "OUT_FOR_DELIVERY")
        val updated = orderDao.getAllOrders().first().first { it.id == "test-ord-1" }
        assertEquals("OUT_FOR_DELIVERY", updated.status)
    }
}
