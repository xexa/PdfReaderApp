package com.example.pdfreaderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MyViewholder> {


    private Context context;
    private ArrayList<File> files;
    private String[] items;

    public PdfAdapter(Context context, ArrayList<File> files, String[] items) {
        this.context = context;
        this.files = files;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pdf_item,parent,false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {

        holder.fileName.setText(items[position]);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PdfViewActivity.class);
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView fileName;
        private RelativeLayout relativeLayout;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            imageView= itemView.findViewById(R.id.pdf_img);
            fileName = itemView.findViewById(R.id.pdf_file_name);
            relativeLayout = itemView.findViewById(R.id.pdf_item);
        }
    }
}
