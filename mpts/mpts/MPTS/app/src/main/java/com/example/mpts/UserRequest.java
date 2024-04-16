package com.example.mpts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

public class UserRequest extends AppCompatActivity implements JsonResponse  {
    EditText e1,e2,e3,e4,e5,e6,e7;
    Button send;
    String title,reason,fd,ft,fl,tl,image;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);
        e1=findViewById(R.id.edt1);
        e2=findViewById(R.id.edt2);
        e3=findViewById(R.id.edt3);
        e4=findViewById(R.id.edt4);
        e5=findViewById(R.id.edt5);
        e6=findViewById(R.id.edt6);
        e7=findViewById(R.id.edt7);
        send=findViewById(R.id.button2);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=e1.getText().toString();
                reason=e2.getText().toString();
                fd=e3.getText().toString();
                ft=e4.getText().toString();
                fl=e5.getText().toString();
                tl=e6.getText().toString();
                image=e7.getText().toString();
                sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                if (title.equalsIgnoreCase("")) {
                    e1.setError("enter the username");
                    e1.setFocusable(true);
                } else if (reason.equalsIgnoreCase("")) {
                    e2.setError("enter the place");
                    e2.setFocusable(true);
                } else if (fd.equalsIgnoreCase("")) {
                    e3.setError("enter the number");
                    e3.setFocusable(true);
                } else if (ft.equalsIgnoreCase("")) {
                    e4.setError("enter the number");
                    e4.setFocusable(true);
                } else if (fl.equalsIgnoreCase("")) {
                    e5.setError("enter the number");
                    e5.setFocusable(true);
                } else if (tl.equalsIgnoreCase("")) {
                    e6.setError("enter the number");
                    e6.setFocusable(true);
                } else if (image.equalsIgnoreCase("")) {
                    e7.setError("enter the number");
                    e7.setFocusable(true);
                }
                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) UserRequest.this;
                    String q = "/userreq?title=" + title + "&reason=" + reason + "&fd=" +fd + "&ft=" +ft+ "&fl=" +fl+ "&tl=" +tl  + "&image=" + image +"&loginid=" + sh.getString("log_id","")  ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);



                }


            }
        });
        }
    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("status",status);

            if (status.equalsIgnoreCase("success")){
                Toast.makeText(this, "Request Send", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),user_home.class));
            }

        } catch (Exception e) {

            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}

