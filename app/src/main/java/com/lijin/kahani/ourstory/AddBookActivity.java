package com.lijin.kahani.ourstory;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.commonsware.cwac.richedit.RichEditText;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Date;


public class AddBookActivity extends ActionBarActivity {
    Book book;
    RichEditText richEditText;
    EditText editText;
    String bookID;
    String author;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ParseObject.registerSubclass(Book.class);
        Parse.initialize(this, "55y2jjhriwFkkm5QaO3nzoBY6JGvyGs1t7Lp2RLy", "aCVcCdBA5vUzR4Brpa0NekhY3NReTKYDHCMIQ7ST");
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        book=new Book();
        richEditText=(RichEditText)findViewById(R.id.story_richtext);
        editText=(EditText)findViewById(R.id.edit_title_text);
        addAuthor();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_dummy) {
            book.setTitle(editText.getText().toString());
            book.setDescription(richEditText.getText().toString());
            book.setAuthor(author);
            book.saveInBackground(new SaveCallback() {
                @Override
                public void done(com.parse.ParseException e) {
                    if (e == null) {
                        bookID=book.getObjectId();
                        Intent intent=new Intent(getApplicationContext(),IndexViewActivity.class);
                        intent.putExtra("BOOKID",bookID);
                        startActivity(intent);

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
        else if(id==R.id.action_dummy_upload){
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);

            intent.setType("image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("scale", true);
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 120);
            intent.putExtra("aspectX", 3);
            intent.putExtra("aspectY", 4);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == 1) {
            final Bundle extras = data.getExtras();

            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                final ParseFile photoFile = new ParseFile("thumbnail_photo.jpg", byteArray);
                //wait
                photoFile.saveInBackground(new SaveCallback() {

                    public void done(com.parse.ParseException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            book.setPhotoFile(photoFile);
                        }
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void addAuthor() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.has("profile")) {
            JSONObject userProfile = currentUser.getJSONObject("profile");
            try {

                if (userProfile.has("name")) {
                    author=userProfile.getString("name");
                }

            } catch (JSONException e) {
                Log.d("ERROR", "Error parsing saved user data.");
            }
        }
    }
}
