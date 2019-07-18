package com.pressfforrespect.codenamespictures;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.InetAddress;

import static com.pressfforrespect.codenamespictures.R.id.tv_deviceInfo;


public class DeviceDetailFragment extends Fragment implements WifiP2pManager.ConnectionInfoListener {
    private View mContentView = null;
    private WifiP2pDevice device;
    private WifiP2pInfo info;
    ProgressDialog progressDialog = null;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.device_detail, null);
        mContentView.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(getActivity(), "Press back to cancel",
                        "Connecting to :" + device.deviceAddress, true, true

                );
                ((DeviceListFragment.DeviceActionListener) getActivity()).connect(config);
            }
        });
        return mContentView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        this.info = wifiP2pInfo;
        this.getView().setVisibility(View.VISIBLE);

        TextView tvGroupOwner = (TextView) mContentView.findViewById(R.id.tv_groupOwner);
        tvGroupOwner.setText(getResources().getString(R.string.group_owner_text)
                + ((info.isGroupOwner) ? getResources().getString(R.string.yes)
                : getResources().getString(R.string.no)));

        TextView tvDeviceInfo = (TextView) mContentView.findViewById(tv_deviceInfo);
        if(info.groupOwnerAddress.getHostAddress()!=null)
            tvDeviceInfo.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress());
    }

    public void showDetails(WifiP2pDevice device) {
        this.device = device;
        this.getView().setVisibility(View.VISIBLE);
        TextView tvDeviceAddress = (TextView) mContentView.findViewById(R.id.tv_deviceAddress);
        tvDeviceAddress.setText(device.deviceAddress);
        TextView tvDeviceInfo = (TextView) mContentView.findViewById(tv_deviceInfo);
        tvDeviceInfo.setText(device.toString());

    }
}
