package com.alvinsvitzer.silentcircle.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alvinsvitzer.silentcircle.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.signUpText) protected TextView mSignUpTextView;
    @InjectView(R.id.userNameField) protected EditText mUsername;
    @InjectView(R.id.passwordField) protected EditText mPassword;
    @InjectView(R.id.loginButton) protected Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Used for showing the progress bar for actions later on. Needs to run before ContentView is set.
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                username = username.trim();
                password = password.trim();

                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                    //Set the message of the dialog box from a string resource
                    builder.setMessage(R.string.login_error_message);
                    //Set the title of the dialog box from a string resources
                    builder.setTitle(R.string.login_error_title);
                    //Leverage android's stock positive button for ok
                    builder.setPositiveButton(android.R.string.ok, null);

                    //Create the dialog box and show it
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    //Activate progress indicator for logging into Parse backend.
                    setProgressBarIndeterminateVisibility(true);
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        public void done(ParseUser user, ParseException e) {

                            //Deactivate progress indicator
                            setProgressBarIndeterminateVisibility(false);

                            if (user != null) {
                                // Hooray! The user is logged in.
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {

                                // Signup failed. Look at the ParseException to see what happened.
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                                //Set the message of the dialog box from a string resource
                                builder.setMessage(e.getMessage());
                                //Set the title of the dialog box from a string resources
                                builder.setTitle(R.string.login_error_title);
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
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
