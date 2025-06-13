package com.android.blerc;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class GarageActivity_ViewBinding implements Unbinder {
    private GarageActivity target;

    public GarageActivity_ViewBinding(GarageActivity garageActivity) {
        this(garageActivity, garageActivity.getWindow().getDecorView());
    }

    public GarageActivity_ViewBinding(GarageActivity garageActivity, View view) {
        this.target = garageActivity;
        garageActivity.garage_back_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_back_img, "field 'garage_back_img'", ImageView.class);
        garageActivity.garagestate1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.garagestate1, "field 'garagestate1'", ImageView.class);
        garageActivity.garagestate2 = (ImageView) Utils.findRequiredViewAsType(view, R.id.garagestate2, "field 'garagestate2'", ImageView.class);
        garageActivity.garagestate3 = (ImageView) Utils.findRequiredViewAsType(view, R.id.garagestate3, "field 'garagestate3'", ImageView.class);
        garageActivity.garagestate4 = (ImageView) Utils.findRequiredViewAsType(view, R.id.garagestate4, "field 'garagestate4'", ImageView.class);
        garageActivity.garagestate5 = (ImageView) Utils.findRequiredViewAsType(view, R.id.garagestate5, "field 'garagestate5'", ImageView.class);
        garageActivity.car_arrow_up = (ImageView) Utils.findRequiredViewAsType(view, R.id.car_arrow_up, "field 'car_arrow_up'", ImageView.class);
        garageActivity.car_arrow_down = (ImageView) Utils.findRequiredViewAsType(view, R.id.car_arrow_down, "field 'car_arrow_down'", ImageView.class);
        garageActivity.car_parameter_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.car_parameter_img, "field 'car_parameter_img'", ImageView.class);
        garageActivity.arrow_up_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.arrow_up_layout, "field 'arrow_up_layout'", LinearLayout.class);
        garageActivity.arrow_down_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.arrow_down_layout, "field 'arrow_down_layout'", RelativeLayout.class);
        garageActivity.animationView = (LottieAnimationView) Utils.findRequiredViewAsType(view, R.id.garage_animation_view, "field 'animationView'", LottieAnimationView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        GarageActivity garageActivity = this.target;
        if (garageActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        garageActivity.garage_back_img = null;
        garageActivity.garagestate1 = null;
        garageActivity.garagestate2 = null;
        garageActivity.garagestate3 = null;
        garageActivity.garagestate4 = null;
        garageActivity.garagestate5 = null;
        garageActivity.car_arrow_up = null;
        garageActivity.car_arrow_down = null;
        garageActivity.car_parameter_img = null;
        garageActivity.arrow_up_layout = null;
        garageActivity.arrow_down_layout = null;
        garageActivity.animationView = null;
    }
}
