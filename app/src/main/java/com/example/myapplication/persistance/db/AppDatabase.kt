package com.example.myapplication.persistance.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.persistance.dao.AppDao
import com.example.myapplication.utility.Constant

@Database(entities = [], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao
    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, Constant.App_DB)
                .fallbackToDestructiveMigration()
                .build()
    }

}