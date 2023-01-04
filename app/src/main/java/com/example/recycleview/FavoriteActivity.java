package com.example.recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.recycleview.restapi.MealActivity;
import com.example.recycleview.sqlite.DisplayData;
import com.example.recycleview.sqlite.TambahMenu;
import com.google.android.material.navigation.NavigationView;

public class FavoriteActivity extends AppCompatActivity {

  private DrawerLayout drawer;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite);
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
            Intent h = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(h);
            break;
          case R.id.nav_food:
            Intent i = new Intent(FavoriteActivity.this, FoodActivity.class);
            startActivity(i);
            break;
          case R.id.nav_favorite:
            Intent j = new Intent(FavoriteActivity.this, FavoriteActivity.class);
            startActivity(j);
            break;
          case R.id.nav_profile:
            Intent k = new Intent(FavoriteActivity.this, ProfileActivity.class);
            startActivity(k);
            break;
          case R.id.nav_sql:
            Intent intent4 = new Intent(FavoriteActivity.this, DisplayData.class);
            startActivity(intent4);
            break;
          case R.id.nav_meal:
            Intent intent5 = new Intent(FavoriteActivity.this, MealActivity.class);
            startActivity(intent5);
            break;
        }
        return true;
      }
    });
  }
}