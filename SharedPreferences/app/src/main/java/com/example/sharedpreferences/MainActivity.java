package com.example.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private EditText keyet;
    private EditText data;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        keyet = findViewById(R.id.key);
        data = findViewById(R.id.Data);
        result = findViewById(R.id.result);
    }

    public void grabar(View v) {
        SharedPreferences pref = getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String key = String.valueOf(keyet.getText());
        String value = String.valueOf(data.getText());
        editor.putString(key,value);
        editor.apply();
        data.setText("");
        result.setText("Guardado correctamente");
    }

    public void recuperar(View v) {
        SharedPreferences pref = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String key = String.valueOf(keyet.getText());
        String value = pref.getString(key,null);
        if (value == null || value.isBlank()) {
            result.setText("No se encontraron datos");
            data.setText("");
            return;
        }
        data.setText(value);
        result.setText("Cargado correctamente");
    }
}