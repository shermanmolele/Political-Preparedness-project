package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertElection(election: Election): Long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllElections(elections: List<Election>)

    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table where isSaved = 1")
    fun getSavedElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table WHERE id=:id")
    fun getElectionById(id: Int): LiveData<Election>

    @Query("DELETE FROM election_table where id=:id")
    fun deleteElection(id: Int)


}