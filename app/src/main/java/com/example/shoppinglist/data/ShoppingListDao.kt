package com.example.shoppinglist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingListItem)
    @Delete
    suspend fun deleteItem(item: ShoppingListItem)
    @Query("DELETE FROM add_item WHERE listId = :listId")
    suspend fun deleteAddItems(listId: Int)
    @Query("SELECT * FROM shop_list_name")
    fun getAllItems() : Flow<List<ShoppingListItem>>
    @Transaction
    suspend fun deleteShoppingList(item: ShoppingListItem) {
        deleteAddItems(item.id!!)
        deleteItem(item)
    }
}