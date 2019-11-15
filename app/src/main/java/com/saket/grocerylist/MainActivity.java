package com.saket.grocerylist;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements CreateProductFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Add HomeListFragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, HomeListFragment.createInstance(), "");
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    //android:onclick from xml layout file
    public void onCreateListClicked(View view) {
        //Go to Create list fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, CreateListFragment.getInstance(null))
                .addToBackStack(null)
                .commit();
    }

}
