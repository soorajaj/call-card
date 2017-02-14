package netfoxs.coms.callcarddialer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import netfoxs.coms.callcarddialer.Bean.Callcard;

public class ChaingnumberActivity extends AppCompatActivity {
    EditText editText_callcadnumber;
    Button button_save,button_cancel;
    SharedPreferences.Editor editor;
    CoordinatorLayout coordinatorLayout;
    SharedPreferences sharedPreferences;
    Spinner spinner_coundry;
    TextView textView_code;
    private String[] countrylist;
    private  String[] codelist;
    EditText editetext_callcardname;
    public int pos;
    public long id;
    CheckBox checkBox;
    public String flag="new";
public String mstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaingnumber);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.cordinaterlayaout);
        editText_callcadnumber= (EditText) findViewById(R.id.editText_callcadnumber);
        button_save= (Button) findViewById(R.id.button_save);
        button_cancel= (Button) findViewById(R.id.button_cancel);
        textView_code= (TextView) findViewById(R.id.textView_code);
        spinner_coundry= (Spinner) findViewById(R.id.spinner_coundry);
        checkBox= (CheckBox) findViewById(R.id.checkBox);
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
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
          flag=getIntent().getStringExtra("flag");
        if(flag.equalsIgnoreCase("fromlist"))
        {

            editetext_callcardname.setText(getIntent().getStringExtra("name"));
            int poss=getIntent().getIntExtra("pos",0);
            spinner_coundry.setSelection(poss);
            textView_code.setText(codelist[poss]);
            editText_callcadnumber.setText(getIntent().getStringExtra("number"));
            id=getIntent().getLongExtra("id", id);
            mstate=getIntent().getStringExtra("state");
            if(mstate.equalsIgnoreCase("true"))
            {
                checkBox.setChecked(true);
            }
        }

        /*sharedPreferences=getSharedPreferences("call", 0);
        editText_callcadnumber.setText(sharedPreferences.getString("number",""));
        textView_code.setText(sharedPreferences.getString("code",""));
        spinner_coundry.setSelection(sharedPreferences.getInt("pos", 0));*/
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

                if (s.length() != 0) {
                    button_save.setVisibility(View.VISIBLE);
                    button_cancel.setVisibility(View.GONE);
                } else {
                    button_save.setVisibility(View.GONE);
                    button_cancel.setVisibility(View.VISIBLE);
                }

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
                             if(flag.equalsIgnoreCase("new"))
                             {
                                /* sharedPreferences = getSharedPreferences("call", 0);
                                 editor = sharedPreferences.edit();
                                 editor.putString("number",editText_callcadnumber.getText().toString().trim());
                                 editor.putString("code",textView_code.getText().toString().trim());
                                 editor.putInt("pos", pos);
                                 editor.commit();*/
                                 finish();
                                 Callcard callcard=new Callcard(editetext_callcardname.getText().toString(),editText_callcadnumber.getText().toString(),textView_code.getText().toString(),pos,"false");
                                 callcard.save();
                             }
                             else {
                                 java.util.List<Callcard> books = Callcard.findWithQuery(Callcard.class, "UPDATE CALLCARD SET carde_name='"+editetext_callcardname.getText().toString()+"', call_number='"+editText_callcadnumber.getText().toString()+"', call_code='"+textView_code.getText().toString()+"', position='"+pos+"' WHERE id='"+id+"'");
                                 finish();
                             }

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
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                                 sharedPreferences = getSharedPreferences("call", 0);
                                 String ids=sharedPreferences.getString("ids","");
                    java.util.List<Callcard> bookss = Callcard.findWithQuery(Callcard.class, "UPDATE CALLCARD SET state='false' WHERE id='" + ids+ "'");
                                 editor = sharedPreferences.edit();
                                 editor.putString("number", editText_callcadnumber.getText().toString().trim());
                                 editor.putString("code", textView_code.getText().toString().trim());
                                 editor.putInt("pos", pos);
                                 editor.putString("ids",String.valueOf(id));
                                 editor.commit();
                    java.util.List<Callcard> books = Callcard.findWithQuery(Callcard.class, "UPDATE CALLCARD SET state='true' WHERE id='"+id+"'");

                }else
                {
                    sharedPreferences = getSharedPreferences("call", 0);
                    String ids=sharedPreferences.getString("ids","");
                    java.util.List<Callcard> bookss=Callcard.findWithQuery(Callcard.class, "SELECT * FROM CALLCARD WHERE id='" + ids+ "'");
                    editor = sharedPreferences.edit();
                    editor.putString("number", bookss.get(0).getCallNumber());
                    editor.putString("code", bookss.get(0).getCallCode());
                    editor.putInt("pos", bookss.get(0).getPosition());
                    editor.putString("ids",String.valueOf( bookss.get(0).getId()));
                    editor.commit();
                    java.util.List<Callcard> booksss = Callcard.findWithQuery(Callcard.class, "UPDATE CALLCARD SET state='true' WHERE id='"+ids+"'");
                    java.util.List<Callcard> books = Callcard.findWithQuery(Callcard.class, "UPDATE CALLCARD SET state='false' WHERE id='"+id+"'");
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

}
