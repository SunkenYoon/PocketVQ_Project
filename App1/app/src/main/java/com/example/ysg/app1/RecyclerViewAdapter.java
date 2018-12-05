package com.example.ysg.app1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context context;
    List<Shared.User> MainImageUploadInfoList;

    public RecyclerViewAdapter(Context context, List<Shared.User> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customsharedlist, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shared.User UploadInfo = MainImageUploadInfoList.get(position);

        holder.nameView.setText(UploadInfo.getUserName());
        holder.wordView.setText(UploadInfo.getWord());
        holder.meanView.setText(UploadInfo.getMean());
        //Loading image from Glide library.
        Glide.with(context).load(UploadInfo.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView nameView;
        public TextView wordView;
        public TextView meanView;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.sharedUserImage);
            nameView = (TextView)itemView.findViewById(R.id.sharedUserName);
            wordView=(TextView)itemView.findViewById(R.id.sharedUserWord);
            meanView=(TextView)itemView.findViewById(R.id.sharedUserMean);
        }
    }
}
