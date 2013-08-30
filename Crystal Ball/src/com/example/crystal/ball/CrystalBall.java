package com.example.crystal.ball;

import java.util.Random;

public class CrystalBall {

	String[] mAnswerArray = {
			"It is certain",
			"Maybe next year when you get to Hogwarts",
			"Better not ask that yet",
			"Yes. Well no--Err Yes until tomorrow",
			"Nope. Give it up!",
			"Cheers! Lets drink instead of ask questions",
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
