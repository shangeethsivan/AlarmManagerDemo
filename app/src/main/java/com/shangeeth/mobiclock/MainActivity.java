package com.shangeeth.mobiclock;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button mSetAlarmBtn;
    protected TextView mTimeRemainingTV;
    private TimePicker mTimePicker;
    private Switch mRepeatSwitch;
    private LinearLayout mCheckBoxGroup;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mRepeatSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCheckBoxGroup.setVisibility(View.VISIBLE);
                } else {
                    mCheckBoxGroup.setVisibility(View.INVISIBLE);
                }
            }
        });

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                mCalendar.setTimeInMillis(System.currentTimeMillis());
                mTimeRemainingTV.setText("Alarm in "
                        + getRemainingTime(mCalendar.get(Calendar.HOUR),
                        mCalendar.get(Calendar.MINUTE), hourOfDay, minute));

            }
        });
    }

    private void init() {

        mTimePicker = (TimePicker) findViewById(R.id.time_picker);
        mRepeatSwitch = (Switch) findViewById(R.id.repeat_switch);
        mCheckBoxGroup = (LinearLayout) findViewById(R.id.checkbox_group_ll);
        mSetAlarmBtn = (Button) findViewById(R.id.alarm_btn);
        mSetAlarmBtn.setOnClickListener(MainActivity.this);
        mTimeRemainingTV = (TextView) findViewById(R.id.time_remaining_tv);

        mCalendar = Calendar.getInstance();
    }

    @Override
    public void onClick(View view) {
    }

    private void setAlarm() {

    }


    private String getRemainingTime(int pCurrentHour, int pCurrentMinute, int pHour, int pMinute) {

        int lFinalHours = 0;
        int lFinalMinutes = 0;
        int lCurrentHourInMinutes = pCurrentHour * 60;
        int lSelectedHourInMinutes = pHour * 60;

        int lTotalCurrentTimeInMinutes = lCurrentHourInMinutes + pCurrentMinute;
        int lTotalSelectedTimeInMinutes = lSelectedHourInMinutes + pMinute;

        int lDiffInMinutes = lTotalSelectedTimeInMinutes - lTotalCurrentTimeInMinutes;

        if (lDiffInMinutes <= 0) {
            int lMaxTime = 1439;
            lDiffInMinutes = lMaxTime + lDiffInMinutes;
        }


        lFinalHours = lDiffInMinutes / 60;
        lFinalMinutes = lDiffInMinutes % 60;

        return (lFinalHours==0?"":lFinalHours+(lFinalHours==1?" Hour":" Hours"))+
                (lFinalMinutes==0?"":" "+lFinalMinutes+(lFinalMinutes==1?" Minute":" Minutes"));
    }
}
