package com.example.submissionfundaawal.detail.favorite

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "login")
    var login: String?,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
)