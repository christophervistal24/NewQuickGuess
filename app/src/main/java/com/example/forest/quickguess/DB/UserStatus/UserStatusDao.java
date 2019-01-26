package com.example.forest.quickguess.DB.UserStatus;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserStatusDao {

    @Insert
    void addUserStatus(UserStatus userStatus);

    @Query("SELECT COUNT(category_id) FROM user_status WHERE category_id = :category_id AND question_result = 1")
    int countAnsweredQuestion(int category_id);

    @Query("SELECT COUNT(id) FROM user_status WHERE category_id =:category_id  AND class_id=:class_id")
    int countAnsweredByClassAndCategoryId(int category_id, int class_id);

    @Query("SELECT COUNT(id) FROM user_status WHERE question_result = 1")
    int countAllForPoints();

    @Query("SELECT * FROM user_status")
    List<UserStatus> getAllStatusOfUser();

    @Query("DELETE FROM user_status WHERE class_id = :class_id AND category_id  = :category_id")
    void deleteByClassIdAndCategoryId(int class_id,int category_id);

    @Query("DELETE FROM user_status")
    void deleteAllFromUserStatus();

    @Query("SELECT COUNT(id) FROM user_status WHERE category_id =:category_id  AND class_id=:class_id AND question_result = 1")
    int countAllCorrectByClassIdAndCategoryId(int category_id, int class_id);


    @Query("SELECT * FROM user_status WHERE class_id =:class_id AND category_id=:category_id")
    List<UserStatus> getStatusOfUserByClassIdAndCategoryId(int class_id, int category_id);

}
