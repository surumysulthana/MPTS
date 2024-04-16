package com.example.movement;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

public class customize_user_req extends ArrayAdapter<String>  {

    private Activity context;
    SharedPreferences sh;
    private String[] title, reason, f_date,f_time,f_location,t_location,qr,stat;


    public customize_user_req(Activity context,String[] title, String[] reason, String[] f_date,String[]f_time, String[] f_location, String[] t_location,String[] qr,String[] stat) {
        //constructor of this class to get the values from main_activity_class

        super(context, R.layout.activity_customize_user_req, title);
        this.context = context;
        this.title = title;
        this.reason=reason;
        this.f_date=f_date;
        this.f_time = f_time;
        this.f_location = f_location;
        this.t_location = t_location;
        this.qr = qr;
        this.stat = stat;

    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //override getView() method

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_customize_user_req, null, true);
        //cust_list_view is xml file of layout created in step no.2

        ImageView im = (ImageView) listViewItem.findViewById(R.id.imageView);
        TextView t=(TextView)listViewItem.findViewById(R.id.textView);
//


        t.setText( "Title : " + title[position]
                + "\nReason : " + reason[position]
                + "\nFrom Date : " + f_date[position]
                + "\nTo Time : " + f_time[position]
                + "\nFrom Location : " + f_location[position]
                + "\nTo Location : " + t_location[position]
                + "\nThe Permit Status : " + stat[position]
        )
        ;


        sh=PreferenceManager.getDefaultSharedPreferences(getContext());

        String pth = "http://"+sh.getString("ip", "")+"/"+qr[position];
        pth = pth.replace("~", "");
//


        Log.d("-------------", pth);
        Picasso.with(context)
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(im);
        return  listViewItem;
    }

    private TextView setText(String string) {
        // TODO Auto-generated method stub
        return null;
    }
}