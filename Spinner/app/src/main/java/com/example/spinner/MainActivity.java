package com.example.spinner;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<CharSequence> aaOpcionesA;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        aaOpcionesA =  ArrayAdapter. createFromResource(getApplicationContext(),
                R.array. opcionesA,
                androidx.appcompat.R.layout. support_simple_spinner_dropdown_item);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    Spinner s = (Spinner) findViewById(R.id.Spinner);
    s.setAdapter(aaOpcionesA);
    s.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tvA = (TextView) findViewById(R.id. tvSeleccionadoA);
                String texto = "Seleccionado: " + parent.getSelectedItem().toString();
                tvA.setText(texto);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                TextView tvA = (TextView) findViewById(R.id. tvSeleccionadoA);
                tvA.setText("");
            }
    });
    }
}