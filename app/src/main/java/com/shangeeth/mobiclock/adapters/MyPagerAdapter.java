package com.shangeeth.mobiclock.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.shangeeth.mobiclock.ui.AlarmFragment;
import com.shangeeth.mobiclock.ui.TimerFragment;

/**
 * Created by user on 01/05/17.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "MyPagerAdapter";

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.e(TAG, "getItem: " + position);
        switch (position) {
            case 0:
                return new AlarmFragment();
            case 1:
                return new TimerFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Alarm";
            case 1:
                return "Timer";
        }
        return null;
    }
}
