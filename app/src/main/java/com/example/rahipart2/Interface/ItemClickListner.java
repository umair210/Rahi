package com.example.rahipart2.Interface;

import android.view.View;
//using interface because of lot of methods in the other class
public interface ItemClickListner
{
void onClick(View view, int position, boolean isLongClick);
}
