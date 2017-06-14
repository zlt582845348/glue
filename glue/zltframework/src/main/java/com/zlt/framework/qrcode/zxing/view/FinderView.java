package com.zlt.framework.qrcode.zxing.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Author: zlt
 * Date: 2016-07-06
 * Time: 15:56
 */
public class FinderView extends ViewfinderView {

    private static final int MIN_FRAME_WIDTH = 50;
    private static final int MIN_FRAME_HEIGHT = 50;
    private static final int MAX_FRAME_WIDTH = 480;
    private static final int MAX_FRAME_HEIGHT = 480;

    private float scanW = 0f;
    private float scanH = 0f;

    public FinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setPercentSize(float scanW, float scanH)
    {
        this.scanW = scanW;
        this.scanH = scanH;
    }

    public Rect getFramingRect() {
        int width = (int) (this.getWidth() * scanW);
        if (width < MIN_FRAME_WIDTH) {
            width = MIN_FRAME_WIDTH;
        } else if (width > MAX_FRAME_WIDTH) {
            width = MAX_FRAME_WIDTH;
        }
        int height = (int) (this.getHeight() * scanH);
        if (height < MIN_FRAME_HEIGHT) {
            height = MIN_FRAME_HEIGHT;
        } else if (height > MAX_FRAME_HEIGHT) {
            height = MAX_FRAME_HEIGHT;
        }
        int leftOffset = (this.getWidth() - width) / 2;
        int topOffset = (this.getHeight() - height) / 2;
        Rect framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
        Log.d("zlt", "Calculated framing rect: " + framingRect);
        return framingRect;
    }

    @Override
    protected void drawText(Canvas canvas, Rect frame) {
//        super.drawText(canvas, frame);
    }

}
