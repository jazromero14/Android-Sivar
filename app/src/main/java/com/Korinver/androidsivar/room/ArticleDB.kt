package com.Korinver.androidsivar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//
//@Database(entities = [Article::class], version = 1, exportSchema = true)
//abstract class ArticleDB : RoomDatabase() {
//    abstract fun articleDao(): ArticlesDao?
//
//    private var INSTANCE: ArticleDB? = null
//
//    open fun getAppDatabase(context: Context): ArticleDB? {
//        if (INSTANCE == null) {
//            INSTANCE = Room.databaseBuilder<ArticleDB>(
//                context.getApplicationContext(),
//                ArticleDB::class.java, "user-database"
//            ).build()
//        }
//        return INSTANCE
//    }
//
//    open fun destroyInstance() {
//        INSTANCE = null
//    }
//}