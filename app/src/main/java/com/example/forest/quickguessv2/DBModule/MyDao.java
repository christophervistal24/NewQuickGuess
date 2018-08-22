package com.example.forest.quickguessv2.DBModule;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface MyDao
{
    @Insert
    public void addUser(User user);

    @Query("SELECT COUNT(username) FROM users WHERE username=:username LIMIT 1")
    public boolean isUserExists(String username);



}
