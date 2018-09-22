package com.example.forest.quickguessv2.DB.Friends;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface FriendsDao {
    @Insert()
    void insertAllFriends(Friends... friends);

    @Query("SELECT COUNT(*) FROM friends")
    int countFriends();

    @Query("UPDATE friends SET status = :status WHERE name =:name")
    void update(String name , int status);

    @Query("SELECT COUNT(name) FROM friends WHERE belongs_to_category = :category_id AND status = 0")
    int countSaveFriendsByCategoryId(int category_id);
//TODO MAKE THIS ONE QUERY
    @Query("SELECT name FROM friends WHERE belongs_to_category = :category_id AND status = 0")
    String[] getFriendNamesByCategoryId(int category_id);

}
