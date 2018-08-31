package com.example.forest.quickguessv2.DB.Life;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

@Dao
public interface LifeDao {

    @Query("SELECT user_life FROM life LIMIT 1")
    int getUserLife();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Life life);


}
