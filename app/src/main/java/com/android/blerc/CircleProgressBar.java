package com.android.blerc;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import me.jessyan.autosize.utils.AutoSizeUtils;

/* loaded from: classes.dex */
public class CircleProgressBar extends View {
    Context context;
    Paint mBgPaint;
    Paint mStepPaint;
    Paint mTxtCirclePaint;
    Paint mTxtPaint;
    float maxProgress;
    int outsideRadius;
    float progress;
    int progressWidth;

    public CircleProgressBar(Context context) {
        super(context);
        this.maxProgress = 100.0f;
        this.progress = 0.0f;
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.maxProgress = 100.0f;
        this.progress = 0.0f;
        init(context);
    }

    public CircleProgressBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.maxProgress = 100.0f;
        this.progress = 0.0f;
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i);
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            size = this.progressWidth + (this.outsideRadius * 2);
        }
        int size2 = View.MeasureSpec.getSize(i2);
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
            size2 = (this.outsideRadius * 2) + this.progressWidth;
        }
        setMeasuredDimension(size, size2);
    }

    private void init(Context context) {
        this.outsideRadius = AutoSizeUtils.dp2px(context, 46.0f);
        this.progressWidth = AutoSizeUtils.dp2px(context, 1.0f);
        this.context = context;
        Paint paint = new Paint();
        this.mBgPaint = paint;
        paint.setStrokeWidth(this.progressWidth);
        this.mBgPaint.setColor(getResources().getColor(R.color.progress_end_bg));
        this.mBgPaint.setAntiAlias(true);
        this.mBgPaint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint();
        this.mStepPaint = paint2;
        paint2.setStrokeWidth(this.progressWidth);
        this.mStepPaint.setColor(getResources().getColor(R.color.progress_start_bg));
        this.mStepPaint.setAntiAlias(true);
        this.mStepPaint.setStyle(Paint.Style.STROKE);
    }

    public void setProgress(float f) {
        this.progress = f;
        postInvalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2;
        canvas.drawCircle(width, width, this.outsideRadius, this.mBgPaint);
        RectF rectF = new RectF();
        int r0 = screenCenterX();
        int r1 = screenCenterY();
        rectF.left = r0 - this.outsideRadius;
        rectF.top = r1 - this.outsideRadius;
        rectF.right = r0 + this.outsideRadius;
        rectF.bottom = r1 + this.outsideRadius;
        canvas.drawArc(rectF, -90.0f, (this.progress / this.maxProgress) * 360.0f, false, this.mStepPaint);
    }

    private int screenCenterY() {
        return 300;
    }

    private int screenCenterX() {
        return 300;
    }
}
