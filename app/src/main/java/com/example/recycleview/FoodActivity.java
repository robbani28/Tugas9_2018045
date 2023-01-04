package com.example.recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.recycleview.adapter.FoodAdapter;
import com.example.recycleview.restapi.MealActivity;
import com.example.recycleview.sqlite.DisplayData;
import com.example.recycleview.worker.MyWorker;
import com.google.android.material.navigation.NavigationView;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.example.recycleview.databinding.ActivityFoodBinding;
import android.view.View;


public class FoodActivity extends AppCompatActivity {
  private ActivityFoodBinding binding;
  private DrawerLayout drawer;
  RecyclerView recylerView;
  String s1[], s2[],s3[];
  int images[] = {
          R.drawable.rawon_nguling,
          R.drawable.sate_komoh,
          R.drawable.botok_tempe,
          R.drawable.klepon,
  };
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityFoodBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    recylerView = findViewById(R.id.recyclerView);
    s1 = getResources().getStringArray(R.array.title);
    s2 = getResources().getStringArray(R.array.deskripsi);
    s3 = getResources().getStringArray(R.array.star);
    FoodAdapter appAdapter = new FoodAdapter(this,s1,s2,s3,images);
    recylerView.setAdapter(appAdapter);
    recylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
            Intent h = new Intent(FoodActivity.this, MainActivity.class);
            startActivity(h);
            break;
          case R.id.nav_food:
            Intent i = new Intent(FoodActivity.this, FoodActivity.class);
            startActivity(i);
            break;
          case R.id.nav_favorite:
            Intent j = new Intent(FoodActivity.this, FavoriteActivity.class);
            startActivity(j);
            break;
          case R.id.nav_profile:
            Intent k = new Intent(FoodActivity.this, ProfileActivity.class);
            startActivity(k);
            break;
          case R.id.nav_sql:
            Intent intent4 = new Intent(FoodActivity.this, DisplayData.class);
            startActivity(intent4);
            break;
          case R.id.nav_meal:
            Intent intent5 = new Intent(FoodActivity.this, MealActivity.class);
            startActivity(intent5);
            break;
        }
        return true;
      }
    });
    final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
    binding.addWorker.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        WorkManager.getInstance().enqueueUniqueWork("Notifikasi", ExistingWorkPolicy.REPLACE, request);
      }
    });
  }
}