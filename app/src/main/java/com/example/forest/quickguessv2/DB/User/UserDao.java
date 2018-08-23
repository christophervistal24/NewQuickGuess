package com.example.forest.quickguessv2.DB.User;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao
{
    @Insert
    public void addUser(User user);

    @Query("SELECT COUNT(username) FROM users WHERE username=:username LIMIT 1")
    public boolean isUserExists(String username);



}
