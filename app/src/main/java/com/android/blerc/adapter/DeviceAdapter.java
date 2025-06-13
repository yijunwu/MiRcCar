package com.android.blerc.adapter;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.blerc.R;
import com.android.blerc.model.BluetoothLeDevice;


/* loaded from: classes.dex */
public class DeviceAdapter extends HelperAdapter<BluetoothLeDevice> {
    private int selectItem;

    public DeviceAdapter(Context context) {
        super(context, R.layout.item_scan_layout);
        this.selectItem = -1;
    }

    @Override // com.android.blerc.adapter.HelperAdapter
    public void HelpConvert(HelperViewHolder helperViewHolder, int i, BluetoothLeDevice bluetoothLeDevice) {
        TextView textView = (TextView) helperViewHolder.getView(R.id.device_name);
        ProgressBar progressBar = (ProgressBar) helperViewHolder.getView(R.id.device_loading);
        if (bluetoothLeDevice != null && bluetoothLeDevice.getDevice() != null) {
            String name = bluetoothLeDevice.getDevice().getName();
            if (name != null && !name.isEmpty()) {
                if (name.startsWith("AJ")) {
                    if (name.startsWith("AJ1")) {
                        name = "Jimny" + this.mContext.getString(R.string.green) + name.split("-")[1].toString();
                    } else if (name.startsWith("AJ2")) {
                        name = "Jimny" + this.mContext.getString(R.string.black) + name.split("-")[1].toString();
                    } else if (name.startsWith("AJ-")) {
                        name = "Jimny" + name.split("-")[1].toString();
                    }
                } else if (name.startsWith("AR")) {
                    if (name.startsWith("AR1")) {
                        name = "Rubicon" + this.mContext.getString(R.string.black) + name.split("-")[1].toString();
                    } else if (name.startsWith("AR-")) {
                        name = "Rubicon" + name.split("-")[1].toString();
                    }
                }
                textView.setText(name);
            } else {
                textView.setText(this.mContext.getString(R.string.unknown_device));
            }
        }
        if (i == this.selectItem) {
            progressBar.setVisibility(0);
        }
    }

    public void setSelectItem(int i) {
        this.selectItem = i;
    }

    public int getSelectItem() {
        return this.selectItem;
    }
}
