package com.example.rahipart2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rahipart2.Interface.ItemClickListner;
import com.example.rahipart2.R;


// Class to show the data of every product
public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView textnoofdays, textlocationoftrip;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);

        imageView=(ImageView) itemView.findViewById(R.id.Tripimage);
        textnoofdays=(TextView) itemView.findViewById(R.id.no_of_days_dispaly);
        textlocationoftrip=(TextView) itemView.findViewById(R.id.location_display);
    }


    //whenever user click on the trip to get the details
    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner =listner = listner;
    }

    @Override
    public void onClick(View v)
    {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
