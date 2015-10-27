package com.alvinsvitzer.silentcircle.helper;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

/**
 * Created by Alvin on 10/11/15.
 */
public class FriendFragment extends ListFragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final String TAG = FriendFragment.class.getSimpleName();
    protected List<ParseUser> mFriendList;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected ParseUser mCurrentUser;

    public static FriendFragment newInstance(int sectionNumber) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friend, container, false);
        return rootView;
    }

    @Override
    public void onResume() {

        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        //Return query of friend relation to place in List to add checkmark back in
        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);

        //Turn off progress flag
        getActivity().setProgressBarIndeterminateVisibility(true);

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, ParseException e) {

                //Turn off progress flag
                getActivity().setProgressBarIndeterminateVisibility(false);

                //Success on retrieving users from Parse
                if (e == null) {

                    mFriendList = friends;
                    String friendNames[] = new String[mFriendList.size()];

                    //For every Parse User, grab the friend name and store it in an string array
                    int x = 0;
                    for (ParseUser user : mFriendList) {
                        friendNames[x] = user.getUsername();
                        x++;
                    }

                    //Adapt ArrayList to List View Items for display
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getListView().getContext(),
                            android.R.layout.simple_list_item_1,
                            friendNames);

                    setListAdapter(adapter);

                } else {

                    //Send error message to log.
                    Log.e(TAG, e.getMessage());

                    //Show data validation error as dialog box from Parse exception

                    AlertDialog.Builder builder = new AlertDialog.Builder(getListView().getContext());

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


}
