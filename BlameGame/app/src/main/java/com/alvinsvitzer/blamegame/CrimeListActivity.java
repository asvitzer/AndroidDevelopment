package com.alvinsvitzer.blamegame;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alvinsvitzer.blamegame.model.Crime;
import com.alvinsvitzer.blamegame.model.CrimeLab;

import java.util.List;

/**
 * Created by Alvin on 1/1/16.
 */
public class CrimeListActivity extends SingleFragmentActivity implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime){

        int detailView = R.id.detail_fragment_container;

        if (findViewById(detailView) == null){
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        }
        else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(detailView, newDetail)
                    .commit();

            findViewById(detailView).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }

    @Override
    public void onCrimeDelete(Crime crime) {

        findViewById(R.id.detail_fragment_container).setVisibility(View.INVISIBLE);
        onCrimeUpdated(crime);

    }
}
