package com.navrug.game.tests;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

public class RenderView extends View {

  private Random rand;
  private Paint paint;

  public RenderView(Context context) {
    super(context);
    rand = new Random();
    paint = new Paint();
  }

  @Override
  protected void onDraw(Canvas canvas) {
//    canvas.drawRGB(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
    canvas.drawRGB(255, 255, 255);

    paint.setColor(Color.RED);
    canvas.drawLine(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1, paint);

    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(0xFF00FF00);
    canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, 40, paint);

    paint.setStyle(Paint.Style.FILL);
    paint.setColor(0x770000FF);
    canvas.drawRect(100, 100, 200, 200, paint);

    invalidate();
  }
}
