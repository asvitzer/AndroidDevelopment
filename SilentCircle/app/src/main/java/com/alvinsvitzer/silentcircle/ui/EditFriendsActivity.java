package com.alvinsvitzer.silentcircle.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;

import com.alvinsvitzer.silentcircle.R;
import com.alvinsvitzer.silentcircle.model.ParseConstants;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class EditFriendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);
        
       // setUpActionBar();

    }

    private void setUpActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume(){
        super.onResume();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        query.setLimit(50);
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


    }

