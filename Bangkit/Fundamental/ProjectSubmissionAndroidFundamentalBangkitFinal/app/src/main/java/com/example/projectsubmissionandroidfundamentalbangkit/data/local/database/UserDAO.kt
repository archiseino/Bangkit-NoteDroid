package com.example.projectsubmissionandroidfundamentalbangkit.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.projectsubmissionandroidfundamentalbangkit.data.local.entity.UserEntity

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("DELETE FROM USER WHERE login = :login")
    suspend fun deleteUser(login: String)

    @Query("SELECT EXISTS(SELECT * FROM USER WHERE login = :login)")
    suspend fun isUserBookmarked(login: String): Int

    @Query("SELECT * FROM USER")
    fun getFavorites(): LiveData<List<UserEntity>>

}