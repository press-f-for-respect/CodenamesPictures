package com.pressfforrespect.codenamespictures;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pressfforrespect.codenamespictures.network.WifiDirectBroadcastReceiver;

public class WifiActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private WifiDirectBroadcastReceiver wifiDirectBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this, getMainLooper(), null);
        wifiDirectBroadcastReceiver = new WifiDirectBroadcastReceiver(wifiP2pManager, channel, this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        Button enableWifiButton = findViewById(R.id.enable_wifi_button);
        enableWifiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableWifi();
            }
        });
        final Button discoverButton = findViewById(R.id.discover_button);
        discoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                discoverPeers();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wifiDirectBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiDirectBroadcastReceiver);
    }

    private void discoverPeers() {
        DeviceListFragment deviceListFragment = new DeviceListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.wifi_activity_container, deviceListFragment).commit();
//        deviceListFragment.onInitiateDiscovery();
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(WifiActivity.this, "Discovery Initiated",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i) {
                Toast.makeText(WifiActivity.this, "Discovery Failed : " + i,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void enableWifi() {
        if (wifiP2pManager != null && channel != null) {
            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }
    }

    @Override
    public void onChannelDisconnected() {

    }
}
