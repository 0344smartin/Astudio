package com.example.geophotosapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.exifinterface.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    EditText et1;
    ImageView imagen1;
    double latitude = 37.4219983;
    double longitude = -122.084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setContentView(R.layout.activity_main);
        imagen1=findViewById(R.id.imageView);
        et1=findViewById(R.id.editTextText);

        pedirPermiso(Manifest.permission.CAMERA,11);
        pedirPermiso(Manifest.permission.ACCESS_FINE_LOCATION, 12);
    }


    public void tomarFoto(View v) {
        Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Crea el archivo en el almacenamiento interno de la aplicación
        File foto = new File(getFilesDir(), et1.getText().toString());


        // Obtiene la URI usando FileProvider
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", foto);
        intento1.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intento1, REQUEST_IMAGE_CAPTURE);
    }

    public void recuperarFoto(View v) {
        Bitmap bitmap1 = BitmapFactory.decodeFile(getFilesDir() + "/" + et1.getText().toString());
        imagen1.setImageBitmap(bitmap1);
    }

    public void verFoto(View v) {
        Intent intento1=new Intent(this,ViewPhoto.class);
        startActivity(intento1);
    }
    private void pedirPermiso(String permiso, int codigoPermiso){
        // Verifica si el permiso ya ha sido concedido
        if (ContextCompat.checkSelfPermission(this, permiso)
                != PackageManager.PERMISSION_GRANTED) {
            // Si el permiso no ha sido concedido, solicítalo
            ActivityCompat.requestPermissions(this,
                    new String[]{permiso},
                    codigoPermiso);
        } else {
            // El permiso ya está concedido
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Obtén la ruta del archivo de la imagen
            File foto = new File(getFilesDir(), et1.getText().toString());
            // Asegúrate de tener las variables latitude y longitude
            try {
                ExifInterface exif = new ExifInterface(foto.getAbsolutePath());
                exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE,Double.toString(33));
                exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,Double.toString(42));
                exif.saveAttributes();
                System.out.println("Done!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}