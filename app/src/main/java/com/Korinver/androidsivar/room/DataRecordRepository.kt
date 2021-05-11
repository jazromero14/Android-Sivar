package com.Korinver.androidsivar.room

import androidx.lifecycle.LiveData

//Repositorio que  ocupara la base de datos
class DataRecordRepository(private val datarecordDao: DataRecordDao) {

    val allItems: LiveData<List<DataRecord>> = datarecordDao.getall()

    fun get(id: Long): LiveData<DataRecord> {
        return datarecordDao.get(id)
    }

    suspend fun update(item: DataRecord) {
        datarecordDao.update(item)
    }

    suspend fun insert(item: DataRecord) {
        datarecordDao.insert(item)
    }

    suspend fun delete(item: DataRecord) {
        datarecordDao.delete(item)
    }
}