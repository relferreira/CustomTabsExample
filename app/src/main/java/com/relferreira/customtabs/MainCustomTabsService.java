package com.relferreira.customtabs;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;


public class MainCustomTabsService extends CustomTabsServiceConnection {

    private final Uri mUri;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;

    public MainCustomTabsService(String url) {
        mUri = Uri.parse(url);
    }

    @Override
    public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
        mCustomTabsClient = customTabsClient;
        mCustomTabsClient.warmup(0L);
        mCustomTabsSession = mCustomTabsClient.newSession(null);
        mCustomTabsSession.mayLaunchUrl(mUri, null, null);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mCustomTabsClient = null;
    }

    public void bindService(Context context){
        CustomTabsClient.bindCustomTabsService(context, "com.android.chrome", this);
    }

    public void unbindService(Context context){
        context.unbindService(this);
    }

}
