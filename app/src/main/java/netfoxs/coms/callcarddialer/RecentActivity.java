package netfoxs.coms.callcarddialer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import netfoxs.coms.callcarddialer.Bean.Recentcalls;
import netfoxs.coms.callcarddialer.adapter.RecentcallAdapter;


public class RecentActivity extends AppCompatActivity {
ListView listview_recentcall;
    RecentcallAdapter recentcallAdapter;
     List<Recentcalls> recentcallses;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listview_recentcall= (ListView) findViewById(R.id.listview_recentcall);
        try
        {
             recentcallses=Recentcalls.findWithQuery(Recentcalls.class,"SELECT * FROM RECENTCALLS order by date_time desc;");
//            recentcallses=Recentcalls.listAll(Recentcalls.class);
             recentcallAdapter=new RecentcallAdapter(RecentActivity.this,recentcallses);
            listview_recentcall.setAdapter(recentcallAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        listview_recentcall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mInterstitialAd = new InterstitialAd(RecentActivity.this);
                // set the ad unit ID
                mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        /*adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                .addTestDevice("B310A5FDC1DF08F17626B9BAAECBC406")
                .build();*/
                AdRequest adRequest = new AdRequest.Builder()
                        .build();
                // Load ads into Interstitial Ads
                mInterstitialAd.loadAd(adRequest);

                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        showInterstitial();
                    }
                });
                SharedPreferences sharedPreferences=getSharedPreferences("call", 0);
                if(sharedPreferences.contains("number")) {
                    String number = sharedPreferences.getString("number", "");
                    String code = sharedPreferences.getString("code", "");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
                    String date = sdf.format(new Date());
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + code + number + ",,," + recentcallses.get(position).getContactNumber()));
                    startActivity(callIntent);
                }
                else
                {
                    Toast.makeText(RecentActivity.this, "Callcard number not selected. Please select one callcard number in the configuration option from Settings", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
