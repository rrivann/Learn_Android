package com.dicoding.mygithubusersubmission.database.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.mygithubusersubmission.database.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = false)
abstract class FavoriteUserDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavoriteUserDatabase? = null
        fun getInstance(context: Context): FavoriteUserDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserDatabase::class.java, "FavoriteUser.db"
                ).build()
            }
    }
}