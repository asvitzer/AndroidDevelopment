package com.alvinsvitzer.blamegame;

import android.support.v4.app.Fragment;

/**
 * Created by Alvin on 1/1/16.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
