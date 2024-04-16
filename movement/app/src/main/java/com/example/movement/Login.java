 package com.example.movement;

 import android.app.AlertDialog;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;
 import android.preference.PreferenceManager;
 import android.text.method.HideReturnsTransformationMethod;
 import android.text.method.PasswordTransformationMethod;
 import android.util.Log;
 import android.view.View;
 import android.widget.Button;
 import android.widget.CheckBox;
 import android.widget.CompoundButton;
 import android.widget.EditText;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AppCompatActivity;

 import org.json.JSONArray;
 import org.json.JSONObject;

 public class Login extends AppCompatActivity implements JsonResponse {

     EditText e1,e2;
     Button b1,b2,b3;
     public static String username,password,bbb,logid,usertype;
     SharedPreferences sh;
     TextView t1;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
         sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

         e1=(EditText) findViewById(R.id.etunm  );
         e2=(EditText) findViewById(R.id.etpass);

         b1=(Button) findViewById(R.id.loginbtn);
         t1=findViewById(R.id.textview4);


       t1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showRegistrationOptions();
    }

           private void showRegistrationOptions() {

               final CharSequence[] items = {"User Registration", "Authority Registration", "Cancel"};

               AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
               builder.setTitle("Select Registration");
               builder.setItems(items, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int item) {
                       if (items[item].equals("User Registration")) {
                           startActivity(new Intent(getApplicationContext(),User_reg.class));
                       } else if (items[item].equals("Authority Registration")) {
                           startActivity(new Intent(getApplicationContext(),Authority_reg.class));

                       } else if (items[item].equals("Cancel")) {
                           dialog.dismiss();
                       }
                   }
               });
               builder.show();
           }
       });



//         CheckBox showPasswordCheckbox = findViewById(R.id.show_password_checkbox);
//         showPasswordCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//             @Override
//             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                 if (isChecked) {
//                     e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                 } else {
//                     e2.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                 }
//             }
//         });

         b1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 username = e1.getText().toString();
                 password = e2.getText().toString();
                 if (username.equalsIgnoreCase("")) {
                     e1.setError("Enter Your Username");
                     e1.setFocusable(true);
                 } else if (password.equalsIgnoreCase("")) {
                     e2.setError("Enter Your Password");
                     e2.setFocusable(true);
                 } else {

                     JsonReq JR = new JsonReq();
                     JR.json_response = (JsonResponse) Login.this;
                     String q = "/autholog?username=" + username + "&password=" + password;
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
             Log.d("pearl", status);

             if (status.equalsIgnoreCase("success")) {
                 JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                 logid = ja1.getJSONObject(0).getString("login_id");
                 usertype = ja1.getJSONObject(0).getString("usertype");

                 SharedPreferences.Editor e = sh.edit();
                 e.putString("log_id", logid);
                 e.putString("usertype", usertype);
                 e.commit();

                 if (usertype.equals("user")) {
                     Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), Userhome.class));
                 }
                 else if(usertype.equals("Authority")){
                     startActivity(new Intent(getApplicationContext(), Auth_home.class));


                 }
             }
             else {
                     Toast.makeText(getApplicationContext(), "Login failed invalid username and password", Toast.LENGTH_LONG).show();
             }
         }
          catch (Exception e) {
             // TODO: handle exception

             Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
         }
     }

 }