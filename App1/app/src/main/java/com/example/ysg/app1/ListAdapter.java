package com.example.ysg.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{
    private ArrayList<ListItem> listItemList = new ArrayList<ListItem>() ;
    public ListAdapter() {
    }
    public int getCount(){
        return listItemList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.customlist, parent, false);
        }
        TextView EngView = (TextView) convertView.findViewById(R.id.english) ;
        TextView KorView = (TextView) convertView.findViewById(R.id.korean) ;
        ListItem listViewItem = listItemList.get(position);
        EngView.setText(listViewItem.getEng());
        KorView.setText(listViewItem.getKor());
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
    public void addItem(String eng, String kor) {
        ListItem item = new ListItem();
        item.setEng(eng);
        item.setKor(kor);
        listItemList.add(item);
    }


}