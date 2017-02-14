package netfoxs.coms.callcarddialer;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import netfoxs.coms.callcarddialer.Bean.Adsclass;
import netfoxs.coms.callcarddialer.adapter.Adsadapter;
import netfoxs.coms.callcarddialer.connectiondetecter.ConnectionDetector;

public class Notification_search extends AppCompatActivity {
    RecyclerView cardList;
    Spinner spinner_coundry;
    private String[] countrylist;
    private  String[] codelist;
    public String mcname;
    CoordinatorLayout coordinatorLayout;
    ArrayList<Adsclass> adsclasses=new ArrayList<Adsclass>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardList= (RecyclerView) findViewById(R.id.cardList);
        cardList.setLayoutManager(new LinearLayoutManager(this));
        spinner_coundry= (Spinner) findViewById(R.id.spinner_coundry);
        countrylist = getResources().getStringArray(R.array.countries_list);
        codelist=getResources().getStringArray(R.array.countrys_code);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countrylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_coundry.setAdapter(dataAdapter);
        spinner_coundry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mcname = codelist[position];
                if (adsclasses.size() == 0) {
                    checkConnection();
                } else {
                    adsclasses.clear();
                    checkConnection();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    public void checkConnection() {
        // TODO Auto-generated method stub
//		System.out.println("inside check connection"+flag);
        if(!ConnectionDetector.isConnectingToInternet(Notification_search.this))
        {
            Snakebarenotify("No internet connection");

        }
        else{

            employedetails();
        }
    }
    public void Snakebarenotify(String message)
    {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    public  void employedetails()
    {
       SharedPreferences sharedPreferences = getSharedPreferences("call", 0);
        int pos=sharedPreferences.getInt("pos", 0);
        String mfrom=codelist[pos];
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";
//String url="http://plataimport.com/jpg/search.php?from="+00+"&to="+00;
        String url="http://plataimport.com/jpg/search.php?from="+mfrom+"&to="+mcname;
        final ProgressDialog pDialog = new ProgressDialog(Notification_search.this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        RequestQueue queue = Volley.newRequestQueue(Notification_search.this);

        StringRequest sr = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("onResponse", response);
                try {
                    pDialog.dismiss();
                  JSONObject jsonObject1=new JSONObject(response);
                    int states=jsonObject1.getInt("status");
                    if(states==1)
                    {
                    JSONArray responarray=jsonObject1.getJSONArray("datas");
                    for (int i=0;i<responarray.length();i++)
                    {
                        JSONObject  adsobject=responarray.getJSONObject(i);
                       String image=adsobject.getString("image");
                       String url=adsobject.getString("url");
                       String date=adsobject.getString("date");
                            Adsclass adsclass=new Adsclass(image,date,url);
                            adsclasses.add(adsclass);
                    }
                    Adsadapter adsadapter=new Adsadapter(adsclasses,Notification_search.this);
                    cardList.setAdapter(adsadapter);

                    }
                    else
                    {
                        Snakebarenotify("No Data Found!");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                pDialog.dismiss();
                Snakebarenotify("error in connection");
            }
        });

        sr.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Adding request to request queue
        queue.add(sr);
    }
}
