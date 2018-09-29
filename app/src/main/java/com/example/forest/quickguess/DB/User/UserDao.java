package com.example.forest.quickguess.DB.User;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao
{
    @Insert
     void addUser(User user);

    @Query("SELECT COUNT(username) FROM users")
    int isUserExists();

    @Query("SELECT username FROM users LIMIT 1")
    String getUsername();

}
