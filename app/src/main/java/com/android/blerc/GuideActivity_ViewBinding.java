package com.android.blerc;

import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cn.bingoogolapple.bgabanner.BGABanner;

/* loaded from: classes.dex */
public class GuideActivity_ViewBinding implements Unbinder {
    private GuideActivity target;

    public GuideActivity_ViewBinding(GuideActivity guideActivity) {
        this(guideActivity, guideActivity.getWindow().getDecorView());
    }

    public GuideActivity_ViewBinding(GuideActivity guideActivity, View view) {
        this.target = guideActivity;
        guideActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        guideActivity.banner = (BGABanner) Utils.findRequiredViewAsType(view, R.id.banner_guide_content, "field 'banner'", BGABanner.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        GuideActivity guideActivity = this.target;
        if (guideActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        guideActivity.garage_back_img = null;
        guideActivity.banner = null;
    }
}
