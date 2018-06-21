package com.maps.att.traskingsystem.ApiMaps;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.maps.att.traskingsystem.R;


public class ListOnlineViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
    public TextView txtEmail;
    ItemClickListenener itemClickListenener;

    public ListOnlineViewHolder(View itemView) {
        super(itemView);
        txtEmail=(TextView) itemView.findViewById(R.id.txt_email);
    }


    public void setItemClickListenener(ItemClickListenener itemClickListenener) {
        this.itemClickListenener = itemClickListenener;
    }

    @Override
    public void onClick(View view) {
        itemClickListenener.onClick(view,getAdapterPosition());

    }
}
