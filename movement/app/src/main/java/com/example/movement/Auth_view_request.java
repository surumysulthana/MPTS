package com.example.movement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Auth_view_request extends AppCompatActivity implements JsonResponse{

    SharedPreferences sh;
    ListView l1;
    String [] perid,route,comp,reply,value,titl,title,reason,f_date,f_time,f_location,t_location,qr,face,date,stat;
    ImageButton img1;
    Button b1;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;


    public static String encodedImage = "", path = "";
    byte[] byteArray = null;
    private Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_view_request);
        startService(new Intent(getApplicationContext(),LocationService.class));

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        l1=findViewById(R.id.list);
        img1=findViewById(R.id.imageButton2);
        b1=findViewById(R.id.button11);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageOption();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAttach();
            }
        });


        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Auth_view_request.this;
        String q = "/auth_view_request?id=" +Scan_qrcode.scan;
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
                    perid = new String[ja1.length()];
                    route = new String[ja1.length()];
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
                    value=new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        perid[i] = ja1.getJSONObject(i).getString("permit_request_id");
                        route[i] = ja1.getJSONObject(i).getString("route");
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

                        value[i] ="Title :" + title[i] + "\nReason :" + reason[i]+"\nFrom Date :"+f_date[i]+"\nFrom Location :"+f_location[i]+"\nTo Location :"+t_location[i]+"\nFrom Time :"+f_time[i]+"" + "\nDate :" + date[i]+ "\nRoute :" + route[i];
                    }
//                    ArrayAdapter<String> ar = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
//                    l1.setAdapter(ar);
                    Coustomize_auth_request p1= new Coustomize_auth_request(Auth_view_request.this, perid,title, reason, f_date,f_location,t_location,f_time,date,route);
                    l1.setAdapter(p1);
//                    l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            JsonReq JR = new JsonReq();
//                            JR.json_response = (JsonResponse) Auth_view_request.this;
//                            String q = "/checklocation?curplace="+LocationService.place + "&perid="+perid[position];
//                            q = q.replace(" ", "%20");
//                            JR.execute(q);
//                        }
//                    });
                }

            }if(method.equalsIgnoreCase("check")){
                String status=jo.getString("status");
                if(status.equalsIgnoreCase("success")){
                    String data=jo.getString("data");
                    if(data.equalsIgnoreCase("matching")){
                        Toast.makeText(this, "Matching", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Not Matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }if (method.equalsIgnoreCase("within")){
                String status=jo.getString("status");
                if (status.equalsIgnoreCase("sucess")){
                    String data=jo.getString("data");
                    if (data.equalsIgnoreCase("Inside the permitted route within 30 kilometers.")){
//                        Toast.makeText(this, "Inside the permitted route within 30 kilometers.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),Correctone.class));

                    }else {
//                        Toast.makeText(this, "Outside the permitted route or beyond 30 kilometers.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));

                    }
                }
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendAttach() {

        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//	            String uid = sh.getString("uid", "");




//                String q = "http://" + IpSetting.ip + "/smart city/apis.php";
            String q = "http://" +sh.getString("ip","")+"/auth_send_pic";
            Log.d("hhttpp",q);



            Map<String, byte[]> aa = new HashMap<>();

            aa.put("image", byteArray);
//            Toast.makeText(getApplicationContext(), "byteArray"+byteArray, Toast.LENGTH_LONG).show();

            aa.put("id",Scan_qrcode.scan.getBytes());
//            aa.put("title",title.getBytes());
//            aa.put("reason",reason.getBytes());
//            aa.put("f_date",f_date.getBytes());
//            aa.put("f_time",f_time.getBytes());
//            aa.put("f_location",f_location.getBytes());
//            aa.put("t_location",t_location.getBytes());

            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Auth_view_request.this;
            fua.execute(aa);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }
    private void selectImageOption() {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Auth_view_request.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
//               CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
                path = FileUtils.getPath(this, uri);
                File fl = new File(path);
                int ln = (int) fl.length();


                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {

                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                img1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();w
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "kkkkkk"+e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                img1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}