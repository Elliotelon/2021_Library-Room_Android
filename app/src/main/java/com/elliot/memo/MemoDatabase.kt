package com.elliot.memo

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf((MemoEntity::class)), version = 1)
abstract class MemoDatabase : RoomDatabase(){

    abstract fun memoDAO() : MemoDAO

    companion object {
        var INSTANCE : MemoDatabase? = null

        fun getInstance(context : Context) : MemoDatabase? {
            Log.d("fail1", "1")

            if(INSTANCE == null){
                Log.d("fail2", "1")

                synchronized(MemoDatabase::class){
                    Log.d("fail3", "1")

                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                    MemoDatabase::class.java, "memo.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    Log.d("fail4", "1")
                    println("INSTANCE : $INSTANCE")
                }
            }


            return INSTANCE
        }
    }
}