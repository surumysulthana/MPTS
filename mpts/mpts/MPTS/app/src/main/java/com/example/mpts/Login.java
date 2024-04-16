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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse{
    EditText uname,pass;

    Button b1;

    TextView t1,t2;

    public static String username,password,logid,usertype;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        uname=(EditText) findViewById(R.id.edt1);
        pass=(EditText) findViewById(R.id.edt2);


        b1=(Button) findViewById(R.id.button2);

        t1=(TextView) findViewById(R.id.sin);
        t2=(TextView) findViewById(R.id.usin);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=uname.getText().toString();
                password=pass.getText().toString();
                if (username.equalsIgnoreCase("")){
                    uname.setError("enter username");
                    uname.setFocusable(true);
                } else if (password.equalsIgnoreCase("")) {
                    pass.setError("enter password");
                    pass.setFocusable(true);

                } else {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Login.this;
                    String q = "/autholog?username=" + username + "&password=" + password ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }
            }
        });


        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),User_Reg.class));
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                logid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("usertype");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", logid);
//                e.commit();
                e.apply();

                if (usertype.equals("Authority")) {
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), New_home.class));
                }else if(usertype.equals("User")){
                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), user_home.class));
                }

            } else {
                Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        }
        catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}