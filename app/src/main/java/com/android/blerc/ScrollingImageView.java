package com.android.blerc;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import me.jessyan.autosize.utils.AutoSizeUtils;

/* loaded from: classes.dex */
public class ScrollingImageView extends RelativeLayout implements View.OnTouchListener, View.OnClickListener {
    private static final int SNAP_VELOCITY = 200;
    private static final String TAG = "ScrollingImageView";
    private int BottomEdge;
    int CLICK_TIME;
    private int CurrentEdge;
    private int[] borders;
    private Context context;
    private int currentItemIndex;
    private LinearLayout dotsLayout;
    private View firstItem;
    private ViewGroup.MarginLayoutParams firstItemParams;
    private boolean firstplay;
    GarageActivity garageActivity;
    private Handler handler;
    private int itemsCount;
    private LinearLayout itemsLayout;
    long lastClickTime;
    private VelocityTracker mVelocityTracker;
    int[] middle_float;
    private int previousItemIndex;
    private int scrollViewHeight;
    private int spacing;
    private int topEdge;
    private float yDown;
    private float yEventDown;
    private float yEventMove;
    private float yEventUp;
    private float yMove;
    private float yUp;

    private boolean shouldScrollToNext() {
        return true;
    }

    private boolean shouldScrollToPrevious() {
        return true;
    }

    static /* synthetic */ int access$908(ScrollingImageView scrollingImageView) {
        int i = scrollingImageView.currentItemIndex;
        scrollingImageView.currentItemIndex = i + 1;
        return i;
    }

    public ScrollingImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.currentItemIndex = 1;
        this.previousItemIndex = 0;
        this.topEdge = 0;
        this.BottomEdge = 0;
        this.handler = new Handler();
        this.middle_float = new int[]{0, 29, 59, 89, 114};
        this.firstplay = false;
        this.lastClickTime = 0L;
        this.CLICK_TIME = 500;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingImageView);
        if (typedArrayObtainStyledAttributes.getBoolean(0, false)) {
            startAutoPlay();
        }
        typedArrayObtainStyledAttributes.recycle();
        this.context = context;
        this.spacing = AutoSizeUtils.dp2px(context, 30.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToNext() {
        new ScrollTask().execute(-20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToPrevious() {
        new ScrollTask().execute(20);
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            initItems();
            initDots();
        }
    }

    private void initItems() {
        this.scrollViewHeight = getWidth();
        LinearLayout linearLayout = (LinearLayout) getChildAt(0);
        this.itemsLayout = linearLayout;
        int childCount = linearLayout.getChildCount();
        this.itemsCount = childCount;
        this.borders = new int[childCount];
        int i = 0;
        while (true) {
            int i2 = this.itemsCount;
            if (i < i2) {
                View childAt = this.itemsLayout.getChildAt(i);
                this.borders[i] = (-i) * (childAt.getHeight() + this.spacing);
                if (i != 0 && i != this.itemsCount - 1) {
                    childAt.setClickable(true);
                    childAt.setOnClickListener(this);
                }
                i++;
            } else {
                this.topEdge = this.borders[i2 - 3];
                View childAt2 = this.itemsLayout.getChildAt(0);
                this.firstItem = childAt2;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt2.getLayoutParams();
                this.firstItemParams = marginLayoutParams;
                marginLayoutParams.topMargin = this.borders[this.currentItemIndex];
                this.firstItem.setLayoutParams(this.firstItemParams);
                return;
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.lastClickTime > this.CLICK_TIME && !this.garageActivity.animator.isRunning()) {
            int iIndexOfChild = ((ViewGroup) view.getParent()).indexOfChild(view);
            Log.e(TAG, "index:" + iIndexOfChild);
            Log.e(TAG, "currentItemIndex:" + iIndexOfChild);
            int i = this.currentItemIndex;
            if (iIndexOfChild > i + 1) {
                scrollToNext();
            } else if (iIndexOfChild < i + 1) {
                scrollToPrevious();
            }
        }
        this.lastClickTime = jCurrentTimeMillis;
    }

    private void initDots() {
        this.garageActivity = (GarageActivity) this.context;
        refreshDotsLayout();
        RxView.clicks(this.garageActivity.arrow_up_layout).throttleFirst(20L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.ScrollingImageView.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (ScrollingImageView.this.garageActivity.animator.isRunning()) {
                    return;
                }
                ScrollingImageView.this.scrollToNext();
            }
        });
        RxView.clicks(this.garageActivity.arrow_down_layout).throttleFirst(20L, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.ScrollingImageView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                if (ScrollingImageView.this.garageActivity.animator.isRunning()) {
                    return;
                }
                ScrollingImageView.this.scrollToPrevious();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDotsLayout() {
        for (int i = 0; i < this.itemsCount; i++) {
            this.itemsLayout.getChildAt(i).setAlpha(0.5f);
        }
        this.itemsLayout.getChildAt(this.currentItemIndex + 1).setAlpha(1.0f);
        int i2 = this.currentItemIndex;
        if (i2 == 0) {
            this.garageActivity.car_arrow_up.setAlpha(1.0f);
            this.garageActivity.car_arrow_down.setAlpha(0.3f);
            this.garageActivity.arrow_up_layout.setClickable(true);
            this.garageActivity.arrow_down_layout.setClickable(false);
        } else if (i2 == this.itemsCount - 3) {
            this.garageActivity.car_arrow_up.setAlpha(0.3f);
            this.garageActivity.car_arrow_down.setAlpha(1.0f);
            this.garageActivity.arrow_up_layout.setClickable(false);
            this.garageActivity.arrow_down_layout.setClickable(true);
        } else {
            this.garageActivity.car_arrow_up.setAlpha(1.0f);
            this.garageActivity.car_arrow_down.setAlpha(1.0f);
            this.garageActivity.arrow_up_layout.setClickable(true);
            this.garageActivity.arrow_down_layout.setClickable(true);
        }
        int[] iArr = this.middle_float;
        float f = iArr[this.previousItemIndex] / 114.0f;
        float f2 = iArr[this.currentItemIndex] / 114.0f;
        if (!this.firstplay) {
            this.garageActivity.animationView.setProgress(f2);
        }
        if (this.garageActivity.animator.isRunning() || !this.firstplay) {
            return;
        }
        this.garageActivity.animator = ValueAnimator.ofFloat(f, f2).setDuration((long) (this.garageActivity.animationViewDuration * Math.abs(f - f2)));
        this.garageActivity.doAnimation();
    }

    private void createVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.garageActivity.animator.isRunning()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            this.yDown = motionEvent.getRawY();
            this.CurrentEdge = this.firstItemParams.topMargin;
            this.yEventDown = motionEvent.getY();
            return false;
        }
        if (action != 1) {
            if (action != 2) {
                return false;
            }
            this.yMove = motionEvent.getRawY();
            this.yEventMove = motionEvent.getY();
            if ((this.firstItemParams.topMargin == this.BottomEdge && this.yMove - this.yDown >= 0.0f) || !beAbleToScroll(this.CurrentEdge + ((int) (this.yMove - this.yDown))) || Math.abs(this.yMove - this.yDown) > AutoSizeUtils.dp2px(this.context, 72.0f)) {
                return false;
            }
            this.firstItemParams.topMargin = this.CurrentEdge + ((int) (this.yMove - this.yDown));
            this.firstItem.setLayoutParams(this.firstItemParams);
            return false;
        }
        this.yUp = motionEvent.getRawY();
        if ((this.firstItemParams.topMargin == this.BottomEdge && this.yMove - this.yDown >= 0.0f) || !beAbleToScroll()) {
            return false;
        }
        if (wantScrollToPrevious()) {
            if (shouldScrollToPrevious()) {
                scrollToPrevious();
                return false;
            }
            scrollToNext();
            return false;
        }
        if (!wantScrollToNext()) {
            return false;
        }
        if (shouldScrollToNext()) {
            scrollToNext();
            return false;
        }
        scrollToPrevious();
        return false;
    }

    private int getScrollVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000);
        return Math.abs((int) this.mVelocityTracker.getXVelocity());
    }

    private boolean wantScrollToPrevious() {
        return this.yUp > this.yDown;
    }

    private boolean wantScrollToNext() {
        return this.yUp < this.yDown;
    }

    private void recycleVelocityTracker() {
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
    }

    private boolean beAbleToScroll(int i) {
        return i < this.BottomEdge && i > this.topEdge;
    }

    private boolean beAbleToScroll() {
        return this.firstItemParams.topMargin < this.BottomEdge && this.firstItemParams.topMargin > this.topEdge;
    }

    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
        ScrollTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(Integer... numArr) {
            int iIntValue = ScrollingImageView.this.firstItemParams.topMargin;
            while (true) {
                iIntValue += numArr[0].intValue();
                if (!ScrollingImageView.this.isCrossBorder(iIntValue, numArr[0].intValue())) {
                    publishProgress(Integer.valueOf(iIntValue));
                    try {
                        ScrollingImageView.this.sleep(10L);
                    } catch (InterruptedException e) {

                    }
                } else {
                    return Integer.valueOf(ScrollingImageView.this.findClosestBorder(iIntValue));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... numArr) {
            ScrollingImageView.this.firstItemParams.topMargin = numArr[0].intValue();
            ScrollingImageView.this.firstItem.setLayoutParams(ScrollingImageView.this.firstItemParams);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            ScrollingImageView.this.firstItemParams.topMargin = num.intValue();
            ScrollingImageView.this.firstItem.setLayoutParams(ScrollingImageView.this.firstItemParams);
            ScrollingImageView.this.firstplay = true;
            ScrollingImageView.this.refreshDotsLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findClosestBorder(int i) {
        this.previousItemIndex = this.currentItemIndex;
        int iAbs = Math.abs(i);
        int i2 = 0;
        int i3 = this.borders[0];
        this.currentItemIndex = 0;
        int iAbs2 = Math.abs(Math.abs(i3) - iAbs);
        while (true) {
            int[] iArr = this.borders;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            int iAbs3 = Math.abs(Math.abs(i4) - iAbs);
            if (iAbs3 < iAbs2) {
                this.currentItemIndex = i2;
                i3 = i4;
                iAbs2 = iAbs3;
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCrossBorder(int i, int i2) {
        for (int i3 : this.borders) {
            if (i2 > 0) {
                if (i >= i3 && i - i2 < i3) {
                    return true;
                }
            } else if (i <= i3 && i - i2 > i3) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sleep(long j) throws InterruptedException {
        try {
            Thread.sleep(j);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAutoPlay() {
        new Timer().scheduleAtFixedRate(new TimerTask() { // from class: com.android.blerc.ScrollingImageView.3
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                if (ScrollingImageView.this.currentItemIndex == ScrollingImageView.this.itemsCount - 1) {
                    ScrollingImageView.this.currentItemIndex = 0;
                    ScrollingImageView.this.handler.post(new Runnable() { // from class: com.android.blerc.ScrollingImageView.3.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ScrollingImageView.this.scrollToFirstItem();
                            ScrollingImageView.this.refreshDotsLayout();
                        }
                    });
                } else {
                    ScrollingImageView.access$908(ScrollingImageView.this);
                    ScrollingImageView.this.handler.post(new Runnable() { // from class: com.android.blerc.ScrollingImageView.3.2
                        @Override // java.lang.Runnable
                        public void run() {
                            ScrollingImageView.this.scrollToNext();
                            ScrollingImageView.this.refreshDotsLayout();
                        }
                    });
                }
            }
        }, 3000L, 3000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scrollToFirstItem() {
        new ScrollToFirstItemTask().execute(Integer.valueOf(this.itemsCount * 20));
    }

    class ScrollToFirstItemTask extends AsyncTask<Integer, Integer, Integer> {
        ScrollToFirstItemTask() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Integer doInBackground(Integer... numArr) {
            int iIntValue = ScrollingImageView.this.firstItemParams.topMargin;
            while (true) {
                iIntValue += numArr[0].intValue();
                if (iIntValue <= 0) {
                    publishProgress(Integer.valueOf(iIntValue));
                    try {
                        ScrollingImageView.this.sleep(20L);
                    } catch (InterruptedException e) {

                    }
                } else {
                    return 0;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onProgressUpdate(Integer... numArr) {
            ScrollingImageView.this.firstItemParams.topMargin = numArr[0].intValue();
            ScrollingImageView.this.firstItem.setLayoutParams(ScrollingImageView.this.firstItemParams);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPostExecute(Integer num) {
            ScrollingImageView.this.firstItemParams.topMargin = num.intValue();
            ScrollingImageView.this.firstItem.setLayoutParams(ScrollingImageView.this.firstItemParams);
        }
    }
}
