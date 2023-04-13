package com.dicoding.storyappsubmission.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.storyappsubmission.database.local.entity.StoryEntity

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(vararg story: StoryEntity)

    @Query("SELECT * FROM story ORDER BY created_at DESC")
    fun getAllStories(): LiveData<List<StoryEntity>>

    @Query("DELETE FROM story")
    fun deleteAll()
}