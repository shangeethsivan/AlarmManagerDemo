package com.shangeeth.mobiclock.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.shangeeth.mobiclock.R;
import com.shangeeth.mobiclock.adapters.MyPagerAdapter;

public class HomeActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    TabLayout mTabLayout;

    private static final String TAG = "HomeActivity";
    private MenuItem mSetAlarmMenuOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_home);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        MyPagerAdapter lMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(lMyPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.main_menu, menu);
        if (mViewPager.getCurrentItem() == 0) {
            menu.findItem(R.id.set_alarm_menu).setVisible(true);
        } else if (mViewPager.getCurrentItem() == 1) {
            menu.findItem(R.id.set_alarm_menu).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set_alarm_menu:
                Fragment lFragment = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + mViewPager.getCurrentItem());
                if (mViewPager.getCurrentItem() == 0) {
                    ((AlarmFragment) lFragment).setAlarm();
                }
                break;
        }
        return true;
    }


}
