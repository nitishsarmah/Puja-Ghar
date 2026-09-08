package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookingEntity::class, OrderEntity::class, CartItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PujaDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao
    abstract fun orderDao(): OrderDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: PujaDatabase? = null

        fun getDatabase(context: Context): PujaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PujaDatabase::class.java,
                    "pujaghar_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
