package com.navrug.mrnorm.impl;

import android.media.SoundPool;

import com.navrug.mrnorm.interfaces.Sound;


public class AndroidSound implements Sound {
  private int _soundId;
  private SoundPool _soundPool;

  public AndroidSound(int _soundId, SoundPool _soundPool) {
    this._soundId = _soundId;
    this._soundPool = _soundPool;
  }

  @Override
  public void play(float volume) {
    _soundPool.play(_soundId, volume, volume, 0, 0, 1);
  }

  @Override
  public void dispose() {
    _soundPool.unload(_soundId);
  }
}
