package com.hank.guess.data

import androidx.room.*

@Dao
interface RecordDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record : Record)

    @Query("select * from record")
    suspend fun getAll():List<Record>

    @Delete
    fun delete(record: Record)
}