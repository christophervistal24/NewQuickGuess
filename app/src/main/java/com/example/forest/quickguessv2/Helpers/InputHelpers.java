package com.example.forest.quickguessv2.Helpers;

public class InputHelpers {

    public static Boolean isProperUsername(String data)
    {
        return !data.matches("[a-zA-Z0-9]*");
    }
}
