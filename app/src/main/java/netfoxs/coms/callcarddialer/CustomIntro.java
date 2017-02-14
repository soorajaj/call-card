package netfoxs.coms.callcarddialer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public final class CustomIntro extends AppIntro {
    SharedPreferences prefrencnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefrencnumber=getSharedPreferences("call",0);
        if(prefrencnumber.contains("number"))
        {
            Intent intent=new Intent(this, Maincall.class);
            startActivity(intent);
            finish();
        }
        addSlide(AppIntroFragment.newInstance("", "",
                R.drawable.firstimage, Color.parseColor("#2196F3")));

        addSlide(AppIntroFragment.newInstance("", Html.fromHtml(""),
                R.drawable.secontimage, Color.parseColor("#2196F3")));

        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
        showSkipButton(false);

//        setVibrate(true);
//        setVibrateIntensity(30);
    }

    private void loadMainActivity(){
        Intent intent = new Intent(this, firstActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        loadMainActivity();
//        Toast.makeText(getApplicationContext(),getString(R.string.skip),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}