package com.example.cats.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "foodName_table")
data class FoodItem(

   @PrimaryKey val name: String
)