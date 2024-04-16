package com.example.mpts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Complaints extends AppCompatActivity implements JsonResponse{
    EditText title,des,date;
    Button send;
    String Title,Description,Date;

    SharedPreferences sh;

    String [] value,titles,description,reply,dates;

    ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        title=findViewById(R.id.edt1);
        des=findViewById(R.id.edt2);

        date=findViewById(R.id.edt4);
        send=findViewById(R.id.button2);

        l1=(ListView) findViewById(R.id.viewcomplaints);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Complaints.this;
        String q = "/reply?lid="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Title = title.getText().toString();
                Description = des.getText().toString();

                Date = date.getText().toString();


                if (Title.equalsIgnoreCase("")) {
                    title.setError("enter the username");
                    title.setFocusable(true);
                } else if (Description.equalsIgnoreCase("")) {
                    des.setError("enter the designation");
                    des.setFocusable(true);
//                } else if (Reply.equalsIgnoreCase("")) {
//                    reply.setError("enter the number");
//                    reply.setFocusable(true);
                } else if (Date.equalsIgnoreCase("")) {
                    date.setError("enter the number");
                    date.setFocusable(true);
                }
                else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Complaints.this;
                    String q = "/authocomp?Title=" + Title + "&Description=" + Description + "&Date=" +Date + "&loginid=" + sh.getString("log_id","");
                    q = q.replace(" ", "%20");
                    JR.execute(q);


                }

            }
        });

    }


    @Override
    public void response(JSONObject jo) {
        try {

//            Toast.makeText(this, "aaaaaaaaa", Toast.LENGTH_SHORT).show();

            String status = jo.getString("status");
            String method = jo.getString("method");

            Log.d("pearl", status);


            if (method.equalsIgnoreCase("viewcomplaint")){

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");

                titles = new String[ja.length()];
                description = new String[ja.length()];
                dates = new String[ja.length()];
                reply = new String[ja.length()];
                value = new String[ja.length()];

                for (int i = 0; i < ja.length(); i++) {
                    titles[i] = ja.getJSONObject(i).getString("title");
                    description[i] = ja.getJSONObject(i).getString("description");
                    dates[i] = ja.getJSONObject(i).getString("date");
                    reply[i] = ja.getJSONObject(i).getString("reply");

                    value[i] = "title: " + titles[i] + "\n description:" + description[i] + "\n date:" + dates[i] + "\n reply:" + reply[i] ;


                }
                l1.setAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value));
//                l1.setAdapter(new );


            }

            } if (method.equalsIgnoreCase("sendcomplaint")){

                if (status.equalsIgnoreCase("success")){
                    Toast.makeText(this, "Send  success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),New_home.class));
                }
            }


        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }


}