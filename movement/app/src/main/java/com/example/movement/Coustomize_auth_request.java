package com.example.movement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

public class Coustomize_auth_request extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] perid,title, reason, f_date, f_location, t_location, f_time, date, route;

    public Coustomize_auth_request(Activity context,String[] perid, String[] title, String[] reason, String[] f_date, String[] f_location, String[] t_location, String[] f_time, String[] date, String[] route) {
        super(context, R.layout.activity_coustomize_auth_request, title);
        this.context = context;
        this.perid = perid;
        this.title = title;
        this.reason = reason;
        this.f_date = f_date;
        this.f_location = f_location;
        this.t_location = t_location;
        this.f_time = f_time;
        this.date = date;
        this.route = route;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_coustomize_auth_request, null, true);

        ImageView gifImageView = listViewItem.findViewById(R.id.gifImageView);
        TextView textView = listViewItem.findViewById(R.id.textView);

        // Load the image into the ImageView using Glide
        Glide.with(context)
                .load(R.drawable.reqqper)

                .into(gifImageView);

        textView.setText("Title: " + title[position]
                + "\nReason: " + reason[position]
                + "\nFrom Date: " + f_date[position]
                + "\nFrom Location: " + f_location[position]
                + "\nTo Location: " + t_location[position]
                + "\nFrom Time: " + f_time[position]
                + "\nDate: " + date[position]
                + "\nRoute: " + route[position]
        );



        textView.setOnClickListener(view -> {
            JsonReq JR = new JsonReq();
            JR.json_response = (JsonResponse) context; // Assuming you meant to use context
            String q = "/checklocation?curplace=" + LocationService.place + "&perid=" + perid[position];
//            String q = "/checklocation?curplace=" + "thalore" + "&perid=" + perid[position];
            q = q.replace(" ", "%20");
            JR.execute(q);
        });

        ImageView im = (ImageView) listViewItem.findViewById(R.id.location);

//        im.setTag(place_id[position]);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                    int ik=Integer.parseInt(view.getTag().toString());

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?q="+Scan_qrcode.lati+","+Scan_qrcode.longi));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


}
        });

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
        String pth = "http://" + sh.getString("ip", "") + "/" + title[position];
        pth = pth.replace("~", "");

        return listViewItem;
    }
}
