package com.riveong.retrofit_dcd.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Saved::class], version = 1)
abstract class SavedRoomDatabase : RoomDatabase() {
    abstract fun savedDao(): SavedDao

    companion object {

        @Volatile
        private var INSTANCE: SavedRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): SavedRoomDatabase {
            if (INSTANCE == null) {
                synchronized(SavedRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SavedRoomDatabase::class.java, "Saved_User_Database"
                    )
                        .build()
                }
            }
            return INSTANCE as SavedRoomDatabase
        }
    }
}
