package com.android.blerc;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;

/* loaded from: classes.dex */
public class BaseMainActivity_ViewBinding implements Unbinder {
    private BaseMainActivity target;

    public BaseMainActivity_ViewBinding(BaseMainActivity baseMainActivity) {
        this(baseMainActivity, baseMainActivity.getWindow().getDecorView());
    }

    public BaseMainActivity_ViewBinding(BaseMainActivity baseMainActivity, View view) {
        this.target = baseMainActivity;
        baseMainActivity.turn_dr = (SeekBar) Utils.findRequiredViewAsType(view, R.id.turn_dr, "field 'turn_dr'", SeekBar.class);
        baseMainActivity.turn_middle = (SeekBar) Utils.findRequiredViewAsType(view, R.id.turn_middle, "field 'turn_middle'", SeekBar.class);
        baseMainActivity.horiSeekBar = (SeekBar) Utils.findRequiredViewAsType(view, R.id.horiSeekBar, "field 'horiSeekBar'", SeekBar.class);
        baseMainActivity.verticalSeekBar = (VerticalSeekBar) Utils.findRequiredViewAsType(view, R.id.verticalSeekBar, "field 'verticalSeekBar'", VerticalSeekBar.class);
        baseMainActivity.horiSeekBar1 = (SeekBar) Utils.findRequiredViewAsType(view, R.id.horiSeekBar1, "field 'horiSeekBar1'", SeekBar.class);
        baseMainActivity.verticalSeekBar1 = (VerticalSeekBar) Utils.findRequiredViewAsType(view, R.id.verticalSeekBar1, "field 'verticalSeekBar1'", VerticalSeekBar.class);
        baseMainActivity.centerbtn = (Button) Utils.findRequiredViewAsType(view, R.id.centerbtn, "field 'centerbtn'", Button.class);
        baseMainActivity.menu_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.menu_img, "field 'menu_img'", ImageView.class);
        baseMainActivity.garage_imgbtn = (ImageView) Utils.findRequiredViewAsType(view, R.id.garage_imgbtn, "field 'garage_imgbtn'", ImageView.class);
        baseMainActivity.switch_mode_imgbtn = (ImageView) Utils.findRequiredViewAsType(view, R.id.switch_mode_imgbtn, "field 'switch_mode_imgbtn'", ImageView.class);
        baseMainActivity.set_imgbtn = (ImageView) Utils.findRequiredViewAsType(view, R.id.set_imgbtn, "field 'set_imgbtn'", ImageView.class);
        baseMainActivity.low_speed_imgbtn = (ImageView) Utils.findRequiredViewAsType(view, R.id.low_speed_imgbtn, "field 'low_speed_imgbtn'", ImageView.class);
        baseMainActivity.brake_imgbtn = (ImageView) Utils.findRequiredViewAsType(view, R.id.brake_imgbtn, "field 'brake_imgbtn'", ImageView.class);
        baseMainActivity.around_left_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_left_img, "field 'around_left_img'", ImageView.class);
        baseMainActivity.around_down_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_down_img, "field 'around_down_img'", ImageView.class);
        baseMainActivity.around_up_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_up_img, "field 'around_up_img'", ImageView.class);
        baseMainActivity.around_right_img = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_right_img, "field 'around_right_img'", ImageView.class);
        baseMainActivity.around_left = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_left, "field 'around_left'", ImageView.class);
        baseMainActivity.around_down = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_down, "field 'around_down'", ImageView.class);
        baseMainActivity.around_up = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_up, "field 'around_up'", ImageView.class);
        baseMainActivity.around_right = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_right, "field 'around_right'", ImageView.class);
        baseMainActivity.around_left_img1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_left_img1, "field 'around_left_img1'", ImageView.class);
        baseMainActivity.around_down_img1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_down_img1, "field 'around_down_img1'", ImageView.class);
        baseMainActivity.around_up_img1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_up_img1, "field 'around_up_img1'", ImageView.class);
        baseMainActivity.around_right_img1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_right_img1, "field 'around_right_img1'", ImageView.class);
        baseMainActivity.around_left1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_left1, "field 'around_left1'", ImageView.class);
        baseMainActivity.around_down1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_down1, "field 'around_down1'", ImageView.class);
        baseMainActivity.around_up1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_up1, "field 'around_up1'", ImageView.class);
        baseMainActivity.around_right1 = (ImageView) Utils.findRequiredViewAsType(view, R.id.around_right1, "field 'around_right1'", ImageView.class);
        baseMainActivity.ver_right = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ver_right, "field 'ver_right'", LinearLayout.class);
        baseMainActivity.hor_right = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.hor_right, "field 'hor_right'", LinearLayout.class);
        baseMainActivity.hor_left = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.hor_left, "field 'hor_left'", LinearLayout.class);
        baseMainActivity.ver_left = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ver_left, "field 'ver_left'", LinearLayout.class);
        baseMainActivity.toptitle = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.toptitle, "field 'toptitle'", LinearLayout.class);
        baseMainActivity.set_close = (ImageView) Utils.findRequiredViewAsType(view, R.id.set_close, "field 'set_close'", ImageView.class);
        baseMainActivity.menu_set = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.menu_set, "field 'menu_set'", LinearLayout.class);
        baseMainActivity.tx_connect_title = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_connect_title, "field 'tx_connect_title'", TextView.class);
        baseMainActivity.tx_connect_tip = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_connect_tip, "field 'tx_connect_tip'", TextView.class);
        baseMainActivity.iv_battery = (ImageView) Utils.findRequiredViewAsType(view, R.id.iv_battery, "field 'iv_battery'", ImageView.class);
        baseMainActivity.tx_turn_middle = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_turn_middle, "field 'tx_turn_middle'", TextView.class);
        baseMainActivity.tx_turn_dr = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_turn_dr, "field 'tx_turn_dr'", TextView.class);
        baseMainActivity.tx_switch_turn = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_switch_turn, "field 'tx_switch_turn'", TextView.class);
        baseMainActivity.tx_switch_accelerator = (TextView) Utils.findRequiredViewAsType(view, R.id.tx_switch_accelerator, "field 'tx_switch_accelerator'", TextView.class);
        baseMainActivity.horiSeekBar1_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.horiSeekBar1_layout, "field 'horiSeekBar1_layout'", LinearLayout.class);
        baseMainActivity.verticalSeekBar_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.verticalSeekBar_layout, "field 'verticalSeekBar_layout'", LinearLayout.class);
        baseMainActivity.horiSeekBar_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.horiSeekBar_layout, "field 'horiSeekBar_layout'", LinearLayout.class);
        baseMainActivity.verticalSeekBar1_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.verticalSeekBar1_layout, "field 'verticalSeekBar1_layout'", LinearLayout.class);
        baseMainActivity.main_layout = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.main_layout, "field 'main_layout'", RelativeLayout.class);
        baseMainActivity.drag_height = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.drag_height, "field 'drag_height'", RelativeLayout.class);
        baseMainActivity.drag_view = (DragView) Utils.findRequiredViewAsType(view, R.id.drag_view, "field 'drag_view'", DragView.class);
        baseMainActivity.drag_view1 = (DragView) Utils.findRequiredViewAsType(view, R.id.drag_view1, "field 'drag_view1'", DragView.class);
        baseMainActivity.left_view = (ScrollView) Utils.findRequiredViewAsType(view, R.id.left_view, "field 'left_view'", ScrollView.class);
        baseMainActivity.right_view = (ScrollView) Utils.findRequiredViewAsType(view, R.id.right_view, "field 'right_view'", ScrollView.class);
        baseMainActivity.hor_sensor_left = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.hor_sensor_left, "field 'hor_sensor_left'", LinearLayout.class);
        baseMainActivity.ver_sensor_right = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ver_sensor_right, "field 'ver_sensor_right'", LinearLayout.class);
        baseMainActivity.left_sensor_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.left_sensor_layout, "field 'left_sensor_layout'", LinearLayout.class);
        baseMainActivity.right_sensor_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.right_sensor_layout, "field 'right_sensor_layout'", LinearLayout.class);
        baseMainActivity.sensor_left_up = (ImageView) Utils.findRequiredViewAsType(view, R.id.sensor_left_up, "field 'sensor_left_up'", ImageView.class);
        baseMainActivity.sensor_left_down = (ImageView) Utils.findRequiredViewAsType(view, R.id.sensor_left_down, "field 'sensor_left_down'", ImageView.class);
        baseMainActivity.sensor_right_up = (ImageView) Utils.findRequiredViewAsType(view, R.id.sensor_right_up, "field 'sensor_right_up'", ImageView.class);
        baseMainActivity.sensor_right_down = (ImageView) Utils.findRequiredViewAsType(view, R.id.sensor_right_down, "field 'sensor_right_down'", ImageView.class);
        baseMainActivity.switch_turn = (SwitchButton) Utils.findRequiredViewAsType(view, R.id.switch_turn, "field 'switch_turn'", SwitchButton.class);
        baseMainActivity.switch_accelerator = (SwitchButton) Utils.findRequiredViewAsType(view, R.id.switch_accelerator, "field 'switch_accelerator'", SwitchButton.class);
        baseMainActivity.turn_dr_subtract = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.turn_dr_subtract, "field 'turn_dr_subtract'", LinearLayout.class);
        baseMainActivity.turn_dr_add = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.turn_dr_add, "field 'turn_dr_add'", LinearLayout.class);
        baseMainActivity.turn_middle_subtract = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.turn_middle_subtract, "field 'turn_middle_subtract'", LinearLayout.class);
        baseMainActivity.turn_middle_add = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.turn_middle_add, "field 'turn_middle_add'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        BaseMainActivity baseMainActivity = this.target;
        if (baseMainActivity == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        baseMainActivity.turn_dr = null;
        baseMainActivity.turn_middle = null;
        baseMainActivity.horiSeekBar = null;
        baseMainActivity.verticalSeekBar = null;
        baseMainActivity.horiSeekBar1 = null;
        baseMainActivity.verticalSeekBar1 = null;
        baseMainActivity.centerbtn = null;
        baseMainActivity.menu_img = null;
        baseMainActivity.garage_imgbtn = null;
        baseMainActivity.switch_mode_imgbtn = null;
        baseMainActivity.set_imgbtn = null;
        baseMainActivity.low_speed_imgbtn = null;
        baseMainActivity.brake_imgbtn = null;
        baseMainActivity.around_left_img = null;
        baseMainActivity.around_down_img = null;
        baseMainActivity.around_up_img = null;
        baseMainActivity.around_right_img = null;
        baseMainActivity.around_left = null;
        baseMainActivity.around_down = null;
        baseMainActivity.around_up = null;
        baseMainActivity.around_right = null;
        baseMainActivity.around_left_img1 = null;
        baseMainActivity.around_down_img1 = null;
        baseMainActivity.around_up_img1 = null;
        baseMainActivity.around_right_img1 = null;
        baseMainActivity.around_left1 = null;
        baseMainActivity.around_down1 = null;
        baseMainActivity.around_up1 = null;
        baseMainActivity.around_right1 = null;
        baseMainActivity.ver_right = null;
        baseMainActivity.hor_right = null;
        baseMainActivity.hor_left = null;
        baseMainActivity.ver_left = null;
        baseMainActivity.toptitle = null;
        baseMainActivity.set_close = null;
        baseMainActivity.menu_set = null;
        baseMainActivity.tx_connect_title = null;
        baseMainActivity.tx_connect_tip = null;
        baseMainActivity.iv_battery = null;
        baseMainActivity.tx_turn_middle = null;
        baseMainActivity.tx_turn_dr = null;
        baseMainActivity.tx_switch_turn = null;
        baseMainActivity.tx_switch_accelerator = null;
        baseMainActivity.horiSeekBar1_layout = null;
        baseMainActivity.verticalSeekBar_layout = null;
        baseMainActivity.horiSeekBar_layout = null;
        baseMainActivity.verticalSeekBar1_layout = null;
        baseMainActivity.main_layout = null;
        baseMainActivity.drag_height = null;
        baseMainActivity.drag_view = null;
        baseMainActivity.drag_view1 = null;
        baseMainActivity.left_view = null;
        baseMainActivity.right_view = null;
        baseMainActivity.hor_sensor_left = null;
        baseMainActivity.ver_sensor_right = null;
        baseMainActivity.left_sensor_layout = null;
        baseMainActivity.right_sensor_layout = null;
        baseMainActivity.sensor_left_up = null;
        baseMainActivity.sensor_left_down = null;
        baseMainActivity.sensor_right_up = null;
        baseMainActivity.sensor_right_down = null;
        baseMainActivity.switch_turn = null;
        baseMainActivity.switch_accelerator = null;
        baseMainActivity.turn_dr_subtract = null;
        baseMainActivity.turn_dr_add = null;
        baseMainActivity.turn_middle_subtract = null;
        baseMainActivity.turn_middle_add = null;
    }
}
