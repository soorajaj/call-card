package netfoxs.coms.callcarddialer;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Maincall extends TabActivity {
SharedPreferences sharedPreferences;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_maincall);
        // create the TabHost that will contain the Tabs
//        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
       /* mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        *//*adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                .addTestDevice("B310A5FDC1DF08F17626B9BAAECBC406")
                .build();*//*
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });*/
        TabHost tabHost = getTabHost();

        this.setNewTab(this, tabHost, "tab1",R.drawable.ic_person_white_36dp, new Intent(this,contactActivity.class));
        this.setNewTab(this, tabHost, "tab2", R.drawable.ic_access_time_white_36dp, new Intent(this, RecentActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        this.setNewTab(this, tabHost, "tab3", R.drawable.ic_dialpad_white_36dp, new Intent(this, KeypadActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        this.setNewTab(this, tabHost, "tab4",R.drawable.ic_settings_white_36dp, new Intent(this,SettingsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }

    private void setNewTab(Context context, TabHost tabHost, String tag, int icon, Intent contentID ){
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag);
        tabSpec.setIndicator(getTabIndicator(tabHost.getContext(), icon)); // new function to inject our own tab layout
        tabSpec.setContent(contentID);
        tabHost.addTab(tabSpec);
    }

    private View getTabIndicator(Context context, int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
//        TextView tv = (TextView) view.findViewById(R.id.textView);
//        /*tv.setText(title);*/
        return view;
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

}
