package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;

import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;
import com.example.splashscreen.Question.MyCount;

import android.os.CountDownTimer;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game extends Activity {
	
	
	DatabaseHelper dbHelper = new DatabaseHelper();
	static User sender = null;
	static User receiver = null;
	static Match match = null;
	static String idMatch = null;
	int round;
	static int send = 0;
	int[] answers = new int[15];
	private Timer timer = new Timer();
	private TimerTask timerTask;
	Handler handler = new Handler();
	private Runnable updateTask;
	Timer myTimer = new Timer();
	InputStream inputStream = null;
	int[] rounds = new int[1];
	int[] answersBot = new int[15];
	Typeface roboto_black = null;
	Typeface roboto_medium_italic = null;
	Typeface roboto_black_italic = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		
		//FragmentContainer.myTimer.cancel();
		
		
		
		Intent intent = getIntent();
		Bundle extras = getIntent().getExtras();
		idMatch = extras.getString("IDEXTRA");
		
		
		
		
		roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
				
		
		
		
		
		
		
		
		
        		
        		Log.w("idmatch - 2", idMatch);
        		match = dbHelper.getMatch(Integer.parseInt(idMatch));
        		sender = dbHelper.getUser(match.IDsender_match);
        		receiver = dbHelper.getUser(match.IDreceiver_match);
        		
        		/*********************** SET ACTIONBAR COMPONENTS ******************************/
                
                ActionBar actionbar = getActionBar();
                actionbar.setIcon(null);
                actionbar.setDisplayShowTitleEnabled(false);
                //actionbar.setDisplayShowHomeEnabled(false);
                
                actionbar.setDisplayUseLogoEnabled(false);
                actionbar.setDisplayShowCustomEnabled(true);
                getActionBar().setDisplayHomeAsUpEnabled(true);
                getActionBar().setHomeButtonEnabled(true);
                LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflator.inflate(R.layout.actionbar_layout, null);
               
                /* set user image */
                
                ImageView actionbar_image = (ImageView) v.findViewById(R.id.playerImage);
                if(!(FragmentContainer.userLobby.FbConnect.equals("0"))){
        			String url = String.format(FragmentContainer.userLobby.playerImage);
        			try {
        				inputStream = new URL(url).openStream();
        			} catch (MalformedURLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        			bitmap = getRoundedShape(bitmap);
        			actionbar_image.setImageBitmap(bitmap);
        			/* set fb connected icon */
        			ImageView fbconnected = (ImageView) v.findViewById(R.id.facebook_connected);
        			fbconnected.setImageResource(R.drawable.facebook_round);
        		}else{
        			if(FragmentContainer.userLobby.playerImage.contains("default")){
        					actionbar_image.setImageResource(R.drawable.player_image);
        			}else{
        			/**TODO add the path of the user image here and set the lobbyImage**/
        			}
        		}
                
                /* set username */
        		TextView user_name = (TextView) v.findViewById(R.id.playerName);
        		
        		user_name.setTypeface(roboto_black_italic);
        		
        		user_name.setText(FragmentContainer.userLobby.playerName);
        		
        		/* set club */
        		TextView user_club = (TextView) v.findViewById(R.id.playerclub);
        		
        		user_name.setTypeface(roboto_medium_italic);
        		user_club.setText(FragmentContainer.userLobby.club);
        		
        		/** set user level **/
        		TextView user_level = (TextView) v.findViewById(R.id.playerLevel);
        		long actualLevel2 = (long) (Math.pow(FragmentContainer.userLobby.playerPoints, (1/1.7)));
        		user_level.setText(Long.toString(actualLevel2));
        		user_level.setTypeface(roboto_medium_italic);
        		RelativeLayout levelContainer = (RelativeLayout) v.findViewById(R.id.levelContainer);
        		levelContainer.setBackgroundResource(R.drawable.red_circle);
        		
        		/** set avatar circle **/
        		ImageView avatar_circle = (ImageView) v.findViewById(R.id.avatar_circle);
        		avatar_circle.setBackgroundResource(R.drawable.avatar_circle);
        		
        		/** set premium / not premium **/
        		TextView user_premium = (TextView) v.findViewById(R.id.playerPremium);
        		
               
        		
                actionbar.setCustomView(v);	
                
                /***************************************************************************************************/
        		
        		


                
                
                
                
        		
        		/** SET GAME LAYOUT */
        		
        		rounds = setRounds(match);
				/* 1 round */
				Button round1 = (Button) findViewById(R.id.game_firstRound);
				if(rounds[0] == 1 ){
						round1.setBackgroundResource(R.drawable.game_disabled);
						round1.setText("");
				}
				if(rounds[0] == 2){
					round1.setBackgroundResource(R.drawable.game_won);
					round1.setText("");
				}
				if(rounds[0] == 3){
					round1.setBackgroundResource(R.drawable.game_lost);
					round1.setText("");
				}
				if(rounds[0] == 4){
					round1.setBackgroundResource(R.drawable.game_draw);
					round1.setText("");
				}
				/* 2 round */
				Button round2 = (Button) findViewById(R.id.game_secondRound);
				if(rounds[1] == 1 ){
						round2.setBackgroundResource(R.drawable.game_disabled);
						round2.setText("");
				}
				if(rounds[1] == 2){
					round2.setBackgroundResource(R.drawable.game_won);
					round2.setText("");
				}
				if(rounds[1] == 3){
					round2.setBackgroundResource(R.drawable.game_lost);
					round2.setText("");
				}
				if(rounds[1] == 4){
					round2.setBackgroundResource(R.drawable.game_draw);
					round2.setText("");
				}
				/* 3 round */
				Button round3 = (Button) findViewById(R.id.game_thirdRound);
				if(rounds[2] == 1 ){
						round3.setBackgroundResource(R.drawable.game_disabled);
						round3.setText("");
				}
				if(rounds[2] == 2){
					round3.setBackgroundResource(R.drawable.game_won);
					round3.setText("");
				}
				if(rounds[2] == 3){
					round3.setBackgroundResource(R.drawable.game_lost);
					round3.setText("");
				}
				if(rounds[2] == 4){
					round3.setBackgroundResource(R.drawable.game_draw);
					round3.setText("");
				}
				/* 4 round */
				Button round4 = (Button) findViewById(R.id.game_fourthRound);
				if(rounds[3] == 1 ){
						round4.setBackgroundResource(R.drawable.game_disabled);
						round4.setText("");
				}
				if(rounds[3] == 2){
					round4.setBackgroundResource(R.drawable.game_won);
					round4.setText("");
				}
				if(rounds[3] == 3){
					round4.setBackgroundResource(R.drawable.game_lost);
					round4.setText("");
				}
				if(rounds[3] == 4){
					round4.setBackgroundResource(R.drawable.game_draw);
					round4.setText("");
				}
				/* 5 round */
				Button round5 = (Button) findViewById(R.id.game_fifthRound);
				if(rounds[4] == 1 ){
						round5.setBackgroundResource(R.drawable.game_disabled);
						round5.setText("");
				}
				if(rounds[4] == 2){
					round5.setBackgroundResource(R.drawable.game_won);
					round5.setText("");
				}
				if(rounds[4] == 3){
					round5.setBackgroundResource(R.drawable.game_lost);
					round5.setText("");
				}
				if(rounds[4] == 4){
					round5.setBackgroundResource(R.drawable.game_draw);
					round5.setText("");
				}
				
				
				/** SET ENABLE/DISABLE BUTTONS **/
				
        		
        		
        		/**sender user **/
        		/** sender **/
        		ImageView sender_img = (ImageView) findViewById(R.id.game_sender_img);
        		Bitmap bitmap = null;
                if(!(sender.FbConnect.equals("0"))){
        			String url = String.format(sender.playerImage);
        			try {
        				inputStream = new URL(url).openStream();
        			} catch (MalformedURLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			bitmap = BitmapFactory.decodeStream(inputStream);
        			
        			
        			/* set fb connected icon */
        			ImageView sender_fb = (ImageView) findViewById(R.id.game_sender_fb);
        			sender_fb.setImageResource(R.drawable.facebook_round);
        		}else{
        			if(sender.playerImage.contains("default")){
        				
        				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_image);
        			}else{
        			/**TODO add the path of the user image here and set the lobbyImage**/
        			}
        		}
                
                //bitmap = getRoundedShape(bitmap);
                sender_img.setImageBitmap(bitmap);
                
                /*set level for the sender */
                TextView sender_level = (TextView) findViewById(R.id.game_sender_level);
                long actualLevel = (long) (Math.pow(sender.playerPoints, (1/1.7)));
                sender_level.setText(Long.toString(actualLevel));
                
                /*set username */
                TextView sender_name = (TextView) findViewById(R.id.game_sender_name);
                sender_name.setText(sender.playerName);
                
                /* set club */
                TextView sender_club = (TextView) findViewById(R.id.game_sender_club);
                sender_club.setText(sender.club);
                
                
        		
        		/** receiver user **/
                
        		
        		ImageView receiver_img = (ImageView) findViewById(R.id.game_receiver_img);
        		bitmap = null;
                if(!(receiver.FbConnect.equals("0"))){
        			String url = String.format(receiver.playerImage);
        			try {
        				inputStream = new URL(url).openStream();
        			} catch (MalformedURLException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			} catch (IOException e1) {
        				// TODO Auto-generated catch block
        				e1.printStackTrace();
        			}
        			bitmap = BitmapFactory.decodeStream(inputStream);
        			
        			
        			/* set fb connected icon */
        			ImageView receiver_fb = (ImageView) findViewById(R.id.game_receiver_fb);
        			receiver_fb.setImageResource(R.drawable.facebook_round);
        		}else{
        			if(receiver.playerImage.contains("default")){
        				
        				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_image);
        			}else{
        			/**TODO add the path of the user image here and set the lobbyImage**/
        			}
        		}
                //bitmap = getRoundedShape(bitmap);
                receiver_img.setImageBitmap(bitmap);
                
                /*set level for the sender */
                TextView receiver_level = (TextView) findViewById(R.id.game_receiver_level);
                long actualLevel_receiver = (long) (Math.pow(receiver.playerPoints, (1/1.7)));
                receiver_level.setText(Long.toString(actualLevel_receiver));
                
                /*set username */
                TextView receiver_name = (TextView) findViewById(R.id.game_receiver_name);
                receiver_name.setText(receiver.playerName);
                
                /* set club */
                TextView receiver_club = (TextView) findViewById(R.id.game_receiver_club);
                receiver_club.setText(receiver.club);
                
                
        		
        		if(isSender(FragmentContainer.userLobby.playerID)){
        			send = 1;
        			fillAnswersSender(match);
        		}else fillAnswersReceiver(match);
        		
        		/**switch round if currentAnswerNumber == 6 **/
        		round = match.currentRoundNumber;
        		
        		/** finish the match if round == 6**/
        		if(round  == 6){
        			dbHelper.setMatchFinished(match.IDmatch);
        			
        			//newgame.setEnabled(false);
        				
        				Intent i = new Intent(Game.this, Final.class);
        				i.putExtra("IDMATCH", Integer.toString(match.IDmatch));
        				startActivity(i);
        				myTimer.cancel();
        				finish();
        			

        			
        		}
        		
/* set font for textviews (sender) */
                
                
                sender_level.setTypeface(roboto_medium_italic);
                
               
                sender_name.setTypeface(roboto_black_italic);
                
                sender_club.setTypeface(roboto_medium_italic);
                
                /* set font for textviews (receiver) */
                
               
                receiver_level.setTypeface(roboto_medium_italic);
                
               
                receiver_name.setTypeface(roboto_black_italic);
                
                
                receiver_club.setTypeface(roboto_medium_italic);
                
                
                
                /* set font for buttons */
                
                Button game_first = (Button) findViewById(R.id.game_firstRound);
                game_first.setTypeface(roboto_black);
                
                Button game_second = (Button) findViewById(R.id.game_secondRound);
                game_second.setTypeface(roboto_black);
                
                Button game_third = (Button) findViewById(R.id.game_thirdRound);
                game_third.setTypeface(roboto_black);
                
                Button game_fourth = (Button) findViewById(R.id.game_fourthRound);
                game_fourth.setTypeface(roboto_black);
                
                Button game_fifth = (Button) findViewById(R.id.game_fifthRound);
                game_fifth.setTypeface(roboto_black);
        				
        		
        		
        		
            	/** set the match points of the users **/
        		/*TextView senderResult = (TextView) findViewById(R.id.senderResult);
        		TextView receiverResult = (TextView) findViewById(R.id.receiverResult);
        		
        		senderResult.setText(Integer.toString(match.pointsSender));
        		receiverResult.setText(Integer.toString(match.pointsReceiver));
            	/**questions**/
        		ImageView question1_send = (ImageView) findViewById(R.id.question1_send);
        		ImageView question1_rec = (ImageView) findViewById(R.id.question1_rec);
        		
        		ImageView question2_send = (ImageView) findViewById(R.id.question2_send);
        		ImageView question2_rec = (ImageView) findViewById(R.id.question2_rec);
        		
        		ImageView question3_send = (ImageView) findViewById(R.id.question3_send);
        		ImageView question3_rec = (ImageView) findViewById(R.id.question3_rec);
        		
        		ImageView question4_send = (ImageView) findViewById(R.id.question4_send);
        		ImageView question4_rec = (ImageView) findViewById(R.id.question4_rec);
        		
        		ImageView question5_send = (ImageView) findViewById(R.id.question5_send);
        		ImageView question5_rec = (ImageView) findViewById(R.id.question5_rec);
        		
        		ImageView question6_send = (ImageView) findViewById(R.id.question6_send);
        		ImageView question6_rec = (ImageView) findViewById(R.id.question6_rec);
        		
        		ImageView question7_send = (ImageView) findViewById(R.id.question7_send);
        		ImageView question7_rec = (ImageView) findViewById(R.id.question7_rec);
        		
        		ImageView question8_send = (ImageView) findViewById(R.id.question8_send);
        		ImageView question8_rec = (ImageView) findViewById(R.id.question8_rec);
        		
        		ImageView question9_send = (ImageView) findViewById(R.id.question9_send);
        		ImageView question9_rec = (ImageView) findViewById(R.id.question9_rec);
        		
        		ImageView question10_send = (ImageView) findViewById(R.id.question10_send);
        		ImageView question10_rec = (ImageView) findViewById(R.id.question10_rec);
        		
        		ImageView question11_send = (ImageView) findViewById(R.id.question11_send);
        		ImageView question11_rec = (ImageView) findViewById(R.id.question11_rec);
        		
        		ImageView question12_send = (ImageView) findViewById(R.id.question12_send);
        		ImageView question12_rec = (ImageView) findViewById(R.id.question12_rec);
        		
        		ImageView question13_send = (ImageView) findViewById(R.id.question13_send);
        		ImageView question13_rec = (ImageView) findViewById(R.id.question13_rec);
        		
        		ImageView question14_send = (ImageView) findViewById(R.id.question14_send);
        		ImageView question14_rec = (ImageView) findViewById(R.id.question14_rec);
        		
        		ImageView question15_send = (ImageView) findViewById(R.id.question15_send);
        		ImageView question15_rec = (ImageView) findViewById(R.id.question15_rec);
        		/** 1 answer set image for correct, incorrect and default answers**/
        		if(match.answer1_send == 0){
        			
        		}else if (match.answer1_send == 1){
        			question1_send.setBackgroundResource(R.drawable.correct);
        		}else question1_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer1_rec == 0){
        			
        		}else if (match.answer1_rec == 1){
        			question1_rec.setBackgroundResource(R.drawable.correct);
        		}else question1_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 2 answer set image for correct, incorrect and default answers**/
        		if(match.answer2_send == 0){
        			
        		}else if (match.answer2_send == 1){
        			question2_send.setBackgroundResource(R.drawable.correct);
        		}else question2_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer2_rec == 0){
        			
        		}else if (match.answer2_rec == 1){
        			question2_rec.setBackgroundResource(R.drawable.correct);
        		}else question2_rec.setBackgroundResource(R.drawable.incorrect);
        			
        		/** 3 answer set image for correct, incorrect and default answers**/
        		if(match.answer3_send == 0){
        			
        		}else if (match.answer3_send == 1){
        			question3_send.setBackgroundResource(R.drawable.correct);
        		}else question3_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer3_rec == 0){
        			
        		}else if (match.answer3_rec == 1){
        			question3_rec.setBackgroundResource(R.drawable.correct);
        		}else question3_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 4 answer set image for correct, incorrect and default answers**/
        		if(match.answer4_send == 0){
        			
        		}else if (match.answer4_send == 1){
        			question4_send.setBackgroundResource(R.drawable.correct);
        		}else question4_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer4_rec == 0){
        			
        		}else if (match.answer4_rec == 1){
        			question4_rec.setBackgroundResource(R.drawable.correct);
        		}else question4_rec.setBackgroundResource(R.drawable.incorrect);
        	

        		/** 5 answer set image for correct, incorrect and default answers**/
        		if(match.answer5_send == 0){
        			
        		}else if (match.answer5_send == 1){
        			question5_send.setBackgroundResource(R.drawable.correct);
        		}else question5_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer5_rec == 0){
        			
        		}else if (match.answer5_rec == 1){
        			question5_rec.setBackgroundResource(R.drawable.correct);
        		}else question5_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 6 answer set image for correct, incorrect and default answers**/
        		if(match.answer6_send == 0){
        			
        		}else if (match.answer6_send == 1){
        			question6_send.setBackgroundResource(R.drawable.correct);
        		}else question6_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer6_rec == 0){
        			
        		}else if (match.answer6_rec == 1){
        			question6_rec.setBackgroundResource(R.drawable.correct);
        		}else question6_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 7 answer set image for correct, incorrect and default answers**/
        		if(match.answer7_send == 0){
        			
        		}else if (match.answer7_send == 1){
        			question7_send.setBackgroundResource(R.drawable.correct);
        		}else question7_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer7_rec == 0){
        			
        		}else if (match.answer7_rec == 1){
        			question7_rec.setBackgroundResource(R.drawable.correct);
        		}else question7_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 8 answer set image for correct, incorrect and default answers**/
        		if(match.answer8_send == 0){
        			
        		}else if (match.answer8_send == 1){
        			question8_send.setBackgroundResource(R.drawable.correct);
        		}else question8_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer8_rec == 0){
        			
        		}else if (match.answer8_rec == 1){
        			question8_rec.setBackgroundResource(R.drawable.correct);
        		}else question8_rec.setBackgroundResource(R.drawable.incorrect);
        	
        		/** 9 answer set image for correct, incorrect and default answers**/
        		if(match.answer9_send == 0){
        			
        		}else if (match.answer9_send == 1){
        			question9_send.setBackgroundResource(R.drawable.correct);
        		}else question9_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer9_rec == 0){
        			
        		}else if (match.answer9_rec == 1){
        			question9_rec.setBackgroundResource(R.drawable.correct);
        		}else question9_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 10 answer set image for correct, incorrect and default answers**/
        		if(match.answer10_send == 0){
        			
        		}else if (match.answer10_send == 1){
        			question10_send.setBackgroundResource(R.drawable.correct);
        		}else question10_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer10_rec == 0){
        			
        		}else if (match.answer10_rec == 1){
        			question10_rec.setBackgroundResource(R.drawable.correct);
        		}else question10_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 11 answer set image for correct, incorrect and default answers**/
        		if(match.answer11_send == 0){
        			
        		}else if (match.answer11_send == 1){
        			question11_send.setBackgroundResource(R.drawable.correct);
        		}else question11_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer11_rec == 0){
        			
        		}else if (match.answer11_rec == 1){
        			question11_rec.setBackgroundResource(R.drawable.correct);
        		}else question11_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 12 answer set image for correct, incorrect and default answers**/
        		if(match.answer12_send == 0){
        			
        		}else if (match.answer12_send == 1){
        			question12_send.setBackgroundResource(R.drawable.correct);
        		}else question12_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer12_rec == 0){
        			
        		}else if (match.answer12_rec == 1){
        			question12_rec.setBackgroundResource(R.drawable.correct);
        		}else question12_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 13 answer set image for correct, incorrect and default answers**/
        		if(match.answer13_send == 0){
        			
        		}else if (match.answer13_send == 1){
        			question13_send.setBackgroundResource(R.drawable.correct);
        		}else question13_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer13_rec == 0){
        			
        		}else if (match.answer13_rec == 1){
        			question13_rec.setBackgroundResource(R.drawable.correct);
        		}else question13_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 14 answer set image for correct, incorrect and default answers**/
        		if(match.answer14_send == 0){
        			
        		}else if (match.answer14_send == 1){
        			question14_send.setBackgroundResource(R.drawable.correct);
        		}else question14_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer14_rec == 0){
        			
        		}else if (match.answer14_rec == 1){
        			question14_rec.setBackgroundResource(R.drawable.correct);
        		}else question14_rec.setBackgroundResource(R.drawable.incorrect);
        		
        		/** 15 answer set image for correct, incorrect and default answers**/
        		if(match.answer15_send == 0){
        			
        		}else if (match.answer15_send == 1){
        			question15_send.setBackgroundResource(R.drawable.correct);
        		}else question15_send.setBackgroundResource(R.drawable.incorrect);
        		
        		if(match.answer15_rec == 0){
        			
        		}else if (match.answer15_rec == 1){
        			question15_rec.setBackgroundResource(R.drawable.correct);
        		}else question15_rec.setBackgroundResource(R.drawable.incorrect);
            
        		
        	      myTimer.schedule(new TimerTask() {
        	         @Override
        	         public void run() {updateGUI();}
        	     }, 0, 1000);
           
	
	}
	
	@Override
	public void onBackPressed()
	{
	    myTimer.cancel();
		finish();  
	}
	
	/** startMatch: go to the question page if the user can play or get deactivated**/
	public void startMatch(View view){
		
		if(isGamePlayable(round)){
			myTimer.cancel();
			Intent i = new Intent(Game.this, PreMatch.class);
			i.putExtra("QUESTION", "1");
			startActivity(i);
			myTimer.cancel();
			finish();
		}else{
			((Button)view).setEnabled(false);
			
		}
	}
	
	public boolean isSender(int id){
		if(id == Game.sender.playerID){
			return true;
		}else return false;			
	}
	
	public void fillAnswersSender(Match match){
		
			answers[0] = match.answer1_send;
			answers[1] = match.answer2_send;
			answers[2] = match.answer3_send;
			answers[3] = match.answer4_send;
			answers[4] = match.answer5_send;
			answers[5] = match.answer6_send;
			answers[6] = match.answer7_send;
			answers[7] = match.answer8_send;
			answers[8] = match.answer9_send;
			answers[9] = match.answer10_send;
			answers[10] = match.answer11_send;
			answers[11] = match.answer12_send;
			answers[12] = match.answer13_send;
			answers[13] = match.answer14_send;
			answers[14] = match.answer15_send;
		
		
	}
	
	public void fillAnswersReceiver(Match match){
		answers[0] = match.answer1_rec;
		answers[1] = match.answer2_rec;
		answers[2] = match.answer3_rec;
		answers[3] = match.answer4_rec;
		answers[4] = match.answer5_rec;
		answers[5] = match.answer6_rec;
		answers[6] = match.answer7_rec;
		answers[7] = match.answer8_rec;
		answers[8] = match.answer9_rec;
		answers[9] = match.answer10_rec;
		answers[10] = match.answer11_rec;
		answers[11] = match.answer12_rec;
		answers[12] = match.answer13_rec;
		answers[13] = match.answer14_rec;
		answers[14] = match.answer15_rec;

	}
	
	public boolean isGamePlayable(int round){
		
		boolean b = false;
		if(round != 6){
			if (answers[(int) (round - 1)*3] == 0) b= true;
		}
		return b;
	}
	
	public void updateGUI(){
			
	      //tv.setText(String.valueOf(i));
	      handler.post(myRunnable);
	}
	
	final Runnable myRunnable = new Runnable() {
	      public void run() {
	    	  
	    	//Button startmatch = (Button) findViewById(R.id.startMatch);
      		
      		Log.w("idmatch - 2", idMatch);
      		match = dbHelper.getMatch(Integer.parseInt(idMatch));
      		sender = dbHelper.getUser(match.IDsender_match);
      		receiver = dbHelper.getUser(match.IDreceiver_match);
      		
      		if(isSender(FragmentContainer.userLobby.playerID)){
      			send = 1;
      			fillAnswersSender(match);
      		}else fillAnswersReceiver(match);
      		/**switch round if currentAnswerNumber == 6 **/
      		round = match.currentRoundNumber;
      		
      					if(match.state == 3){
      	        			
      	        			
      	        			
      							
      	        				Intent i = new Intent(Game.this, Final.class);
      	        				i.putExtra("IDMATCH", Integer.toString(match.IDmatch));
      	        				startActivity(i);
      	        				myTimer.cancel();
      	        				finish();

      	        			
      	        		}			
      		
      		
      					/** SET GAME LAYOUT */
      	        		
      	        		rounds = setRounds(match);
      					/* 1 round */
      					Button round1 = (Button) findViewById(R.id.game_firstRound);
      					if(rounds[0] == 1 ){
      							round1.setBackgroundResource(R.drawable.game_disabled);
      							round1.setText("");
      					}
      					if(rounds[0] == 2){
      						round1.setBackgroundResource(R.drawable.game_won);
      						round1.setText("");
      					}
      					if(rounds[0] == 3){
      						round1.setBackgroundResource(R.drawable.game_lost);
      						round1.setText("");
      					}
      					if(rounds[0] == 4){
      						round1.setBackgroundResource(R.drawable.game_draw);
      						round1.setText("");
      					}
      					/* 2 round */
      					Button round2 = (Button) findViewById(R.id.game_secondRound);
      					if(rounds[1] == 1 ){
      							round2.setBackgroundResource(R.drawable.game_disabled);
      							round2.setText("");
      					}
      					if(rounds[1] == 2){
      						round2.setBackgroundResource(R.drawable.game_won);
      						round2.setText("");
      					}
      					if(rounds[1] == 3){
      						round2.setBackgroundResource(R.drawable.game_lost);
      						round2.setText("");
      					}
      					if(rounds[1] == 4){
      						round2.setBackgroundResource(R.drawable.game_draw);
      						round2.setText("");
      					}
      					/* 3 round */
      					Button round3 = (Button) findViewById(R.id.game_thirdRound);
      					if(rounds[2] == 1 ){
      							round3.setBackgroundResource(R.drawable.game_disabled);
      							round3.setText("");
      					}
      					if(rounds[2] == 2){
      						round3.setBackgroundResource(R.drawable.game_won);
      						round3.setText("");
      					}
      					if(rounds[2] == 3){
      						round3.setBackgroundResource(R.drawable.game_lost);
      						round3.setText("");
      					}
      					if(rounds[2] == 4){
      						round3.setBackgroundResource(R.drawable.game_draw);
      						round3.setText("");
      					}
      					/* 4 round */
      					Button round4 = (Button) findViewById(R.id.game_fourthRound);
      					if(rounds[3] == 1 ){
      							round4.setBackgroundResource(R.drawable.game_disabled);
      							round4.setText("");
      					}
      					if(rounds[3] == 2){
      						round4.setBackgroundResource(R.drawable.game_won);
      						round4.setText("");
      					}
      					if(rounds[3] == 3){
      						round4.setBackgroundResource(R.drawable.game_lost);
      						round4.setText("");
      					}
      					if(rounds[3] == 4){
      						round4.setBackgroundResource(R.drawable.game_draw);
      						round4.setText("");
      					}
      					/* 5 round */
      					Button round5 = (Button) findViewById(R.id.game_fifthRound);
      					if(rounds[4] == 1 ){
      							round5.setBackgroundResource(R.drawable.game_disabled);
      							round5.setText("");
      					}
      					if(rounds[4] == 2){
      						round5.setBackgroundResource(R.drawable.game_won);
      						round5.setText("");
      					}
      					if(rounds[4] == 3){
      						round5.setBackgroundResource(R.drawable.game_lost);
      						round5.setText("");
      					}
      					if(rounds[4] == 4){
      						round5.setBackgroundResource(R.drawable.game_draw);
      						round5.setText("");
      					}
      					
      					
      					/** SET ENABLE/DISABLE BUTTONS **/
      					
      	        		
      	        		
      	        		
      	                
      	                
      	        		
      	        		if(isSender(FragmentContainer.userLobby.playerID)){
      	        			send = 1;
      	        			fillAnswersSender(match);
      	        		}else fillAnswersReceiver(match);
      	        		
      	        		/**switch round if currentAnswerNumber == 6 **/
      	        		round = match.currentRoundNumber;
      	        		
      	        		/** finish the match if round == 6**/
      	        		if(round  == 6){
      	        			dbHelper.setMatchFinished(match.IDmatch);
      	        			
      	        			//newgame.setEnabled(false);
      	        				
      	        				Intent i = new Intent(Game.this, Final.class);
      	        				i.putExtra("IDMATCH", Integer.toString(match.IDmatch));
      	        				startActivity(i);
      	        				myTimer.cancel();
      	        				finish();
      	        			

      	        			
      	        		}
      	        				
      	        		
      	        		
      	        		
      	            	/** set the match points of the users **/
      	        		/*TextView senderResult = (TextView) findViewById(R.id.senderResult);
      	        		TextView receiverResult = (TextView) findViewById(R.id.receiverResult);
      	        		
      	        		senderResult.setText(Integer.toString(match.pointsSender));
      	        		receiverResult.setText(Integer.toString(match.pointsReceiver));
      	            	/**questions**/
      	        		ImageView question1_send = (ImageView) findViewById(R.id.question1_send);
      	        		ImageView question1_rec = (ImageView) findViewById(R.id.question1_rec);
      	        		
      	        		ImageView question2_send = (ImageView) findViewById(R.id.question2_send);
      	        		ImageView question2_rec = (ImageView) findViewById(R.id.question2_rec);
      	        		
      	        		ImageView question3_send = (ImageView) findViewById(R.id.question3_send);
      	        		ImageView question3_rec = (ImageView) findViewById(R.id.question3_rec);
      	        		
      	        		ImageView question4_send = (ImageView) findViewById(R.id.question4_send);
      	        		ImageView question4_rec = (ImageView) findViewById(R.id.question4_rec);
      	        		
      	        		ImageView question5_send = (ImageView) findViewById(R.id.question5_send);
      	        		ImageView question5_rec = (ImageView) findViewById(R.id.question5_rec);
      	        		
      	        		ImageView question6_send = (ImageView) findViewById(R.id.question6_send);
      	        		ImageView question6_rec = (ImageView) findViewById(R.id.question6_rec);
      	        		
      	        		ImageView question7_send = (ImageView) findViewById(R.id.question7_send);
      	        		ImageView question7_rec = (ImageView) findViewById(R.id.question7_rec);
      	        		
      	        		ImageView question8_send = (ImageView) findViewById(R.id.question8_send);
      	        		ImageView question8_rec = (ImageView) findViewById(R.id.question8_rec);
      	        		
      	        		ImageView question9_send = (ImageView) findViewById(R.id.question9_send);
      	        		ImageView question9_rec = (ImageView) findViewById(R.id.question9_rec);
      	        		
      	        		ImageView question10_send = (ImageView) findViewById(R.id.question10_send);
      	        		ImageView question10_rec = (ImageView) findViewById(R.id.question10_rec);
      	        		
      	        		ImageView question11_send = (ImageView) findViewById(R.id.question11_send);
      	        		ImageView question11_rec = (ImageView) findViewById(R.id.question11_rec);
      	        		
      	        		ImageView question12_send = (ImageView) findViewById(R.id.question12_send);
      	        		ImageView question12_rec = (ImageView) findViewById(R.id.question12_rec);
      	        		
      	        		ImageView question13_send = (ImageView) findViewById(R.id.question13_send);
      	        		ImageView question13_rec = (ImageView) findViewById(R.id.question13_rec);
      	        		
      	        		ImageView question14_send = (ImageView) findViewById(R.id.question14_send);
      	        		ImageView question14_rec = (ImageView) findViewById(R.id.question14_rec);
      	        		
      	        		ImageView question15_send = (ImageView) findViewById(R.id.question15_send);
      	        		ImageView question15_rec = (ImageView) findViewById(R.id.question15_rec);
      	        		/** 1 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer1_send == 0){
      	        			
      	        		}else if (match.answer1_send == 1){
      	        			question1_send.setBackgroundResource(R.drawable.correct);
      	        		}else question1_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer1_rec == 0){
      	        			
      	        		}else if (match.answer1_rec == 1){
      	        			question1_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question1_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 2 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer2_send == 0){
      	        			
      	        		}else if (match.answer2_send == 1){
      	        			question2_send.setBackgroundResource(R.drawable.correct);
      	        		}else question2_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer2_rec == 0){
      	        			
      	        		}else if (match.answer2_rec == 1){
      	        			question2_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question2_rec.setBackgroundResource(R.drawable.incorrect);
      	        			
      	        		/** 3 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer3_send == 0){
      	        			
      	        		}else if (match.answer3_send == 1){
      	        			question3_send.setBackgroundResource(R.drawable.correct);
      	        		}else question3_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer3_rec == 0){
      	        			
      	        		}else if (match.answer3_rec == 1){
      	        			question3_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question3_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 4 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer4_send == 0){
      	        			
      	        		}else if (match.answer4_send == 1){
      	        			question4_send.setBackgroundResource(R.drawable.correct);
      	        		}else question4_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer4_rec == 0){
      	        			
      	        		}else if (match.answer4_rec == 1){
      	        			question4_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question4_rec.setBackgroundResource(R.drawable.incorrect);
      	        	

      	        		/** 5 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer5_send == 0){
      	        			
      	        		}else if (match.answer5_send == 1){
      	        			question5_send.setBackgroundResource(R.drawable.correct);
      	        		}else question5_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer5_rec == 0){
      	        			
      	        		}else if (match.answer5_rec == 1){
      	        			question5_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question5_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 6 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer6_send == 0){
      	        			
      	        		}else if (match.answer6_send == 1){
      	        			question6_send.setBackgroundResource(R.drawable.correct);
      	        		}else question6_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer6_rec == 0){
      	        			
      	        		}else if (match.answer6_rec == 1){
      	        			question6_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question6_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 7 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer7_send == 0){
      	        			
      	        		}else if (match.answer7_send == 1){
      	        			question7_send.setBackgroundResource(R.drawable.correct);
      	        		}else question7_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer7_rec == 0){
      	        			
      	        		}else if (match.answer7_rec == 1){
      	        			question7_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question7_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 8 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer8_send == 0){
      	        			
      	        		}else if (match.answer8_send == 1){
      	        			question8_send.setBackgroundResource(R.drawable.correct);
      	        		}else question8_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer8_rec == 0){
      	        			
      	        		}else if (match.answer8_rec == 1){
      	        			question8_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question8_rec.setBackgroundResource(R.drawable.incorrect);
      	        	
      	        		/** 9 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer9_send == 0){
      	        			
      	        		}else if (match.answer9_send == 1){
      	        			question9_send.setBackgroundResource(R.drawable.correct);
      	        		}else question9_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer9_rec == 0){
      	        			
      	        		}else if (match.answer9_rec == 1){
      	        			question9_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question9_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 10 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer10_send == 0){
      	        			
      	        		}else if (match.answer10_send == 1){
      	        			question10_send.setBackgroundResource(R.drawable.correct);
      	        		}else question10_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer10_rec == 0){
      	        			
      	        		}else if (match.answer10_rec == 1){
      	        			question10_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question10_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 11 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer11_send == 0){
      	        			
      	        		}else if (match.answer11_send == 1){
      	        			question11_send.setBackgroundResource(R.drawable.correct);
      	        		}else question11_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer11_rec == 0){
      	        			
      	        		}else if (match.answer11_rec == 1){
      	        			question11_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question11_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 12 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer12_send == 0){
      	        			
      	        		}else if (match.answer12_send == 1){
      	        			question12_send.setBackgroundResource(R.drawable.correct);
      	        		}else question12_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer12_rec == 0){
      	        			
      	        		}else if (match.answer12_rec == 1){
      	        			question12_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question12_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 13 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer13_send == 0){
      	        			
      	        		}else if (match.answer13_send == 1){
      	        			question13_send.setBackgroundResource(R.drawable.correct);
      	        		}else question13_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer13_rec == 0){
      	        			
      	        		}else if (match.answer13_rec == 1){
      	        			question13_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question13_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 14 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer14_send == 0){
      	        			
      	        		}else if (match.answer14_send == 1){
      	        			question14_send.setBackgroundResource(R.drawable.correct);
      	        		}else question14_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer14_rec == 0){
      	        			
      	        		}else if (match.answer14_rec == 1){
      	        			question14_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question14_rec.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		/** 15 answer set image for correct, incorrect and default answers**/
      	        		if(match.answer15_send == 0){
      	        			
      	        		}else if (match.answer15_send == 1){
      	        			question15_send.setBackgroundResource(R.drawable.correct);
      	        		}else question15_send.setBackgroundResource(R.drawable.incorrect);
      	        		
      	        		if(match.answer15_rec == 0){
      	        			
      	        		}else if (match.answer15_rec == 1){
      	        			question15_rec.setBackgroundResource(R.drawable.correct);
      	        		}else question15_rec.setBackgroundResource(R.drawable.incorrect);

	    	  /**questions*
      		ImageView question1_send = (ImageView) findViewById(R.id.question1_send);
      		ImageView question1_rec = (ImageView) findViewById(R.id.question1_rec);
      		
      		ImageView question2_send = (ImageView) findViewById(R.id.question2_send);
      		ImageView question2_rec = (ImageView) findViewById(R.id.question2_rec);
      		
      		ImageView question3_send = (ImageView) findViewById(R.id.question3_send);
      		ImageView question3_rec = (ImageView) findViewById(R.id.question3_rec);
      		
      		ImageView question4_send = (ImageView) findViewById(R.id.question4_send);
      		ImageView question4_rec = (ImageView) findViewById(R.id.question4_rec);
      		
      		ImageView question5_send = (ImageView) findViewById(R.id.question5_send);
      		ImageView question5_rec = (ImageView) findViewById(R.id.question5_rec);
      		
      		ImageView question6_send = (ImageView) findViewById(R.id.question6_send);
      		ImageView question6_rec = (ImageView) findViewById(R.id.question6_rec);
      		
      		ImageView question7_send = (ImageView) findViewById(R.id.question7_send);
      		ImageView question7_rec = (ImageView) findViewById(R.id.question7_rec);
      		
      		ImageView question8_send = (ImageView) findViewById(R.id.question8_send);
      		ImageView question8_rec = (ImageView) findViewById(R.id.question8_rec);
      		
      		ImageView question9_send = (ImageView) findViewById(R.id.question9_send);
      		ImageView question9_rec = (ImageView) findViewById(R.id.question9_rec);
      		
      		ImageView question10_send = (ImageView) findViewById(R.id.question10_send);
      		ImageView question10_rec = (ImageView) findViewById(R.id.question10_rec);
      		
      		ImageView question11_send = (ImageView) findViewById(R.id.question11_send);
      		ImageView question11_rec = (ImageView) findViewById(R.id.question11_rec);
      		
      		ImageView question12_send = (ImageView) findViewById(R.id.question12_send);
      		ImageView question12_rec = (ImageView) findViewById(R.id.question12_rec);
      		
      		ImageView question13_send = (ImageView) findViewById(R.id.question13_send);
      		ImageView question13_rec = (ImageView) findViewById(R.id.question13_rec);
      		
      		ImageView question14_send = (ImageView) findViewById(R.id.question14_send);
      		ImageView question14_rec = (ImageView) findViewById(R.id.question14_rec);
      		
      		ImageView question15_send = (ImageView) findViewById(R.id.question15_send);
      		ImageView question15_rec = (ImageView) findViewById(R.id.question15_rec);
      		/** 1 answer set image for correct, incorrect and default answers*
      		if(match.answer1_send == 0){
      			question1_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer1_send == 1){
      			question1_send.setBackgroundResource(R.drawable.correct);
      		}else question1_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer1_rec == 0){
      			question1_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer1_rec == 1){
      			question1_rec.setBackgroundResource(R.drawable.correct);
      		}else question1_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 2 answer set image for correct, incorrect and default answers*
      		if(match.answer2_send == 0){
      			question2_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer2_send == 1){
      			question2_send.setBackgroundResource(R.drawable.correct);
      		}else question2_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer2_rec == 0){
      			question2_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer2_rec == 1){
      			question2_rec.setBackgroundResource(R.drawable.correct);
      		}else question2_rec.setBackgroundResource(R.drawable.incorrect);
      			
      		/** 3 answer set image for correct, incorrect and default answers*
      		if(match.answer3_send == 0){
      			question3_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer3_send == 1){
      			question3_send.setBackgroundResource(R.drawable.correct);
      		}else question3_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer3_rec == 0){
      			question3_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer3_rec == 1){
      			question3_rec.setBackgroundResource(R.drawable.correct);
      		}else question3_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 4 answer set image for correct, incorrect and default answers*
      		if(match.answer4_send == 0){
      			question4_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer4_send == 1){
      			question4_send.setBackgroundResource(R.drawable.correct);
      		}else question4_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer4_rec == 0){
      			question4_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer4_rec == 1){
      			question4_rec.setBackgroundResource(R.drawable.correct);
      		}else question4_rec.setBackgroundResource(R.drawable.incorrect);
      	

      		/** 5 answer set image for correct, incorrect and default answers*
      		if(match.answer5_send == 0){
      			question5_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer5_send == 1){
      			question5_send.setBackgroundResource(R.drawable.correct);
      		}else question5_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer5_rec == 0){
      			question5_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer5_rec == 1){
      			question5_rec.setBackgroundResource(R.drawable.correct);
      		}else question5_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 6 answer set image for correct, incorrect and default answers*
      		if(match.answer6_send == 0){
      			question6_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer6_send == 1){
      			question6_send.setBackgroundResource(R.drawable.correct);
      		}else question6_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer6_rec == 0){
      			question6_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer6_rec == 1){
      			question6_rec.setBackgroundResource(R.drawable.correct);
      		}else question6_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 7 answer set image for correct, incorrect and default answers*
      		if(match.answer7_send == 0){
      			question7_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer7_send == 1){
      			question7_send.setBackgroundResource(R.drawable.correct);
      		}else question7_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer7_rec == 0){
      			question7_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer7_rec == 1){
      			question7_rec.setBackgroundResource(R.drawable.correct);
      		}else question7_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 8 answer set image for correct, incorrect and default answers*
      		if(match.answer8_send == 0){
      			question8_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer8_send == 1){
      			question8_send.setBackgroundResource(R.drawable.correct);
      		}else question8_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer8_rec == 0){
      			question8_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer8_rec == 1){
      			question8_rec.setBackgroundResource(R.drawable.correct);
      		}else question8_rec.setBackgroundResource(R.drawable.incorrect);
      	
      		/** 9 answer set image for correct, incorrect and default answers*
      		if(match.answer9_send == 0){
      			question9_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer9_send == 1){
      			question9_send.setBackgroundResource(R.drawable.correct);
      		}else question9_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer9_rec == 0){
      			question9_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer9_rec == 1){
      			question9_rec.setBackgroundResource(R.drawable.correct);
      		}else question9_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 10 answer set image for correct, incorrect and default answers*
      		if(match.answer10_send == 0){
      			question10_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer10_send == 1){
      			question10_send.setBackgroundResource(R.drawable.correct);
      		}else question10_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer10_rec == 0){
      			question10_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer10_rec == 1){
      			question10_rec.setBackgroundResource(R.drawable.correct);
      		}else question10_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 11 answer set image for correct, incorrect and default answers*
      		if(match.answer11_send == 0){
      			question11_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer11_send == 1){
      			question11_send.setBackgroundResource(R.drawable.correct);
      		}else question11_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer11_rec == 0){
      			question11_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer11_rec == 1){
      			question11_rec.setBackgroundResource(R.drawable.correct);
      		}else question11_rec.setBackgroundResource(R.drawable.incorrect);
      
      		/** 12 answer set image for correct, incorrect and default answers*
      		if(match.answer12_send == 0){
      			question12_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer12_send == 1){
      			question12_send.setBackgroundResource(R.drawable.correct);
      		}else question12_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer12_rec == 0){
      			question12_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer12_rec == 1){
      			question12_rec.setBackgroundResource(R.drawable.correct);
      		}else question12_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 13 answer set image for correct, incorrect and default answers*
      		if(match.answer13_send == 0){
      			question13_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer13_send == 1){
      			question13_send.setBackgroundResource(R.drawable.correct);
      		}else question13_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer13_rec == 0){
      			question13_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer13_rec == 1){
      			question13_rec.setBackgroundResource(R.drawable.correct);
      		}else question13_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 14 answer set image for correct, incorrect and default answers*
      		if(match.answer14_send == 0){
      			question14_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer14_send == 1){
      			question14_send.setBackgroundResource(R.drawable.correct);
      		}else question14_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer14_rec == 0){
      			question14_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer14_rec == 1){
      			question14_rec.setBackgroundResource(R.drawable.correct);
      		}else question14_rec.setBackgroundResource(R.drawable.incorrect);
      		
      		/** 15 answer set image for correct, incorrect and default answers*
      		if(match.answer15_send == 0){
      			question15_send.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer15_send == 1){
      			question15_send.setBackgroundResource(R.drawable.correct);
      		}else question15_send.setBackgroundResource(R.drawable.incorrect);
      		
      		if(match.answer15_rec == 0){
      			question15_rec.setBackgroundResource(R.drawable.answers);
      		}else if (match.answer15_rec == 1){
      			question15_rec.setBackgroundResource(R.drawable.correct);
      		}else question15_rec.setBackgroundResource(R.drawable.incorrect);
	      */
      				// let the bot playing
      		    		AsyncTaskRunner runner = new AsyncTaskRunner();
      		  	    
      		  	    runner.execute("0");			
	      }
	      
	   
	      
	   };
	   
	   
	   public int[] setRounds(Match match){
		   int[] rounds = new int[5];
		   if(sender.playerID == FragmentContainer.userLobby.playerID){
			   /* 1 round */
			   rounds[0] = 0;
			   if((match.answer1_send != 0) || (match.answer1_rec != 0)){
				   int tot_round1 = 0;
				   int round1_send = 0;
				   int round1_rec = 0;
				   /* sender calculation - 1 round*/
				   if(match.answer1_send != 0){
					   tot_round1 ++;
					   if(match.answer1_send == 1){
						   round1_send ++;
					   }
				   }
				   if(match.answer2_send != 0){
					   tot_round1 ++;
					   if(match.answer2_send == 1){
						   round1_send ++;
					   }
				   }
				   if(match.answer3_send != 0){
					   tot_round1 ++;
					   if(match.answer3_send == 1){
						   round1_send ++;
					   }
				   }
				   if(match.answer1_rec != 0){
					   tot_round1 ++;
					   if(match.answer1_rec == 1){
						   round1_rec ++;
					   }
				   }
				   if(match.answer2_rec != 0){
					   tot_round1 ++;
					   if(match.answer2_rec == 1){
						   round1_rec ++;
					   }
				   }
				   if(match.answer3_rec != 0){
					   tot_round1 ++;
					   if(match.answer3_rec == 1){
						   round1_rec ++;
					   }
				   }
				   if(tot_round1 != 6){
					   rounds[0] = 1;
				   }else{
					   if(round1_send > round1_rec) rounds[0] = 2;
					   if(round1_send < round1_rec) rounds[0] = 3;
					   if(round1_send == round1_rec) rounds[0] = 4;
				   }
			   }
			   /* 2 round */
			   rounds[1] = 0;
			   if((match.answer4_send != 0) || (match.answer4_rec != 0)){
				   int tot_round2 = 0;
				   int round2_send = 0;
				   int round2_rec = 0;
				   /* sender calculation - 1 round*/
				   if(match.answer4_send != 0){
					   tot_round2 ++;
					   if(match.answer4_send == 1){
						   round2_send ++;
					   }
				   }
				   if(match.answer5_send != 0){
					   tot_round2 ++;
					   if(match.answer5_send == 1){
						   round2_send ++;
					   }
				   }
				   if(match.answer6_send != 0){
					   tot_round2 ++;
					   if(match.answer6_send == 1){
						   round2_send ++;
					   }
				   }
				   if(match.answer4_rec != 0){
					   tot_round2 ++;
					   if(match.answer4_rec == 1){
						   round2_rec ++;
					   }
				   }
				   if(match.answer5_rec != 0){
					   tot_round2 ++;
					   if(match.answer5_rec == 1){
						   round2_rec ++;
					   }
				   }
				   if(match.answer6_rec != 0){
					   tot_round2 ++;
					   if(match.answer6_rec == 1){
						   round2_rec ++;
					   }
				   }
				   if(tot_round2 != 6){
					   rounds[1] = 1;
				   }else{
					   if(round2_send > round2_rec) rounds[1] = 2;
					   if(round2_send < round2_rec) rounds[1] = 3;
					   if(round2_send == round2_rec) rounds[1] = 4;
				   }
			   }
			   /* 3 round */
			   rounds[2] = 0;
			   if((match.answer7_send != 0) || (match.answer7_rec != 0)){
				   int tot_round3 = 0;
				   int round3_send = 0;
				   int round3_rec = 0;
				   /* sender calculation - 3 round*/
				   if(match.answer7_send != 0){
					   tot_round3 ++;
					   if(match.answer7_send == 1){
						   round3_send ++;
					   }
				   }
				   if(match.answer8_send != 0){
					   tot_round3 ++;
					   if(match.answer8_send == 1){
						   round3_send ++;
					   }
				   }
				   if(match.answer9_send != 0){
					   tot_round3 ++;
					   if(match.answer9_send == 1){
						   round3_send ++;
					   }
				   }
				   if(match.answer7_rec != 0){
					   tot_round3 ++;
					   if(match.answer7_rec == 1){
						   round3_rec ++;
					   }
				   }
				   if(match.answer8_rec != 0){
					   tot_round3 ++;
					   if(match.answer8_rec == 1){
						   round3_rec ++;
					   }
				   }
				   if(match.answer9_rec != 0){
					   tot_round3 ++;
					   if(match.answer9_rec == 1){
						   round3_rec ++;
					   }
				   }
				   if(tot_round3 != 6){
					   rounds[2] = 1;
				   }else{
					   if(round3_send > round3_rec) rounds[2] = 2;
					   if(round3_send < round3_rec) rounds[2] = 3;
					   if(round3_send == round3_rec) rounds[2] = 4;
				   }
			   }
			   /* 4 round */
			   rounds[3] = 0;
			   if((match.answer10_send != 0) || (match.answer10_rec != 0)){
				   int tot_round4 = 0;
				   int round4_send = 0;
				   int round4_rec = 0;
				   /* sender calculation - 3 round*/
				   if(match.answer10_send != 0){
					   tot_round4 ++;
					   if(match.answer10_send == 1){
						   round4_send ++;
					   }
				   }
				   if(match.answer11_send != 0){
					   tot_round4 ++;
					   if(match.answer11_send == 1){
						   round4_send ++;
					   }
				   }
				   if(match.answer12_send != 0){
					   tot_round4 ++;
					   if(match.answer12_send == 1){
						   round4_send ++;
					   }
				   }
				   if(match.answer10_rec != 0){
					   tot_round4 ++;
					   if(match.answer10_rec == 1){
						   round4_rec ++;
					   }
				   }
				   if(match.answer11_rec != 0){
					   tot_round4 ++;
					   if(match.answer11_rec == 1){
						   round4_rec ++;
					   }
				   }
				   if(match.answer12_rec != 0){
					   tot_round4 ++;
					   if(match.answer12_rec == 1){
						   round4_rec ++;
					   }
				   }
				   if(tot_round4 != 6){
					   rounds[3] = 1;
				   }else{
					   if(round4_send > round4_rec) rounds[3] = 2;
					   if(round4_send < round4_rec) rounds[3] = 3;
					   if(round4_send == round4_rec) rounds[3] = 4;
				   }
			   }
			   /* 5 round */
			   rounds[4] = 0;
			   if((match.answer13_send != 0) || (match.answer13_rec != 0)){
				   int tot_round5 = 0;
				   int round5_send = 0;
				   int round5_rec = 0;
				   /* sender calculation - 3 round*/
				   if(match.answer13_send != 0){
					   tot_round5 ++;
					   if(match.answer13_send == 1){
						   round5_send ++;
					   }
				   }
				   if(match.answer14_send != 0){
					   tot_round5 ++;
					   if(match.answer14_send == 1){
						   round5_send ++;
					   }
				   }
				   if(match.answer15_send != 0){
					   tot_round5 ++;
					   if(match.answer15_send == 1){
						   round5_send ++;
					   }
				   }
				   if(match.answer13_rec != 0){
					   tot_round5 ++;
					   if(match.answer13_rec == 1){
						   round5_rec ++;
					   }
				   }
				   if(match.answer14_rec != 0){
					   tot_round5 ++;
					   if(match.answer14_rec == 1){
						   round5_rec ++;
					   }
				   }
				   if(match.answer15_rec != 0){
					   tot_round5 ++;
					   if(match.answer15_rec == 1){
						   round5_rec ++;
					   }
				   }
				   if(tot_round5 != 6){
					   rounds[4] = 1;
				   }else{
					   if(round5_send > round5_rec) rounds[4] = 2;
					   if(round5_send < round5_rec) rounds[4] = 3;
					   if(round5_send == round5_rec) rounds[4] = 4;
				   }
			   }
		   }else{
			   
				   /* 1 round */
				   rounds[0] = 0;
				   if((match.answer1_send != 0) || (match.answer1_rec != 0)){
					   int tot_round1 = 0;
					   int round1_send = 0;
					   int round1_rec = 0;
					   /* sender calculation - 1 round*/
					   if(match.answer1_send != 0){
						   tot_round1 ++;
						   if(match.answer1_send == 1){
							   round1_send ++;
						   }
					   }
					   if(match.answer2_send != 0){
						   tot_round1 ++;
						   if(match.answer2_send == 1){
							   round1_send ++;
						   }
					   }
					   if(match.answer3_send != 0){
						   tot_round1 ++;
						   if(match.answer3_send == 1){
							   round1_send ++;
						   }
					   }
					   if(match.answer1_rec != 0){
						   tot_round1 ++;
						   if(match.answer1_rec == 1){
							   round1_rec ++;
						   }
					   }
					   if(match.answer2_rec != 0){
						   tot_round1 ++;
						   if(match.answer2_rec == 1){
							   round1_rec ++;
						   }
					   }
					   if(match.answer3_rec != 0){
						   tot_round1 ++;
						   if(match.answer3_rec == 1){
							   round1_rec ++;
						   }
					   }
					   if(tot_round1 != 6){
						   rounds[0] = 1;
					   }else{
						   if(round1_send < round1_rec) rounds[0] = 2;
						   if(round1_send > round1_rec) rounds[0] = 3;
						   if(round1_send == round1_rec) rounds[0] = 4;
					   }
				   }
				   /* 2 round */
				   rounds[1] = 0;
				   if((match.answer4_send != 0) || (match.answer4_rec != 0)){
					   int tot_round2 = 0;
					   int round2_send = 0;
					   int round2_rec = 0;
					   /* sender calculation - 1 round*/
					   if(match.answer4_send != 0){
						   tot_round2 ++;
						   if(match.answer4_send == 1){
							   round2_send ++;
						   }
					   }
					   if(match.answer5_send != 0){
						   tot_round2 ++;
						   if(match.answer5_send == 1){
							   round2_send ++;
						   }
					   }
					   if(match.answer6_send != 0){
						   tot_round2 ++;
						   if(match.answer6_send == 1){
							   round2_send ++;
						   }
					   }
					   if(match.answer4_rec != 0){
						   tot_round2 ++;
						   if(match.answer4_rec == 1){
							   round2_rec ++;
						   }
					   }
					   if(match.answer5_rec != 0){
						   tot_round2 ++;
						   if(match.answer5_rec == 1){
							   round2_rec ++;
						   }
					   }
					   if(match.answer6_rec != 0){
						   tot_round2 ++;
						   if(match.answer6_rec == 1){
							   round2_rec ++;
						   }
					   }
					   if(tot_round2 != 6){
						   rounds[1] = 1;
					   }else{
						   if(round2_send < round2_rec) rounds[1] = 2;
						   if(round2_send > round2_rec) rounds[1] = 3;
						   if(round2_send == round2_rec) rounds[1] = 4;
					   }
				   }
				   /* 3 round */
				   rounds[2] = 0;
				   if((match.answer7_send != 0) || (match.answer7_rec != 0)){
					   int tot_round3 = 0;
					   int round3_send = 0;
					   int round3_rec = 0;
					   /* sender calculation - 3 round*/
					   if(match.answer7_send != 0){
						   tot_round3 ++;
						   if(match.answer7_send == 1){
							   round3_send ++;
						   }
					   }
					   if(match.answer8_send != 0){
						   tot_round3 ++;
						   if(match.answer8_send == 1){
							   round3_send ++;
						   }
					   }
					   if(match.answer9_send != 0){
						   tot_round3 ++;
						   if(match.answer9_send == 1){
							   round3_send ++;
						   }
					   }
					   if(match.answer7_rec != 0){
						   tot_round3 ++;
						   if(match.answer7_rec == 1){
							   round3_rec ++;
						   }
					   }
					   if(match.answer8_rec != 0){
						   tot_round3 ++;
						   if(match.answer8_rec == 1){
							   round3_rec ++;
						   }
					   }
					   if(match.answer9_rec != 0){
						   tot_round3 ++;
						   if(match.answer9_rec == 1){
							   round3_rec ++;
						   }
					   }
					   if(tot_round3 != 6){
						   rounds[2] = 1;
					   }else{
						   if(round3_send < round3_rec) rounds[2] = 2;
						   if(round3_send > round3_rec) rounds[2] = 3;
						   if(round3_send == round3_rec) rounds[2] = 4;
					   }
				   }
				   /* 4 round */
				   rounds[3] = 0;
				   if((match.answer10_send != 0) || (match.answer10_rec != 0)){
					   int tot_round4 = 0;
					   int round4_send = 0;
					   int round4_rec = 0;
					   /* sender calculation - 3 round*/
					   if(match.answer10_send != 0){
						   tot_round4 ++;
						   if(match.answer10_send == 1){
							   round4_send ++;
						   }
					   }
					   if(match.answer11_send != 0){
						   tot_round4 ++;
						   if(match.answer11_send == 1){
							   round4_send ++;
						   }
					   }
					   if(match.answer12_send != 0){
						   tot_round4 ++;
						   if(match.answer12_send == 1){
							   round4_send ++;
						   }
					   }
					   if(match.answer10_rec != 0){
						   tot_round4 ++;
						   if(match.answer10_rec == 1){
							   round4_rec ++;
						   }
					   }
					   if(match.answer11_rec != 0){
						   tot_round4 ++;
						   if(match.answer11_rec == 1){
							   round4_rec ++;
						   }
					   }
					   if(match.answer12_rec != 0){
						   tot_round4 ++;
						   if(match.answer12_rec == 1){
							   round4_rec ++;
						   }
					   }
					   if(tot_round4 != 6){
						   rounds[3] = 1;
					   }else{
						   if(round4_send < round4_rec) rounds[3] = 2;
						   if(round4_send > round4_rec) rounds[3] = 3;
						   if(round4_send == round4_rec) rounds[3] = 4;
					   }
				   }
				   /* 5 round */
				   rounds[4] = 0;
				   if((match.answer13_send != 0) || (match.answer13_rec != 0)){
					   int tot_round5 = 0;
					   int round5_send = 0;
					   int round5_rec = 0;
					   /* sender calculation - 3 round*/
					   if(match.answer13_send != 0){
						   tot_round5 ++;
						   if(match.answer13_send == 1){
							   round5_send ++;
						   }
					   }
					   if(match.answer14_send != 0){
						   tot_round5 ++;
						   if(match.answer14_send == 1){
							   round5_send ++;
						   }
					   }
					   if(match.answer15_send != 0){
						   tot_round5 ++;
						   if(match.answer15_send == 1){
							   round5_send ++;
						   }
					   }
					   if(match.answer13_rec != 0){
						   tot_round5 ++;
						   if(match.answer13_rec == 1){
							   round5_rec ++;
						   }
					   }
					   if(match.answer14_rec != 0){
						   tot_round5 ++;
						   if(match.answer14_rec == 1){
							   round5_rec ++;
						   }
					   }
					   if(match.answer15_rec != 0){
						   tot_round5 ++;
						   if(match.answer15_rec == 1){
							   round5_rec ++;
						   }
					   }
					   if(tot_round5 != 6){
						   rounds[4] = 1;
					   }else{
						   if(round5_send < round5_rec) rounds[4] = 2;
						   if(round5_send > round5_rec) rounds[4] = 3;
						   if(round5_send == round5_rec) rounds[4] = 4;
					   }
				   }
			   
		   }
		   return rounds;
	   }
	   
	   
	   public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
	        int targetWidth = 125;
	        int targetHeight = 125;

	        
	        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
	                targetHeight, Bitmap.Config.ARGB_8888);
	        BitmapFactory.Options opts = new BitmapFactory.Options();
    		// opts.inJustDecodeBounds = true; 
    		opts.inSampleSize=2; 
    		opts.inPurgeable = true; // Tell to garbage collector that whether it needs free memory, the Bitmap can be cleared
    		opts.inTempStorage = new byte[32 * 1024];
	        Canvas canvas = new Canvas(targetBitmap);
	        Path path = new Path();
	        path.addCircle(
	                ((float) targetWidth - 1) / 2,
	                ((float) targetHeight - 1) / 2,
	                (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
	                Path.Direction.CCW);

	        canvas.clipPath(path);
	        Bitmap sourceBitmap = scaleBitmapImage;
	        canvas.drawBitmap(
	                sourceBitmap,
	                new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap
	                        .getHeight()), new Rect(0, 0, targetWidth,
	                        targetHeight), null);
	        return targetBitmap;
	    }
	   public void BotStartPlaying(int round){
		   for(int i = 1; i < 4; i++){
			   int question = ((round -1)*3) + i;
			   int random = 1 + (int)(Math.random()* ((3) + 1));
			   SystemClock.sleep(2000);
			   Log.w("bot " + question + "answer", Integer.toString(random));
			   if(random == 1){
				   dbHelper.setCorrect(match.IDmatch, 0, question);
			   }else{
				   dbHelper.setWrong(match.IDmatch, 0, question, random);
			   }
			   
		   }
	   }
	   
	   public class AsyncTaskRunner extends AsyncTask<String,String,String>{

		   @Override
		   protected String doInBackground(String... arg0) {
			   // TODO Auto-generated method stub
			   return null;
		   }
		   
		   @Override
		   protected void onPostExecute(String string){
			   
    		    	if(receiver.playerID < 0){
    		    			fillAnswersBot(match);
    		    			if(!(isGamePlayable(match.currentRoundNumber))){
    		    				for(int i = 1; i < 4; i++){
    		    				   int question = ((round -1)*3) + i;
    		    				   int random = 1 + (int)(Math.random()* ((3) + 1));
    		    				   
    		    				   Log.w("bot " + question + "answer", Integer.toString(random));
    		    				   if(random == 1){
    		    					   dbHelper.setCorrect(match.IDmatch, 0, question);
    		    				   }else{
    		    					   dbHelper.setWrong(match.IDmatch, 0, question, random);
    		    				   }
    		    				   
    		    			   }
    		    			}
    		    		}
		   }
    	        
	   }
	   public void fillAnswersBot(Match match){
			answersBot[0] = match.answer1_rec;
			answersBot[1] = match.answer2_rec;
			answersBot[2] = match.answer3_rec;
			answersBot[3] = match.answer4_rec;
			answersBot[4] = match.answer5_rec;
			answersBot[5] = match.answer6_rec;
			answersBot[6] = match.answer7_rec;
			answersBot[7] = match.answer8_rec;
			answersBot[8] = match.answer9_rec;
			answersBot[9] = match.answer10_rec;
			answersBot[10] = match.answer11_rec;
			answersBot[11] = match.answer12_rec;
			answersBot[12] = match.answer13_rec;
			answersBot[13] = match.answer14_rec;
			answersBot[14] = match.answer15_rec;
		}
        
	
}