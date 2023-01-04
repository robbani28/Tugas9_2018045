package com.example.recycleview.restapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.recycleview.FoodActivity;
import com.example.recycleview.FavoriteActivity;
import com.example.recycleview.MainActivity;
import com.example.recycleview.ProfileActivity;
import com.example.recycleview.R;
import com.example.recycleview.databinding.ActivityMealBinding;
import com.example.recycleview.sqlite.DisplayData;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MealActivity extends AppCompatActivity implements View.OnClickListener {

  //declaration variable
  private ActivityMealBinding binding;
  private DrawerLayout drawer;
  String index;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setup view binding
    binding = ActivityMealBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    binding.fetchButton.setOnClickListener(this);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    drawer = findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new
            ActionBarDrawerToggle(this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
    drawer.addDrawerListener(toggle);
    toggle.syncState();
    NavigationView navigationView = findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
          case R.id.nav_alarm:
            Intent intent = new Intent(MealActivity.this, MainActivity.class);
            startActivity(intent);
            break;
          case R.id.nav_food:
            Intent i = new Intent(MealActivity.this, FoodActivity.class);
            startActivity(i);
            break;
          case R.id.nav_favorite:
            Intent j = new Intent(MealActivity.this, FavoriteActivity.class);
            startActivity(j);
            break;
          case R.id.nav_profile:
            Intent k = new Intent(MealActivity.this, ProfileActivity.class);
            startActivity(k);
            break;
          case R.id.nav_sql:
            Intent l = new Intent(MealActivity.this, DisplayData.class);
            startActivity(l);
            break;
          case R.id.nav_meal:
            Intent m = new Intent(MealActivity.this, MealActivity.class);
            startActivity(m);
            break;
        }
        return true;
      }
    });
  }

  //onclik button fetch
  @Override
  public void onClick(View v) {
    if (v.getId() == R.id.fetch_button) {
      index = binding.inputId.getText().toString();
      try {
        getData();
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }
  }

  //get data using api link
  public void getData() throws MalformedURLException {
    Uri uri = Uri.parse("https://www.themealdb.com/api/json/v1/1/search.php?f=m")
            .buildUpon().build();
    URL url = new URL(uri.toString());
    new DOTask().execute(url);
  }

  class DOTask extends AsyncTask<URL, Void, String> {
    //connection request
    @Override
    protected String doInBackground(URL... urls) {
      URL url = urls[0];
      String data = null;
      try {
        data = NetworkUtils.makeHTTPRequest(url);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return data;
    }

    @Override
    protected void onPostExecute(String s) {
      try {
        parseJson(s);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    //get data json
    public void parseJson(String data) throws JSONException {
      JSONObject jsonObject = null;
      try {
        jsonObject = new JSONObject(data);
      } catch (JSONException e) {
        e.printStackTrace();
      }
      JSONArray jsonArray = jsonObject.getJSONArray("meals");
      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
        String idMeal = jsonObject1.get("idMeal").toString();

        if (idMeal.equals(index)) {
          String id = jsonObject1.getString("idMeal");
          String name = jsonObject1.getString("strMeal");
          String category = jsonObject1.getString("strCategory");
          String area = jsonObject1.getString("strArea");
          String image = jsonObject1.getString("strMealThumb");
          String youtube = jsonObject1.getString("strYoutube");
          String tags = jsonObject1.getString("strTags");
          String source = jsonObject1.getString("strSource");

          binding.resultId.setText(id);
          binding.resultName.setText(name);
          binding.resultCategory.setText(category);
          binding.resultArea.setText(area);
          Glide.with(MealActivity.this).load(image).into(binding.resultImage);
          binding.resultYoutube.setText(youtube);
          binding.resultTags.setText(tags);
          binding.resultSource.setText(source);
          break;
        } else {
          binding.resultId.setText("Not Found");
          binding.resultName.setText("Not Found");
          binding.resultCategory.setText("Not Found");
          binding.resultArea.setText("Not Found");
          binding.resultYoutube.setText("Not Found");
          binding.resultTags.setText("Not Found");
          binding.resultSource.setText("Not Found");
          break;
        }
      }
    }
  }
}