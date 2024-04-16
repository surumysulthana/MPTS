package com.example.movement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Auth_view_notification extends AppCompatActivity implements JsonResponse{

    ListView l1;
    String [] comp,reply,date,value,titl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_view_notification);
        l1=findViewById(R.id.list);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Auth_view_notification.this;
        String q = "/notification";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try{

                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");


                    titl = new String[ja1.length()];
                    comp = new String[ja1.length()];
//                    reply = new String[ja1.length()];
                    date = new String[ja1.length()];
                    value=new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        titl[i] = ja1.getJSONObject(i).getString("title");
                        comp[i] = ja1.getJSONObject(i).getString("description");
//                        reply[i] = ja1.getJSONObject(i).getString("reply");
                        date[i] = ja1.getJSONObject(i).getString("date");

                        value[i] ="Title :" + titl[i] + "\nDescription :" + comp[i] + "\nDate :" + date[i];
                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);
                    Custome_athu_noti p1= new Custome_athu_noti(Auth_view_notification.this, titl, comp, date);
                    l1.setAdapter(p1);
                }


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}