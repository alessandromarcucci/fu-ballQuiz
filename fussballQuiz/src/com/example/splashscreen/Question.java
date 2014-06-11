package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreen.DatabaseHelper;
import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;


@SuppressLint("ResourceAsColor")
public class Question extends Activity {
	DatabaseHelper dbHelper = new DatabaseHelper();
	RelativeLayout tv;
	public static Integer[] mThumbsID = {R.drawable.numbernine, R.drawable.numbereight, R.drawable.numberseven, R.drawable.numbersix, R.drawable.numberfive, R.drawable.numberfour, R.drawable.numberthree, R.drawable.numbertwo, R.drawable.numberone};
	int i;
	int idMatch= Game.match.IDmatch;
	Match match = null;
	int sender = 0;
	
	int question;
	com.example.splashscreen.DatabaseHelper.Question userQuestion;
	String[] answers = new String[4];
	int[] arrayAnswers = {0,1,2,3};
	int actualQuestion;
	InputStream inputStream = null;
	MyCount counter = new MyCount(10000, 1000);
	Typeface roboto_black = null;
	Typeface roboto_medium_italic = null;
	Typeface roboto_black_italic = null;
	
	private static int[] COLORS_1GRAF = new int[] { Color.GREEN, Color.RED }; 
	private static String[] NAME_LIST_1GRAF = new String[] {"Right","Wrong"};
	private CategorySeries mSeries_1GRAF = new CategorySeries("");
	private DefaultRenderer mRenderer_1GRAF = new DefaultRenderer();
	private GraphicalView mChartView_1GRAF = null;
	
	static ImageView first = null;
	static ImageView second = null;
	static ImageView third = null;
	
	static ImageView first_rec = null;
	static ImageView second_rec = null;
	static ImageView third_rec = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_layout);
		Intent intent = getIntent();
		Bundle extras = getIntent().getExtras();
		question = Integer.parseInt(extras.getString("QUESTION"));
		i= 0;
		tv = (RelativeLayout) findViewById(R.id.countDownImage);
		
		match = dbHelper.getMatch(Game.match.IDmatch);
		
		roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		
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
		long actualLevel = (long) (Math.pow(FragmentContainer.userLobby.playerPoints, (1/1.7)));
		user_level.setText(Long.toString(actualLevel));
		user_level.setTypeface(roboto_medium_italic);
		RelativeLayout levelContainer = (RelativeLayout) v.findViewById(R.id.levelContainer);
		levelContainer.setBackgroundResource(R.drawable.red_circle);
		
		/** set avatar circle **/
		ImageView avatar_circle = (ImageView) v.findViewById(R.id.avatar_circle);
		avatar_circle.setBackgroundResource(R.drawable.avatar_circle);
		
		/** set premium / not premium **/
		TextView user_premium = (TextView) v.findViewById(R.id.playerPremium);
		
       
		
        actionbar.setCustomView(v);
		
		
		
		
        /**sender user **/
		/** sender **/
		ImageView sender_img = (ImageView) findViewById(R.id.game_sender_img);
		Bitmap bitmap = null;
        if(!(Game.sender.FbConnect.equals("0"))){
			String url = String.format(Game.sender.playerImage);
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
			if(Game.sender.playerImage.contains("default")){
				
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
        //bitmap = getRoundedShape(bitmap);
        sender_img.setImageBitmap(bitmap);
        
        /*set level for the sender */
        TextView sender_level = (TextView) findViewById(R.id.game_sender_level);
        
        sender_level.setText(Long.toString(actualLevel));
        sender_level.setTypeface(roboto_medium_italic);
        
        /*set username */
        TextView sender_name = (TextView) findViewById(R.id.game_sender_name);
        sender_name.setText(Game.sender.playerName);
        sender_name.setTypeface(roboto_black_italic);
        
        /* set club */
        TextView sender_club = (TextView) findViewById(R.id.game_sender_club);
        sender_club.setText(Game.sender.club);
        sender_club.setTypeface(roboto_medium_italic);
        
        /* set answer tile */
        first = (ImageView) findViewById(R.id.first_question_send);
        second = (ImageView) findViewById(R.id.second_question_send);
        third = (ImageView) findViewById(R.id.third_question_send);
        if(match.currentRoundNumber == 1){
        	if(match.answer1_send == 1){
        		first.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer1_send != 0) && (match.answer1_send != 1)){
        		first.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer2_send == 1){
        		second.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer2_send != 0) && (match.answer2_send != 1)){
        		second.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer3_send == 1){
        		third.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer3_send != 0) && (match.answer3_send != 1)){
        		third.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 2){
        	if(match.answer4_send == 1){
        		first.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer4_send != 0) && (match.answer4_send != 1)){
        		first.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer5_send == 1){
        		second.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer5_send != 0) && (match.answer5_send != 1)){
        		second.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer6_send == 1){
        		third.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer6_send != 0) && (match.answer6_send != 1)){
        		third.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 3){
        	if(match.answer7_send == 1){
        		first.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer7_send != 0) && (match.answer7_send != 1)){
        		first.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer8_send == 1){
        		second.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer8_send != 0) && (match.answer8_send != 1)){
        		second.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer9_send == 1){
        		third.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer9_send != 0) && (match.answer9_send != 1)){
        		third.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 4){
        	if(match.answer10_send == 1){
        		first.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer10_send != 0) && (match.answer10_send != 1)){
        		first.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer11_send == 1){
        		second.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer11_send != 0) && (match.answer11_send != 1)){
        		second.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer12_send == 1){
        		third.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer12_send != 0) && (match.answer12_send != 1)){
        		third.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 5){
        	if(match.answer13_send == 1){
        		first.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer13_send != 0) && (match.answer13_send != 1)){
        		first.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer14_send == 1){
        		second.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer14_send != 0) && (match.answer14_send != 1)){
        		second.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer15_send == 1){
        		third.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer15_send != 0) && (match.answer15_send != 1)){
        		third.setBackgroundResource(R.drawable.falso);
        	}
        }
		/** receiver user **/
        
		
		ImageView receiver_img = (ImageView) findViewById(R.id.game_receiver_img);
		bitmap = null;
        if(!(Game.receiver.FbConnect.equals("0"))){
			String url = String.format(Game.receiver.playerImage);
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
			if(Game.receiver.playerImage.contains("default")){
				
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
        //bitmap = getRoundedShape(bitmap);
        receiver_img.setImageBitmap(bitmap);
        
        /*set level for the sender */
        TextView receiver_level = (TextView) findViewById(R.id.game_receiver_level);
        long actualLevel_receiver = (long) (Math.pow(Game.receiver.playerPoints, (1/1.7)));
        receiver_level.setText(Long.toString(actualLevel_receiver));
        receiver_level.setTypeface(roboto_medium_italic);
        
        /*set username */
        TextView receiver_name = (TextView) findViewById(R.id.game_receiver_name);
        receiver_name.setText(Game.receiver.playerName);
        receiver_name.setTypeface(roboto_black_italic);
        
        /* set club */
        TextView receiver_club = (TextView) findViewById(R.id.game_receiver_club);
        receiver_club.setText(Game.receiver.club);
        receiver_club.setTypeface(roboto_medium_italic);
		
        /* set answer tile */
        first_rec = (ImageView) findViewById(R.id.first_question_rec);
        second_rec = (ImageView) findViewById(R.id.second_question_rec);
        third_rec = (ImageView) findViewById(R.id.third_question_rec);
        if(match.currentRoundNumber == 1){
        	if(match.answer1_rec == 1){
        		first_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer1_rec != 0) && (match.answer1_rec != 1)){
        		first_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer2_rec == 1){
        		second_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer2_rec != 0) && (match.answer2_rec != 1)){
        		second_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer3_rec == 1){
        		third_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer3_rec != 0) && (match.answer3_rec != 1)){
        		third_rec.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 2){
        	if(match.answer4_rec == 1){
        		first_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer4_rec != 0) && (match.answer4_rec != 1)){
        		first_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer5_rec == 1){
        		second_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer5_rec != 0) && (match.answer5_rec != 1)){
        		second_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer6_rec == 1){
        		third_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer6_rec != 0) && (match.answer6_rec != 1)){
        		third_rec.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 3){
        	if(match.answer7_rec == 1){
        		first_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer7_rec != 0) && (match.answer7_rec != 1)){
        		first_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer8_rec == 1){
        		second_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer8_rec != 0) && (match.answer8_rec != 1)){
        		second_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer9_rec == 1){
        		third_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer9_rec != 0) && (match.answer9_rec != 1)){
        		third_rec.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 4){
        	if(match.answer10_rec == 1){
        		first_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer10_rec != 0) && (match.answer10_rec != 1)){
        		first_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer11_rec == 1){
        		second_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer11_rec != 0) && (match.answer11_rec != 1)){
        		second_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer12_rec == 1){
        		third_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer12_rec != 0) && (match.answer12_rec != 1)){
        		third_rec.setBackgroundResource(R.drawable.falso);
        	}
        }
        if(match.currentRoundNumber == 5){
        	if(match.answer13_rec == 1){
        		first_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer13_rec != 0) && (match.answer13_rec != 1)){
        		first_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer14_rec == 1){
        		second_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer14_rec != 0) && (match.answer14_rec != 1)){
        		second_rec.setBackgroundResource(R.drawable.falso);
        	}
        	if(match.answer15_rec == 1){
        		third_rec.setBackgroundResource(R.drawable.correct);
        	}
        	if((match.answer15_rec != 0) && (match.answer15_rec != 1)){
        		third_rec.setBackgroundResource(R.drawable.falso);
        	}
        }
	
			
		/** set questions **/
		
		
		//retrieve all the answers of the user
		actualQuestion = (Game.match.currentRoundNumber - 1)*3 + question;
		if(isSender(FragmentContainer.userLobby.playerID)){
			sender = 1;
		};	
		userQuestion = dbHelper.getActualQuestion(idMatch, actualQuestion);
		/**randomize order of answers **/
		answers[0] = userQuestion.correct;
		answers[1] = userQuestion.wrong1;
		answers[2] = userQuestion.wrong2;
		answers[3] = userQuestion.wrong3;
		
		arrayAnswers = ShuffleArray(arrayAnswers);
		
		/** set the question in the layout **/
		TextView quest = (TextView) findViewById(R.id.question);
		quest.setTypeface(roboto_medium_italic);
		TextView answ1 = (TextView) findViewById(R.id.answer1);
		answ1.setTypeface(roboto_medium_italic);
		TextView answ2 = (TextView) findViewById(R.id.answer2);
		answ2.setTypeface(roboto_medium_italic);
		TextView answ3 = (TextView) findViewById(R.id.answer3);
		answ3.setTypeface(roboto_medium_italic);
		TextView answ4 = (TextView) findViewById(R.id.answer4);
		answ4.setTypeface(roboto_medium_italic);
		quest.setText(userQuestion.question);
		
		answ1.setText(answers[arrayAnswers[0]]);
		answ2.setText(answers[arrayAnswers[1]]);
		answ3.setText(answers[arrayAnswers[2]]);
		answ4.setText(answers[arrayAnswers[3]]);
		
		/* set continue button */
		Button clickToContinue = (Button) findViewById(R.id.clickToContinue);
		clickToContinue.getBackground().setAlpha(128);  // 50% transparent
		clickToContinue.setEnabled(false);
		
		
		counter.start();
		
	}

	public class MyCount extends CountDownTimer {

        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
        	dbHelper.setWrong(idMatch, sender, actualQuestion, 5);
        	if(question == 3){
        	   	Intent i = new Intent(Question.this, Game.class);
           		i.putExtra("IDEXTRA", Integer.toString(idMatch));
           		
           		startActivity(i);
           		finish();
        	}else{
        		Intent i = new Intent(Question.this, PreMatch.class);
        		i.putExtra("QUESTION", Integer.toString(question + 1));
        		
        		startActivity(i);
        		finish();
        	}
        }

        @Override
        public void onTick(long millisUntilFinished) {
        	tv.setBackgroundResource(mThumbsID[i]);
            i++;
        }
    }
	
	
	
	public boolean isSender(int id){
		if(id == Game.sender.playerID){
			return true;
		}else return false;			
	}
	
	private int[] ShuffleArray(int[] array)
	{
	    int index;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            array[index] ^= array[i];
	            array[i] ^= array[index];
	            array[index] ^= array[i];
	        }
	    }
	    return array;
	}
	
	
	@SuppressLint("ResourceAsColor")
	public void getAnswer(View view){
		counter.cancel();
		int questionNumber;
		Button button = (Button)view;
		String buttonText = (String) button.getText().toString();
		Log.w("answer", buttonText);
		if (buttonText.equals(answers[0])){
			dbHelper.setCorrect(idMatch, sender, actualQuestion);
			button.setBackgroundResource(R.drawable.round_background_right);
			Log.w("correct", answers[0]);
			
			
			
		}
		if (buttonText.equals(answers[1])){
			Button button1 = (Button) findViewById(R.id.answer1);
			String answer1 = (String) button1.getText().toString();
			Button button2 = (Button) findViewById(R.id.answer2);
			String answer2 = (String) button2.getText().toString();
			Button button3 = (Button) findViewById(R.id.answer3);
			String answer3 = (String) button3.getText().toString();
			Button button4 = (Button) findViewById(R.id.answer4);
			String answer4 = (String) button4.getText().toString();
			questionNumber = 2;
			dbHelper.setWrong(idMatch, sender, actualQuestion, questionNumber);
			button.setBackgroundResource(R.drawable.round_background_wrong);
			Log.w("wrong1", answers[1]);
			if(answer1.equals(answers[0])){
				button1.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer2.equals(answers[0])){
				button2.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer3.equals(answers[0])){
				button3.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer4.equals(answers[0])){
				button4.setBackgroundResource(R.drawable.round_background_right);
			}
			
			
			
		}
		if(buttonText.equals(answers[2])){
			Button button1 = (Button) findViewById(R.id.answer1);
			String answer1 = (String) button1.getText().toString();
			Button button2 = (Button) findViewById(R.id.answer2);
			String answer2 = (String) button2.getText().toString();
			Button button3 = (Button) findViewById(R.id.answer3);
			String answer3 = (String) button3.getText().toString();
			Button button4 = (Button) findViewById(R.id.answer4);
			String answer4 = (String) button4.getText().toString();
			questionNumber = 3;
			dbHelper.setWrong(idMatch, sender, actualQuestion, questionNumber);
			button.setBackgroundResource(R.drawable.round_background_wrong);
			Log.w("wrong2", answers[2]);
			if(answer1.equals(answers[0])){
				button1.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer2.equals(answers[0])){
				button2.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer3.equals(answers[0])){
				button3.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer4.equals(answers[0])){
				button4.setBackgroundResource(R.drawable.round_background_right);
			}
		}
		if(buttonText.equals(answers[3])){
			Button button1 = (Button) findViewById(R.id.answer1);
			String answer1 = (String) button1.getText().toString();
			Button button2 = (Button) findViewById(R.id.answer2);
			String answer2 = (String) button2.getText().toString();
			Button button3 = (Button) findViewById(R.id.answer3);
			String answer3 = (String) button3.getText().toString();
			Button button4 = (Button) findViewById(R.id.answer4);
			String answer4 = (String) button4.getText().toString();
			questionNumber = 4;
			dbHelper.setWrong(idMatch, sender, actualQuestion, questionNumber);
			button.setBackgroundResource(R.drawable.round_background_wrong);
			Log.w("wrong3", answers[3]);
			if(answer1.equals(answers[0])){
				button1.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer2.equals(answers[0])){
				button2.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer3.equals(answers[0])){
				button3.setBackgroundResource(R.drawable.round_background_right);
			}
			if(answer4.equals(answers[0])){
				button4.setBackgroundResource(R.drawable.round_background_right);
			}
		}
		Log.w("correct", answers[0]);
		Button clickToContinue = (Button) findViewById(R.id.clickToContinue);
		clickToContinue.setEnabled(true);
		Button answer1 = (Button) findViewById(R.id.answer1);
		Button answer2 = (Button) findViewById(R.id.answer2);
		Button answer3 = (Button) findViewById(R.id.answer3);
		Button answer4 = (Button) findViewById(R.id.answer4);
		answer1.setEnabled(false);
		answer2.setEnabled(false);
		answer3.setEnabled(false);
		answer4.setEnabled(false);
		RelativeLayout countDownImage = (RelativeLayout) findViewById(R.id.countDownImage);
		countDownImage.setBackground(null);
		//1.graphic
				int[] questionPercentage = dbHelper.getAnswered(match.IDmatch, match.currentAnswerNumber);
				double[] VALUES_1GRAF = null;
				if (buttonText.equals(answers[0])){
					VALUES_1GRAF= new double[] { questionPercentage[0] + 1, questionPercentage[1] + 1 };
				}else{
					VALUES_1GRAF= new double[] { questionPercentage[0], questionPercentage[1] + 1 };
				}
				mRenderer_1GRAF.setPanEnabled(false);
				mRenderer_1GRAF.setShowLegend(false);
				for (int i = 0; i < VALUES_1GRAF.length; i++) {  
					mSeries_1GRAF.add(NAME_LIST_1GRAF[i] + " " + VALUES_1GRAF[i], VALUES_1GRAF[i]);  
					SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();  
					renderer.setColor(COLORS_1GRAF[(mSeries_1GRAF.getItemCount() - 1) % COLORS_1GRAF.length]);  
					mRenderer_1GRAF.addSeriesRenderer(renderer);  
				}  
				if (mChartView_1GRAF != null) {  
					mChartView_1GRAF.repaint();  
				}  
				if (mChartView_1GRAF == null) {  
					
					mChartView_1GRAF = ChartFactory.getPieChartView(this, mSeries_1GRAF, mRenderer_1GRAF);  
					mRenderer_1GRAF.setClickEnabled(true);  
					mRenderer_1GRAF.setSelectableBuffer(10);  
			  		mChartView_1GRAF.setOnLongClickListener(new View.OnLongClickListener() {  
			  			public boolean onLongClick(View v) {  
			  				return true;
			  			}  
			  		});  
			  		countDownImage.addView(mChartView_1GRAF, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
				}  
				else {  
					mChartView_1GRAF.repaint();  
				}
		
		clickToContinue.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(question == 3){
		    	   	Intent i = new Intent(Question.this, Game.class);
		       		i.putExtra("IDEXTRA", Integer.toString(idMatch));
		       		startActivity(i);
		       		finish();
		    	}else{
		    		Intent i = new Intent(Question.this, PreMatch.class);
		    		i.putExtra("QUESTION", Integer.toString(question + 1));
		    		startActivity(i);
		    		finish();
		    	}
				
			}
		});
		
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
	
	public void onBackPressed(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setTitle("Confirm");
	    builder.setMessage("Are you sure?");

	    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing but close the dialog
	        	counter.cancel();
	        	int s= 0;
	        	if(FragmentContainer.userLobby.playerID == match.IDsender_match){
	        		s=1;
	        	}
	        	dbHelper.setRemainingRoundWrong(match.IDmatch, match.currentRoundNumber, match.currentAnswerNumber, s);
	    	    Intent i = new Intent(Question.this, Game.class);
	    		i.putExtra("IDEXTRA", Integer.toString(match.IDmatch));
	    		startActivity(i);
	        	finish();
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });

	    AlertDialog alert = builder.create();
	    alert.show();
		}
	
	
}
