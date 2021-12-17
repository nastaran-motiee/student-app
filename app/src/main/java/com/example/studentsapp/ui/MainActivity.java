package com.example.studentsapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.studentsapp.R;

public class MainActivity extends AppCompatActivity {
    NavController navController;
    NavHostFragment navHostFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * change the AppBar label to the label of the fragment
         */
        /*When creating the NavHostFragment using FragmentContainerView
         or if manually adding the NavHostFragment to your activity
        via a FragmentTransaction, attempting to retrieve the NavController
        in onCreate() of an Activity via Navigation.findNavController(Activity, @IdRes int)
        will fail. You should retrieve the NavController directly from the NavHostFragment instead.*/
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            navController.navigateUp();
        }else{
            return NavigationUI.onNavDestinationSelected(item,navController);
        }
        return true;
    }

}