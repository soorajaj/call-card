package netfoxs.coms.callcarddialer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import netfoxs.coms.callcarddialer.Bean.Callcard;
import netfoxs.coms.callcarddialer.adapter.Callcardadapter;

public class Callcardnumber extends AppCompatActivity {
     ListView callcardlist;
    List<Callcard> adsclasses=new ArrayList<>();
    Button button_addmore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callcardnumber);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        button_addmore= (Button) findViewById(R.id.button_addmore);
        callcardlist= (ListView) findViewById(R.id.callcardlist);



        button_addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Callcardnumber.this,ChaingnumberActivity.class);
                intent.putExtra("flag","new");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adsclasses=Callcard.listAll(Callcard.class);
        Callcardadapter adsadapter=new Callcardadapter(adsclasses,Callcardnumber.this);
        callcardlist.setAdapter(adsadapter);
    }
}
