package com.pressfforrespect.codenamespictures;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.pressfforrespect.codenamespictures.network.WifiDirectBroadcastReceiver;

public class WifiActivity extends AppCompatActivity implements WifiP2pManager.ChannelListener, DeviceListFragment.DeviceActionListener {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private WifiDirectBroadcastReceiver wifiDirectBroadcastReceiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        Toolbar wifiToolbar = (Toolbar) findViewById(R.id.wifi_toolbar);
        setSupportActionBar(wifiToolbar);
        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this, getMainLooper(), null);
        wifiDirectBroadcastReceiver = new WifiDirectBroadcastReceiver(wifiP2pManager, channel, this);
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);



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
        final DeviceListFragment deviceListFragment = (DeviceListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list);
        deviceListFragment.onInitiateDiscovery();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.discover_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_directEnable :
                enableWifi();
                return true;
            case R.id.action_directDiscover:
                discoverPeers();
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showDetails(WifiP2pDevice device) {
        DeviceDetailFragment fragment = (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_detail);
        fragment.showDetails(device);
    }

    @Override
    public void cancelDisconnect() {

    }

    @Override
    public void connect(WifiP2pConfig config) {
        wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i) {

            }
        });
    }

    @Override
    public void disconnect() {

    }
}
