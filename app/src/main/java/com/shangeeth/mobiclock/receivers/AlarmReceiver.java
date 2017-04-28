package com.shangeeth.mobiclock.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.shangeeth.mobiclock.ui.AlarmDetailActivity;

/**
 * Created by user on 26/04/17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm Received", Toast.LENGTH_SHORT).show();
        Log.e(AlarmReceiver.class.getSimpleName(), "onReceive: Alarm" );
        context.startActivity(new Intent(context, AlarmDetailActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
