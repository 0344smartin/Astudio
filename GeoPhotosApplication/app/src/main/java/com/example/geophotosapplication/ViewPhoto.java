package com.example.geophotosapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ViewPhoto extends AppCompatActivity {


    private ListView lv1;
    private ImageView iv1;
    private String[] archivos;
    private ArrayAdapter<String> adaptador1;
    private  File f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);


        File dir = getFilesDir();
        archivos = dir.list();
        adaptador1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, archivos);
        lv1 = (ListView) findViewById(R.id.listView);
        lv1.setAdapter(adaptador1);
        iv1 = (ImageView) findViewById(R.id.imageView2);


        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                f = new File(getFilesDir(),archivos[arg2]);
                Bitmap bitmap1 = BitmapFactory.decodeFile(f.getPath());
                iv1.setImageBitmap(bitmap1);
            }
        });
    }
     public void abrirEnlace(View v){
         ExifInterface exif = null;
         try {
             exif = new ExifInterface(f.getPath());
             String bad = Double.toString(0.0);
             String lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
             String lon = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
             double latitud = Double.parseDouble(lat);
             double longitud = Double.parseDouble(lon);

             String uri = String.format("geo:%f,%f?q=%f,%f(Ubicación de %s)", latitud, longitud,latitud,longitud,f.getName());
             Uri gmmIntentUri= Uri.parse(uri);
             Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
             startActivity(intent);
         } catch (IOException e) {
             throw new RuntimeException(e);
         }
     }
}