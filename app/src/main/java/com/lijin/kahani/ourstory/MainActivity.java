package com.lijin.kahani.ourstory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.facebook.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    ListView listView;
    BookListAdapter bookListAdapter;
    ArrayList<ParseObject> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseObject.registerSubclass(Book.class);
        Parse.initialize(this, "55y2jjhriwFkkm5QaO3nzoBY6JGvyGs1t7Lp2RLy", "aCVcCdBA5vUzR4Brpa0NekhY3NReTKYDHCMIQ7ST");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        listView=(ListView)findViewById(R.id.book_list_view);
        arrayList=new ArrayList<ParseObject>();
        bookListAdapter=new BookListAdapter(this,arrayList);
        listView.setAdapter(bookListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        query();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_book) {
            /*Intent intent=new Intent(this,AddBookActivity.class);
            startActivity(intent);
            return true;*/
            List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_about_me",
                    "user_relationships", "user_birthday", "user_location");
            ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
                @Override
                public void done(ParseUser user, com.parse.ParseException err) {
                    if (user == null) {
                        Log.d("Liji",
                                "Uh oh. The user cancelled the Facebook login.");
                    } else if (user.isNew()) {
                        Log.d("Liji",
                                "User signed up and logged in through Facebook!");
                    } else {
                        Log.d("liji",
                                "User logged in through Facebook!");
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void query(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Book");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> chapterList, com.parse.ParseException e) {
                if (e == null) {
                    arrayList.clear();
                    for(ParseObject parseObject:chapterList){
                        arrayList.add(parseObject);
                    }
                    bookListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}
