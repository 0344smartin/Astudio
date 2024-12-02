package com.example.myagenda;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Asset> assets = new ArrayList<>();
    private TextView id,descripcion, precio;
    private ArrayAdapter arrayAdapter;

    MySQLiteManager manager = new MySQLiteManager(this, "administracion", null, 1);

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
        ListView listAssets = (ListView)findViewById(R.id.listAssets);
        precio = findViewById(R.id.precio);
        descripcion = findViewById(R.id.desc);
        id = findViewById(R.id.id);
        manager.getAll(assets);
        // Set the adapter for the list view
        arrayAdapter = new ArrayAdapter<Asset>(this,  android.R.layout.simple_list_item_1, assets);
        listAssets.setAdapter(arrayAdapter);

        listAssets.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view,
                            int index, long id)
                    {
                        TextView tv = (TextView)view;
                        Asset atd = assets.get(index);
                        manager.delete(atd);
                        Toast
                                .makeText(getApplicationContext(),
                                        "Deleted item "
                                                + atd.getDescripcion(),
                                        Toast.LENGTH_SHORT)
                                .show();
                        updateAssets();
                        return true;
                    }
                });
        updateAssets();
    }

    private void updateAssets() {
        arrayAdapter.clear();
        manager.getAll(assets);

        arrayAdapter.notifyDataSetChanged();
    }

    public void insertar(View v) {
        Asset a = new Asset();
        a.setDescripcion(descripcion.getText().toString());
        a.setId(id.getText().toString());
        a.setPrecio(precio.getText().toString());
    if (manager.add(a)) {
        Toast.makeText(this, "Se cargaron los datos del artículo",
                Toast.LENGTH_SHORT).show();
        updateAssets();
    } else {
        Toast.makeText(this, "Error al crear el artículo", Toast.LENGTH_SHORT).show();
    }}

}