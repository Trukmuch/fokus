package com.isaiahvonrundstedt.fokus.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isaiahvonrundstedt.fokus.features.subject.Subject
import com.isaiahvonrundstedt.fokus.features.subject.SubjectPackage

@Dao
interface SubjectDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(subject: Subject)

    @Delete
    suspend fun remove(subject: Subject)

    @Update
    suspend fun update(subject: Subject)

    @Query("SELECT * FROM subjects")
    suspend fun fetch(): List<SubjectPackage>

    @Query("SELECT * FROM subjects")
    suspend fun fetchCore(): List<Subject>

    @Transaction
    @Query("SELECT * FROM subjects ORDER BY code ASC")
    fun fetchLiveData(): LiveData<List<SubjectPackage>>

}