package com.example.movement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

public class Scan_qrcode extends AppCompatActivity implements JsonResponse{

    private SurfaceView cameraPreview;
    public static String scan,longi,lati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);
//        cameraPreview = findViewById(R.id.camera_preview);
//
//        // Set the desired size for the camera preview
//        int desiredWidth = 500;  // Set your desired width
//        int desiredHeight = 500; // Set your desired height
//
//        // Adjust the layout parameters of the SurfaceView
//        cameraPreview.getLayoutParams().width = desiredWidth;
//        cameraPreview.getLayoutParams().height = desiredHeight;

        // Initialize the QR code scanner
        IntentIntegrator integrator = new IntentIntegrator(this);
//        integrator.setCaptureActivity(Scan_qrcode.class);
        integrator.setPrompt("Scan a QR Code");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();

    }

    // onActivityResult method should be defined outside of onCreate

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getApplicationContext(), "no value", Toast.LENGTH_SHORT).show();
            } else {
                String scannedData = result.getContents();
                scan=result.getContents();
                // Use the scanned data as needed

//                Toast.makeText(getApplicationContext(), "result: " + scannedData, Toast.LENGTH_SHORT).show();

                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) Scan_qrcode.this;
                String q = "/scan?id=" + scannedData ;
                q = q.replace(" ", "%20");
                JR.execute(q);

            }
        }
    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                lati=jo.getString("lati");
                longi=jo.getString("longi");
                startActivity(new Intent(getApplicationContext(), Auth_view_request.class));


            }else {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Auth_home.class));
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
