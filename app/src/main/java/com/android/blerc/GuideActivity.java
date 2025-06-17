package com.android.blerc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import butterknife.ButterKnife;
import cn.bingoogolapple.bgabanner.BGABanner;
import com.android.blerc.db.DBConstant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.functions.Consumer;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class GuideActivity extends BaseActivity {
    private final String TAG = "Guide";
    BGABanner banner;
    ImageView garage_back_img;

    @Override // com.android.blerc.BaseActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        RxView.clicks(this.garage_back_img).throttleFirst(1L, TimeUnit.SECONDS).subscribe(new Consumer<Object>() { // from class: com.android.blerc.GuideActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                GuideActivity.this.finish();
            }
        });
        this.banner.setAdapter(new BGABanner.Adapter<ImageView, Integer>() { // from class: com.android.blerc.GuideActivity.2
            @Override // cn.bingoogolapple.bgabanner.BGABanner.Adapter
            public void fillBannerItem(BGABanner bGABanner, final ImageView imageView, Integer num, int i) {
                Glide.with((Activity) GuideActivity.this)
                        .load(num)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .dontAnimate()
                        .into(new CustomTarget<Drawable>() { // from class: com.android.blerc.GuideActivity.2.1
                    @Override // com.bumptech.glide.request.target.Target
                    public void onLoadCleared(Drawable drawable) {
                    }

                    public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                        imageView.setImageBitmap(GuideActivity.createScaledBitmap(((BitmapDrawable) drawable).getBitmap(), imageView.getMeasuredWidth(), imageView.getMeasuredHeight()));
                    }
                });
            }
        });
        if (DBConstant.getInstance(this).getLanguage().equals("zh")) {
            this.banner.setData(Arrays.asList(Integer.valueOf(R.mipmap.guide_1), Integer.valueOf(R.mipmap.guide_2), Integer.valueOf(R.mipmap.guide_3), Integer.valueOf(R.mipmap.guide_4), Integer.valueOf(R.mipmap.guide_5), Integer.valueOf(R.mipmap.guide_6), Integer.valueOf(R.mipmap.guide_7)), Arrays.asList("", "", "", "", "", "", ""));
        } else {
            this.banner.setData(Arrays.asList(Integer.valueOf(R.mipmap.guide_1_en), Integer.valueOf(R.mipmap.guide_2_en), Integer.valueOf(R.mipmap.guide_3_en), Integer.valueOf(R.mipmap.guide_4_en), Integer.valueOf(R.mipmap.guide_5_en), Integer.valueOf(R.mipmap.guide_6_en), Integer.valueOf(R.mipmap.guide_7_en)), Arrays.asList("", "", "", "", "", "", ""));
        }
    }

    public static Bitmap createScaledBitmap(Bitmap bitmap, int i, int i2) {
        Matrix matrix = new Matrix();
        float f = i;
        float width = f / bitmap.getWidth();
        float f2 = i2;
        if (bitmap.getHeight() * width < f2) {
            width = f2 / bitmap.getHeight();
        }
        float width2 = (f - (bitmap.getWidth() * width)) / 2.0f;
        float height = (f2 - (bitmap.getHeight() * width)) / 2.0f;
        matrix.setScale(width, width);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(false);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 1));
        canvas.translate(width2, height);
        canvas.save();
        canvas.drawBitmap(bitmap, matrix, paint);
        canvas.restore();
        return bitmapCreateBitmap;
    }
}
