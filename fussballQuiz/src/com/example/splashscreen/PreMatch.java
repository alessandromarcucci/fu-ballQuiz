package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PreMatch extends Activity {
	DatabaseHelper dbHelper = new DatabaseHelper();
	String question = null;
	User sender = Game.sender;
	User receiver = Game.receiver;
	Match match = null;
	InputStream inputStream = null;
	Typeface roboto_black =  null;
	Typeface roboto_medium_italic = null;
	Typeface roboto_black_italic = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prematch_layout);
		Intent intent = getIntent();
		Bundle extras = getIntent().getExtras();
		question = extras.getString("QUESTION");
		
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
		
		
		/* set font for the ready button */
        Button prematch = (Button) findViewById(R.id.prematchbutton);
        prematch.setTypeface(roboto_black_italic);
		
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
        long actualLevel = (long) (Math.pow(FragmentContainer.userLobby.playerPoints, (1/1.7)));
        sender_level.setText(Long.toString(actualLevel));
        sender_level.setTypeface(roboto_medium_italic);
        
        /*set username */
        TextView sender_name = (TextView) findViewById(R.id.game_sender_name);
        sender_name.setText(sender.playerName);
        sender_name.setTypeface(roboto_black_italic);
        
        /* set club */
        TextView sender_club = (TextView) findViewById(R.id.game_sender_club);
        sender_club.setText(sender.club);
        sender_club.setTypeface(roboto_medium_italic);
        
        /* set answer tile */
        ImageView first = (ImageView) findViewById(R.id.first_question_send);
        ImageView second = (ImageView) findViewById(R.id.second_question_send);
        ImageView third = (ImageView) findViewById(R.id.third_question_send);
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
        receiver_level.setTypeface(roboto_medium_italic);
        /*set username */
        TextView receiver_name = (TextView) findViewById(R.id.game_receiver_name);
        receiver_name.setText(receiver.playerName);
        receiver_name.setTypeface(roboto_black_italic);
        /* set club */
        TextView receiver_club = (TextView) findViewById(R.id.game_receiver_club);
        receiver_club.setText(receiver.club);
        receiver_club.setTypeface(roboto_medium_italic);
		
        /* set answer tile */
        ImageView first_rec = (ImageView) findViewById(R.id.first_question_rec);
        ImageView second_rec = (ImageView) findViewById(R.id.second_question_rec);
        ImageView third_rec = (ImageView) findViewById(R.id.third_question_rec);
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
	}
	
	public void goToQuestion(View view){
		Intent i = new Intent(PreMatch.this, Question.class);
		i.putExtra("QUESTION", question);
		startActivity(i);
		finish();
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
        	int s= 0;
        	if(FragmentContainer.userLobby.playerID == match.IDsender_match){
        		s=1;
        	}
        	dbHelper.setRemainingRoundWrong(match.IDmatch, match.currentRoundNumber, match.currentAnswerNumber, s);
    	    Intent i = new Intent(PreMatch.this, Game.class);
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
