package com.alvinsvitzer.weathervane.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.alvinsvitzer.weathervane.R;

/**
 * Created by ALVIN on 5/17/15.
 */

public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Oops! Error!");

        builder.setMessage(R.string.fragment_error)
                .setPositiveButton(R.string.fragment_error_positive, null);//Null listener since it's just an Okay button.
        // Create the AlertDialog object and returns it
        return builder.create();
    }
}
