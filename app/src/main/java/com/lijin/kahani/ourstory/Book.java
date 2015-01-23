package com.lijin.kahani.ourstory;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;

/**
 * Created by LIJIN on 1/20/2015.
 */
@ParseClassName("Book")
public class Book extends ParseObject {
    public Book(){

    }
    public String getAuthor() {
        return getString("author");
    }

    public void setAuthor(String author) {
        put("author",author);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }

    public double getRating() {
        return getInt("rating");
    }

    public void setRating(int rating) {
        put("rating", rating);
    }

    public String getDescription() {
        return getString("description");
    }

    public void setDescription(String description) {
        put("description",description);
    }

    public long getNumberOfViews() {
        return getLong("views");
    }

    public void setNumberOfViews(long numberOfViews) {
        put("views",numberOfViews);
    }

    public ParseFile getPhotoFile() {
        return getParseFile("photo");
    }

    public void setPhotoFile(ParseFile file) {
        put("photo", file);
    }
}
