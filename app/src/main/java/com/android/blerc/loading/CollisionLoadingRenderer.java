package com.android.blerc.loading;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public class CollisionLoadingRenderer extends LoadingRenderer {
    private static final int DEFAULT_BALL_COUNT = 7;
    private static final float DEFAULT_BALL_RADIUS = 7.5f;
    private static final float DEFAULT_HEIGHT = 60.0f;
    private static final float DEFAULT_OVAL_HEIGHT = 1.5f;
    private static final float DEFAULT_WIDTH = 165.0f;
    private static final float END_RIGHT_DURATION_OFFSET = 0.75f;
    private static final int MAX_ALPHA = 255;
    private static final int OVAL_ALPHA = 64;
    private static final float START_LEFT_DURATION_OFFSET = 0.25f;
    private static final float START_RIGHT_DURATION_OFFSET = 0.5f;
    private float mBallCenterY;
    private int mBallCount;
    private float mBallMoveXOffsets;
    private float mBallQuadCoefficient;
    private float mBallRadius;
    private float mBallSideOffsets;
    private int[] mColors;
    private float mLeftBallMoveXOffsets;
    private float mLeftBallMoveYOffsets;
    private float mLeftOvalShapeRate;
    private final RectF mOvalRect;
    private float mOvalVerticalRadius;
    private final Paint mPaint;
    private float[] mPositions;
    private float mRightBallMoveXOffsets;
    private float mRightBallMoveYOffsets;
    private float mRightOvalShapeRate;
    private static final Interpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final Interpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final int[] DEFAULT_COLORS = {Color.parseColor("#FF28435D"), Color.parseColor("#FFC32720")};
    private static final float END_LEFT_DURATION_OFFSET = 1.0f;
    private static final float[] DEFAULT_POSITIONS = {0.0f, END_LEFT_DURATION_OFFSET};

    @Override // com.android.blerc.loading.LoadingRenderer
    protected void reset() {
    }

    private CollisionLoadingRenderer(Context context) {
        super(context);
        this.mPaint = new Paint(1);
        this.mOvalRect = new RectF();
        init(context);
        adjustParams();
        setupPaint();
    }

    private void init(Context context) {
        this.mBallRadius = DensityUtil.dip2px(context, DEFAULT_BALL_RADIUS);
        this.mWidth = DensityUtil.dip2px(context, DEFAULT_WIDTH);
        this.mHeight = DensityUtil.dip2px(context, DEFAULT_HEIGHT);
        this.mOvalVerticalRadius = DensityUtil.dip2px(context, DEFAULT_OVAL_HEIGHT);
        this.mColors = DEFAULT_COLORS;
        this.mPositions = DEFAULT_POSITIONS;
        this.mBallCount = 7;
        float f = this.mBallRadius * 2.0f * DEFAULT_OVAL_HEIGHT;
        this.mBallMoveXOffsets = f;
        this.mBallQuadCoefficient = END_LEFT_DURATION_OFFSET / f;
    }

    private void adjustParams() {
        this.mBallCenterY = this.mHeight / 2.0f;
        this.mBallSideOffsets = (this.mWidth - ((this.mBallRadius * 2.0f) * (this.mBallCount - 2))) / 2.0f;
    }

    private void setupPaint() {
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setShader(new LinearGradient(this.mBallSideOffsets, 0.0f, this.mWidth - this.mBallSideOffsets, 0.0f, this.mColors, this.mPositions, Shader.TileMode.CLAMP));
    }

    @Override // com.android.blerc.loading.LoadingRenderer
    protected void draw(Canvas canvas) {
        int r5 = 200; // TODO wuyijun
        int iSave = canvas.save();
        for (int i = 1; i < this.mBallCount - 1; i++) {
            this.mPaint.setAlpha(255);
            float f = this.mBallRadius;
            canvas.drawCircle(((r5 - 1) * f) + this.mBallSideOffsets, this.mBallCenterY, f, this.mPaint);
            this.mOvalRect.set((this.mBallRadius * (r5 - 2)) + this.mBallSideOffsets, this.mHeight - (this.mOvalVerticalRadius * 2.0f), (this.mBallRadius * i * 2) + this.mBallSideOffsets, this.mHeight);
            this.mPaint.setAlpha(64);
            canvas.drawOval(this.mOvalRect, this.mPaint);
        }
        this.mPaint.setAlpha(255);
        float f2 = this.mBallSideOffsets;
        float f3 = this.mBallRadius;
        canvas.drawCircle((f2 - f3) - this.mLeftBallMoveXOffsets, this.mBallCenterY - this.mLeftBallMoveYOffsets, f3, this.mPaint);
        RectF rectF = this.mOvalRect;
        float f4 = this.mBallSideOffsets;
        float f5 = this.mBallRadius;
        float f6 = ((f4 - f5) - (f5 * this.mLeftOvalShapeRate)) - this.mLeftBallMoveXOffsets;
        float f7 = this.mHeight;
        float f8 = this.mOvalVerticalRadius;
        float f9 = this.mLeftOvalShapeRate;
        float f10 = (f7 - f8) - (f8 * f9);
        float f11 = this.mBallSideOffsets;
        float f12 = this.mBallRadius;
        float f13 = ((f11 - f12) + (f12 * f9)) - this.mLeftBallMoveXOffsets;
        float f14 = this.mHeight;
        float f15 = this.mOvalVerticalRadius;
        rectF.set(f6, f10, f13, (f14 - f15) + (f15 * this.mLeftOvalShapeRate));
        this.mPaint.setAlpha(64);
        canvas.drawOval(this.mOvalRect, this.mPaint);
        this.mPaint.setAlpha(255);
        float f16 = this.mBallRadius;
        canvas.drawCircle((((this.mBallCount * 2) - 3) * f16) + this.mBallSideOffsets + this.mRightBallMoveXOffsets, this.mBallCenterY - this.mRightBallMoveYOffsets, f16, this.mPaint);
        RectF rectF2 = this.mOvalRect;
        float f17 = this.mBallRadius;
        float f18 = ((((this.mBallCount * 2) - 3) * f17) - (f17 * this.mRightOvalShapeRate)) + this.mBallSideOffsets + this.mRightBallMoveXOffsets;
        float f19 = this.mHeight;
        float f20 = this.mOvalVerticalRadius;
        float f21 = this.mRightOvalShapeRate;
        float f22 = (f19 - f20) - (f20 * f21);
        float f23 = this.mBallRadius;
        float f24 = (((this.mBallCount * 2) - 3) * f23) + (f23 * f21) + this.mBallSideOffsets + this.mRightBallMoveXOffsets;
        float f25 = this.mHeight;
        float f26 = this.mOvalVerticalRadius;
        rectF2.set(f18, f22, f24, (f25 - f26) + (f26 * this.mRightOvalShapeRate));
        this.mPaint.setAlpha(64);
        canvas.drawOval(this.mOvalRect, this.mPaint);
        canvas.restoreToCount(iSave);
    }

    @Override // com.android.blerc.loading.LoadingRenderer
    protected void computeRender(float f) {
        if (f <= START_LEFT_DURATION_OFFSET) {
            computeLeftBallMoveOffsets(DECELERATE_INTERPOLATOR.getInterpolation(f / START_LEFT_DURATION_OFFSET));
            return;
        }
        if (f <= START_RIGHT_DURATION_OFFSET) {
            computeLeftBallMoveOffsets(ACCELERATE_INTERPOLATOR.getInterpolation(END_LEFT_DURATION_OFFSET - ((f - START_LEFT_DURATION_OFFSET) / START_LEFT_DURATION_OFFSET)));
        } else if (f <= END_RIGHT_DURATION_OFFSET) {
            computeRightBallMoveOffsets(DECELERATE_INTERPOLATOR.getInterpolation((f - START_RIGHT_DURATION_OFFSET) / START_LEFT_DURATION_OFFSET));
        } else if (f <= END_LEFT_DURATION_OFFSET) {
            computeRightBallMoveOffsets(ACCELERATE_INTERPOLATOR.getInterpolation(END_LEFT_DURATION_OFFSET - ((f - END_RIGHT_DURATION_OFFSET) / START_LEFT_DURATION_OFFSET)));
        }
    }

    private void computeLeftBallMoveOffsets(float f) {
        this.mRightBallMoveXOffsets = 0.0f;
        this.mRightBallMoveYOffsets = 0.0f;
        this.mLeftOvalShapeRate = END_LEFT_DURATION_OFFSET - f;
        float f2 = this.mBallMoveXOffsets * f;
        this.mLeftBallMoveXOffsets = f2;
        double dPow = Math.pow(f2, 2.0d);
        double d = this.mBallQuadCoefficient;
        Double.isNaN(d);
        this.mLeftBallMoveYOffsets = (float) (dPow * d);
    }

    private void computeRightBallMoveOffsets(float f) {
        this.mLeftBallMoveXOffsets = 0.0f;
        this.mLeftBallMoveYOffsets = 0.0f;
        this.mRightOvalShapeRate = END_LEFT_DURATION_OFFSET - f;
        float f2 = this.mBallMoveXOffsets * f;
        this.mRightBallMoveXOffsets = f2;
        double dPow = Math.pow(f2, 2.0d);
        double d = this.mBallQuadCoefficient;
        Double.isNaN(d);
        this.mRightBallMoveYOffsets = (float) (dPow * d);
    }

    @Override // com.android.blerc.loading.LoadingRenderer
    protected void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // com.android.blerc.loading.LoadingRenderer
    protected void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void apply(Builder builder) {
        this.mWidth = builder.mWidth > 0 ? builder.mWidth : this.mWidth;
        this.mHeight = builder.mHeight > 0 ? builder.mHeight : this.mHeight;
        this.mOvalVerticalRadius = builder.mOvalVerticalRadius > 0.0f ? builder.mOvalVerticalRadius : this.mOvalVerticalRadius;
        this.mBallRadius = builder.mBallRadius > 0.0f ? builder.mBallRadius : this.mBallRadius;
        this.mBallMoveXOffsets = builder.mBallMoveXOffsets > 0.0f ? builder.mBallMoveXOffsets : this.mBallMoveXOffsets;
        this.mBallQuadCoefficient = builder.mBallQuadCoefficient > 0.0f ? builder.mBallQuadCoefficient : this.mBallQuadCoefficient;
        this.mBallCount = builder.mBallCount > 0 ? builder.mBallCount : this.mBallCount;
        this.mDuration = builder.mDuration > 0 ? builder.mDuration : this.mDuration;
        this.mColors = builder.mColors != null ? builder.mColors : this.mColors;
        adjustParams();
        setupPaint();
    }

    public static class Builder {
        private int mBallCount;
        private float mBallMoveXOffsets;
        private float mBallQuadCoefficient;
        private float mBallRadius;
        private int[] mColors;
        private Context mContext;
        private int mDuration;
        private int mHeight;
        private float mOvalVerticalRadius;
        private int mWidth;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setWidth(int i) {
            this.mWidth = i;
            return this;
        }

        public Builder setHeight(int i) {
            this.mHeight = i;
            return this;
        }

        public Builder setOvalVerticalRadius(int i) {
            this.mOvalVerticalRadius = i;
            return this;
        }

        public Builder setBallRadius(int i) {
            this.mBallRadius = i;
            return this;
        }

        public Builder setBallMoveXOffsets(int i) {
            this.mBallMoveXOffsets = i;
            return this;
        }

        public Builder setBallQuadCoefficient(int i) {
            this.mBallQuadCoefficient = i;
            return this;
        }

        public Builder setBallCount(int i) {
            this.mBallCount = i;
            return this;
        }

        public Builder setColors(int[] iArr) {
            this.mColors = iArr;
            return this;
        }

        public Builder setDuration(int i) {
            this.mDuration = i;
            return this;
        }

        public CollisionLoadingRenderer build() {
            CollisionLoadingRenderer collisionLoadingRenderer = new CollisionLoadingRenderer(this.mContext);
            collisionLoadingRenderer.apply(this);
            return collisionLoadingRenderer;
        }
    }
}
