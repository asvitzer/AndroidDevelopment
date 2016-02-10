package com.example.crystal.ball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crystal.ball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {

	private CrystalBall mCrystalBall = new CrystalBall();
	private TextView mAnswerLabel;
	private Button mGetAnswerButton;
	private ImageView mCrystalImage;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Set view layouts
		mAnswerLabel = (TextView) findViewById(R.id.textView1);
		mCrystalImage = (ImageView) findViewById(R.id.imageView1);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		mShakeDetector = new ShakeDetector(new OnShakeListener() {

			@Override
			public void onShake() {
				handleNewAnswer();
			}
		});

		mGetAnswerButton = (Button) findViewById(R.id.button1);
		mGetAnswerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				handleNewAnswer();
			}
		});
	
		Toast.makeText(this, "Activity created", Toast.LENGTH_LONG).show();	
	}
	
	@Override
	public void onResume(){
		super.onResume();
		mSensorManager.registerListener(mShakeDetector, mAccelerometer
				, SensorManager.SENSOR_DELAY_UI);

	}

	@Override
	public void onPause(){
		super.onPause();
		mSensorManager.unregisterListener(mShakeDetector);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private void animateBall(){

		mCrystalImage.setImageResource(R.drawable.ball_animation);
		AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalImage.getDrawable();

		if (ballAnimation.isRunning()){
			ballAnimation.stop();
		}

		ballAnimation.start();

	}

	private void animateText(){

		AlphaAnimation textFade = new AlphaAnimation(0, 1);
		textFade.setDuration(1500);
		textFade.setFillAfter(true);

		mAnswerLabel.setAnimation(textFade);

	}

	private void playSound(){
		MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
		player.start();
		player.setOnCompletionListener(new OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	private void handleNewAnswer() {
		String answer = mCrystalBall.retrieveAnswer();
		//update label with random answer 
		mAnswerLabel.setText(answer);

		animateBall();
		animateText();
		playSound();
	}
}
