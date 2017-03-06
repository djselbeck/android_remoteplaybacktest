package com.example.hendrik.remoteplaybacktest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaButtonReceiver;
import android.util.Log;

/**
 * Created by hendrik on 06.03.17.
 */

public class RemoteControllerClient extends MediaButtonReceiver {
    public static final String TAG = RemoteControllerClient.class.getSimpleName();
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v(TAG,"Onreceive: " + intent.getAction());
    }
}
