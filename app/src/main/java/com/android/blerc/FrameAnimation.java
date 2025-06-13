package com.android.blerc;

import android.widget.ImageView;

/* loaded from: classes.dex */
public class FrameAnimation {
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
    private int[] mFrameRess;
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

    public FrameAnimation(ImageView imageView, int[] iArr, int i, boolean z) {
        this.mImageView = imageView;
        this.mFrameRess = iArr;
        this.mDuration = i;
        this.mLastFrame = iArr.length - 1;
        this.mIsRepeat = z;
        play(0);
    }

    public FrameAnimation(ImageView imageView, int[] iArr, int[] iArr2, boolean z) {
        this.mImageView = imageView;
        this.mFrameRess = iArr;
        this.mDurations = iArr2;
        this.mLastFrame = iArr.length - 1;
        this.mIsRepeat = z;
        playByDurations(0);
    }

    public FrameAnimation(ImageView imageView, int[] iArr, int i, int i2) {
        this.mImageView = imageView;
        this.mFrameRess = iArr;
        this.mDuration = i;
        this.mDelay = i2;
        this.mLastFrame = iArr.length - 1;
        playAndDelay(0);
    }

    public FrameAnimation(ImageView imageView, int[] iArr, int[] iArr2, int i) {
        this.mImageView = imageView;
        this.mFrameRess = iArr;
        this.mDurations = iArr2;
        this.mDelay = i;
        this.mLastFrame = iArr.length - 1;
        playByDurationsAndDelay(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playByDurationsAndDelay(final int i) {
        int i2;
        this.mImageView.postDelayed(new Runnable() { // from class: com.android.blerc.FrameAnimation.1
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimation.this.mPause) {
                    FrameAnimation.this.mCurrentSelect = 1;
                    FrameAnimation.this.mCurrentFrame = i;
                    return;
                }
                if (i == 0 && FrameAnimation.this.mAnimationListener != null) {
                    FrameAnimation.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimation.this.mImageView.setBackgroundResource(FrameAnimation.this.mFrameRess[i]);
                if (i == FrameAnimation.this.mLastFrame) {
                    if (FrameAnimation.this.mAnimationListener != null) {
                        FrameAnimation.this.mAnimationListener.onAnimationRepeat();
                    }
                    FrameAnimation.this.mNext = true;
                    FrameAnimation.this.playByDurationsAndDelay(0);
                    return;
                }
                FrameAnimation.this.playByDurationsAndDelay(i + 1);
            }
        }, (!this.mNext || (i2 = this.mDelay) <= 0) ? this.mDurations[i] : i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playAndDelay(final int i) {
        int i2;
        ImageView imageView = this.mImageView;
        Runnable runnable = new Runnable() { // from class: com.android.blerc.FrameAnimation.2
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimation.this.mPause) {
                    if (FrameAnimation.this.mPause) {
                        FrameAnimation.this.mCurrentSelect = 2;
                        FrameAnimation.this.mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                FrameAnimation.this.mNext = false;
                if (i == 0 && FrameAnimation.this.mAnimationListener != null) {
                    FrameAnimation.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimation.this.mImageView.setBackgroundResource(FrameAnimation.this.mFrameRess[i]);
                if (i == FrameAnimation.this.mLastFrame) {
                    if (FrameAnimation.this.mAnimationListener != null) {
                        FrameAnimation.this.mAnimationListener.onAnimationRepeat();
                    }
                    FrameAnimation.this.mNext = true;
                    FrameAnimation.this.playAndDelay(0);
                    return;
                }
                FrameAnimation.this.playAndDelay(i + 1);
            }
        };
        if (!this.mNext || (i2 = this.mDelay) <= 0) {
            i2 = this.mDuration;
        }
        imageView.postDelayed(runnable, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playByDurations(final int i) {
        this.mImageView.postDelayed(new Runnable() { // from class: com.android.blerc.FrameAnimation.3
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimation.this.mPause) {
                    if (FrameAnimation.this.mPause) {
                        FrameAnimation.this.mCurrentSelect = 3;
                        FrameAnimation.this.mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                if (i == 0 && FrameAnimation.this.mAnimationListener != null) {
                    FrameAnimation.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimation.this.mImageView.setBackgroundResource(FrameAnimation.this.mFrameRess[i]);
                if (i == FrameAnimation.this.mLastFrame) {
                    if (FrameAnimation.this.mIsRepeat) {
                        if (FrameAnimation.this.mAnimationListener != null) {
                            FrameAnimation.this.mAnimationListener.onAnimationRepeat();
                        }
                        FrameAnimation.this.playByDurations(0);
                        return;
                    } else {
                        if (FrameAnimation.this.mAnimationListener != null) {
                            FrameAnimation.this.mAnimationListener.onAnimationEnd();
                            return;
                        }
                        return;
                    }
                }
                FrameAnimation.this.playByDurations(i + 1);
            }
        }, this.mDurations[i]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void play(final int i) {
        this.mImageView.postDelayed(new Runnable() { // from class: com.android.blerc.FrameAnimation.4
            @Override // java.lang.Runnable
            public void run() {
                if (FrameAnimation.this.mPause) {
                    if (FrameAnimation.this.mPause) {
                        FrameAnimation.this.mCurrentSelect = 4;
                        FrameAnimation.this.mCurrentFrame = i;
                        return;
                    }
                    return;
                }
                if (i == 0 && FrameAnimation.this.mAnimationListener != null) {
                    FrameAnimation.this.mAnimationListener.onAnimationStart();
                }
                FrameAnimation.this.mImageView.setBackgroundResource(FrameAnimation.this.mFrameRess[i]);
                if (i == FrameAnimation.this.mLastFrame) {
                    if (FrameAnimation.this.mIsRepeat) {
                        if (FrameAnimation.this.mAnimationListener != null) {
                            FrameAnimation.this.mAnimationListener.onAnimationRepeat();
                        }
                        FrameAnimation.this.play(0);
                        return;
                    } else {
                        if (FrameAnimation.this.mAnimationListener != null) {
                            FrameAnimation.this.mAnimationListener.onAnimationEnd();
                            return;
                        }
                        return;
                    }
                }
                FrameAnimation.this.play(i + 1);
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
