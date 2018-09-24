package com.example.forest.quickguessv2.DB.Friends;

import android.content.Context;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategory;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.FriendsActivity;
import com.example.forest.quickguessv2.Helpers.RedirectHelper;

public class FriendsRepositories {

    private Context context;

    public FriendsRepositories(Context context)
    {
        this.context = context;
    }

    public void friendsCreator(String friend_name , int category_id)
    {
        Friends friends = new Friends();
        friends.setName(friend_name);
        friends.setBelongs_to_category(category_id);
        DB.getInstance(context).friendsDao().insertAllFriends(friends);
    }

    //5
    //10
    //15
    public void checkAnsweredQuestion(int category_id,int[] items)
    {
        int countAnswered = DB.getInstance(context).userStatusDao().countAnsweredQuestion(category_id);
        int remainingNeedToSaveFriends = DB.getInstance(context).friendsDao().countSaveFriendsByCategoryId(category_id);
        if (countAnswered >= items[0] && remainingNeedToSaveFriends == 3)
        {
            new RedirectHelper(context, FriendsActivity.class);
        } else if (countAnswered >= items[1] && remainingNeedToSaveFriends == 2)
        {
            new RedirectHelper(context, FriendsActivity.class);
        } else if (countAnswered >= items[2] && remainingNeedToSaveFriends == 1)
        {
            new RedirectHelper(context, FriendsActivity.class);
        }
    }

}
