package com.Korinver.androidsivar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datarecords")
data class DataRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val record: String


)