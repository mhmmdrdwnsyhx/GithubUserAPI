package com.example.submissionfundaawal.detail.favorite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavEntity::class], version = 6)
abstract class FavDBase : RoomDatabase() {
    abstract fun FavUserDao(): FavDao
    companion object {
        @Volatile
        private var INSTANCE: FavDBase? = null
        @JvmStatic
        fun getDBase(context: Context): FavDBase {
            if (INSTANCE == null) {
                synchronized(FavDBase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavDBase::class.java, "favorite_user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as FavDBase
        }
    }
}
