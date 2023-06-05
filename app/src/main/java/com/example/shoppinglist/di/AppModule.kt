package com.example.shoppinglist.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglist.data.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//DI создаем базу даннхы и репозитории
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideMainDb(app: Application): MainDb {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "shop_list_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideShopRepository(db: MainDb) : ShoppingListRepository {
        return ShoppingListRepositoryImpl(db.shoppingListDao)
    }

    @Provides
    @Singleton
    fun provideAddItemRepository(db: MainDb) : AddItemRepository {
        return AddItemRepositoryImpl(db.addItemDao)
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: MainDb) : NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }
}