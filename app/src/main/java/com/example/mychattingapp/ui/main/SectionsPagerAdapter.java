package com.example.mychattingapp.ui.main;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mychattingapp.MainActivity;
import com.example.mychattingapp.NaverMapActivity;
import com.example.mychattingapp.R;
import com.example.mychattingapp.fragment.ChatFragment;
import com.example.mychattingapp.fragment.HomeFragment;
import com.example.mychattingapp.fragment.MapContainerFragment;
import com.example.mychattingapp.fragment.MypageFragment;
import com.example.mychattingapp.fragment.PeopleFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.menu0,R.string.menu1, R.string.menu2,R.string.menu3, R.string.menu4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return new HomeFragment();
        }
        switch (position){
            case 1:
                return new MapContainerFragment();
        }
        switch (position){
            case 2:
                return new ChatFragment();
        }
        switch (position){
            case 3:
                return new PeopleFragment();
        }
        switch (position){
            case 4:
                return new MypageFragment();
        }
        return PlaceholderFragment.newInstance(position+1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 5;
    }
}