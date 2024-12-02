package com.example.agendaapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    final private String agenda = "Wololo";
    ArrayList<String> contactos = new ArrayList<>();
    EditText name,phone;
    ArrayAdapter<String> adapter;
    ListView lv1;
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
        lv1 = findViewById(R.id.agenda);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        adapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contactos);
        lv1.setAdapter(adapter);
        leeSharedPreferences();
    }

    private void leeSharedPreferences(){
        Map<String,?> data = getSharedPreferences(agenda, Context.MODE_PRIVATE).getAll();
        contactos.clear();
        for (Map.Entry<String,?> e : data.entrySet()) {
            contactos.add(String.format("%s: %s",e.getKey(),e.getValue()));
        }
    }
    public void agregar(View v) {
        SharedPreferences.Editor editor = getSharedPreferences(agenda, Context.MODE_PRIVATE).edit();
        editor.putString(name.getText().toString(),phone.getText().toString());
        editor.commit();
        leeSharedPreferences();
    }
}