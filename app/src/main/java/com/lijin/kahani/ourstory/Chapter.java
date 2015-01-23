package com.lijin.kahani.ourstory;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by LIJIN on 1/20/2015.
 */
@ParseClassName("Chapter")
public class Chapter extends ParseObject{
    public Chapter(){

    }

    public String getBookID() {
        return getString("bookid");
    }

    public void setBookID(String bookID) {
        put("bookid",bookID);
    }

    public String getTitle() {
        return getString("title");
    }

    public void setTitle(String title) {
        put("title",title);
    }

    public String getContent() {
        return getString("content");
    }

    public void setContent(String content) {
        put("content",content);
    }

    public int getChapterNo() {
        return getInt("cno");
    }

    public void setChapterNo(int cno) {
        put("cno",cno);
    }

}
