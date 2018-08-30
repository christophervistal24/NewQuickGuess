package com.example.forest.quickguessv2.Utilities;

import android.os.Bundle;

public class GameBundleUitl {


    public static void setQuestionResult(Bundle bundle, String key, String result) {
        bundle.putString(key,result);
    }
}
