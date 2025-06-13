package com.android.blerc.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.blerc.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;


/* loaded from: classes.dex */
public class GuideDialog_ViewBinding implements Unbinder {
    private GuideDialog target;

    public GuideDialog_ViewBinding(GuideDialog guideDialog) {
        this(guideDialog, guideDialog.getWindow().getDecorView());
    }

    public GuideDialog_ViewBinding(GuideDialog guideDialog, View view) {
        this.target = guideDialog;
        guideDialog.skip_imgbtn = (ImageView) Utils.findRequiredViewAsType(view, R.id.skip_imgbtn, "field 'skip_imgbtn'", ImageView.class);
        guideDialog.guide_view = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_view, "field 'guide_view'", RelativeLayout.class);
        guideDialog.banner1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner1, "field 'banner1'", ImageView.class);
        guideDialog.banner2 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner2, "field 'banner2'", ImageView.class);
        guideDialog.banner3 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner3, "field 'banner3'", ImageView.class);
        guideDialog.banner4 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner4, "field 'banner4'", ImageView.class);
        guideDialog.banner5 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner5, "field 'banner5'", ImageView.class);
        guideDialog.banner6 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner6, "field 'banner6'", ImageView.class);
        guideDialog.banner7 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner7, "field 'banner7'", ImageView.class);
        guideDialog.banner8 = (ImageView) Utils.findRequiredViewAsType(view, R.id.banner8, "field 'banner8'", ImageView.class);
        guideDialog.guide_layout1 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout1, "field 'guide_layout1'", RelativeLayout.class);
        guideDialog.guide_icon1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon1, "field 'guide_icon1'", ImageView.class);
        guideDialog.guide_line1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line1, "field 'guide_line1'", ImageView.class);
        guideDialog.tx_title1 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title1, "field 'tx_title1'", TextView.class);
        guideDialog.tx_tip1 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip1, "field 'tx_tip1'", TextView.class);
        guideDialog.guide_layout2 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout2, "field 'guide_layout2'", RelativeLayout.class);
        guideDialog.guide_icon2 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon2, "field 'guide_icon2'", ImageView.class);
        guideDialog.guide_line2 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line2, "field 'guide_line2'", ImageView.class);
        guideDialog.tx_title2 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title2, "field 'tx_title2'", TextView.class);
        guideDialog.tx_tip2 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip2, "field 'tx_tip2'", TextView.class);
        guideDialog.guide_layout3 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout3, "field 'guide_layout3'", RelativeLayout.class);
        guideDialog.guide_icon3 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon3, "field 'guide_icon3'", ImageView.class);
        guideDialog.guide_line3 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line3, "field 'guide_line3'", ImageView.class);
        guideDialog.tx_title3 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title3, "field 'tx_title3'", TextView.class);
        guideDialog.tx_tip3 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip3, "field 'tx_tip3'", TextView.class);
        guideDialog.guide_layout4 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout4, "field 'guide_layout4'", RelativeLayout.class);
        guideDialog.guide_icon4 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon4, "field 'guide_icon4'", ImageView.class);
        guideDialog.guide_line4 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line4, "field 'guide_line4'", ImageView.class);
        guideDialog.tx_title4 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title4, "field 'tx_title4'", TextView.class);
        guideDialog.tx_tip4 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip4, "field 'tx_tip4'", TextView.class);
        guideDialog.guide_layout5 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout5, "field 'guide_layout5'", RelativeLayout.class);
        guideDialog.guide_icon5 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon5, "field 'guide_icon5'", ImageView.class);
        guideDialog.guide_line5 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line5, "field 'guide_line5'", ImageView.class);
        guideDialog.tx_title5 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title5, "field 'tx_title5'", TextView.class);
        guideDialog.tx_tip5 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip5, "field 'tx_tip5'", TextView.class);
        guideDialog.guide_layout6 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout6, "field 'guide_layout6'", RelativeLayout.class);
        guideDialog.guide_icon6 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon6, "field 'guide_icon6'", ImageView.class);
        guideDialog.guide_line6 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line6, "field 'guide_line6'", ImageView.class);
        guideDialog.tx_title6 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title6, "field 'tx_title6'", TextView.class);
        guideDialog.tx_tip6 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip6, "field 'tx_tip6'", TextView.class);
        guideDialog.guide_layout7 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout7, "field 'guide_layout7'", RelativeLayout.class);
        guideDialog.guide_icon7 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon7, "field 'guide_icon7'", ImageView.class);
        guideDialog.guide_line7 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line7, "field 'guide_line7'", ImageView.class);
        guideDialog.tx_title7 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title7, "field 'tx_title7'", TextView.class);
        guideDialog.tx_tip7 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip7, "field 'tx_tip7'", TextView.class);
        guideDialog.guide_layout8 = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.guide_layout8, "field 'guide_layout8'", RelativeLayout.class);
        guideDialog.guide_icon8 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_icon8, "field 'guide_icon8'", ImageView.class);
        guideDialog.guide_line8 = (ImageView) Utils.findRequiredViewAsType(view, R.id.guide_line8, "field 'guide_line8'", ImageView.class);
        guideDialog.tx_title8 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_title8, "field 'tx_title8'", TextView.class);
        guideDialog.tx_tip8 = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_tip8, "field 'tx_tip8'", TextView.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        GuideDialog guideDialog = this.target;
        if (guideDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        guideDialog.skip_imgbtn = null;
        guideDialog.guide_view = null;
        guideDialog.banner1 = null;
        guideDialog.banner2 = null;
        guideDialog.banner3 = null;
        guideDialog.banner4 = null;
        guideDialog.banner5 = null;
        guideDialog.banner6 = null;
        guideDialog.banner7 = null;
        guideDialog.banner8 = null;
        guideDialog.guide_layout1 = null;
        guideDialog.guide_icon1 = null;
        guideDialog.guide_line1 = null;
        guideDialog.tx_title1 = null;
        guideDialog.tx_tip1 = null;
        guideDialog.guide_layout2 = null;
        guideDialog.guide_icon2 = null;
        guideDialog.guide_line2 = null;
        guideDialog.tx_title2 = null;
        guideDialog.tx_tip2 = null;
        guideDialog.guide_layout3 = null;
        guideDialog.guide_icon3 = null;
        guideDialog.guide_line3 = null;
        guideDialog.tx_title3 = null;
        guideDialog.tx_tip3 = null;
        guideDialog.guide_layout4 = null;
        guideDialog.guide_icon4 = null;
        guideDialog.guide_line4 = null;
        guideDialog.tx_title4 = null;
        guideDialog.tx_tip4 = null;
        guideDialog.guide_layout5 = null;
        guideDialog.guide_icon5 = null;
        guideDialog.guide_line5 = null;
        guideDialog.tx_title5 = null;
        guideDialog.tx_tip5 = null;
        guideDialog.guide_layout6 = null;
        guideDialog.guide_icon6 = null;
        guideDialog.guide_line6 = null;
        guideDialog.tx_title6 = null;
        guideDialog.tx_tip6 = null;
        guideDialog.guide_layout7 = null;
        guideDialog.guide_icon7 = null;
        guideDialog.guide_line7 = null;
        guideDialog.tx_title7 = null;
        guideDialog.tx_tip7 = null;
        guideDialog.guide_layout8 = null;
        guideDialog.guide_icon8 = null;
        guideDialog.guide_line8 = null;
        guideDialog.tx_title8 = null;
        guideDialog.tx_tip8 = null;
    }
}
