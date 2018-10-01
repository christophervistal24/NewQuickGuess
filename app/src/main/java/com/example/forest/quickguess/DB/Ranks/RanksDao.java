package com.example.forest.quickguess.DB.Ranks;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RanksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Ranks... ranks);

    @Query("SELECT * FROM ranks")
    List<Ranks> getAll();

}
