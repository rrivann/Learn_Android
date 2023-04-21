package com.dicoding.storyappsubmission.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dicoding.storyappsubmission.data.local.entity.RemoteKeys
import com.dicoding.storyappsubmission.data.local.entity.StoryEntity

@Database(
    entities = [StoryEntity::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}