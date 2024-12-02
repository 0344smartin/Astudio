package com.example.sinresacaapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<Cocktail> cocktails;
    String jsonResponse;
    ListView list;
    ArrayAdapter<Cocktail> adapter;
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
        list = findViewById(R.id.list);
        cocktails = new ArrayList<>();
        adapter = new ArrayAdapter<Cocktail>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, cocktails);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?a=Non_Alcoholic";
        try {
            Request request= new Request.Builder().url(apiUrl).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {

                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    assert response.body() != null;
                    jsonResponse = response.body().string();
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(jsonResponse);
                        JSONArray drinksArray = jsonObject.getJSONArray("drinks");
                        for (int i = 0; i < drinksArray.length(); i++) {
                            JSONObject drink = drinksArray.getJSONObject(i);
                            String drinkName = drink.getString("strDrink");
                            int drinkId = drink.getInt("idDrink");
                            cocktails.add(new Cocktail(drinkName, drinkId));
                        }
                        runOnUiThread(() -> {
                            adapter.notifyDataSetChanged();
                        });
                    } catch(Exception ignored){

                    }
                }
            });

        } catch (Exception ignored){}
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item from the ArrayList using the position
                Cocktail selectedCocktail = cocktails.get(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                int idDrink  = selectedCocktail.getIdDrink();
                intent.putExtra("idDrink", idDrink);
                startActivity(intent);
            }
        });
    }
}