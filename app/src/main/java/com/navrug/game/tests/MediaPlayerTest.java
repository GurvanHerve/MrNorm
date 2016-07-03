package com.navrug.game.tests;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.navrug.game.R;


public class MediaPlayerTest extends FragmentActivity implements MediaPlayer.OnPreparedListener {

  private MediaPlayer mediaPlayer;
  private boolean mpReady;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.media_player);

//    mediaPlayer = new MediaPlayer();
//    mediaPlayer.setOnPreparedListener(this);
//    try {
//      AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.song);
//      mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
//      mediaPlayer.prepare();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }

    mediaPlayer = MediaPlayer.create(this, R.raw.song);
    mediaPlayer.setOnPreparedListener(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    mediaPlayer.stop();
    mediaPlayer.release();
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    mpReady = true;
  }

  public void onPlayClicked(View v) {
    if (mpReady) {
      mediaPlayer.start();
    }
  }

  public void onPauseClicked(View v) {
    mediaPlayer.pause();
  }

  public void onStopClicked(View v) {
    mediaPlayer.stop();
  }
}
