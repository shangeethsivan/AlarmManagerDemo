package com.shangeeth.mobiclock.ui;

import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.shangeeth.mobiclock.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlarmDetailActivity extends AppCompatActivity implements AudioManager.OnAudioFocusChangeListener {

    private TextView mTimeTV;
    private Button mCloseAlarmBTN;
    private Ringtone mRingtone;
    private Vibrator mVibrator;
    public boolean isActivityOpenedAgain = false;
    private static final String TAG = "AlarmDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_alarm_detail);
        Log.e(TAG, "onCreate: ");

        Window lWindow = getWindow();
        lWindow.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        lWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        lWindow.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        lWindow.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        AudioManager lAm = (AudioManager) getSystemService(AUDIO_SERVICE);

        int result = lAm.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        Uri lUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mRingtone = RingtoneManager.getRingtone(getApplicationContext(), lUri);

        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mRingtone.play();
            mVibrator.vibrate(2000);
        }

        mTimeTV = (TextView) findViewById(R.id.time_tv);
        mCloseAlarmBTN = (Button) findViewById(R.id.close_alarm);

        if (getIntent().getBooleanExtra(getString(R.string.is_timer),false)) {
            mTimeTV.setText("TIMER COMPLETED");
        } else {
            Calendar lCalendar = Calendar.getInstance();
            lCalendar.setTimeInMillis(System.currentTimeMillis());

            SimpleDateFormat lSimpleDateFormat = new SimpleDateFormat("hh:mm a");
            mTimeTV.setText(lSimpleDateFormat.format(lCalendar.getTime()));
        }

        mCloseAlarmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRingtone.stop();
                mVibrator.cancel();
                finish();
            }
        });

    }

    @Override
    public void onAudioFocusChange(int focusChange) {

        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
            mRingtone.stop();
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mRingtone.play();
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            mRingtone.stop();
        }
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG, "onResume: " + isActivityOpenedAgain);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause: ");
//        mRingtone.stop();
//        mVibrator.cancel();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onStop() {
        Log.e(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }
}
