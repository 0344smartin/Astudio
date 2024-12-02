package com.example.httpapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private OkHttpClient client;
    private TextView error, email, name,telf;
    private EditText id;

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
        client = new OkHttpClient();
        error = (TextView) findViewById(R.id.tv_error);
        email = (TextView) findViewById(R.id.tv_email);
        name = (TextView) findViewById(R.id.tv_name);
        telf = (TextView) findViewById(R.id.tv_telf);
        id = (EditText) findViewById(R.id.et_id);

    }

    public void makeRequest(View v) {
        String request_url = "https://api-generator.retool.com/OSQatS/data/" + id.getText();
        Request request= new Request.Builder().url(request_url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                error.setText("Error en la petición HTTP");
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        // Convertir el string JSON en un objeto JSONObject
                        JSONObject jsonObject = new JSONObject(responseBody);
                        // Extraer los campos del JSON
                        int id = jsonObject.getInt("id");
                        String nombre_usuario = jsonObject.getString("Column 1");
                        String telefono_usuario = jsonObject.getString("Column 2");
                        String correo_usuario = jsonObject.getString("Column 3");


                        runOnUiThread(() -> {
                            error.setText("");
                            name.setText(nombre_usuario);
                            telf.setText(telefono_usuario);
                            email.setText(correo_usuario);
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }}});
    }
}