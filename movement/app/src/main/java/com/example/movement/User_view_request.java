package com.example.movement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User_view_request extends AppCompatActivity implements JsonResponse{

    SharedPreferences sh;
    ListView l1;
    String [] comp,reply,value,titl,title,reason,f_date,f_time,f_location,t_location,qr,face,date,stat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_request);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=findViewById(R.id.list);

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) User_view_request.this;
        String q = "/user_view_request?id=" + sh.getString("log_id","");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String method = jo.getString("method");

            if (method.equalsIgnoreCase("view")) {
                String status = jo.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");
                    Log.d("pearl", method);

                    title = new String[ja1.length()];
                    reason = new String[ja1.length()];
                    f_date = new String[ja1.length()];
                    f_time = new String[ja1.length()];
                    f_location = new String[ja1.length()];
                    t_location = new String[ja1.length()];
                    qr = new String[ja1.length()];
                    face = new String[ja1.length()];
                    stat = new String[ja1.length()];
                    date = new String[ja1.length()];
                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        title[i] = ja1.getJSONObject(i).getString("title");
                        reason[i] = ja1.getJSONObject(i).getString("reason");
                        f_date[i] = ja1.getJSONObject(i).getString("from_date");
                        f_time[i] = ja1.getJSONObject(i).getString("from_time");
                        f_location[i] = ja1.getJSONObject(i).getString("from_location");
                        t_location[i] = ja1.getJSONObject(i).getString("to_location");
                        qr[i] = ja1.getJSONObject(i).getString("qr_code");
                        face[i] = ja1.getJSONObject(i).getString("face_image");
                        stat[i] = ja1.getJSONObject(i).getString("status");
                        date[i] = ja1.getJSONObject(i).getString("date");

                        value[i] = "Title :" + title[i] + "\nReason :" + reason[i] + "\nDate :" + date[i];
                    }

                    customize_user_req p1 = new customize_user_req(User_view_request.this, title, reason, f_date, f_time, f_location, t_location, qr, stat);
                    l1.setAdapter(p1);
                } else {
                    // If status is not success, show "Your request is pending" message
                    TextView pendingText = findViewById(R.id.pending_text);
                    pendingText.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}