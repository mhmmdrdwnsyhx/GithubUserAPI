package com.example.submissionfundaawal.detail.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavDao {
    @Query("SELECT * FROM FavEntity ORDER BY login ASC")
    fun getAllUser(): LiveData<List<FavEntity>> //get all user from favorite list

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(user: FavEntity) //add user to favorite list

    @Query("DELETE FROM FavEntity WHERE FavEntity.id = :id")
    fun removeFav(id: Int) //delete user from favorite list
}