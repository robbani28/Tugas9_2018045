package com.example.recycleview.sqlite;

import static com.example.recycleview.sqlite.DBmain.TABLENAME;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.recycleview.FoodActivity;
import com.example.recycleview.FavoriteActivity;
import com.example.recycleview.MainActivity;
import com.example.recycleview.ProfileActivity;
import com.example.recycleview.R;
import com.example.recycleview.databinding.ActivityDisplayDataBinding;
import com.example.recycleview.restapi.MealActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DisplayData extends AppCompatActivity {
    DBmain dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    private ActivityDisplayDataBinding binding;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDisplayDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // action bar
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_sql);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_alarm:
                        Intent intent = new Intent(DisplayData.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_food:
                        Intent intent1 = new Intent(DisplayData.this, FoodActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_favorite:
                        Intent intent2 = new Intent(DisplayData.this, FavoriteActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_profile:
                        Intent intent3 = new Intent(DisplayData.this, ProfileActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_sql:
                        Intent intent4 = new Intent(DisplayData.this, DisplayData.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_meal:
                        Intent intent5 = new Intent(DisplayData.this, MealActivity.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });
        findId();
        dBmain = new DBmain(this);
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(DisplayData.this, TambahMenu.class);
                startActivity(a);
            }
        });
    }
    private void displayData() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from "+TABLENAME,null);
        ArrayList<Model> models = new ArrayList<>();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            byte[]avatar = cursor.getBlob(5);
            String star = cursor.getString(2);
            String price = cursor.getString(3);
            String about = cursor.getString(4);
            models.add(new Model(id,avatar,title,star,price,about));
        }
        cursor.close();
        myAdapter = new MyAdapter(this, R.layout.single_data,models,sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }
    private void findId() {
        recyclerView = findViewById(R.id.rv);
    }
}