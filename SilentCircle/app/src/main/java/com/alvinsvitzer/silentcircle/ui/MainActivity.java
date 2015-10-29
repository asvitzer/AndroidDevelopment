package com.alvinsvitzer.silentcircle.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.alvinsvitzer.silentcircle.R;
import com.alvinsvitzer.silentcircle.helper.SectionsPagerAdapter;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final int TAKE_PHOTO_REQUEST = 1;
    public static final int TAKE_VIDEO_REQUEST = 2;
    public static final int PICK_PHOTO_REQUEST = 3;
    public static final int PICK_VIDEO_REQUEST = 4;

    //Setting up OnClickListener in member variable area for readability when used later
    protected DialogInterface.OnClickListener mDialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch(which){
                //Take Picture
                case 0:
                    Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePhoto, TAKE_PHOTO_REQUEST);
                //Take Video
                case 1:
                //Choose Picture
                case 2:
                //Choose Video
                case 3:
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {

        //Used for showing the progress bar for actions later on. Needs to run before ContentView is set.
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null){
            //Start login process if there is no current user signed in
            startLoginActivity();
        }
        else{

            //If there is a user signed in already, print to the log file
            Log.i(TAG, currentUser.getUsername());
        }

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

    }

    public void startLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Do I not need break statements? I'm, guessing not since they're all going to new activities
        switch(id){

            case R.id.main_menu_logout:
                ParseUser.logOut();
                startLoginActivity();
            case R.id.main_menu_edit_friends:
                Intent intent = new Intent(this, EditFriendsActivity.class);
                startActivity(intent);
            case R.id.main_menu_camera:

                //AlertDialog builder that uses the Camera Choices array in
                //String resources to populate the options
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                //Set the message of the dialog box from a string resource
                builder.setItems(R.array.camera_choices,mDialogListener);

                //Create the dialog box and show it
                AlertDialog userDialog = builder.create();
                userDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

}
