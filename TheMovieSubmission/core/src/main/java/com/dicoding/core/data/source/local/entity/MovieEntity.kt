package com.dicoding.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val movieId: String,

    @ColumnInfo("overview")
    val overview: String,

    @ColumnInfo("original_language")
    val originalLanguage: String,

    @ColumnInfo("original_title")
    val originalTitle: String,

    @ColumnInfo("video")
    val video: Boolean,

    @ColumnInfo("title")
    val title: String,

    @ColumnInfo("poster_path")
    val posterPath: String,

    @ColumnInfo("backdrop_path")
    val backdropPath: String,

    @ColumnInfo("release_date")
    val releaseDate: String,

    @ColumnInfo("popularity")
    val popularity: Double,

    @ColumnInfo("vote_average")
    val voteAverage: Double,

    @ColumnInfo("adult")
    val adult: Boolean,

    @ColumnInfo("vote_count")
    val voteCount: Int,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)