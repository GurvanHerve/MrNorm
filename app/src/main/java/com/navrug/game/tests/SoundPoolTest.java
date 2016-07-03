package com.navrug.game.tests;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.navrug.game.R;


public class SoundPoolTest extends FragmentActivity implements View.OnClickListener, SoundPool.OnLoadCompleteListener {

  private SoundPool soundPool;
  private int soundId;
  private boolean soundLoaded;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Button btn = new Button(this);
    btn.setText("Play sound");
    btn.setOnClickListener(this);
    setContentView(btn);

    setVolumeControlStream(AudioManager.STREAM_MUSIC);
    soundPool = getSoundPool();
    soundPool.setOnLoadCompleteListener(this);
    soundId = soundPool.load(this, R.raw.gunshot, 1);
  }

  @Override
  protected void onStop() {
    if (soundPool != null) {
      soundPool.unload(soundId);
      soundPool.release();
    }
    super.onStop();
  }

  private static SoundPool getSoundPool() {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//      SoundPool.Builder builder = new SoundPool.Builder();
//      builder.setMaxStreams(20);
//      return builder.build();
//    } else {
      return new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
//    }
  }

  @Override
  public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
    soundLoaded = sampleId == soundId && status == 0;
  }

  @Override
  public void onClick(View v) {
    if (soundPool != null && soundLoaded) {
      soundPool.play(soundId, 1.0F, 1.0F, 0, 0, 1);
    }
  }
}
