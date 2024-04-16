package com.example.movement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Autho_profile extends AppCompatActivity implements  JsonResponse {

    SharedPreferences sh;
    ListView l1;
    String [] name,desig,phone,email,id_proof,value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autho_profile);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=findViewById(R.id.list);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Autho_profile.this;
        String q = "/authoprofile?id=" + sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }


    @Override
    public void response(JSONObject jo) throws JSONException {
        try{
            String method=jo.getString("method");

            if(method.equalsIgnoreCase("view")){
                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");
                    Log.d("pearl", method);

                    name = new String[ja1.length()];
                    desig = new String[ja1.length()];
                    phone = new String[ja1.length()];
                    email = new String[ja1.length()];
                    id_proof = new String[ja1.length()];

                    value=new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        name[i] = ja1.getJSONObject(i).getString("name");
                        desig[i] = ja1.getJSONObject(i).getString("designation");
                        phone[i] = ja1.getJSONObject(i).getString("phone");
                        email[i] = ja1.getJSONObject(i).getString("email");
                        id_proof[i] = ja1.getJSONObject(i).getString("id_proof");


                        value[i] ="Name :" + name[i] + "\nDesignation :" + desig[i] + "\nPhone :" + phone[i]+ "\nEmail :" + email[i]+ "\nID Proof :" + id_proof[i];
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
