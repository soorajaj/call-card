package netfoxs.coms.callcarddialer.utils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import netfoxs.coms.callcarddialer.R;


public class Aboutus extends AppCompatActivity {
WebView textContent;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
       /* AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                .addTestDevice("B310A5FDC1DF08F17626B9BAAECBC406")
                .build();*/
        mAdView.loadAd(adRequest);
        textContent= (WebView) findViewById(R.id.textContent);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "Call card dialer is an application which allows you to make calls easily when you are using calling cards.  After entering the access number of your calling card in the settings , you can easily call your contacts from the list.\n" +
                "There is recent calls option to access contacts easily.\n" +
                " Also you can see what best offers you have from your place to other destinations. This simple application not only a time saver app but also a money saver.The offers are posted in the application by the calling card companies or agencies.  By clicking the offers it will take you to the website assigned by them.   We do not have any responsibility for the problems that you may face from those websites.";
        text+= "</p></body></html>";
        textContent.loadData(text, "text/html", "utf-8");
    }

}
