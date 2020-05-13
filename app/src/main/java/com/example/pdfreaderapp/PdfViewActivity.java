package com.example.pdfreaderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class PdfViewActivity extends AppCompatActivity {

    private PDFView pdfView;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);

        pdfView = findViewById(R.id.pdf_view);

        position = getIntent().getIntExtra("position", -1);

        viewPdf();

    }

    private void viewPdf() {
        pdfView.fromFile(MainActivity.mFiles.get(position))
                .enableSwipe(true)
                .enableAnnotationRendering(true)
                .enableDoubletap(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
}
