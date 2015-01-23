package com.lijin.kahani.ourstory;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by LIJIN on 1/20/2015.
 */
public class ChapterListAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList<ParseObject> arrayList;
    private static LayoutInflater inflater=null;
    public ChapterListAdapter(Activity activity, ArrayList<ParseObject> arrayList){
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

        public TextView vCno;
        public TextView vTitle;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){
            vi = inflater.inflate(R.layout.chapter_list_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.vCno = (TextView) vi.findViewById(R.id.chapterNo);
            holder.vTitle=(TextView)vi.findViewById(R.id.chapterName);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(arrayList.size()<=0)
        {
            holder.vTitle.setText("Click + To add Chapter");

        }
        else
        {

            /************  Set Model values in Holder elements ***********/

            holder.vCno.setText( String.valueOf(arrayList.get(position).getInt("cno")));
            holder.vTitle.setText( arrayList.get(position).getString("title"));


            /******** Set Item Click Listner for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnListItemClickListener( position ));
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
}
