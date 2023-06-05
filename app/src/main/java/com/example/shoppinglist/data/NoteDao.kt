package com.example.shoppinglist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: NoteItem)
    @Delete
    suspend fun deleteItem(item: NoteItem)
    @Query("SELECT * FROM note_table")
    fun getAllItems() : Flow<List<NoteItem>>
    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteItemById(id: Int) : NoteItem
}