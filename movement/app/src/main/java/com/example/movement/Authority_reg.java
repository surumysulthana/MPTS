package com.example.movement;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Authority_reg extends AppCompatActivity implements JsonResponse{

    EditText e1,e2,e3,e4,e5,e6,e7,e8;
    Button b1;
    String fname,lname,email,place,phone,uname,pass,age;

    private static final int PICK_PDF_REQUEST = 1;

    private Button uploadButton;
    private TextView pdfTextView;
    private ImageView uploadImageView;

    private String loginId;
    private SharedPreferences sharedPreferences;
    private int flag = 0;
    private byte[] pdfByteArray;
    private static String viewPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_reg);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        uploadImageView = findViewById(R.id.imgupload);

        e1=findViewById(R.id.editTextText);

        e3=findViewById(R.id.editTextText3);
        e4=findViewById(R.id.editTextTextEmailAddress);

        e6=findViewById(R.id.editTextPhone);
        e7=findViewById(R.id.editTextText4);
        e8=findViewById(R.id.editTextTextPassword);
        b1=findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname=e1.getText().toString();

                place=e3.getText().toString();
                email=e4.getText().toString();

                phone=e6.getText().toString();
                uname=e7.getText().toString();
                pass=e8.getText().toString();


                uploadPdf();

            }
        });
        uploadImageView.setOnClickListener(arg0 -> {
            flag = 1;
            selectPdfOption();
        });
    }
    private void openPdfFile() {
        if (viewPdf != null && !viewPdf.isEmpty()) {
            File file = new File(IPSetting.ip+"/" + viewPdf);
//            File file = new File(getFilesDir(), viewPdf); // Assuming 'viewPdf' is the file name
            Toast.makeText(getApplicationContext(), "PDF Path is empty"+file, Toast.LENGTH_SHORT).show();
            openFile(file);
        } else {
            Toast.makeText(getApplicationContext(), "PDF Path is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadPdf() {
        try {
//            String uploadUrl = "http://" + IPSetting.ip + "/api/upload_pdf";
            String uploadUrl = "http://" + IPSetting.ip + "/autho_reg" ;

            Map<String, byte[]> params = new HashMap<>();
            params.put("pdf", pdfByteArray); // Change "pdf" to the appropriate key
            params.put("fname", fname.getBytes());
            params.put("place", place.getBytes());
            params.put("email", email.getBytes());
            params.put("phone", phone.getBytes());
            params.put("uname", uname.getBytes());
            params.put("pass", pass.getBytes());

            FileUploadAsync fileUploadAsync = new FileUploadAsync(uploadUrl);
            fileUploadAsync.json_response = Authority_reg.this;
            fileUploadAsync.execute(params);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdfOption() {
        final CharSequence[] items = {"Upload PDF", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Authority_reg.this);
        builder.setTitle("Select PDF");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Upload PDF")) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri pdfUri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(pdfUri);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                inputStream.close();

                if (flag == 1) {
                    pdfByteArray = bos.toByteArray();
                    Toast.makeText(getApplicationContext(), "PDF Selected", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error selecting PDF", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void response(JSONObject jo) {
        try {
//            String method = jo.getString("method");
//
//            if (method.equalsIgnoreCase("upload_pdf")) {
//                handleUploadPdfResponse(jo);
//            } else if (method.equalsIgnoreCase("view_pdf")) {
//                handleViewPdfResponse(jo);
//            }
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success")){
                Toast.makeText(this, "REGISTRATION SUCCESS", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error in response: " + e, Toast.LENGTH_LONG).show();
        }
    }

    private void handleUploadPdfResponse(JSONObject jo) {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "PDF Uploaded Successfully", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Userhome.class));
            } else {
                Toast.makeText(getApplicationContext(), "Failed to upload PDF", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error handling upload response: " + e, Toast.LENGTH_LONG).show();
        }
    }

    private void handleViewPdfResponse(JSONObject jo) {
        try {
            String status = jo.getString("status");
            if (status.equalsIgnoreCase("success")) {
                viewPdf = jo.getString("data");
                pdfTextView.setText(viewPdf);
                Toast.makeText(getApplicationContext(), "PDF Loaded Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Failed to Load PDF", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error handling view PDF response: " + e, Toast.LENGTH_LONG).show();
        }
    }

    public void openFile(File file) {

        Uri uri = Uri.fromFile(file);
        Uri urii= Uri.parse("192.168.29.105:5044/static/resume/78fee65f-e55b-4db6-8db1-0f843bb6b37eabc.pdf");
        Log.d("urii--", String.valueOf(urii));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(urii);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No PDF viewer available", Toast.LENGTH_SHORT).show();
        }
    }

    private void setIntentDataTypeBasedOnFileExtension(Intent intent, Uri uri) {
        String fileExtension = getFileExtension(uri.getPath());
        Log.d("fileExtension",fileExtension);
        Log.d("uriii", String.valueOf(uri));
        Uri urii= Uri.parse("192.168.29.105:5044/static/resume/78fee65f-e55b-4db6-8db1-0f843bb6b37eabc.pdf");
        Log.d("urii--", String.valueOf(urii));
        switch (fileExtension) {
            case "doc":
            case "docx":
                intent.setDataAndType(uri, "application/msword");
                break;
            case "pdf":
//                intent.setDataAndType(uri, "application/pdf");
                intent.setDataAndType(urii, "application/pdf");
                break;
            case "ppt":
            case "pptx":
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
                break;
            case "xls":
            case "xlsx":
                intent.setDataAndType(uri, "application/vnd.ms-excel");
                break;
            case "zip":
            case "rar":
                intent.setDataAndType(uri, "application/x-wav");
                break;
            case "rtf":
                intent.setDataAndType(uri, "application/rtf");
                break;
            case "wav":
            case "mp3":
                intent.setDataAndType(uri, "audio/x-wav");
                break;
            case "gif":
                intent.setDataAndType(uri, "image/gif");
                break;
            case "jpg":
            case "jpeg":
            case "png":
                intent.setDataAndType(uri, "image/jpeg");
                break;
            case "txt":
                intent.setDataAndType(uri, "text/plain");
                break;
            case "3gp":
            case "mpg":
            case "mpeg":
            case "mpe":
            case "mp4":
            case "avi":
                intent.setDataAndType(uri, "video/*");
                break;
            default:
                intent.setDataAndType(uri, "*/*");
                break;
        }
    }

    private String getFileExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1);
    }



}