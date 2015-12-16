package com.relferreira.customtabs;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "http://google.com";

    private Uri mUri;
    private Button mCustomTabButton;
    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;
    private MainCustomTabsService mCustomTabsServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUri = Uri.parse(URL);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCustomTabButton = (Button) findViewById(R.id.button_custom_tab);
        mCustomTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomTab();
            }
        });

        mCustomTabsServiceConnection = new MainCustomTabsService(URL);
        mCustomTabsServiceConnection.bindService(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mCustomTabsServiceConnection.unbindService(this);
    }

    private void openCustomTab(){
        Uri uri = Uri.parse("https://google.com");
        CustomTabsIntent.Builder customTabsIntentBuilder = new CustomTabsIntent.Builder(null);
        customTabsIntentBuilder.setToolbarColor(ContextCompat.getColor(this, R.color.primary_color));
        customTabsIntentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back));
        customTabsIntentBuilder.setActionButton(BitmapFactory.decodeResource(getResources(), R.drawable.ic_share), "Share", shareIntent());
        customTabsIntentBuilder.addMenuItem("Share", shareIntent());
        customTabsIntentBuilder.setStartAnimations(this, android.R.anim.fade_in,  android.R.anim.fade_out);
        customTabsIntentBuilder.setExitAnimations(this, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        CustomTabsIntent customTabsIntent = customTabsIntentBuilder.build();
        customTabsIntent.launchUrl(this, uri);
    }

    private PendingIntent shareIntent(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        return PendingIntent.getActivity(this, 0, intent, 0);
    }

}
