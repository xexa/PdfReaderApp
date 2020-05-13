package com.example.pdfreaderapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1;
    private RecyclerView recyclerView;
    private PdfAdapter pdfAdapter;
    public static ArrayList<File> mFiles = new ArrayList<>();
    private File folder;
    private String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        permission();


    }

    private void permission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "Please grant permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        } else {
            Toast.makeText(this, "Granted", Toast.LENGTH_SHORT).show();
            initView();
        }
    }

    private void initView() {
        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        mFiles = getPdfFiles(folder);

        ArrayList<File> myPdf = getPdfFiles(Environment.getExternalStorageDirectory());

        items = new String[myPdf.size()];
        for (int i = 0 ; i < items.length ; i++){
            items[i] = myPdf.get(i).getName().replace(".pdf", "");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pdfAdapter = new PdfAdapter(this, mFiles,items);
        recyclerView.setAdapter(pdfAdapter);
    }

    private ArrayList<File> getPdfFiles(File folder) {
        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = folder.listFiles();

        if (files != null){
            for (File currentFile : files){
                if (currentFile.isDirectory() && !currentFile.isHidden()){
                    arrayList.addAll(getPdfFiles(currentFile));
                }else {
                    if (currentFile.getName().endsWith(".pdf")){
                        arrayList.add(currentFile);
                    }
                }
            }
        }

        return arrayList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                initView();
            }else {
                Toast.makeText(this, "Please grant permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
