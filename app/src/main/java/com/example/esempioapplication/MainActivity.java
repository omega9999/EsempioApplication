package com.example.esempioapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.viewpager);
        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);
    }

    private class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

        SimpleFragmentPagerAdapter(@NonNull final FragmentManager fm) {
            super(fm);
            this.mFragments = new ArrayList<>();

            this.mFragments.add(new MainFragment());
            this.mFragments.add(new Mp3Fragment());
            this.mFragments.add(new ScrollingFragment());
            this.mFragments.add(new GradientFragment());
            this.mFragments.add(new CompassFragment());
        }

        @Override
        public Fragment getItem(final int position) {
            return this.mFragments.get(position);
        }

        @Override
        public int getCount() {
            return this.mFragments.size();
        }

        private final ArrayList<Fragment> mFragments;
    }

    private static final String TAG = MainActivity.class.getSimpleName();
}
