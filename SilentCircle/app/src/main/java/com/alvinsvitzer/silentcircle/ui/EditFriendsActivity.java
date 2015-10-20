package com.alvinsvitzer.silentcircle.ui;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.alvinsvitzer.silentcircle.R;
import com.alvinsvitzer.silentcircle.model.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class EditFriendsActivity extends ListActivity {

    public static final String TAG = EditFriendsActivity.class.getSimpleName();
    protected List<ParseUser> mUserList;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Used for showing the progress bar for actions later on. Needs to run before ContentView is set.
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
        
       // setUpActionBar();

        //Set List View for activity to allow multiple selection. In this case, friends from Parse.
        getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

    }

    private void setUpActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume(){
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        //Activate progress indicator for creating user object and passing into Parse.
        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(50);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                setProgressBarIndeterminateVisibility(true);

                //Success
                if (e == null) {

                    mUserList = users;
                    String userNames[] = new String[mUserList.size()];

                    int x = 0;
                    for (ParseUser user : mUserList) {
                        userNames[x] = user.getUsername();
                        x++;
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            EditFriendsActivity.this,
                            android.R.layout.simple_list_item_checked,
                            userNames);

                    setListAdapter(adapter);

                } else {

                    //Send error message to log.
                    Log.e(TAG, e.getMessage());

                    //Show data validation error as dialog box from Parse exception

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditFriendsActivity.this);

                    //Set the message of the dialog box from a string resource
                    builder.setMessage(e.getMessage());
                    //Set the title of the dialog box from a string resources
                    builder.setTitle(R.string.error_title);
                    //Leverage android's stock positive button for ok
                    builder.setPositiveButton(android.R.string.ok, null);

                    //Create the dialog box and show it
                    AlertDialog userDialog = builder.create();
                    userDialog.show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.edit_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){

            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (getListView().isItemChecked(position)){

            //Add Friend Logic

            //Add relation based off of users checked and passed in from onListItemClick
            mFriendsRelation.add(mUserList.get(position));

            //Save in background asynchronously
            mCurrentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e != null) {
                        //Send error message to log.
                        Log.e(TAG, e.getMessage());

                    }

                }
            });

        } else {

            //Remove Friend Relation

        }

    }
}

