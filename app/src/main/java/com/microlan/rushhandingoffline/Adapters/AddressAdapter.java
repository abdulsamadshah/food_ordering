package com.microlan.rushhandingoffline.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.microlan.rushhandingoffline.R;
import com.microlan.rushhandingoffline.model.UserAddressItem;

import java.util.List;

public class AddressAdapter extends  RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    Context context;

    List<UserAddressItem> userAddress;
/*
    public AddressAdapter(ItemInCart counters, List<UserAddressItem> userAddress) {

        this.context=counters;
        this.userAddress=userAddress;
    }
*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.single_grid_item_addresses, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserAddressItem item=userAddress.get(position);
        holder.AddressText.setText(""+item.getAddress1()+""+item.getAddress2());
        holder.city.setText(item.getTownCity());
        holder.state.setText(item.getState());
        holder.AddressPincodeText.setText(item.getPincode());
        holder.Name.setText(item.getFullName());
        holder.mobile.setText(item.getMobileNumber());


    }

    @Override
    public int getItemCount() {
        return userAddress.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        Button playspin;
        ImageView CancelImage;
        TextView AddressTitleText,AddressText,Name,mobile,city,state,select_address,AddressPincodeText;
        public MyViewHolder(@NonNull View gridView) {
            super(gridView);
             AddressTitleText = (TextView)gridView.findViewById(R.id.addressTitleText);
             AddressText = (TextView)gridView.findViewById(R.id.addressText);
             Name = (TextView)gridView.findViewById(R.id.Name);
             mobile = (TextView)gridView.findViewById(R.id.mobile);
             city = (TextView)gridView.findViewById(R.id.city);
             state = (TextView)gridView.findViewById(R.id.state);
             select_address = (TextView)gridView.findViewById(R.id.select_address);
             AddressPincodeText = (TextView)gridView.findViewById(R.id.pincodeText);
             CancelImage = (ImageView)gridView.findViewById(R.id.deleteImage);
        }
    }

}
