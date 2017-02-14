package netfoxs.coms.callcarddialer;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import netfoxs.coms.callcarddialer.Bean.Recentcalls;
import netfoxs.coms.callcarddialer.utils.CircularContactView;
import netfoxs.coms.callcarddialer.utils.ContactImageUtil;
import netfoxs.coms.callcarddialer.utils.ImageCache;
import netfoxs.coms.callcarddialer.utils.async_task_thread_pool.AsyncTaskEx;
import netfoxs.coms.callcarddialer.utils.async_task_thread_pool.AsyncTaskThreadPool;
import sensomate.library.PinnedHeaderListView;
import sensomate.library.SearchablePinnedHeaderListViewAdapter;
import sensomate.library.StringArrayAlphabetIndexer;

public class contactActivity extends AppCompatActivity {
    private LayoutInflater mInflater;
    private PinnedHeaderListView mListView;
    private ContactsAdapter mAdapter;
    CoordinatorLayout coordinatorLayout;
    ArrayList<Contact> contacts=new ArrayList<Contact>();
    public int PERMISSION_REQUEST_CONTACT = 12;
    public static ArrayList<Contact> result = new ArrayList<>();
    ArrayList<Contact> result1 = new ArrayList<>();
    InterstitialAd mInterstitialAd;
    AdRequest adRequest;
    ProgressDialog progressDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(contactActivity.this);
        setContentView(R.layout.activity_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mListView = (PinnedHeaderListView) findViewById(android.R.id.list);
//        final ArrayList<Contact> contacts = getContacts();
        askForContactPermission();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(contactActivity.this, contacts.get(position).contactnumber, Toast.LENGTH_SHORT).show();

            }
        });




    }




    public static int getResIdFromAttribute(final Activity activity, final int attr) {
        if (attr == 0)
            return 0;
        final TypedValue typedValue = new TypedValue();
        activity.getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId;
    }

   /* private ArrayList<Contact> getContacts() {

        if (checkContactsReadPermission()) {

            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                Contact contact = new Contact();
                contact.displayName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                contact.contactnumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contact.photoId = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                contact.contactUri = Uri.parse(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID)) + "," + phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY)));
                result.add(contact);
            }
            phones.close();
           *//* Uri uri = ContactsQuery.CONTENT_URI;
            final Cursor cursor = managedQuery(uri, ContactsQuery.PROJECTION, ContactsQuery.SELECTION, null, ContactsQuery.SORT_ORDER);
            if (cursor == null)
                return null;

            while (cursor.moveToNext()) {
                Contact contact = new Contact();
                contact.contactUri = ContactsContract.Contacts.getLookupUri(
                        cursor.getLong(ContactsQuery.ID),
                        cursor.getString(ContactsQuery.LOOKUP_KEY));
                contact.displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
                contact.photoId = cursor.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
                result.add(contact);
            }*//*


        }
        return result;
    }*/

   /* private boolean checkContactsReadPermission() {
        String permission = "android.permission.READ_CONTACTS";
        int res = checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.mAsyncTaskThreadPool.cancelAllTasks(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public static class Contact {
        long contactId;
        Uri contactUri;
        String displayName;
        String photoId;
        String contactnumber;

        public long getContactId() {
            return contactId;
        }

        public void setContactId(long contactId) {
            this.contactId = contactId;
        }

        public Uri getContactUri() {
            return contactUri;
        }

        public void setContactUri(Uri contactUri) {
            this.contactUri = contactUri;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getPhotoId() {
            return photoId;
        }

        public void setPhotoId(String photoId) {
            this.photoId = photoId;
        }

        public String getContactnumber() {
            return contactnumber;
        }

        public void setContactnumber(String contactnumber) {
            this.contactnumber = contactnumber;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menuItem_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                performSearch(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void performSearch(final String queryText) {
        mAdapter.getFilter().filter(queryText);
        mAdapter.setHeaderViewVisible(TextUtils.isEmpty(queryText));
    }


    private class ContactsAdapter extends SearchablePinnedHeaderListViewAdapter<Contact> {
        private ArrayList<Contact> mContacts;
        private final int CONTACT_PHOTO_IMAGE_SIZE;
        private final int[] PHOTO_TEXT_BACKGROUND_COLORS;
        private final AsyncTaskThreadPool mAsyncTaskThreadPool = new AsyncTaskThreadPool(1, 2, 10);

        @Override
        public CharSequence getSectionTitle(int sectionIndex) {
            return ((StringArrayAlphabetIndexer.AlphaBetSection) getSections()[sectionIndex]).getName();
        }

        public ContactsAdapter(final ArrayList<Contact> contacts) {
            setData(contacts);

            PHOTO_TEXT_BACKGROUND_COLORS = getResources().getIntArray(R.array.contacts_text_background_colors);
            CONTACT_PHOTO_IMAGE_SIZE = getResources().getDimensionPixelSize(
                    R.dimen.list_item__contact_imageview_size);
        }

        public void setData(final ArrayList<Contact> contacts) {
            this.mContacts = contacts;
            final String[] generatedContactNames = generateContactNames(contacts);
            setSectionIndexer(new StringArrayAlphabetIndexer(generatedContactNames, true));
        }

        private String[] generateContactNames(final List<Contact> contacts) {
            final ArrayList<String> contactNames = new ArrayList<String>();
            if (contacts != null)
                for (final Contact contactEntity : contacts)
                    contactNames.add(contactEntity.displayName);
            return contactNames.toArray(new String[contactNames.size()]);
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            final View rootView;
            if (convertView == null) {
                holder = new ViewHolder();
                rootView = mInflater.inflate(R.layout.listview_item, parent, false);
                holder.friendProfileCircularContactView = (CircularContactView) rootView
                        .findViewById(R.id.listview_item__friendPhotoImageView);
                holder.friendProfileCircularContactView.getTextView().setTextColor(0xFFffffff);
                holder.friendName = (TextView) rootView
                        .findViewById(R.id.listview_item__friendNameTextView);
                holder.headerView = (TextView) rootView.findViewById(R.id.header_text);
                holder.layout_click = (LinearLayout) rootView.findViewById(R.id.layout_click);
                rootView.setTag(holder);
            } else {
                rootView = convertView;
                holder = (ViewHolder) rootView.getTag();
            }
            final Contact contact = getItem(position);
            final String displayName = contact.displayName;
            holder.friendName.setText(displayName);
            holder.layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (!(contact.contactnumber == null)) {
                            mInterstitialAd = new InterstitialAd(contactActivity.this);
                            // set the ad unit ID
                            mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));

        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        // Check the LogCat to get your test device ID
                .addTestDevice("B310A5FDC1DF08F17626B9BAAECBC406")
                .build();
                            AdRequest adRequest = new AdRequest.Builder()
                                    .build();
                            // Load ads into Interstitial Ads
                            mInterstitialAd.loadAd(adRequest);

                            mInterstitialAd.setAdListener(new AdListener() {
                                public void onAdLoaded() {
                                    showInterstitial();
                                }
                            });
                            Log.i("contact", contact.contactnumber.toString());
                            SharedPreferences sharedPreferences = getSharedPreferences("call", 0);
                            if(sharedPreferences.contains("number"))
                            {
                                String number = sharedPreferences.getString("number", "");
                                String code=sharedPreferences.getString("code","");
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    callIntent.setPackage("com.android.server.telecom");
                                } else {
                                    callIntent.setPackage("com.android.phone");
                                }
                                DateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy hh:mm ss");
                                Date today = new Date();
                                String s = dateFormatter.format(today);
                                Recentcalls recentcalls=new Recentcalls(contact.displayName,contact.contactnumber,s);
                                recentcalls.save();
                                callIntent.setData(Uri.parse("tel:"+code+ number +",,," + contact.contactnumber));
                                startActivity(callIntent);
                            }else
                            {
                              Snakebarenotify("Callcard number not selected. Please select one callcard number in the configuration option from Settings");
                            }

                        } else {
                            Snakebarenotify("No contact number associated with this contact ");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            boolean hasPhoto = !TextUtils.isEmpty(contact.photoId);
            if (holder.updateTask != null && !holder.updateTask.isCancelled())
                holder.updateTask.cancel(true);
            final Bitmap cachedBitmap = hasPhoto ? ImageCache.INSTANCE.getBitmapFromMemCache(contact.photoId) : null;
            if (cachedBitmap != null)
                holder.friendProfileCircularContactView.setImageBitmap(cachedBitmap);
            else {
                final int backgroundColorToUse = PHOTO_TEXT_BACKGROUND_COLORS[position
                        % PHOTO_TEXT_BACKGROUND_COLORS.length];
                if (TextUtils.isEmpty(displayName))
                    holder.friendProfileCircularContactView.setImageResource(R.drawable.ic_person_white_120dp,
                            backgroundColorToUse);
                else {
                    final String characterToShow = TextUtils.isEmpty(displayName) ? "" : displayName.substring(0, 1).toUpperCase(Locale.getDefault());
                    holder.friendProfileCircularContactView.setTextAndBackgroundColor(characterToShow, backgroundColorToUse);
                }
                if (hasPhoto) {
                    holder.updateTask = new AsyncTaskEx<Void, Void, Bitmap>() {

                        @Override
                        public Bitmap doInBackground(final Void... params) {
                            if (isCancelled())
                                return null;
                            final Bitmap b = ContactImageUtil.loadContactPhotoThumbnail(contactActivity.this, contact.photoId, CONTACT_PHOTO_IMAGE_SIZE);
                            if (b != null)
                                return ThumbnailUtils.extractThumbnail(b, CONTACT_PHOTO_IMAGE_SIZE,
                                        CONTACT_PHOTO_IMAGE_SIZE);
                            return null;
                        }

                        @Override
                        public void onPostExecute(final Bitmap result) {
                            super.onPostExecute(result);
                            if (result == null)
                                return;
                            ImageCache.INSTANCE.addBitmapToCache(contact.photoId, result);
                            holder.friendProfileCircularContactView.setImageBitmap(result);
                        }
                    };
                    mAsyncTaskThreadPool.executeAsyncTask(holder.updateTask);
                }
            }
            bindSectionHeader(holder.headerView, null, position);

            return rootView;
        }

        @Override
        public boolean doFilter(final Contact item, final CharSequence constraint) {
            if (TextUtils.isEmpty(constraint))
                return true;
            final String displayName = item.displayName;
            return !TextUtils.isEmpty(displayName) && displayName.toLowerCase(Locale.getDefault())
                    .contains(constraint.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public ArrayList<Contact> getOriginalList() {
            return mContacts;
        }


    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    // /////////////////////////////////////////////////////////////////////////////////////
    // ViewHolder //
    // /////////////
    private static class ViewHolder {
        public CircularContactView friendProfileCircularContactView;
        TextView friendName, headerView;
        LinearLayout layout_click;
        public AsyncTaskEx<Void, Void, Bitmap> updateTask;
    }


    public ArrayList<Contact> readContacts() {
         progressDialog=new ProgressDialog(contactActivity.this);
        progressDialog.setMessage("loading");
        progressDialog.show();
        ArrayList<Contact> result = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("......Contact Details.....");
        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String phone = null;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";
        String imaglookup;
        Bitmap bitmap = null;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                imaglookup = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);
                    sb.append("\n Contact Name:" + name);
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sb.append("\n Phone number:" + phone);
                        System.out.println("phone" + phone);
                    }
                    pCur.close();
                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
                        System.out.println("Email " + emailContact + " Email Type : " + emailType);
                    }
                    emailCur.close();
                }
                if (image_uri != null) {
                    System.out.println(Uri.parse(image_uri));
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(image_uri));
                        sb.append("\n Image in Bitmap:" + bitmap);
                        System.out.println(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                sb.append("\n........................................");

                Contact contact = new Contact();
                contact.displayName = name;
                contact.contactnumber = phone;
                contact.contactUri = Uri.parse(id + "," + imaglookup);
                if (image_uri != null) {
                    contact.photoId = image_uri;
                } else {
                    contact.photoId = "";
                }
                result.add(contact);
            }
        }
        progressDialog.dismiss();
        return result;
    }

    public void Snakebarenotify(String message) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void askForContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(contactActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(contactActivity.this,
                        Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contactActivity.this);
                    builder.setTitle("Contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("please confirm Contacts access");//TODO put real question
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {Manifest.permission.READ_CONTACTS}
                                    , PERMISSION_REQUEST_CONTACT);
                        }
                    });
                    builder.show();
                    // Show an expanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(contactActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            PERMISSION_REQUEST_CONTACT);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {



               /* ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
                listViewContactsLoader.execute();*/
                ArrayList<Contact> contacts=new ArrayList<Contact>();
                contacts=readContacts();
                Collections.sort(contacts, new Comparator<Contact>() {
                    @Override
                    public int compare(Contact lhs, Contact rhs) {
                        char lhsFirstLetter = TextUtils.isEmpty(lhs.displayName) ? ' ' : lhs.displayName.charAt(0);
                        char rhsFirstLetter = TextUtils.isEmpty(rhs.displayName) ? ' ' : rhs.displayName.charAt(0);
                        int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                        if (firstLetterComparison == 0)
                            return lhs.displayName.compareTo(rhs.displayName);
                        return firstLetterComparison;
                    }
                });
                mAdapter = new ContactsAdapter(contacts);

                int pinnedHeaderBackgroundColor = getResources().getColor(getResIdFromAttribute(contactActivity.this, android.R.attr.colorBackground));
                mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
                mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
                mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mListView, false));
                mListView.setAdapter(mAdapter);
                mListView.setOnScrollListener(mAdapter);
                mListView.setEnableHeaderTransparencyChanges(false);
            }
        } else {


            ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
            listViewContactsLoader.execute();
            ArrayList<Contact> contacts=new ArrayList<Contact>();
            contacts=readContacts();
            Collections.sort(contacts, new Comparator<Contact>() {
                @Override
                public int compare(Contact lhs, Contact rhs) {
                    char lhsFirstLetter = TextUtils.isEmpty(lhs.displayName) ? ' ' : lhs.displayName.charAt(0);
                    char rhsFirstLetter = TextUtils.isEmpty(rhs.displayName) ? ' ' : rhs.displayName.charAt(0);
                    int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                    if (firstLetterComparison == 0)
                        return lhs.displayName.compareTo(rhs.displayName);
                    return firstLetterComparison;
                }
            });
            mAdapter = new ContactsAdapter(contacts);

            int pinnedHeaderBackgroundColor = getResources().getColor(getResIdFromAttribute(contactActivity.this, android.R.attr.colorBackground));
            mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
            mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
            mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mListView, false));
            mListView.setAdapter(mAdapter);
            mListView.setOnScrollListener(mAdapter);
            mListView.setEnableHeaderTransparencyChanges(false);
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

                   /* ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();
                    listViewContactsLoader.execute();*/

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    // set the ad unit ID


                    ArrayList<Contact> contacts=new ArrayList<Contact>();
                    contacts=readContacts();
                    Collections.sort(contacts, new Comparator<Contact>() {
                        @Override
                        public int compare(Contact lhs, Contact rhs) {
                            char lhsFirstLetter = TextUtils.isEmpty(lhs.displayName) ? ' ' : lhs.displayName.charAt(0);
                            char rhsFirstLetter = TextUtils.isEmpty(rhs.displayName) ? ' ' : rhs.displayName.charAt(0);
                            int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                            if (firstLetterComparison == 0)
                                return lhs.displayName.compareTo(rhs.displayName);
                            return firstLetterComparison;
                        }
                    });
                    mAdapter = new ContactsAdapter(contacts);

                    int pinnedHeaderBackgroundColor = getResources().getColor(getResIdFromAttribute(contactActivity.this, android.R.attr.colorBackground));
                    mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
                    mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
                    mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mListView, false));
                    mListView.setAdapter(mAdapter);
                    mListView.setOnScrollListener(mAdapter);
                    mListView.setEnableHeaderTransparencyChanges(false);
                } else {
                    Snakebarenotify("No permission for contacts");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * An AsyncTask class to retrieve and load listview with contacts
     */
    public class ListViewContactsLoader extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {

            String image_uri = "";
            String imaglookup;
            String id;
            Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

try
{



            // Querying the table ContactsContract.Contacts to retrieve all the contacts
            Cursor contactsCursor = getContentResolver().query(contactsUri, null, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

            if (contactsCursor.moveToFirst()) {
                do {
                    long contactId = contactsCursor.getLong(contactsCursor.getColumnIndex("_ID"));


                    Uri dataUri = ContactsContract.Data.CONTENT_URI;

                    // Querying the table ContactsContract.Data to retrieve individual items like
                    // home phone, mobile phone, work email etc corresponding to each contact
                    Cursor dataCursor = getContentResolver().query(dataUri, null,
                            ContactsContract.Data.CONTACT_ID + "=" + contactId,
                            null, null);


                    String displayName = "";
                    String nickName = "";
                    String homePhone = "";
                    String mobilePhone = "";
                    String workPhone = "";
                    String photoPath = "";
                    byte[] photoByte = null;
                    String homeEmail = "";
                    String workEmail = "";
                    String companyName = "";
                    String title = "";


                    if (dataCursor.moveToFirst()) {
                        // Getting Display Name
                        displayName = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                        do {

                            // Getting NickName
                            if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
                                nickName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                            id = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts._ID));
                            image_uri = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                            imaglookup = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));

                            // Getting Phone numbers
                            if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                                switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                        homePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                        mobilePhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                        workPhone = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                }
                            }

                            // Getting EMails
                            if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                                switch (dataCursor.getInt(dataCursor.getColumnIndex("data2"))) {
                                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                        homeEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                        workEmail = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                        break;
                                }
                            }

                            // Getting Organization details
                            if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                                companyName = dataCursor.getString(dataCursor.getColumnIndex("data1"));
                                title = dataCursor.getString(dataCursor.getColumnIndex("data4"));
                            }

                            // Getting Photo
                            if (dataCursor.getString(dataCursor.getColumnIndex("mimetype")).equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                                photoByte = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));

                                if (photoByte != null) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoByte, 0, photoByte.length);

                                    // Getting Caching directory
                                    File cacheDirectory = getBaseContext().getCacheDir();

                                    // Temporary file to store the contact image
                                    File tmpFile = new File(cacheDirectory.getPath() + "/wpta_" + contactId + ".png");

                                    // The FileOutputStream to the temporary file
                                    try {
                                        FileOutputStream fOutStream = new FileOutputStream(tmpFile);

                                        // Writing the bitmap to the temporary file as png file
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOutStream);

                                        // Flush the FileOutputStream
                                        fOutStream.flush();

                                        //Close the FileOutputStream
                                        fOutStream.close();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    photoPath = tmpFile.getPath();
                                }

                            }

                        } while (dataCursor.moveToNext());

                        String details = "";

                        // Concatenating various information to single string
                        if (homePhone != null && !homePhone.equals(""))
                            details = "HomePhone : " + homePhone + "\n";
                        if (mobilePhone != null && !mobilePhone.equals(""))
                            details += "MobilePhone : " + mobilePhone + "\n";
                        if (workPhone != null && !workPhone.equals(""))
                            details += "WorkPhone : " + workPhone + "\n";
                        if (nickName != null && !nickName.equals(""))
                            details += "NickName : " + nickName + "\n";
                        if (homeEmail != null && !homeEmail.equals(""))
                            details += "HomeEmail : " + homeEmail + "\n";
                        if (workEmail != null && !workEmail.equals(""))
                            details += "WorkEmail : " + workEmail + "\n";
                        if (companyName != null && !companyName.equals(""))
                            details += "CompanyName : " + companyName + "\n";
                        if (title != null && !title.equals(""))
                            details += "Title : " + title + "\n";

                        Contact contact = new Contact();
                        contact.displayName = displayName;
                        if (mobilePhone != null && !mobilePhone.equals(""))
                            contact.contactnumber = mobilePhone;
                        contact.contactUri = Uri.parse(id + "," + imaglookup);
                        if (image_uri != null) {
                            contact.photoId = image_uri;
                        } else {
                            contact.photoId = "";
                        }
                        result1.add(contact);


                        // Adding id, display name, path to photo and other details to cursor
                    }

                } while (contactsCursor.moveToNext());
            }

}catch (Exception e)
{
    e.printStackTrace();
}

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            Collections.sort(result1, new Comparator<Contact>() {
                @Override
                public int compare(Contact lhs, Contact rhs) {
                    char lhsFirstLetter = TextUtils.isEmpty(lhs.displayName) ? ' ' : lhs.displayName.charAt(0);
                    char rhsFirstLetter = TextUtils.isEmpty(rhs.displayName) ? ' ' : rhs.displayName.charAt(0);
                    int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                    if (firstLetterComparison == 0)
                        return lhs.displayName.compareTo(rhs.displayName);
                    return firstLetterComparison;
                }
            });
            mAdapter = new ContactsAdapter(result1);

            int pinnedHeaderBackgroundColor = getResources().getColor(getResIdFromAttribute(contactActivity.this, android.R.attr.colorBackground));
            mAdapter.setPinnedHeaderBackgroundColor(pinnedHeaderBackgroundColor);
            mAdapter.setPinnedHeaderTextColor(getResources().getColor(R.color.pinned_header_text));
            mListView.setPinnedHeaderView(mInflater.inflate(R.layout.pinned_header_listview_side_header, mListView, false));
            mListView.setAdapter(mAdapter);
            mListView.setOnScrollListener(mAdapter);
            mListView.setEnableHeaderTransparencyChanges(false);
        }
    }


}

