package com.example.forest.quickguess.Helpers;

public class InputHelpers {

    public static Boolean isProperUsername(String data)
    {
        return !data.matches("[a-zA-Z0-9]*");
    }
}
