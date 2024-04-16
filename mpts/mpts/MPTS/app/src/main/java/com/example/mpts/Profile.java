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
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Profile extends AppCompatActivity implements JsonResponse{

    ListView l1;
    Button edit;
    SharedPreferences sh;
    String [] authority_id,login_id,name,desigination,phone,email,id_proof;
    String[] value;
    @SuppressLint("MissingInflatedId")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        l1=findViewById(R.id.view);
        edit=findViewById(R.id.edit);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq JR = new JsonReq();
        JR.json_response=(JsonResponse)Profile.this;
//        Toast.makeText(this, "iuytlid" +sh.getString("log_id","") , Toast.LENGTH_SHORT).show();
        String q = "/authoprofile?lid="+sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Edit.class));
            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
//            Toast.makeText(this, "aaaaaaaaa", Toast.LENGTH_SHORT).show();

            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja = (JSONArray) jo.getJSONArray("data");

                authority_id = new String[ja.length()];
                login_id = new String[ja.length()];
                name = new String[ja.length()];
                desigination = new String[ja.length()];
                phone = new String[ja.length()];
                email = new String[ja.length()];
                id_proof = new String[ja.length()];
                value = new String[ja.length()];

                for (int i = 0; i < ja.length(); i++) {
                    authority_id[i] = ja.getJSONObject(i).getString("authority_id");
                    login_id[i] = ja.getJSONObject(i).getString("login_id");
                    name[i] = ja.getJSONObject(i).getString("name");
                    desigination[i] = ja.getJSONObject(i).getString("designation");
                    phone[i] = ja.getJSONObject(i).getString("phone");
                    email[i] = ja.getJSONObject(i).getString("email");
                    id_proof[i] = ja.getJSONObject(i).getString("id_proof");

                    value[i] = "Authority_Id: " + authority_id[i] + "\n Login_id:" + login_id[i] + "\n Name:" + name[i] + "\n Desigination:" + desigination[i] + "\n Phone:" + phone[i] + "\n Email:" + email[i] + "\n Id_proof:" + id_proof[i];


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