package com.dicoding.mygithubusersubmission.database.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dicoding.mygithubusersubmission.database.local.entity.FavoriteUserEntity

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("DELETE FROM favoriteUser WHERE username = :username")
    suspend fun deleteFavorite(username: String?)

    @Query("SELECT * FROM favoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String?): LiveData<FavoriteUserEntity>

    @Query("SELECT * FROM favoriteUser")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserEntity>>
}