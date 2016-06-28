package com.navrug.mrnorm.interfaces;

public interface Music {
  void play();
  void stop();
  void pause();
  void setLooping(boolean looping);
  void setVolume(float volume);
  boolean isPlaying();
  boolean isStopped();
  boolean isLooping();
  void dispose();
}