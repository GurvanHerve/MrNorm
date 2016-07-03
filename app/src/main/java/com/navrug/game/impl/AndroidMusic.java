package com.navrug.game.impl;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.navrug.game.interfaces.Music;

import java.io.IOException;


public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {

  private MediaPlayer _player;
  private boolean _isPrepared = false;

  public AndroidMusic(AssetFileDescriptor descriptor) {
    _player = new MediaPlayer();
    try {
      _player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
      _player.prepare();
      _isPrepared = true;
      _player.setOnCompletionListener(this);
      _player.setOnPreparedListener(this);
    } catch (IOException e) {
      throw new RuntimeException("Couldn't load music");
    }
  }

  @Override
  public void play() {
    if (_player.isPlaying()) {
      return;
    }
    try {
      synchronized (this) {
        if (!_isPrepared) {
          _player.prepare();
        }
        _player.start();
      }
    } catch (IllegalStateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    _player.stop();
    synchronized (this) {
      _isPrepared = false;
    }
  }

  @Override
  public void pause() {
    if (_player.isPlaying()) {
      _player.pause();
    }
  }

  @Override
  public void setLooping(boolean looping) {
    _player.setLooping(looping);
  }

  @Override
  public void setVolume(float volume) {

  }

  @Override
  public boolean isPlaying() {
    return _player.isPlaying();
  }

  @Override
  public boolean isStopped() {
    return !_isPrepared;
  }

  @Override
  public boolean isLooping() {
    return _player.isLooping();
  }

  @Override
  public void dispose() {
    if (_player.isPlaying()) {
      _player.stop();
    }
    _player.release();
  }

  @Override
  public void onCompletion(MediaPlayer mp) {
    synchronized (this) {
      _isPrepared = false;
    }
  }

  @Override
  public void onPrepared(MediaPlayer mp) {
    _isPrepared = true;
  }
}
