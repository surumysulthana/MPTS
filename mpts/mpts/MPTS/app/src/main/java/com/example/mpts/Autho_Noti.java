package com.example.mpts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Autho_Noti extends AppCompatActivity implements JsonResponse{
    ListView l1;
    SharedPreferences sh;
    String [] title,des,date;
    String[] value;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autho_noti);
        l1 = findViewById(R.id.view);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        com.example.mpts.JsonReq JR = new com.example.mpts.JsonReq();
        JR.json_response = (JsonResponse) Autho_Noti.this;
        String q="/notification";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }
    @Override
    public void response(JSONObject jo) {
        try {
//            Toast.makeText(this, "aaaaaaaaa", Toast.LENGTH_SHORT).show();

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");


                title = new String[ja.length()];
                des = new String[ja.length()];
                date = new String[ja.length()];

                value = new String[ja.length()];

                for (int i = 0; i < ja.length(); i++) {
                    title[i] = ja.getJSONObject(i).getString("title");
                    des[i] = ja.getJSONObject(i).getString("description");
                    date[i] = ja.getJSONObject(i).getString("date");


                    value[i] = "Title: " + title[i] + "\n Description:" + des[i] + "\n Date:" + date[i] ;


                }
                l1.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value));
//                l1.setAdapter(new );


            }
        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
