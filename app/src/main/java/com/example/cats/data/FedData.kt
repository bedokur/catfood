package com.example.cats.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fed_table")
data class FedData(
    @ColumnInfo(name = "feedTime") val feedTime: String,
    @ColumnInfo(name = "foodName") val foodName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
