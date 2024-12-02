package com.example.ejerciciolistviews;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private void updateOrder(){
        if (dish == null || ingredient == null){
            return;
        }
        TextView order = findViewById(R.id.Order);
        order.setText(String.format("Ha pedido %s con %s.",dish, ingredient));
    }

    private String dish, ingredient;

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

        ListView lvFood = findViewById(R.id.LVFood);
        ArrayAdapter<CharSequence> aaFood = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.comidas,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        lvFood.setAdapter(aaFood);
        lvFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvf = findViewById(R.id.TVFood);
                dish = adapterView.getItemAtPosition(i).toString();
                String texto = "Seleccionado: "+ dish;
                tvf.setText(texto);
                updateOrder();
            }
        });
        ListView lvIngredient = findViewById(R.id.LVingredient);
        ArrayAdapter<CharSequence> aaIngredient = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.ingredientes,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        lvIngredient.setAdapter(aaIngredient);
        lvIngredient.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvi = findViewById(R.id.TVIngredient);
                ingredient = adapterView.getItemAtPosition(i).toString();
                String texto = "Seleccionado: "+ ingredient;
                tvi.setText(texto);
updateOrder();
            }
        });
    }
}