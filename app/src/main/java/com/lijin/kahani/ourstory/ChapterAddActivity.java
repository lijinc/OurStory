package com.lijin.kahani.ourstory;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsware.cwac.richedit.RichEditText;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class ChapterAddActivity extends ActionBarActivity {
    Chapter chapter;
    String bookID;
    RichEditText richEditText;
    EditText editText;
    int cno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "55y2jjhriwFkkm5QaO3nzoBY6JGvyGs1t7Lp2RLy", "aCVcCdBA5vUzR4Brpa0NekhY3NReTKYDHCMIQ7ST");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        ParseObject.registerSubclass(Chapter.class);
        setContentView(R.layout.activity_chapter_add);
        richEditText=(RichEditText)findViewById(R.id.chapter_richtext);
        editText=(EditText)findViewById(R.id.edit_chaptitle_text);
        chapter=new Chapter();
        Intent intent=getIntent();
        bookID=intent.getStringExtra("BOOKID");
        chapter.setBookID(bookID);
        cno=intent.getIntExtra("CNO",0);
        chapter.setChapterNo(cno);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chapter_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dummy_chap) {
            chapter.setTitle(editText.getText().toString());
            chapter.setChapterNo(cno);
            chapter.setContent(richEditText.getText().toString());
            chapter.saveInBackground(new SaveCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null) {
                     finish();
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error saving: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }

            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
