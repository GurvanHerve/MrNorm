package com.navrug.mrnorm.tests;

import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class TestFragment extends ListFragment {

  private String tests[] = { "LifeCycleTest", "SingleTouchTest", "MultiTouchTest",
      "KeyTest", "AccelerometerTest", "AssetsTest",
      "ExternalStorageTest", "SoundPoolTest", "MediaPlayerTest",
      "FullScreenTest", "RenderViewTest", "ShapeTest", "BitmapTest",
      "FontTest", "SurfaceViewTest" };

  @Override
  public void onStart() {
    super.onStart();
    setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, tests));
  }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    try {
      startActivity(new Intent(getActivity(), Class.forName("com.navrug.mrnorm." + tests[position])));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
