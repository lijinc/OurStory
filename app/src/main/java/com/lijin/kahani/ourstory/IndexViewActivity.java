package com.lijin.kahani.ourstory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class IndexViewActivity extends ActionBarActivity {
    ListView listView;
    ChapterListAdapter chapterListAdapter;
    ArrayList<ParseObject> arrayList;
    String bookID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_view);
        Parse.initialize(this, "55y2jjhriwFkkm5QaO3nzoBY6JGvyGs1t7Lp2RLy", "aCVcCdBA5vUzR4Brpa0NekhY3NReTKYDHCMIQ7ST");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        arrayList=new ArrayList<ParseObject>();
        Intent intent=getIntent();
        bookID=intent.getStringExtra("BOOKID");
        listView=(ListView)findViewById(R.id.chapter_list_view);
        chapterListAdapter=new ChapterListAdapter(this,arrayList);
        listView.setAdapter(chapterListAdapter);
        query();
    }

    @Override
    protected void onResume() {
        super.onResume();
        query();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_index_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_chap) {
            Intent intent=new Intent(this,ChapterAddActivity.class);
            intent.putExtra("BOOKID",bookID);
            intent.putExtra("CNO",arrayList.size()+1);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void query(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Chapter");
        query.whereEqualTo("bookid",bookID);
        query.orderByAscending("cno");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> chapterList, com.parse.ParseException e) {
                if (e == null) {
                    arrayList.clear();
                    for(ParseObject parseObject:chapterList){
                        arrayList.add(parseObject);
                    }
                    chapterListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
    }
}
