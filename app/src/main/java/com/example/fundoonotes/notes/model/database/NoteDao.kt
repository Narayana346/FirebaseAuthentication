package com.example.fundoonotes.notes.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fundoonotes.notes.model.Note
import kotlinx.coroutines.Deferred

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("Select * from notes_table WHERE deleteInfo = 0")
    fun getAllNotes(): LiveData<List<Note>>
    @Query("Select * from notes_table WHERE deleteInfo = 1")
    fun getAllSoftDeletedNotes(): LiveData<List<Note>>
    @Query("Select * from notes_table WHERE syncInfo = 0 & deleteInfo = 0")
    suspend fun getAllSoftSyncedNotes(): List<Note>
    @Query("Update notes_table Set deleteInfo = 1 WHERE id =:id")
    suspend fun softDelete(id: Long?)
    @Query("Update notes_table Set title=:title,note=:note, syncInfo = 0 WHERE id =:id")
    suspend fun softSynced(id: Long?,title:String? , note:String?)
    @Query("UPDATE notes_table Set title=:title,note=:note, syncInfo = 1 WHERE id =:id")
    suspend fun update(id:Long? , title:String? , note:String?)
    @Query("Update notes_table Set deleteInfo = 0 WHERE id =:id")
    suspend fun undo(id: Long?)

    @Query("SELECT * FROM notes_table WHERE date =:date")
    suspend fun getNote(date:String):Note
}