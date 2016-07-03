package com.navrug.game.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public abstract class FragmentActivity<F extends Fragment> extends Activity {

  private Class<? extends Fragment> _clazz;
  private String _tag;

  public FragmentActivity(Class<? extends Fragment> clazz) {
    _clazz = clazz;
    _tag = clazz.getSimpleName();
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Fragment frag;
    FragmentManager fragManager = getSupportFragmentManager();
    FragmentTransaction ft = fragManager.beginTransaction();
    if ((frag = fragManager.findFragmentByTag(_tag)) != null) {
      ft.replace(android.R.id.content, frag, _tag);
    } else {
      ft.add(android.R.id.content, Fragment.instantiate(this, _clazz.getName()), _tag);
    }
    ft.commit();
  }

  public F getFragment() {
    return (F) getSupportFragmentManager().findFragmentByTag(_tag);
  }
}
