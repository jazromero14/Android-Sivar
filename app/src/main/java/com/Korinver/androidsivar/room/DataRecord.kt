package com.Korinver.androidsivar.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Tabla de base de datos local que se ocupara en el CRUD de Articulos
@Entity(tableName = "datarecords")
data class DataRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author : String,
    val title: String,
    val description : String,
    val content : String

)