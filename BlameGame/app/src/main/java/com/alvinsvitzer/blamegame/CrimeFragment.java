package com.alvinsvitzer.blamegame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.alvinsvitzer.blamegame.model.Crime;
import com.alvinsvitzer.blamegame.model.CrimeLab;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * A placeholder fragment containing a simple view.
 */
public class CrimeFragment extends Fragment {

    private static final String TAG = CrimeFragment.class.getSimpleName();

    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CONTACT = 3;

    private Crime mCrime;
    private EditText mCrimeTitle;
    private CheckBox mSolvedCheckBox;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mReportButton;
    private Button mSuspectButton;

    public static CrimeFragment newInstance(UUID crimeId){

        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onPause() {
        super.onPause();

        CrimeLab.getInstance(getActivity()).updateCrime(mCrime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        UUID crimeId = (UUID) getArguments()
                .getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.getInstance(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mReportButton = (Button) v.findViewById(R.id.crime_report);
        mReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
                intent.createChooser(intent, getString(R.string.send_report));
                startActivity(intent);
            }
        });

        final Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        mSuspectButton = (Button) v.findViewById(R.id.crime_report);
        mSuspectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(pickContact, REQUEST_CONTACT);
            }
        });

        if(mCrime.getSuspect() != null){
            mSuspectButton.setText(mCrime.getSuspect());
        }

        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.solved_check_box);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mTimeButton = (Button) v.findViewById(R.id.crime_time);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        updateDate(0);

        mCrimeTitle = (EditText) v.findViewById(R.id.crime_title);
        mCrimeTitle.setText(mCrime.getTitle());
        mCrimeTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(pickContact, PackageManager.MATCH_DEFAULT_ONLY)==null){
            mSuspectButton.setEnabled(false);
        }

        return v;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {

            case R.id.menu_item_delete_crime:
                CrimeLab.getInstance(getActivity()).removeCrime(mCrime);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_crime_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "ResultCode " + resultCode + "RequestCode " + requestCode);

        if (resultCode != Activity.RESULT_OK){
            return;
        }

        Date date;

        switch(requestCode){

            case REQUEST_DATE:

                date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                mCrime.setDate(date);
                updateDate(1);
                break;

            case REQUEST_TIME:

                int hour = data.getIntExtra(TimePickerFragment.EXTRA_HOUR, 0);
                int minute = data.getIntExtra(TimePickerFragment.EXTRA_MINUTE, 0);

                Log.d(TAG, "hour " + hour + "minute " + minute);

                Calendar cal = Calendar.getInstance();
                cal.setTime(mCrime.getDate());

                //Modify the time portion of the date with the values the user selected from timepicker
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);

                date = cal.getTime();
                mCrime.setDate(date);
                updateDate(2);
                break;

            case REQUEST_CONTACT:

                if (data == null){
                    return;
                }

                Uri contactUri = data.getData();

                String[] queryFields = new String[]{
                        ContactsContract.Contacts.DISPLAY_NAME
                };

                Cursor c = getActivity().getContentResolver().query(contactUri, queryFields, null, null, null);

                try{
                    if (c.getCount() == 0){
                        return;
                    }

                    c.moveToFirst();
                    String suspect = c.getString(0);
                    mCrime.setSuspect(suspect);
                    mSuspectButton.setText(suspect);

                } finally {
                    c.close();
                }


        }

    }

    private void updateDate(int updateOption) {

        //1 Update Date only
        //2 Update Time only
        //0 Update Date/Time

        String formatDate;
        String formatTime;

        switch(updateOption){

            case 1:
                formatDate = android.text.format.DateFormat
                        .getLongDateFormat(getActivity())
                        .format(mCrime.getDate());
                mDateButton.setText(formatDate);
                break;
            case 2:
                formatTime = android.text.format.DateFormat
                        .getTimeFormat(getActivity())
                        .format(mCrime.getDate());
                mTimeButton.setText(formatTime);
                break;
            default:

                formatDate = android.text.format.DateFormat
                        .getLongDateFormat(getActivity())
                        .format(mCrime.getDate());
                mDateButton.setText(formatDate);

                formatTime = android.text.format.DateFormat
                        .getTimeFormat(getActivity())
                        .format(mCrime.getDate());
                mTimeButton.setText(formatTime);
        }

    }

    private String getCrimeReport(){

        String solvedString = null;
        if (mCrime.isSolved()){
            solvedString = getString(R.string.crime_report_solved);
        } else {
            solvedString = getString(R.string.crime_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mCrime.getDate()).toString();

        String suspect = mCrime.getSuspect();
        if (suspect == null){
            suspect = getString(R.string.crime_report_no_suspect);
        } else {
            suspect = getString(R.string.crime_report_suspect, suspect);
        }

        String report = getString(R.string.crime_report, mCrime.getTitle(), dateString, solvedString, suspect);

        return report;
    }
}
