package com.example.crystal.ball;

import java.util.Random;

public class CrystalBall {

	String[] mAnswerArray = {
			"It is certain",
			"Maybe next year",
			"Better not ask that yet",
			"Yes. Well no--Err ask again tomorrow",
			"Nope. Give it up!",
			"Lets drink instead of ask",
			"Well this is awkward",
			"Unavailable resources to answer question"};
	
	public String retrieveAnswer(){
		

		
		String answer = "";
		
		Random ranGen = new Random(); //Instantiate random generator
		int randomNum = ranGen.nextInt(mAnswerArray.length);
		
		answer = mAnswerArray[randomNum];
		
		return answer;
		
		
	}
	
}
