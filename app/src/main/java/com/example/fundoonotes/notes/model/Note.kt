package com.example.fundoonotes.notes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName="notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id:Long?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "date")
    val date:String,
    @ColumnInfo(name = "deleteInfo")
    var isDeleted: Boolean = false,
    @ColumnInfo(name = "syncInfo")
    var isSynced: Boolean = false
):Serializable
