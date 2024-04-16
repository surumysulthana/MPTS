package com.example.mpts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity implements  JsonResponse{
    EditText name,desi,num,email,idproof,user_name,password;
    Button regi;
    String username,designation,phone,mail,id,uname,pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.edt1);
        desi=findViewById(R.id.edt2);
        num=findViewById(R.id.edt3);
        email=findViewById(R.id.edt4);
        idproof=findViewById(R.id.edt5);
        regi=findViewById(R.id.button2);
        user_name=findViewById(R.id.edt6);
        password=findViewById(R.id.edt7);

        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = name.getText().toString();
                designation = desi.getText().toString();
                phone = num.getText().toString();
                mail = email.getText().toString();
                id = idproof.getText().toString();
                uname = user_name.getText().toString();
                pswd = password.getText().toString();


                if (username.equalsIgnoreCase("")) {
                    name.setError("enter the username");
                    name.setFocusable(true);
                } else if (designation.equalsIgnoreCase("")) {
                    desi.setError("enter the designation");
                    desi.setFocusable(true);
                } else if (phone.equalsIgnoreCase("")) {
                    num.setError("enter the number");
                    num.setFocusable(true);
                } else if (mail.equalsIgnoreCase("")) {
                    email.setError("enter the number");
                    email.setFocusable(true);
                } else if (id.equalsIgnoreCase("")) {
                    idproof.setError("enter the number");
                    idproof.setFocusable(true);
                } else if (uname.equalsIgnoreCase("")) {
                    user_name.setError("enter the number");
                    user_name.setFocusable(true);
                } else if (pswd.equalsIgnoreCase("")) {
                    password.setError("enter the number");
                    password.setFocusable(true);
                }
                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Register.this;
                    String q = "/authoreg?username=" + username + "&designation=" + designation + "&phone=" +phone + "&mail=" +mail + "&id=" +id+ "&uname=" +uname+ "&pswd=" +pswd;
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
                Toast.makeText(this, "Registration success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }

        } catch (Exception e) {

            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}