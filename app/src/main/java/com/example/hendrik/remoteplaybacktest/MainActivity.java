package com.example.hendrik.remoteplaybacktest;

import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    TestVolumeControlProvider mVolumeProvider;

    MediaSessionCompat mMediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mStartBtn = (Button)findViewById(R.id.button_start);
        Button mStopBtn = (Button)findViewById(R.id.button_stop);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( mMediaSession != null) {
                    return;
                }

                mMediaSession = new MediaSessionCompat(MainActivity.this, MainActivity.this.getString(R.string.app_name));
                mMediaSession.setCallback(new TestMediaSessionCallback());
                mVolumeProvider = new TestVolumeControlProvider();
                mMediaSession.setPlaybackToRemote(mVolumeProvider);
                mMediaSession.setActive(true);

                mMediaSession.setPlaybackState(new PlaybackStateCompat.Builder().setState(PlaybackStateCompat.STATE_PLAYING, 0, 1.0f)
                        .setActions(PlaybackStateCompat.ACTION_SKIP_TO_NEXT + PlaybackStateCompat.ACTION_PAUSE +
                                PlaybackStateCompat.ACTION_PLAY + PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS +
                                PlaybackStateCompat.ACTION_STOP + PlaybackStateCompat.ACTION_SEEK_TO).build());
            }
        });

        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMediaSession == null ){
                    return;
                }

                mMediaSession.setActive(false);
                mMediaSession.release();
                mMediaSession = null;
            }
        });
    }



    private class TestVolumeControlProvider extends VolumeProviderCompat {
        private  final String TAG = TestVolumeControlProvider.class.getSimpleName();

        public int mVolume = 25;

        public TestVolumeControlProvider() {
            super(VOLUME_CONTROL_ABSOLUTE, 100, 25);
        }

        @Override
        public void onSetVolumeTo(int volume) {
            Log.v(TAG,"onSetVolumeTo: " + volume);
            mVolume = volume;
        }

        @Override
        public void onAdjustVolume(int direction) {
            Log.v(TAG,"onAdjustVolume: " + direction);
        }
    }


    private class TestMediaSessionCallback extends MediaSessionCompat.Callback {
        private  final String TAG = TestMediaSessionCallback.class.getSimpleName();
        @Override
        public void onPlay() {
            super.onPlay();
            Log.v(TAG,"onPlay");
        }

        @Override
        public void onPause() {
            super.onPause();
            Log.v(TAG,"onPause");
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            Log.v(TAG,"onSkipToNext");
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            Log.v(TAG,"onSkipToPrevious");
        }

        @Override
        public void onStop() {
            super.onStop();
            Log.v(TAG,"onStop");
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            Log.v(TAG,"onSeekTo: " + pos);
        }
    }
}
