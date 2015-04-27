package ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvinsvitzer.adventuretime.R;

import model.Page;
import model.Story;


public class StoryActivity extends Activity {

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        String mName = intent.getStringExtra(getString(R.string.key_name));
        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.buttonChoice1);
        mChoice2 = (Button) findViewById(R.id.buttonChoice2);

        loadPage(0);

    }


    private void loadPage(int choice){

        mPage = mStory.getPage(choice);

        Drawable drawable = getResources().getDrawable(mPage.getImageId());
        mImageView.setImageDrawable(drawable);

        //Dynamically replace placeholder strings in text with user's name
        String pageText = mPage.getText();
        pageText = String.format(pageText, mName);

        mTextView.setText(pageText);

        if (mPage.getIsFinal() == false) {
            mChoice1.setText(mPage.getChoice1().getText());
            mChoice2.setText(mPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
        else{
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("Play again. . .");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

    }


}
