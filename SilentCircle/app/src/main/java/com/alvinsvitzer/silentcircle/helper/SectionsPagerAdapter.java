package com.alvinsvitzer.silentcircle.helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alvinsvitzer.silentcircle.R;
import com.alvinsvitzer.silentcircle.ui.MainActivity;

import java.util.Locale;

/**
 * Created by asvitzer on 10/09/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    protected Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {

        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as separate clases in the helper folder).

        switch(position){
        case 0:
            return InboxFragment.newInstance(position + 1);
        case 1:
            return FriendFragment.newInstance(position + 1);
        }

        return null; //Should never happen
    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Locale l = Locale.getDefault();
        switch (position) {
            case 0:
                return mContext.getString(R.string.Inbox).toUpperCase(l);
            case 1:
                return mContext.getString(R.string.Friends).toUpperCase(l);
        }
        return null;
    }
}

