package com.example.movement;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

public class customize_notification extends ArrayAdapter<String>  {

    private Activity context;
    SharedPreferences sh;
    private String[] titl, comp, date;


    public customize_notification(Activity context,String[] titl, String[] comp, String[] date) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_customize_notification, titl);
        this.context = context;
        this.titl = titl;
        this.comp=comp;
        this.date=date;


    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_customize_notification, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView gifImageView = listViewItem.findViewById(R.id.gifImageView);

        // Load the image into the ImageView using Glide
        Glide.with(context)
                .load(R.drawable.qqqq)
                .transform(new CircleCrop())
                .into(gifImageView);


//        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView);
        TextView t=(TextView)listViewItem.findViewById(R.id.textView);
//


        t.setText( "Title : " + titl[position]
                + "\nNotification : " + comp[position]
                + "\n Date : " + date[position]

        )
        ;


        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+titl[position];
        pth = pth.replace("~", "");
//
//
        return  listViewItem;
    }



    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}