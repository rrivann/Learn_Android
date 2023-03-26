package com.dicoding.mygithubusersubmission.database.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteUser")
class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",
    var avatarUrl: String? = null
)
