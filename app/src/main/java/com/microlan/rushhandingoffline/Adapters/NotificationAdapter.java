package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microlan.rushhandingoffline.Activities.MainActivity;
import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.AnnouncementItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

   Context context;
    List<AnnouncementItem> announcement;
    public NotificationAdapter(MainActivity notification, List<AnnouncementItem> announcement) {

  this.announcement=announcement;
  this.context=notification;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationlist, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AnnouncementItem item=announcement.get(position);
        holder.massege.setText(""+item.getMsg());
        String Base_url="http://microlanpos.com/demo/uploads/";

        Picasso.get().load(Base_url+item.getImage()).placeholder(R.drawable.ic_menu_gallery).into(holder.notifyimage);

    }

    @Override
    public int getItemCount() {
        return announcement.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView massege;
        ImageView notifyimage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            massege=itemView.findViewById(R.id.massege);
            notifyimage=itemView.findViewById(R.id.notifyimage);
        }
    }
}
