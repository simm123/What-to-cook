package com.simmcard.cook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.simmcard.cook.databinding.ActivityMainBinding;
import com.simmcard.cook.ui.dashboard.ViewDishActivity;
import com.simmcard.cook.ui.dashboard.EditDishActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Snackbar message = Snackbar.make(findViewById(R.id.calendarView), (CharSequence)String.format("%s", getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)), 10000);
        //message.show();
    }

    //TODO check and remove this method
    public void newDish(View view) {
        startActivity(new Intent(this, EditDishActivity.class));
    }

    public void openViewDishActivity(){
        Intent intent = new Intent(this, ViewDishActivity.class);
        startActivity(intent);
    }
}