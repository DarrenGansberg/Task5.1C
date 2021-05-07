package com.darrengansberg.tourismapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

/* An activity class that is used to browse destinations, and used as the
tourism apps main and only activity
 */
public class BrowseDestinationsActivity extends AppCompatActivity {

    private BrowseDestinationsFragment browseDestinationsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            browseDestinationsFragment = BrowseDestinationsFragment.newInstance();
            browseDestinationsFragment.setDestinationClickedListener(new PlacesToGoItemClickListener());

            FragmentContainerView fg = findViewById(R.id.app_fragment_container);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(R.id.app_fragment_container, browseDestinationsFragment);
            transaction.commit();
        }
        else {
            FragmentManager fm = getSupportFragmentManager();
            browseDestinationsFragment = (BrowseDestinationsFragment)fm.getFragment(savedInstanceState, "browseDestinationsFragment");
            browseDestinationsFragment.setDestinationClickedListener(new PlacesToGoItemClickListener());
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fm = getSupportFragmentManager();
        fm.putFragment(outState, "browseDestinationsFragment", browseDestinationsFragment);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        browseDestinationsFragment = (BrowseDestinationsFragment)fm.getFragment(savedInstanceState, "browseDestinationsFragment");
        browseDestinationsFragment.setDestinationClickedListener(new PlacesToGoItemClickListener());

    }

    private class PlacesToGoItemClickListener implements DestinationItemClickedListener {

        @Override
        public void onDestinationClicked(int destinationId) {

            //Destination d = placesToGo.get(Integer.parseInt(destinationId));
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            ViewDestinationFragment fragment = ViewDestinationFragment.newInstance(destinationId);
            transaction.setCustomAnimations(R.anim.slide_in,
                    R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
                    .replace(R.id.app_fragment_container, fragment)
                    .addToBackStack(null);
            transaction.commit();
        }
    }

}