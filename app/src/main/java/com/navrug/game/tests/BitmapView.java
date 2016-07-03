package com.navrug.game.tests;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import java.io.IOException;


public class BitmapView extends View {

  private Bitmap bitmap;

  public BitmapView(Context context) {
    super(context);
    try {
      BitmapFactory.Options opt = new BitmapFactory.Options();
      opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
      bitmap = BitmapFactory.decodeStream(context.getAssets().open("bitmap.png"), null, opt);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {

    if (bitmap != null) {
      canvas.drawBitmap(bitmap, null, new Rect(canvas.getWidth() / 4, canvas.getHeight() / 4, canvas.getWidth() * 3/4, canvas.getHeight() * 3/4), null);
    }

    invalidate();
  }
}
