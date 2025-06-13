package com.android.blerc.dialog;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.blerc.R;

import butterknife.Unbinder;
import butterknife.internal.Utils;


/* loaded from: classes.dex */
public class ListViewDialog_ViewBinding implements Unbinder {
    private ListViewDialog target;

    public ListViewDialog_ViewBinding(ListViewDialog listViewDialog) {
        this(listViewDialog, listViewDialog.getWindow().getDecorView());
    }

    public ListViewDialog_ViewBinding(ListViewDialog listViewDialog, View view) {
        this.target = listViewDialog;
        listViewDialog.mListView = (ListView) Utils.findRequiredViewAsType(view, R.id.ble_lv, "field 'mListView'", ListView.class);
        listViewDialog.start_scan = (TextView) Utils.findRequiredViewAsType(view, R.id.start_scan, "field 'start_scan'", TextView.class);
        listViewDialog.stop_scan = (RelativeLayout) Utils.findRequiredViewAsType(view, R.id.stop_scan, "field 'stop_scan'", RelativeLayout.class);
        listViewDialog.ble_list_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.ble_list_layout, "field 'ble_list_layout'", LinearLayout.class);
        listViewDialog.btn_divider = Utils.findRequiredView(view, R.id.btn_divider, "field 'btn_divider'");
        listViewDialog.submit = (TextView) Utils.findRequiredViewAsType(view, R.id.submit, "field 'submit'", TextView.class);
        listViewDialog.btn_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.btn_layout, "field 'btn_layout'", LinearLayout.class);
        listViewDialog.layout_divider = Utils.findRequiredView(view, R.id.layout_divider, "field 'layout_divider'");
        listViewDialog.ble_list_title = (TextView) Utils.findRequiredViewAsType(view, R.id.ble_list_title, "field 'ble_list_title'", TextView.class);
        listViewDialog.fail_layout = (LinearLayout) Utils.findRequiredViewAsType(view, R.id.fail_layout, "field 'fail_layout'", LinearLayout.class);
    }

    @Override // butterknife.Unbinder
    public void unbind() {
        ListViewDialog listViewDialog = this.target;
        if (listViewDialog == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.target = null;
        listViewDialog.mListView = null;
        listViewDialog.start_scan = null;
        listViewDialog.stop_scan = null;
        listViewDialog.ble_list_layout = null;
        listViewDialog.btn_divider = null;
        listViewDialog.submit = null;
        listViewDialog.btn_layout = null;
        listViewDialog.layout_divider = null;
        listViewDialog.ble_list_title = null;
        listViewDialog.fail_layout = null;
    }
}
