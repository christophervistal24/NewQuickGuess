package com.example.forest.quickguessv2.DB.UserStatus;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserStatusDao {

    @Insert
    void addUserStatus(UserStatus userStatus);

    @Query("SELECT COUNT(category_id) FROM user_status WHERE category_id = :category_id")
    int countAnsweredQuestion(int category_id);

}
