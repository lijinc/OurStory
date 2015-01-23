package com.lijin.kahani.ourstory;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by LIJIN on 1/21/2015.
 */
public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "55y2jjhriwFkkm5QaO3nzoBY6JGvyGs1t7Lp2RLy", "aCVcCdBA5vUzR4Brpa0NekhY3NReTKYDHCMIQ7ST");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
