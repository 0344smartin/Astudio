package com.example.sinresacaapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {

    private ImageView photo;
    private TextView name;
    private ListView ingredientsList;
    private ArrayList<Ingredient> ingredients;
    private ArrayAdapter<Ingredient> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        int idDrink = intent.getIntExtra("idDrink", 0);
        photo = findViewById(R.id.DrinkPhoto);
         name= findViewById(R.id.DrinkName);
        String imageUrl = "https://www.thecocktaildb.com/images/media/drink/vuquyv1468876052.jpg";
        ingredientsList = findViewById(R.id.ingredients);
        ingredients = new ArrayList<>();
        adapter = new ArrayAdapter<Ingredient>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, ingredients);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        ingredientsList.setAdapter(adapter);
        OkHttpClient client = new OkHttpClient();
        String apiUrl = "\n" +
                "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + idDrink;
        try {
            Request request = new Request.Builder().url(apiUrl).build();
            client.newCall(request).enqueue(new MyCallback());
        } catch (Exception ignored){
        }
    }

    private class MyCallback implements Callback {

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {

        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            assert response.body() != null;
            String jsonResponse = response.body().string();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(jsonResponse);
                JSONArray drinksArray = jsonObject.getJSONArray("drinks");
                JSONObject drink = drinksArray.getJSONObject(0);
                String drinkName = drink.getString("strDrink");
                int drinkId = drink.getInt("idDrink");
                String drinkPhoto = drink.getString("strDrinkThumb");
                for (int i =1; i <=15 ; i++){
                    String ingName = drink.getString("strIngredient" + i);
                    String ingAmount = drink.getString("strMeasure" + i);
                    if (ingName.equalsIgnoreCase("null")){
                        continue;
                    }
                    ingredients.add(new Ingredient(ingName, ingAmount));
                }

                runOnUiThread(() -> {
                        updatePhoto(drinkPhoto);
                        updateName(drinkName);
                        updateIngredients();
                });
                Cocktail cocktail = new Cocktail(drinkName, drinkId);
                System.out.println(cocktail);
            } catch (Exception ignored) {

            }
        }
    }

    private void updateIngredients() {
        adapter.notifyDataSetChanged();
    }

    private void updateName(String drinkName) {
        name.setText(drinkName);
    }

    private void updatePhoto(String url) {
        Glide.with(this).load(url).into(photo);
    }
}