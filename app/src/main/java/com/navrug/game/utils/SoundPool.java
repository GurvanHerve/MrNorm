package com.navrug.game.utils;

import android.media.AudioManager;
import android.os.Build;

public final class SoundPool {

  public static android.media.SoundPool getSoundPool() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      android.media.SoundPool.Builder builder = new android.media.SoundPool.Builder();
      builder.setMaxStreams(20);
      return builder.build();
    } else {
    return new android.media.SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }
  }
}
