package com.navrug.game.tests;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;


public class FontView extends View {

  private Typeface font;
  private Paint paint;

  public FontView(Context context) {
    super(context);
    font = Typeface.createFromAsset(context.getAssets(), "waltographUI.ttf");
    paint = new Paint();
    paint.setTypeface(font);
    paint.setTextSize(30);
    paint.setColor(Color.BLUE);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawText("Waltograph Font", 100, 100, paint);
  }
}
