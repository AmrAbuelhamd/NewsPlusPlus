package com.blogspot.soyamr.newsplusplus.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeys(
    @PrimaryKey val articleId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)