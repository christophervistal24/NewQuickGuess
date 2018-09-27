package com.example.forest.quickguessv2;

import android.app.Application;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Friends.FriendsRepositories;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.Helpers.Connectivity;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    PointsRepositories pointsRepositories;
    FriendsRepositories friendsRepositories;
    Points points;
  /*  private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher (Context context)
    {
        ApplicationClass app = (ApplicationClass) context.getApplicationContext();
        return app.refWatcher;
    }*/
    //        refWatcher = LeakCanary.install(this);


    @Override
    public void onCreate() {
        super.onCreate();
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(this);
        friendsRepositories = new FriendsRepositories(this);
        insertCategories();
        insertQuestions();
        insertUserPoints();
        insertAllFriends();
    }


    private void insertUserPoints()
    {
        if (Connectivity.isConnectedWifi(getApplicationContext()))
        {
            pointsRepositories = new PointsRepositories(getApplicationContext());
            points = new Points();
            pointsRepositories.sendPoints(points);
        }
    }


    private void insertCategories()
    {
        questionCategoryRepositories.categoryCreator("people","All about peoples"); //1
        questionCategoryRepositories.categoryCreator("plants","All about plants"); //2
        questionCategoryRepositories.categoryCreator("animals","All about animals"); //3
        questionCategoryRepositories.categoryCreator("geography","All about places"); //4
        questionCategoryRepositories.categoryCreator("sports","All about sports"); //5
        questionCategoryRepositories.categoryCreator("music","All about music"); //6
        questionCategoryRepositories.categoryCreator("technology","All about technologies"); //7
        questionCategoryRepositories.categoryCreator("entertainment","All about entertainment"); //8
    }

    private void insertQuestions()
    {
        //insert questions
        if (DB.getInstance(this).questionsDao().countQuestion() == 0)
        {

//99 Madagascar
//Easy
            questionRepositories.questionCreator("In which country are lemurs found in nature?","Madagascar","U.S.A","Finland","New Zealand","Madagascar","Today the black lemur is an endangered species and is found only in a small area on Madagascar and on two small islands off its northwest coast. On one island they have the benefit of a reserve of natural forest.","madagascar_99.png",3,1);

            //98 four
//Medium
            questionRepositories.questionCreator("How many different types of hyenas are there?","six","ten","five","four","four","There are 4 known species of hyena, the spotted hyena, the striped hyena, the brown hyena and the aardwolf.","four_98.png",3,2);


//100 Earthworms
//Easy
            questionRepositories.questionCreator("What is the main diet of a mole?","Beetle","Dragonfly","Ants","Earthworms","Earthworms","It is a misconception that moles burrow into gardens to eat the roots of plants. They are actually after the earthworms that are found in garden soil. Moles love earthworms so much that they eat nearly their body weight worth of earthworms per day. Moles also consume insect larvae.","earthworms_100.png",3,1);



//89 five minutes
//Medium
            questionRepositories.questionCreator("How long can a hippo hold their breath in the water?","ten minutes","five minutes","one minute","fifteen minutes","five minutes","Their nostrils close, and they can hold their breath for 5 minutes or longer when submerged. Hippo can even underwater, using a reflex that allows them to bob up, take a breath, and sink and back down without waking up.","five_minutes_89.png",3,2);


//94 New Zealand
//Difficult
            questionRepositories.questionCreator("Where would find a tuatara?","Africa","New Zealand","Sweden","Australia","New Zealand","This New Zealand native has unique, ancient lineage that goes back to the time of the dinosaurs.","new_zealand_94.png",3,3);

//92 Airedale
//Difficult
            questionRepositories.questionCreator("What is the largest terrier?","Scottish","Bull","Boston","Airedale","Airedale","Airedale it is traditionally called \"King of Terriers\" because it is the largest of the terrier breeds. The Airedale was bred from the old English black and tan terrier (now extinct).","airedale_92.png",3,3);

        }
    }

    private void insertAllFriends()
    {
        if (DB.getInstance(getApplicationContext()).friendsDao().countFriends() == 0)
        {
            friendsRepositories.friendsCreator("Patrick",1);
            friendsRepositories.friendsCreator("Steven",1);
            friendsRepositories.friendsCreator("Howard",1);
            friendsRepositories.friendsCreator("Louis",2);
            friendsRepositories.friendsCreator("Bruce",2);
            friendsRepositories.friendsCreator("Earl",2);
            friendsRepositories.friendsCreator("Roger",3);
            friendsRepositories.friendsCreator("Gary",3);
            friendsRepositories.friendsCreator("Joshua",3);
            friendsRepositories.friendsCreator("Anthony",4);
            friendsRepositories.friendsCreator("Steve",4);
            friendsRepositories.friendsCreator("Wayne",4);
            friendsRepositories.friendsCreator("Robert",5);
            friendsRepositories.friendsCreator("Gerald",5);
            friendsRepositories.friendsCreator("Matthew",5);
            friendsRepositories.friendsCreator("Philip",6);
            friendsRepositories.friendsCreator("Thomas",6);
            friendsRepositories.friendsCreator("Justin",6);
            friendsRepositories.friendsCreator("Martin",7);
            friendsRepositories.friendsCreator("Ronald",7);
            friendsRepositories.friendsCreator("Alan",7);
            friendsRepositories.friendsCreator("Russell",8);
            friendsRepositories.friendsCreator("Jason",8);
            friendsRepositories.friendsCreator("Albert",8);
        }

    }

}
