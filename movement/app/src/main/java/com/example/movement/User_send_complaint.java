package com.example.movement;

import androidx.appcompat.app.AppCompatActivity;

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

public class User_send_complaint extends AppCompatActivity implements JsonResponse{

    EditText e1,e2;
    Button b1;
    String feedback,title;
    SharedPreferences sh;
    ListView l1;
    String [] comp,reply,date,value,titl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_send_complaint);
        e1=(EditText) findViewById(R.id.complaint);
        e2=findViewById(R.id.editTextText5);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1=(Button) findViewById(R.id.button5);
        l1=findViewById(R.id.list);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_send_complaint.this;
        String q = "/user_view_complaint?id=" + sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedback=e1.getText().toString();
                title=e2.getText().toString();
                if(feedback.equalsIgnoreCase("")){
                    e1.setError("Empty");
                    e1.setFocusable(true);
                }else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) User_send_complaint.this;
                    String q = "/user_send_complaint?feed=" + feedback + "&id=" + sh.getString("log_id","")+"&title="+title;
                    q = q.replace(" ", "%20");
                    JR.execute(q);
                }
            }
        });
    }
    @Override
    public void response(JSONObject jo) {
        try{
            String method=jo.getString("method");
            if(method.equalsIgnoreCase("send")){
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "Sent", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));
                }
            }
            if(method.equalsIgnoreCase("view")){
                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");
                    Log.d("pearl", method);

                    titl = new String[ja1.length()];
                    comp = new String[ja1.length()];
                    reply = new String[ja1.length()];
                    date = new String[ja1.length()];
                    value=new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        titl[i] = ja1.getJSONObject(i).getString("title");
                        comp[i] = ja1.getJSONObject(i).getString("description");
                        reply[i] = ja1.getJSONObject(i).getString("reply");
                        date[i] = ja1.getJSONObject(i).getString("date");

                        value[i] ="Title :" + titl[i] + "\nComplaint :" + comp[i] + "\nReply :" + reply[i] + "\nDate :" + date[i];
                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
                    l1.setAdapter(ar);
                }

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}