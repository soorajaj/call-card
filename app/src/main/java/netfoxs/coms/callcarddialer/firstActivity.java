package netfoxs.coms.callcarddialer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import netfoxs.coms.callcarddialer.Bean.Callcard;

public class firstActivity extends AppCompatActivity {
    EditText editText_callcadnumber;
    Button button_save,button_cancel;
    SharedPreferences prefrencnumber;
    public int PERMISSION_REQUEST_CONTACT = 12;
    SharedPreferences.Editor editor;
    CoordinatorLayout coordinatorLayout;
    SharedPreferences sharedPreferences;
    Spinner spinner_coundry;
    TextView textView_code;
    EditText editetext_callcardname;
    private String[] countrylist;
    private  String[] codelist;
    public int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        askForContactPermission();
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.cordinaterlayaout);
        editText_callcadnumber= (EditText) findViewById(R.id.editText_callcadnumber);
        button_save= (Button) findViewById(R.id.button_save);
        button_cancel= (Button) findViewById(R.id.button_cancel);
        textView_code= (TextView) findViewById(R.id.textView_code);
        spinner_coundry= (Spinner) findViewById(R.id.spinner_coundry);
        countrylist = getResources().getStringArray(R.array.countries_list);
        codelist=getResources().getStringArray(R.array.countrys_code);
        editetext_callcardname= (EditText) findViewById(R.id.editetext_callcardname);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countrylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_coundry.setAdapter(dataAdapter);
        spinner_coundry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     textView_code.setText(codelist[position]);
        pos=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});
        /*final String flag=getIntent().getStringExtra("flag");
        if(flag.equalsIgnoreCase("fromsettings"))
        {
            prefrencnumber=getSharedPreferences("call", 0);
            editText_callcadnumber.setText(prefrencnumber.getString("number",""));
        }*/

        editText_callcadnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               /* if(flag.equalsIgnoreCase("fromsettings"))
                {
                if(s.length()!=0)
                {
                   button_save.setVisibility(View.VISIBLE);
                    button_cancel.setVisibility(View.GONE);
                }
                    else
                {
                    button_save.setVisibility(View.GONE);
                    button_cancel.setVisibility(View.VISIBLE);
                }
                }*/
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (!editText_callcadnumber.getText().toString().equalsIgnoreCase(""))
                 {
                     if(!textView_code.getText().toString().equalsIgnoreCase("0-00"))
                     {
                         sharedPreferences = getSharedPreferences("call", 0);
                         editor = sharedPreferences.edit();
                         editor.putString("number", editText_callcadnumber.getText().toString().trim());
                         editor.putString("code", textView_code.getText().toString().trim());
                         editor.putInt("pos", pos);
                         editor.putString("ids","1");
                         editor.commit();
                         Intent intent=new Intent(firstActivity.this,Maincall.class);
                         startActivity(intent);
                         finish();
                         Callcard callcard=new Callcard(editetext_callcardname.getText().toString(),editText_callcadnumber.getText().toString(),textView_code.getText().toString(),pos,"true");
                         callcard.save();
                     }
                     else
                     {
                         Snakebarenotify("Please select a country ");
                     }

                 } else {
                     Snakebarenotify("Please enter a valid number");
                 }
             }
         });
    }
    public  void Snakebarenotify(String message)
    {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(firstActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(firstActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(firstActivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(firstActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS,Manifest.permission.CALL_PHONE}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(firstActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {

            }
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 12: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {
    }
}
