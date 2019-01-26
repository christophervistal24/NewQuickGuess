package com.example.forest.quickguess.Utilities;

public class DifficultLevelUtil {

    public static int getTimerForDifficulty(int level)
    {
        int time = 0;
        switch  (level) {
            case 1 :
                time = 31000;
                break;

            case 2 :
                time = 21000;
                break;

            case 3:
                time = 11000;
                break;

        }

        return time;


    }
}
