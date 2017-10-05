package com.ru.droid.lab;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Jonni on 10/4/2017.
 */

public class GraphicView extends View {

    private Paint paint;

    public GraphicView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setARGB(255, 0, 255, 0);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawCircle(Circle.getCircle().getX(), Circle.getCircle().getY(),
                Circle.getCircle().getRadius(), paint);
    }

    public void update() {
        this.invalidate();
    }
}
