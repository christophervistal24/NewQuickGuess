package com.example.forest.quickguessv2.DB.UserStatus;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface UserStatusDao {

    @Insert
    void addUserStatus(UserStatus userStatus);

}
