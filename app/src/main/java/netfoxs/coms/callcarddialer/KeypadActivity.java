package netfoxs.coms.callcarddialer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import netfoxs.coms.callcarddialer.Bean.Recentcalls;


public class KeypadActivity extends AppCompatActivity {
     Button button_one,button_two,button_three,button_four,button_five,button_six,button_seven,button_eight,button_nine,button_star,button_zero,button_hash;
     ImageButton iamagebutton_call,iamgebutton_backspace;
    Button imagebutton_resentcontact;
    EditText edittext_phonenumber;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button_one= (Button) findViewById(R.id.button_one);
        button_two= (Button) findViewById(R.id.button_two);
        button_three= (Button) findViewById(R.id.button_three);
        button_four= (Button) findViewById(R.id.button_four);
        button_five= (Button) findViewById(R.id.button_five);
        button_six= (Button) findViewById(R.id.button_six);
        button_seven= (Button) findViewById(R.id.button_seven);
        button_eight= (Button) findViewById(R.id.button_eight);
        button_nine= (Button) findViewById(R.id.button_nine);
        button_star= (Button) findViewById(R.id.button_star);
        button_zero= (Button) findViewById(R.id.button_zero);
        button_hash= (Button) findViewById(R.id.button_hash);

        imagebutton_resentcontact= (Button) findViewById(R.id.imagebutton_resentcontact);
        iamagebutton_call= (ImageButton) findViewById(R.id.iamagebutton_call);
        iamgebutton_backspace= (ImageButton) findViewById(R.id.iamgebutton_backspace);

        edittext_phonenumber= (EditText) findViewById(R.id.edittext_phonenumber);
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
     /*   AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                .addTestDevice("B310A5FDC1DF08F17626B9BAAECBC406")
                .build();*/
        mAdView.loadAd(adRequest);
        button_one.setOnClickListener(onClickListener);
        button_two.setOnClickListener(onClickListener);
        button_three.setOnClickListener(onClickListener);
        button_four.setOnClickListener(onClickListener);
        button_five.setOnClickListener(onClickListener);
        button_six.setOnClickListener(onClickListener);
        button_seven.setOnClickListener(onClickListener);
        button_eight.setOnClickListener(onClickListener);
        button_nine.setOnClickListener(onClickListener);
        button_zero.setOnClickListener(onClickListener);
        button_star.setOnClickListener(onClickListener);
        button_hash.setOnClickListener(onClickListener);
        iamgebutton_backspace.setOnClickListener(onClickListener);
        iamagebutton_call.setOnClickListener(onClickListener);
        imagebutton_resentcontact.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.button_one:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"1");
                    break;
                case R.id.button_two:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"2");
                    break;
                case R.id.button_three:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"3");
                    break;
                case R.id.button_four:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"4");
                    break;
                case R.id.button_five:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"5");
                    break;
                case R.id.button_six:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"6");
                    break;
                case R.id.button_seven:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"7");
                    break;
                case R.id.button_eight:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"8");
                    break;
                case R.id.button_nine:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"9");
                    break;
                case R.id.button_zero:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"0");
                    break;
                case R.id.button_star:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"*");
                    break;
                case R.id.button_hash:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"#");
                    break;
                case R.id.iamgebutton_backspace:
                    if(!(edittext_phonenumber.length()==0))
                        edittext_phonenumber.setText(edittext_phonenumber.getText().delete(edittext_phonenumber.length() - 1, edittext_phonenumber.length()));
                    break;
                case R.id.iamagebutton_call:
                    try {
                        mInterstitialAd = new InterstitialAd(KeypadActivity.this);
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
                        SharedPreferences sharedPreferences=getSharedPreferences("call",0);
                        if(sharedPreferences.contains("number")) {
                            String number = sharedPreferences.getString("number", "");
                            String code = sharedPreferences.getString("code", "");
                            DateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy hh:mm ss");

                            Date today = new Date();
                            String s = dateFormatter.format(today);
                            Recentcalls recentcalls = new Recentcalls("Unknown", edittext_phonenumber.getText().toString(),s);
                            recentcalls.save();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + code + number + ",,," + edittext_phonenumber.getText().toString().trim()));
                            startActivity(callIntent);

                            ArrayList<contactActivity.Contact> contacts = new ArrayList<contactActivity.Contact>();
                            contacts.addAll(contactActivity.result);
                        }
                        else
                        {
                            Toast.makeText(KeypadActivity.this,"Callcard number not selected. Please select one callcard number in the configuration option from Settings",Toast.LENGTH_LONG).show();
                        }

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case R.id.imagebutton_resentcontact:
                    edittext_phonenumber.setText(edittext_phonenumber.getText().toString()+"+");
            }

        }
    };

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
