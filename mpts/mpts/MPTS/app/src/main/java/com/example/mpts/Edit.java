package com.example.mpts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Edit extends AppCompatActivity implements JsonResponse {
    EditText name,desi,num,email,idproof;
    Button edit;
    SharedPreferences sh;
    String username,designation,phone,mail,id;
    String[] value;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        name=findViewById(R.id.edt1);
        desi=findViewById(R.id.edt2);
        num=findViewById(R.id.edt3);
        email=findViewById(R.id.edt4);
        idproof=findViewById(R.id.edt5);
        edit=findViewById(R.id.button2);


        com.example.mpts.JsonReq JR =new com.example.mpts.JsonReq();
        JR.json_response=(JsonResponse)Edit.this;
//        Toast.makeText(this, "iuytlid" +sh.getString("log_id","") , Toast.LENGTH_SHORT).show();
        String q = "/editprofile?lid="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = name.getText().toString();
                designation = desi.getText().toString();
                phone = num.getText().toString();
                mail = email.getText().toString();
                id = idproof.getText().toString();


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
                }
                else {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Edit.this;
                    String q = "/update?lid="+sh.getString("log_id","")+"&username=" + username + "&designation=" + designation + "&phone=" +phone + "&mail=" +mail + "&id=" +id ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);


                }



            }
        });
        };

    @Override
    public void response(JSONObject jo) {
        try {
//            Toast.makeText(this, "aaaaaaaaa", Toast.LENGTH_SHORT).show();

            String status = jo.getString("status");
            Log.d("pearl", status);
            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");



                value = new String[ja.length()];


                username = ja.getJSONObject(0).getString("name");
                designation = ja.getJSONObject(0).getString("designation");
                phone=ja.getJSONObject(0).getString("phone");
                mail = ja.getJSONObject(0).getString("email");
                id = ja.getJSONObject(0).getString("id_proof");
                name.setText(username);
                desi.setText(designation);
                num.setText(phone);
                email.setText(mail);
                idproof.setText(id);







            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
