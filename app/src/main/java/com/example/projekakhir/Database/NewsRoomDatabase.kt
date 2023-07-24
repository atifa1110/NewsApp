package com.example.projekakhir.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.projekakhir.News
import com.example.projekakhir.Util.Constant

@Database(entities = [News::class], version = 3, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun getNewsDao() : NewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NewsRoomDatabase {
            if (INSTANCE == null) {
                synchronized(NewsRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NewsRoomDatabase::class.java, Constant.DATABASE_NAME)
                        .build()
                }
            }
            return INSTANCE as NewsRoomDatabase
        }
    }
}