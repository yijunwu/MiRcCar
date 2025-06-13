package com.android.blerc;

import android.graphics.Bitmap;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class FrameAnimationBitmap {
    private static final int SELECTED_A = 1;
    private static final int SELECTED_B = 2;
    private static final int SELECTED_C = 3;
    private static final int SELECTED_D = 4;
    private AnimationListener mAnimationListener;
    private int mCurrentFrame;
    private int mCurrentSelect;
    private int mDelay;
    private int mDuration;
    private int[] mDurations;
    private Bitmap[] mFrameRess;
    private ImageView mImageView;
    private boolean mIsRepeat;
    private int mLastFrame;
    private boolean mNext;
    private boolean mPause;

    public interface AnimationListener {
        void onAnimationEnd();

        void onAnimationRepeat();

        void onAnimationStart();
    }

    public FrameAnimationBitmap(ImageView imageView, Bitmap[] bitmapArr, int i, boolean z) {
        this.mImageView = imageView;
        this.mFrameRess = bitmapArr;
        this.mDuration = i;
        this.mLastFrame = bitmapArr.length - 1;
        this.mIsRepeat = z;
        play(0);
    }

    public FrameAnimationBitmap(ImageView imageView, Bitmap[] bitmapArr, int[] iArr, boolean z) {
        this.mImageView = imageView;
        this.mFrameRess = bitmapArr;
        this.mDurations = iArr;
        this.mLastFrame = bitmapArr.length - 1;
        this.mIsRepeat = z;
        playByDurations(0);
    }

    public FrameAnimationBitmap(ImageView imageView, Bitmap[] bitmapArr, int i, int i2) {
        this.mImageView = imageView;
        this.mFrameRess = bitmapArr;
        this.mDuration = i;
        this.mDelay = i2;
        this.mLastFrame = bitmapArr.length - 1;
        playAndDelay(0);
    }

    public FrameAnimationBitmap(ImageView imageView, Bitmap[] bitmapArr, int[] iArr, int i) {
        this.mImageView = imageView;
        this.mFrameRess = bitmapArr;
        this.mDurations = iArr;
        this.mDelay = i;
        this.mLastFrame = bitmapArr.length - 1;
        playByDurationsAndDelay(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playByDurationsAndDelay(final int i) {
        int i2;
        this.mImageView.postDelayed(new Runnable() { // from class: com.android.blerc.FrameAnimationBitmap.1
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimationBitmap.this.mPause) {
                    FrameAnimationBitmap.this.mCurrentSelect = 1;
                    FrameAnimationBitmap.this.mCurrentFrame = i;
                    return;
                }
                if (i == 0 && FrameAnimationBitmap.this.mAnimationListener != null) {
                    FrameAnimationBitmap.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimationBitmap.this.mImageView.setImageBitmap(FrameAnimationBitmap.this.mFrameRess[i]);
                if (i == FrameAnimationBitmap.this.mLastFrame) {
                    if (FrameAnimationBitmap.this.mAnimationListener != null) {
                        FrameAnimationBitmap.this.mAnimationListener.onAnimationRepeat();
                    }
                    FrameAnimationBitmap.this.mNext = true;
                    FrameAnimationBitmap.this.playByDurationsAndDelay(0);
                    return;
                }
                FrameAnimationBitmap.this.playByDurationsAndDelay(i + 1);
            }
        }, (!this.mNext || (i2 = this.mDelay) <= 0) ? this.mDurations[i] : i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAndDelay(final int i) {
        int i2;
        ImageView imageView = this.mImageView;
        Runnable runnable = new Runnable() { // from class: com.android.blerc.FrameAnimationBitmap.2
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimationBitmap.this.mPause) {
                    if (FrameAnimationBitmap.this.mPause) {
                        FrameAnimationBitmap.this.mCurrentSelect = 2;
                        FrameAnimationBitmap.this.mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                FrameAnimationBitmap.this.mNext = false;
                if (i == 0 && FrameAnimationBitmap.this.mAnimationListener != null) {
                    FrameAnimationBitmap.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimationBitmap.this.mImageView.setImageBitmap(FrameAnimationBitmap.this.mFrameRess[i]);
                if (i == FrameAnimationBitmap.this.mLastFrame) {
                    if (FrameAnimationBitmap.this.mAnimationListener != null) {
                        FrameAnimationBitmap.this.mAnimationListener.onAnimationRepeat();
                    }
                    FrameAnimationBitmap.this.mNext = true;
                    FrameAnimationBitmap.this.playAndDelay(0);
                    return;
                }
                FrameAnimationBitmap.this.playAndDelay(i + 1);
            }
        };
        if (!this.mNext || (i2 = this.mDelay) <= 0) {
            i2 = this.mDuration;
        }
        imageView.postDelayed(runnable, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playByDurations(final int i) {
        this.mImageView.postDelayed(new Runnable() { // from class: com.android.blerc.FrameAnimationBitmap.3
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimationBitmap.this.mPause) {
                    if (FrameAnimationBitmap.this.mPause) {
                        FrameAnimationBitmap.this.mCurrentSelect = 3;
                        FrameAnimationBitmap.this.mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                if (i == 0 && FrameAnimationBitmap.this.mAnimationListener != null) {
                    FrameAnimationBitmap.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimationBitmap.this.mImageView.setImageBitmap(FrameAnimationBitmap.this.mFrameRess[i]);
                if (i == FrameAnimationBitmap.this.mLastFrame) {
                    if (FrameAnimationBitmap.this.mIsRepeat) {
                        if (FrameAnimationBitmap.this.mAnimationListener != null) {
                            FrameAnimationBitmap.this.mAnimationListener.onAnimationRepeat();
                        }
                        FrameAnimationBitmap.this.playByDurations(0);
                        return;
                    } else {
                        if (FrameAnimationBitmap.this.mAnimationListener != null) {
                            FrameAnimationBitmap.this.mAnimationListener.onAnimationEnd();
                            return;
                        }
                        return;
                    }
                }
                FrameAnimationBitmap.this.playByDurations(i + 1);
            }
        }, this.mDurations[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void play(final int i) {
        this.mImageView.postDelayed(new Runnable() { // from class: com.android.blerc.FrameAnimationBitmap.4
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimationBitmap.this.mPause) {
                    if (FrameAnimationBitmap.this.mPause) {
                        FrameAnimationBitmap.this.mCurrentSelect = 4;
                        FrameAnimationBitmap.this.mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                if (i == 0 && FrameAnimationBitmap.this.mAnimationListener != null) {
                    FrameAnimationBitmap.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimationBitmap.this.mImageView.setImageBitmap(FrameAnimationBitmap.this.mFrameRess[i]);
                if (i == FrameAnimationBitmap.this.mLastFrame) {
                    if (FrameAnimationBitmap.this.mIsRepeat) {
                        if (FrameAnimationBitmap.this.mAnimationListener != null) {
                            FrameAnimationBitmap.this.mAnimationListener.onAnimationRepeat();
                        }
                        FrameAnimationBitmap.this.play(0);
                        return;
                    } else {
                        if (FrameAnimationBitmap.this.mAnimationListener != null) {
                            FrameAnimationBitmap.this.mAnimationListener.onAnimationEnd();
                            return;
                        }
                        return;
                    }
                }
                FrameAnimationBitmap.this.play(i + 1);
            }
        }, this.mDuration);
    }

    public void setAnimationListener(AnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public void release() {
        pauseAnimation();
    }

    public void pauseAnimation() {
        this.mPause = true;
    }

    public boolean isPause() {
        return this.mPause;
    }

    public void restartAnimation() {
        if (this.mPause) {
            this.mPause = false;
            int i = this.mCurrentSelect;
            if (i == 1) {
                playByDurationsAndDelay(this.mCurrentFrame);
                return;
            }
            if (i == 2) {
                playAndDelay(this.mCurrentFrame);
            } else if (i == 3) {
                playByDurations(this.mCurrentFrame);
            } else {
                if (i != 4) {
                    return;
                }
                play(this.mCurrentFrame);
            }
        }
    }
}
