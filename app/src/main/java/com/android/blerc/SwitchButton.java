package com.android.blerc;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import com.blankj.utilcode.constant.MemoryConstants;

/* loaded from: classes.dex */
public class SwitchButton extends View implements Checkable {
    private final int ANIMATE_STATE_DRAGING;
    private final int ANIMATE_STATE_NONE;
    private final int ANIMATE_STATE_PENDING_DRAG;
    private final int ANIMATE_STATE_PENDING_RESET;
    private final int ANIMATE_STATE_PENDING_SETTLE;
    private final int ANIMATE_STATE_SWITCH;
    private ViewState afterState;
    private int animateState;
    private Animator.AnimatorListener animatorListener;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private final ArgbEvaluator argbEvaluator;
    private int background;
    private ViewState beforeState;
    private int borderWidth;
    private float bottom;
    private int buttonColor;
    private float buttonMaxX;
    private float buttonMinX;
    private Paint buttonPaint;
    private float buttonRadius;
    Canvas canvas;
    private float centerX;
    private float centerY;
    private int checkLineColor;
    private float checkLineLength;
    private int checkLineWidth;
    private int checkedColor;
    private float checkedLineOffsetX;
    private float checkedLineOffsetY;
    private boolean enableEffect;
    private float height;
    private boolean isChecked;
    private boolean isEventBroadcast;
    private boolean isTouchingDown;
    private boolean isUiInited;
    private float left;
    private OnCheckedChangeListener onCheckedChangeListener;
    private Paint paint;
    private Runnable postPendingDrag;
    private RectF rect;
    private float right;
    private int shadowColor;
    private boolean shadowEffect;
    private int shadowOffset;
    private int shadowRadius;
    private boolean showIndicator;
    private float top;
    private long touchDownTime;
    private int uncheckCircleColor;
    private float uncheckCircleOffsetX;
    private float uncheckCircleRadius;
    private int uncheckCircleWidth;
    private int uncheckColor;
    private ValueAnimator valueAnimator;
    private float viewRadius;
    private ViewState viewState;
    private float width;
    private static final int DEFAULT_WIDTH = dp2pxInt(58.0f);
    private static final int DEFAULT_HEIGHT = dp2pxInt(36.0f);

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SwitchButton switchButton, boolean z);
    }

    @Override // android.view.View
    public final void setOnClickListener(View.OnClickListener onClickListener) {
    }

    @Override // android.view.View
    public final void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
    }

    public SwitchButton(Context context) {
        super(context);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.canvas = null;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.android.blerc.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i = SwitchButton.this.animateState;
                if (i == 1 || i == 3 || i == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * fFloatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.android.blerc.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                int i = SwitchButton.this.animateState;
                if (i == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, null);
    }

    public SwitchButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.canvas = null;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.android.blerc.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i = SwitchButton.this.animateState;
                if (i == 1 || i == 3 || i == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * fFloatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.android.blerc.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                int i = SwitchButton.this.animateState;
                if (i == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, attributeSet);
    }

    public SwitchButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.canvas = null;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.android.blerc.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i2 = SwitchButton.this.animateState;
                if (i2 == 1 || i2 == 3 || i2 == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * fFloatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i2 == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.android.blerc.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                int i2 = SwitchButton.this.animateState;
                if (i2 == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i2 == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i2 == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i2 != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, attributeSet);
    }

    public SwitchButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.ANIMATE_STATE_NONE = 0;
        this.ANIMATE_STATE_PENDING_DRAG = 1;
        this.ANIMATE_STATE_DRAGING = 2;
        this.ANIMATE_STATE_PENDING_RESET = 3;
        this.ANIMATE_STATE_PENDING_SETTLE = 4;
        this.ANIMATE_STATE_SWITCH = 5;
        this.canvas = null;
        this.rect = new RectF();
        this.animateState = 0;
        this.argbEvaluator = new ArgbEvaluator();
        this.isTouchingDown = false;
        this.isUiInited = false;
        this.isEventBroadcast = false;
        this.postPendingDrag = new Runnable() { // from class: com.android.blerc.SwitchButton.1
            @Override // java.lang.Runnable
            public void run() {
                if (SwitchButton.this.isInAnimating()) {
                    return;
                }
                SwitchButton.this.pendingDragState();
            }
        };
        this.animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.blerc.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                int i22 = SwitchButton.this.animateState;
                if (i22 == 1 || i22 == 3 || i22 == 4) {
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkedLineColor), Integer.valueOf(SwitchButton.this.afterState.checkedLineColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.beforeState.radius + ((SwitchButton.this.afterState.radius - SwitchButton.this.beforeState.radius) * fFloatValue);
                    if (SwitchButton.this.animateState != 1) {
                        SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    }
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(fFloatValue, Integer.valueOf(SwitchButton.this.beforeState.checkStateColor), Integer.valueOf(SwitchButton.this.afterState.checkStateColor))).intValue();
                } else if (i22 == 5) {
                    SwitchButton.this.viewState.buttonX = SwitchButton.this.beforeState.buttonX + ((SwitchButton.this.afterState.buttonX - SwitchButton.this.beforeState.buttonX) * fFloatValue);
                    float f = (SwitchButton.this.viewState.buttonX - SwitchButton.this.buttonMinX) / (SwitchButton.this.buttonMaxX - SwitchButton.this.buttonMinX);
                    SwitchButton.this.viewState.checkStateColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, Integer.valueOf(SwitchButton.this.uncheckColor), Integer.valueOf(SwitchButton.this.checkedColor))).intValue();
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius * f;
                    SwitchButton.this.viewState.checkedLineColor = ((Integer) SwitchButton.this.argbEvaluator.evaluate(f, 0, Integer.valueOf(SwitchButton.this.checkLineColor))).intValue();
                }
                SwitchButton.this.postInvalidate();
            }
        };
        this.animatorListener = new Animator.AnimatorListener() { // from class: com.android.blerc.SwitchButton.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                int i22 = SwitchButton.this.animateState;
                if (i22 == 1) {
                    SwitchButton.this.animateState = 2;
                    SwitchButton.this.viewState.checkedLineColor = 0;
                    SwitchButton.this.viewState.radius = SwitchButton.this.viewRadius;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i22 == 3) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    return;
                }
                if (i22 == 4) {
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                } else {
                    if (i22 != 5) {
                        return;
                    }
                    SwitchButton switchButton = SwitchButton.this;
                    switchButton.isChecked = true ^ switchButton.isChecked;
                    SwitchButton.this.animateState = 0;
                    SwitchButton.this.postInvalidate();
                    SwitchButton.this.broadcastEvent();
                }
            }
        };
        init(context, attributeSet);
    }

    @Override // android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(0, 0, 0, 0);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = attributeSet != null ? context.obtainStyledAttributes(attributeSet, com.suke.widget.R.styleable.SwitchButton) : null;
        this.shadowEffect = optBoolean(typedArrayObtainStyledAttributes, 10, true);
        this.uncheckCircleColor = optColor(typedArrayObtainStyledAttributes, 15, -5592406);
        this.uncheckCircleWidth = optPixelSize(typedArrayObtainStyledAttributes, 17, dp2pxInt(1.5f));
        this.uncheckCircleOffsetX = dp2px(10.0f);
        this.uncheckCircleRadius = optPixelSize(typedArrayObtainStyledAttributes, 16, dp2px(4.0f));
        this.checkedLineOffsetX = dp2px(4.0f);
        this.checkedLineOffsetY = dp2px(4.0f);
        this.shadowRadius = optPixelSize(typedArrayObtainStyledAttributes, 12, dp2pxInt(2.5f));
        this.shadowOffset = optPixelSize(typedArrayObtainStyledAttributes, 11, dp2pxInt(1.5f));
        this.shadowColor = optColor(typedArrayObtainStyledAttributes, 9, 855638016);
        this.uncheckColor = optColor(typedArrayObtainStyledAttributes, 14, -2236963);
        this.checkedColor = optColor(typedArrayObtainStyledAttributes, 4, -11414681);
        this.borderWidth = optPixelSize(typedArrayObtainStyledAttributes, 1, dp2pxInt(1.0f));
        this.checkLineColor = optColor(typedArrayObtainStyledAttributes, 5, -1);
        this.checkLineWidth = optPixelSize(typedArrayObtainStyledAttributes, 6, dp2pxInt(1.0f));
        this.checkLineLength = dp2px(6.0f);
        this.buttonColor = optColor(typedArrayObtainStyledAttributes, 2, -1);
        int iOptInt = optInt(typedArrayObtainStyledAttributes, 7, 300);
        this.isChecked = optBoolean(typedArrayObtainStyledAttributes, 3, false);
        this.showIndicator = optBoolean(typedArrayObtainStyledAttributes, 13, true);
        this.background = optColor(typedArrayObtainStyledAttributes, 0, -1);
        this.enableEffect = optBoolean(typedArrayObtainStyledAttributes, 8, true);
        if (typedArrayObtainStyledAttributes != null) {
            typedArrayObtainStyledAttributes.recycle();
        }
        this.paint = new Paint(1);
        Paint paint = new Paint(1);
        this.buttonPaint = paint;
        paint.setColor(this.buttonColor);
        if (this.shadowEffect) {
            this.buttonPaint.setShadowLayer(this.shadowRadius, 0.0f, this.shadowOffset, this.shadowColor);
        }
        this.viewState = new ViewState();
        this.beforeState = new ViewState();
        this.afterState = new ViewState();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.valueAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(iOptInt);
        this.valueAnimator.setRepeatCount(0);
        this.valueAnimator.addUpdateListener(this.animatorUpdateListener);
        this.valueAnimator.addListener(this.animatorListener);
        super.setClickable(true);
        setPadding(0, 0, 0, 0);
        if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        if (mode == 0 || mode == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec(DEFAULT_WIDTH, MemoryConstants.GB);
        }
        if (mode2 == 0 || mode2 == Integer.MIN_VALUE) {
            i2 = View.MeasureSpec.makeMeasureSpec(DEFAULT_HEIGHT, MemoryConstants.GB);
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        float fMax = Math.max(this.shadowRadius + this.shadowOffset, this.borderWidth);
        float f = i2 - fMax;
        float f2 = f - fMax;
        this.height = f2;
        float f3 = i - fMax;
        this.width = f3 - fMax;
        float f4 = f2 * 0.5f;
        this.viewRadius = f4;
        this.buttonRadius = f4 - dp2pxInt(3.0f);
        this.left = fMax;
        this.top = fMax;
        this.right = f3;
        this.bottom = f;
        this.centerX = (fMax + f3) * 0.5f;
        this.centerY = (f + fMax) * 0.5f;
        float f5 = this.viewRadius;
        this.buttonMinX = fMax + f5;
        this.buttonMaxX = f3 - f5;
        if (isChecked()) {
            setCheckedViewState(this.viewState);
        } else {
            setUncheckViewState(this.viewState);
        }
        this.isUiInited = true;
        postInvalidate();
    }

    private void setUncheckViewState(ViewState viewState) {
        viewState.radius = 0.0f;
        viewState.checkStateColor = this.uncheckColor;
        viewState.checkedLineColor = 0;
        viewState.buttonX = this.buttonMinX;
    }

    private void setCheckedViewState(ViewState viewState) {
        viewState.radius = this.viewRadius;
        viewState.checkStateColor = this.checkedColor;
        viewState.checkedLineColor = this.checkLineColor;
        viewState.buttonX = this.buttonMaxX;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        this.paint.setAntiAlias(true);
        if (this.showIndicator) {
            drawUncheckIndicator(canvas);
        }
        float f = this.viewState.radius * 0.5f;
        this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.paint.setColor(this.viewState.checkStateColor);
        this.paint.setStrokeWidth(this.borderWidth + (2.0f * f));
        drawRoundRect(canvas, this.left + f, this.top + f, this.right - f, this.bottom - f, this.viewRadius, this.paint);
        if (this.showIndicator) {
            drawCheckedIndicator(canvas);
        }
        drawButton(canvas, this.viewState.buttonX, this.centerY);
    }

    protected void drawCheckedIndicator(Canvas canvas) {
        int i = this.viewState.checkedLineColor;
        float f = this.checkLineWidth;
        float f2 = this.left;
        float f3 = this.viewRadius;
        float f4 = (f2 + f3) - this.checkedLineOffsetX;
        float f5 = this.centerY;
        float f6 = this.checkLineLength;
        drawCheckedIndicator(canvas, i, f, f4, f5 - f6, (f2 + f3) - this.checkedLineOffsetY, f5 + f6, this.paint);
    }

    protected void drawCheckedIndicator(Canvas canvas, int i, float f, float f2, float f3, float f4, float f5, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(i);
        paint.setStrokeWidth(f);
        canvas.drawLine(f2, f3, f4, f5, paint);
    }

    private void drawUncheckIndicator(Canvas canvas) {
        drawUncheckIndicator(canvas, this.uncheckCircleColor, this.uncheckCircleWidth, this.right - this.uncheckCircleOffsetX, this.centerY, this.uncheckCircleRadius, this.paint);
    }

    protected void drawUncheckIndicator(Canvas canvas, int i, float f, float f2, float f3, float f4, Paint paint) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(i);
        paint.setStrokeWidth(f);
        canvas.drawCircle(f2, f3, f4, paint);
    }

    private void drawArc(Canvas canvas, float f, float f2, float f3, float f4, float f5, float f6, Paint paint) {
        if (Build.VERSION.SDK_INT >= 21) {
            canvas.drawArc(f, f2, f3, f4, f5, f6, true, paint);
        } else {
            this.rect.set(f, f2, f3, f4);
            canvas.drawArc(this.rect, f5, f6, true, paint);
        }
    }

    private void drawRoundRect(Canvas canvas, float f, float f2, float f3, float f4, float f5, Paint paint) {
        if (Build.VERSION.SDK_INT >= 21) {
            canvas.drawRoundRect(f, f2, f3, f4, f5, f5, paint);
        } else {
            this.rect.set(f, f2, f3, f4);
            canvas.drawRoundRect(this.rect, f5, f5, paint);
        }
    }

    private void drawButton(Canvas canvas, float f, float f2) {
        if (this.isChecked) {
            this.buttonPaint.setColor(-1);
        } else {
            this.buttonPaint.setColor(this.buttonColor);
        }
        this.buttonPaint.setAntiAlias(true);
        canvas.drawCircle(f, f2, this.buttonRadius, this.buttonPaint);
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (z == isChecked()) {
            postInvalidate();
        } else {
            toggle(this.enableEffect, false);
        }
    }

    @Override // android.widget.Checkable
    public boolean isChecked() {
        return this.isChecked;
    }

    @Override // android.widget.Checkable
    public void toggle() {
        toggle(true);
    }

    public void toggle(boolean z) {
        toggle(z, true);
    }

    private void toggle(boolean z, boolean z2) {
        if (isEnabled()) {
            if (this.isEventBroadcast) {
                throw new RuntimeException("should NOT switch the state in method: [onCheckedChanged]!");
            }
            if (!this.isUiInited) {
                this.isChecked = !this.isChecked;
                if (z2) {
                    broadcastEvent();
                    return;
                }
                return;
            }
            if (this.valueAnimator.isRunning()) {
                this.valueAnimator.cancel();
            }
            if (!this.enableEffect || !z) {
                this.isChecked = !this.isChecked;
                if (isChecked()) {
                    setCheckedViewState(this.viewState);
                } else {
                    setUncheckViewState(this.viewState);
                }
                postInvalidate();
                if (z2) {
                    broadcastEvent();
                    return;
                }
                return;
            }
            this.animateState = 5;
            this.beforeState.copy(this.viewState);
            if (isChecked()) {
                setUncheckViewState(this.afterState);
            } else {
                setCheckedViewState(this.afterState);
            }
            this.valueAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void broadcastEvent() {
        OnCheckedChangeListener onCheckedChangeListener = this.onCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            this.isEventBroadcast = true;
            onCheckedChangeListener.onCheckedChanged(this, isChecked());
        }
        this.isEventBroadcast = false;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.isTouchingDown = true;
            this.touchDownTime = System.currentTimeMillis();
            removeCallbacks(this.postPendingDrag);
            postDelayed(this.postPendingDrag, 100L);
        } else if (actionMasked == 1) {
            this.isTouchingDown = false;
            removeCallbacks(this.postPendingDrag);
            if (System.currentTimeMillis() - this.touchDownTime <= 300) {
                toggle();
            } else if (isDragState()) {
                boolean z = Math.max(0.0f, Math.min(1.0f, motionEvent.getX() / ((float) getWidth()))) > 0.5f;
                if (z == isChecked()) {
                    pendingCancelDragState();
                } else {
                    this.isChecked = z;
                    pendingSettleState();
                }
            } else if (isPendingDragState()) {
                pendingCancelDragState();
            }
        } else if (actionMasked == 2) {
            float x = motionEvent.getX();
            if (isPendingDragState()) {
                float fMax = Math.max(0.0f, Math.min(1.0f, x / getWidth()));
                ViewState viewState = this.viewState;
                float f = this.buttonMinX;
                viewState.buttonX = f + ((this.buttonMaxX - f) * fMax);
            } else if (isDragState()) {
                float fMax2 = Math.max(0.0f, Math.min(1.0f, x / getWidth()));
                ViewState viewState2 = this.viewState;
                float f2 = this.buttonMinX;
                viewState2.buttonX = f2 + ((this.buttonMaxX - f2) * fMax2);
                this.viewState.checkStateColor = ((Integer) this.argbEvaluator.evaluate(fMax2, Integer.valueOf(this.uncheckColor), Integer.valueOf(this.checkedColor))).intValue();
                postInvalidate();
            }
        } else if (actionMasked == 3) {
            this.isTouchingDown = false;
            removeCallbacks(this.postPendingDrag);
            if (isPendingDragState() || isDragState()) {
                pendingCancelDragState();
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInAnimating() {
        return this.animateState != 0;
    }

    private boolean isPendingDragState() {
        int i = this.animateState;
        return i == 1 || i == 3;
    }

    private boolean isDragState() {
        return this.animateState == 2;
    }

    public void setShadowEffect(boolean z) {
        if (this.shadowEffect == z) {
            return;
        }
        this.shadowEffect = z;
        if (z) {
            this.buttonPaint.setShadowLayer(this.shadowRadius, 0.0f, this.shadowOffset, this.shadowColor);
        } else {
            this.buttonPaint.setShadowLayer(0.0f, 0.0f, 0.0f, 0);
        }
    }

    public void setEnableEffect(boolean z) {
        this.enableEffect = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pendingDragState() {
        if (!isInAnimating() && this.isTouchingDown) {
            if (this.valueAnimator.isRunning()) {
                this.valueAnimator.cancel();
            }
            this.animateState = 1;
            this.beforeState.copy(this.viewState);
            this.afterState.copy(this.viewState);
            if (isChecked()) {
                this.afterState.checkStateColor = this.checkedColor;
                this.afterState.buttonX = this.buttonMaxX;
                this.afterState.checkedLineColor = this.checkedColor;
            } else {
                this.afterState.checkStateColor = this.uncheckColor;
                this.afterState.buttonX = this.buttonMinX;
                this.afterState.radius = this.viewRadius;
            }
            this.valueAnimator.start();
        }
    }

    private void pendingCancelDragState() {
        if (isDragState() || isPendingDragState()) {
            if (this.valueAnimator.isRunning()) {
                this.valueAnimator.cancel();
            }
            this.animateState = 3;
            this.beforeState.copy(this.viewState);
            if (isChecked()) {
                setCheckedViewState(this.afterState);
            } else {
                setUncheckViewState(this.afterState);
            }
            this.valueAnimator.start();
        }
    }

    private void pendingSettleState() {
        if (this.valueAnimator.isRunning()) {
            this.valueAnimator.cancel();
        }
        this.animateState = 4;
        this.beforeState.copy(this.viewState);
        if (isChecked()) {
            setCheckedViewState(this.afterState);
        } else {
            setUncheckViewState(this.afterState);
        }
        this.valueAnimator.start();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    private static float dp2px(float f) {
        return TypedValue.applyDimension(1, f, Resources.getSystem().getDisplayMetrics());
    }

    private static int dp2pxInt(float f) {
        return (int) dp2px(f);
    }

    private static int optInt(TypedArray typedArray, int i, int i2) {
        return typedArray == null ? i2 : typedArray.getInt(i, i2);
    }

    private static float optPixelSize(TypedArray typedArray, int i, float f) {
        return typedArray == null ? f : typedArray.getDimension(i, f);
    }

    private static int optPixelSize(TypedArray typedArray, int i, int i2) {
        return typedArray == null ? i2 : typedArray.getDimensionPixelOffset(i, i2);
    }

    private static int optColor(TypedArray typedArray, int i, int i2) {
        return typedArray == null ? i2 : typedArray.getColor(i, i2);
    }

    private static boolean optBoolean(TypedArray typedArray, int i, boolean z) {
        return typedArray == null ? z : typedArray.getBoolean(i, z);
    }

    private static class ViewState {
        float buttonX;
        int checkStateColor;
        int checkedLineColor;
        float radius;

        ViewState() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void copy(ViewState viewState) {
            this.buttonX = viewState.buttonX;
            this.checkStateColor = viewState.checkStateColor;
            this.checkedLineColor = viewState.checkedLineColor;
            this.radius = viewState.radius;
        }
    }
}
