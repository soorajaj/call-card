package netfoxs.coms.callcarddialer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import netfoxs.coms.callcarddialer.utils.Aboutus;


public class SettingsActivity extends AppCompatActivity {
ImageView rateapp_iamgevuiew,tellafrient_imagview,aboutus,feedback,imageview_configuration,imageveiw_notification;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mcontext=SettingsActivity.this;
        rateapp_iamgevuiew= (ImageView) findViewById(R.id.rateapp_iamgevuiew);
        tellafrient_imagview= (ImageView) findViewById(R.id.tellafrient_imagview);
        imageveiw_notification= (ImageView) findViewById(R.id.imageveiw_notification);
        aboutus= (ImageView) findViewById(R.id.aboutus);
        feedback= (ImageView) findViewById(R.id.feedback);
        imageview_configuration= (ImageView) findViewById(R.id.imageview_configuration);
        rateapp_iamgevuiew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse("market://details?id=netfoxs.coms.callcarddialer"));
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=netfoxs.coms.callcarddialer"));
                  startActivity(intent);
            }
        });

        tellafrient_imagview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=netfoxs.coms.callcarddialer");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Call card dialer is an application which allows you to make calls easily when you are using calling cards");
                startActivity(Intent.createChooser(intent, "Share"));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this, Aboutus.class);
                startActivity(intent);
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // custom dialog
                final Dialog dialog = new Dialog(mcontext);
                dialog.setContentView(R.layout.feedbacdilog);
                // Custom Android Allert Dialog Title
                dialog.setTitle("Feedback");

                Button dialogButtonCancel = (Button) dialog.findViewById(R.id.cancel_button);
                Button dialogButtonOk = (Button) dialog.findViewById(R.id.send_button);
                final EditText Edittext_feedback= (EditText) dialog.findViewById(R.id.Edittext_feedback);
                // Click cancel to dismiss android custom dialog box
                dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                // Your android custom dialog ok action
                // Action for custom dialog ok button click
                dialogButtonOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent feedbackEmail = new Intent(Intent.ACTION_SEND);

                        feedbackEmail.setType("text/email");
                        feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{"callcarddialer@gmail.com"});
                        feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        feedbackEmail.putExtra(Intent.EXTRA_TEXT,Edittext_feedback.getText().toString());
                        startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        imageview_configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,Callcardnumber.class);
                startActivity(intent);
            }
        });
        imageveiw_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(SettingsActivity.this,Notification_search.class);
                startActivity(intent);
            }
        });
    }
}
