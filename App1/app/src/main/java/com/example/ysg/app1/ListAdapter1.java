package com.example.ysg.app1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter1 extends BaseAdapter{
    private ArrayList<ListItem1> listItemList = new ArrayList<ListItem1>() ;

    public int getCount(){
        return listItemList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customlist1, parent, false);
        }
        Typeface myfont;
        myfont=Typeface.createFromAsset(context.getAssets(),"font/font4.ttf");
        TextView Vocab = (TextView) convertView.findViewById(R.id.vocab) ;
        Vocab.setTypeface(myfont);
        ListItem1 listViewItem = listItemList.get(position);
        Vocab.setText(listViewItem.getVocab());
        return convertView;
    }
    @Override
    public long getItemId(int position) {
        return position ;
    }
    @Override
    public Object getItem(int position) {

        return listItemList.get(position) ;
    }
    public void addItem(String vocab) {
        ListItem1 item = new ListItem1();
        item.setVocab(vocab);
        listItemList.add(item);
    }
    public void clear(){
        listItemList.clear();
    }




}