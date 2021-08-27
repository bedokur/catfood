package com.example.cats.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Hours(val hours: Int)
data class Minutes(val minutes: Int)

@Entity(tableName = "time_table")
data class TimeData(
    @Embedded
    val dHours: Hours,
    @Embedded
    val dMinutes: Minutes
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}
