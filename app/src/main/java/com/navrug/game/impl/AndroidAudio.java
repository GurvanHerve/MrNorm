package com.navrug.game.impl;

import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;

import com.navrug.game.interfaces.Audio;
import com.navrug.game.interfaces.Music;
import com.navrug.game.interfaces.Sound;

import java.io.IOException;


public class AndroidAudio implements Audio {
  private AssetManager _assets;
  private SoundPool _soundPool;

  public AndroidAudio(AppCompatActivity activity) {
    activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    _assets = activity.getAssets();
    _soundPool = com.navrug.game.utils.SoundPool.getSoundPool();
  }

  @Override
  public Music newMusic(String filename) {
    try {
      return new AndroidMusic(_assets.openFd(filename));
    } catch (IOException e) {
      throw new RuntimeException("Couldn't load music: '" + filename + "'");
    }
  }

  @Override
  public Sound newSound(String filename) {
    try {
      return new AndroidSound(_soundPool.load(_assets.openFd(filename), 0), _soundPool);
    } catch (IOException e) {
      throw new RuntimeException("Couldn't load sound: '" + filename + "'");
    }
  }
}
