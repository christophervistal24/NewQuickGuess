package com.example.forest.quickguess.DB.Points;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


@Dao
public interface PointsDao {


    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(Points points);

    @Update(onConflict = OnConflictStrategy.FAIL)
    void update(Points points);

    @Query("SELECT points FROM user_points LIMIT 1")
    int getUserPoints();

    @Query("DELETE FROM user_points")
    void delete();
}
