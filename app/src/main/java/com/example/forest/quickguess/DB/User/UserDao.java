package com.example.forest.quickguess.DB.User;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.forest.quickguess.DB.Points.Points;

@Dao
public interface UserDao
{
    @Insert
    void addUser(User user);

    @Query("SELECT COUNT(username) FROM users")
    int isUserExists();

    @Query("SELECT username FROM users LIMIT 1")
    String getUsername();

    @Query("UPDATE users SET username=:username")
    void updateUser(String username);
}
