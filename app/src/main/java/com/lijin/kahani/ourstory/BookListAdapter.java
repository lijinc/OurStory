package com.lijin.kahani.ourstory;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by LIJIN on 1/21/2015.
 */
public class BookListAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList<ParseObject> arrayList;
    private static LayoutInflater inflater=null;
    public BookListAdapter(Activity activity, ArrayList<ParseObject> arrayList){
        this.activity=activity;
        this.arrayList=arrayList;
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){
        if(arrayList==null)
            return 0;
        if(arrayList.size()<=0)
            return 1;
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return new Long(0);
    }
    public static class ViewHolder{

        public TextView vAuthor;
        public TextView vTitle;
        public TextView vRating;
        public TextView vView;
        public ImageView vThumb;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.book_list_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.vTitle = (TextView) vi.findViewById(R.id.title_text);
            holder.vAuthor=(TextView)vi.findViewById(R.id.author_text);
            holder.vRating=(TextView)vi.findViewById(R.id.rating_txt);
            holder.vView=(TextView)vi.findViewById(R.id.views_text);
            holder.vThumb=(ImageView)vi.findViewById(R.id.thumbnail_img);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(arrayList.size()>0){
            /************  Set Model values in Holder elements ***********/

            holder.vTitle.setText( arrayList.get(position).getString("title"));
            holder.vAuthor.setText( arrayList.get(position).getString("author"));
            holder.vRating.setText( "r "+String.valueOf(arrayList.get(position).getInt("rating")));
            holder.vView.setText("v " + String.valueOf(arrayList.get(position).getInt("views")));
            ParseFile image = arrayList.get(position).getParseFile("photo");
            displayImage(image,holder.vThumb);
            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnListItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {

    }

    private class OnListItemClickListener  implements View.OnClickListener{
        private int mPosition;

        OnListItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {



        }
    }

    private void displayImage(ParseFile thumbnail, final ImageView img) {

        if (thumbnail != null) {
            thumbnail.getDataInBackground(new GetDataCallback() {

                @Override
                public void done(byte[] data, com.parse.ParseException e) {

                    if (e == null) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
                                data.length);

                        if (bmp != null) {

                            // img.setImageBitmap(Bitmap.createScaledBitmap(bmp,
                            // (display.getWidth() / 5),
                            // (display.getWidth() /50), false));
                            img.setImageBitmap(bmp);
                            // img.setPadding(10, 10, 0, 0);



                        }
                    } else {
                        Log.e("paser after downloade", " null");
                    }

                }
            });
        } else {

            Log.e("parse file is", " null");

            // img.setImageResource(R.drawable.ic_launcher);
        }

    }
}