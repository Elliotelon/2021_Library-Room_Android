package com.elliot.memo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface MemoDAO {

    @Insert(onConflict = REPLACE)
    suspend fun insert(memo : MemoEntity)

    @Query("SELECT * FROM memo")
    suspend fun getAll() : List<MemoEntity>

    @Delete
    suspend fun delete(memo : MemoEntity)
}