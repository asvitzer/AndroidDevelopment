package com.alvinsvitzer.nerdlauncher;

import android.support.v4.app.Fragment;
import android.os.Bundle;

public class NerdLauncherActivity extends SingleFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_nerd_launcher);
    }

    @Override
    protected Fragment createFragment() {
        return NerdLauncherFragment.newInstance();
    }
}