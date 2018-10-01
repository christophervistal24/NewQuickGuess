package com.example.forest.quickguess.DB.UserStatus;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserStatusDao {

    @Insert
    void addUserStatus(UserStatus userStatus);

    @Query("SELECT COUNT(category_id) FROM user_status WHERE category_id = :category_id")
    int countAnsweredQuestion(int category_id);

    @Query("SELECT COUNT(id) FROM user_status WHERE category_id =:category_id  AND class_id=:class_id")
    int countAnsweredByClassAndCategoryId(int category_id, int class_id);

    @Query("SELECT COUNT(id) FROM user_status")
    int countAllForPoints();

    @Query("SELECT * FROM user_status")
    List<UserStatus> getAllStatusOfUser();

}
