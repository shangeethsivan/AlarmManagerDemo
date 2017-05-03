package com.shangeeth.mobiclock.ui;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.shangeeth.mobiclock.R;
import com.shangeeth.mobiclock.receivers.AlarmReceiver;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {


    private TimePicker mTimePicker;
    private Switch mRepeatSwitch;
    private LinearLayout mCheckBoxGroup;
    private TextView mTimeRemainingTV;
    private Calendar mCalendar;
    private String mRepeatedDayData;
    private static final String TAG = "AlarmFragment";
    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG, "onCreateView: " );
        // Inflate the layout for this fragment
        View lView = inflater.inflate(R.layout.fragment_alarm, container, false);


        mTimePicker = (TimePicker) lView.findViewById(R.id.time_picker);
        mRepeatSwitch = (Switch) lView.findViewById(R.id.repeat_switch);
        mCheckBoxGroup = (LinearLayout) lView.findViewById(R.id.checkbox_group_ll);
        mTimeRemainingTV = (TextView) lView.findViewById(R.id.time_remaining_tv);

        mCalendar = Calendar.getInstance();

        final Handler lHandler = new Handler();
        setListeners(lView);

        //TODO: set Alarm here
        //getSupportActionBar().setTitle("Set Alarm");

        return lView;
    }

    private void setListeners(View pView) {

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

                Log.e("Time from picker", "onTimeChanged: " + hourOfDay + " " + minute);
                mCalendar.setTimeInMillis(System.currentTimeMillis());
                mTimeRemainingTV.setText("Alarm in "
                        + getRemainingTime(mCalendar.get(Calendar.HOUR_OF_DAY),
                        mCalendar.get(Calendar.MINUTE), hourOfDay, minute));

            }
        });

        ((CheckBox) pView.findViewById(R.id.sunday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = "1" + mRepeatedDayData.substring(1);
                else
                    mRepeatedDayData = "0" + mRepeatedDayData.substring(1);

            }
        });
        ((CheckBox) pView.findViewById(R.id.monday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = mRepeatedDayData.substring(0, 1) + "1" + mRepeatedDayData.substring(2);
                else
                    mRepeatedDayData = mRepeatedDayData.substring(0, 1) + "0" + mRepeatedDayData.substring(2);

            }
        });
        ((CheckBox) pView.findViewById(R.id.tuesday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = mRepeatedDayData.substring(0, 2) + "1" + mRepeatedDayData.substring(3);
                else
                    mRepeatedDayData = mRepeatedDayData.substring(0, 2) + "0" + mRepeatedDayData.substring(3);

            }
        });
        ((CheckBox) pView.findViewById(R.id.wednesday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = mRepeatedDayData.substring(0, 3) + "1" + mRepeatedDayData.substring(4);
                else
                    mRepeatedDayData = mRepeatedDayData.substring(0, 3) + "0" + mRepeatedDayData.substring(4);

            }
        });
        ((CheckBox) pView.findViewById(R.id.thursday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = mRepeatedDayData.substring(0, 4) + "1" + mRepeatedDayData.substring(5);
                else
                    mRepeatedDayData = mRepeatedDayData.substring(0, 4) + "0" + mRepeatedDayData.substring(5);

            }
        });
        ((CheckBox) pView.findViewById(R.id.friday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = mRepeatedDayData.substring(0, 5) + "1" + mRepeatedDayData.substring(6);
                else
                    mRepeatedDayData = mRepeatedDayData.substring(0, 5) + "0" + mRepeatedDayData.substring(6);

            }
        });
        ((CheckBox) pView.findViewById(R.id.saturday_cbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mRepeatedDayData = mRepeatedDayData.substring(0, 6) + "1";
                else
                    mRepeatedDayData = mRepeatedDayData.substring(0, 6) + "0";
            }
        });
    }


    /**
     * Gets the remaining time for the alarm as string for display
     *
     * @param pCurrentHour
     * @param pCurrentMinute
     * @param pHour
     * @param pMinute
     * @return remaining time in string format
     */
    private String getRemainingTime(int pCurrentHour, int pCurrentMinute, int pHour, int pMinute) {

        int lFinalHours;
        int lFinalMinutes;

        int lDiffInMinutes = calculateRemainingTime(pCurrentHour, pCurrentMinute, pHour, pMinute);

        if (lDiffInMinutes == 0)
            return "less than a minute";
        else {
            lFinalHours = lDiffInMinutes / 60;
            lFinalMinutes = lDiffInMinutes % 60;

            return (lFinalHours == 0 ? "" : lFinalHours + (lFinalHours == 1 ? " Hour" : " Hours")) +
                    (lFinalMinutes == 0 ? "" : " " + lFinalMinutes + (lFinalMinutes == 1 ? " Minute" : " Minutes"));
        }
    }

    /**
     * Calculates the remaining time in minutes if the difference is negative it will add up 1440 minutes to the difference
     *
     * @param pCurrentHour
     * @param pCurrentMinute
     * @param pHour          Selected hour
     * @param pMinute        Selected Minute
     * @return returns the remaining time for the alarm in minutes
     */
    public int calculateRemainingTime(int pCurrentHour, int pCurrentMinute, int pHour, int pMinute) {

        int lCurrentHourInMinutes = pCurrentHour * 60;
        int lSelectedHourInMinutes = pHour * 60;

        int lTotalCurrentTimeInMinutes = lCurrentHourInMinutes + pCurrentMinute + 1;
        int lTotalSelectedTimeInMinutes = lSelectedHourInMinutes + pMinute;

        int lDiffInMinutes = lTotalSelectedTimeInMinutes - lTotalCurrentTimeInMinutes;

        if (lDiffInMinutes < 0) {
            int lMaxTime = 1440;
            lDiffInMinutes = lMaxTime + lDiffInMinutes;
        }

        return lDiffInMinutes;
    }


    /**
     * Sets the alarm for the selected time in the time picker
     */
    public void setAlarm() {

        if (mRepeatSwitch.isChecked()) {

            SharedPreferences lSharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

            SharedPreferences.Editor lEditor = lSharedPreferences.edit();
            lEditor.putBoolean(getString(R.string.is_repeat_alarm_set), true);
            lEditor.putString(getString(R.string.repetive_days), mRepeatedDayData);
            lEditor.putString(getString(R.string.time_of_alarm), mTimePicker.getCurrentHour() + ":" + mTimePicker.getCurrentMinute());

        }
        AlarmManager lAlarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        int lRemainingTimeInMinutes = calculateRemainingTime(mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE), mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute()) + 1;

        Intent lIntent = new Intent(getContext(), AlarmReceiver.class);
        PendingIntent lPendingIntent = PendingIntent.getBroadcast(getContext(), 101, lIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar lCalendar = Calendar.getInstance();
        lCalendar.set(Calendar.SECOND, 0);
        lCalendar.set(Calendar.MILLISECOND, 0);

        lAlarmManager.setExact(AlarmManager.RTC_WAKEUP,
                lCalendar.getTimeInMillis() + (lRemainingTimeInMinutes * 60000), lPendingIntent);

        Toast.makeText(getActivity(), "Alarm in " + getRemainingTime(mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE), mTimePicker.getCurrentHour(), mTimePicker.getCurrentMinute()) + " from now.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        Log.e(TAG, "onAttach: " );
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onCreate: " );
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated: " );
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.e(TAG, "onStart: " );
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.e(TAG, "onResume: " );
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.e(TAG, "onPause: " );
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e(TAG, "onStop: " );
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView: " );
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: " );
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.e(TAG, "onDetach: " );
        super.onDetach();
    }
}
