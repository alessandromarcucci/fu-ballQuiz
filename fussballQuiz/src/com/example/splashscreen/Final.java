package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;

public class Final extends Activity{
	DatabaseHelper dbHelper = new DatabaseHelper();
	static User sender = null;
	static User receiver = null;
	static Match match = null;
	int send = 0;
	InputStream inputStream = null;
	private static int[] COLORS = new int[] { Color.BLUE, Color.GREEN }; 
	private static String[] NAME_LIST = new String[] {"TOT POINTS", "THIS MATCH"};
	private CategorySeries mSeries = new CategorySeries("");
	private DefaultRenderer mRenderer = new DefaultRenderer();
	private GraphicalView mChartView = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_match);
		Intent intent = getIntent();
		Bundle extras = getIntent().getExtras();
		int idMatch = Integer.parseInt(extras.getString("IDMATCH"));
		Log.w("idMatch", Integer.toString(idMatch));
		
		
		match = dbHelper.getMatch(idMatch);
		sender = dbHelper.getUser(match.IDsender_match);
		receiver = dbHelper.getUser(match.IDreceiver_match);
		if(isSender(FragmentContainer.userLobby.playerID)){
			send = 1;
		}
		
		/** SET ACTIONBAR COMPONENTS **/
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
			//bitmap = getRoundedShape(bitmap);
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
		user_name.setText(FragmentContainer.userLobby.playerName);
		
		/* set club */
		TextView user_club = (TextView) v.findViewById(R.id.playerclub);
		user_club.setText(FragmentContainer.userLobby.club);
		
		/** set user level **/
		TextView user_level = (TextView) v.findViewById(R.id.playerLevel);
		long actualLevel = (long) (Math.pow(FragmentContainer.userLobby.playerPoints, (1/1.7)));
		user_level.setText(Long.toString(actualLevel));
		user_level.setBackgroundResource(R.drawable.red_circle);
		
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
        bitmap = getRoundedShape(bitmap);
        sender_img.setImageBitmap(bitmap);
        
        /*set level for the sender */
        TextView sender_level = (TextView) findViewById(R.id.game_sender_level);
        
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
        
        
        
        /** SET VICTORY TEXT AND CROWN IMAGE **/
        TextView victoryText = (TextView) findViewById(R.id.victoryText);
        ImageView sender_crown = (ImageView) findViewById(R.id.final_crown_sender);
        ImageView receiver_crown = (ImageView) findViewById(R.id.final_crown_receiver);
        
        if(match.pointsSender == match.pointsReceiver){
        	victoryText.setTextColor(getResources().getColor(R.color.YellowGame));
        	victoryText.setText("draw");
        }
        if(match.IDsender_match == FragmentContainer.userLobby.playerID){
        		if(match.pointsSender > match.pointsReceiver){
        			victoryText.setTextColor(getResources().getColor(R.color.GreenGame));
        			victoryText.setText("Du hast gewonnen");
        			sender_crown.setBackgroundResource(R.drawable.crown);
        		}
        		if(match.pointsSender < match.pointsReceiver){
        			victoryText.setTextColor(getResources().getColor(R.color.RedGame));
        			victoryText.setText("Du hast verloren");
        			receiver_crown.setBackgroundResource(R.drawable.crown);
        		}
        }else{
        	if(match.pointsSender > match.pointsReceiver){
    			victoryText.setTextColor(getResources().getColor(R.color.RedGame));
    			victoryText.setText("Du hast verloren");
    			sender_crown.setBackgroundResource(R.drawable.crown);
    		}
    		if(match.pointsSender < match.pointsReceiver){
    			victoryText.setTextColor(getResources().getColor(R.color.GreenGame));
    			victoryText.setText("Du hast gewonnen");
    			receiver_crown.setBackgroundResource(R.drawable.crown);
    		}
        }
        
        
		
		/** setting values for field Match **/
		int totalPoints = 0;
        
        ProgressBar progressMatch = (ProgressBar) findViewById(R.id.progressMatch);
        TextView textMatch = (TextView) findViewById(R.id.textMatch);
        progressMatch.setMax(15);
        if(match.IDsender_match == FragmentContainer.userLobby.playerID){
        	progressMatch.setProgress(match.pointsSender);
        	textMatch.setText(Integer.toString(match.pointsSender));
        	totalPoints += match.pointsSender;
        }else{
        	progressMatch.setProgress(match.pointsReceiver);
        	textMatch.setText(Integer.toString(match.pointsReceiver));
        	totalPoints += match.pointsReceiver;

        }
        
        /** setting values for field Victory **/
        ProgressBar progressVictory = (ProgressBar) findViewById(R.id.progressVictory);
        TextView textVictory = (TextView) findViewById(R.id.textVictory);
        progressVictory.setMax(3);
        if(match.IDsender_match == FragmentContainer.userLobby.playerID){
        		progressVictory.setProgress(match.victoryPointsSender);
        		textVictory.setText(Integer.toString(match.victoryPointsSender));
        		totalPoints += match.victoryPointsSender;
        }else{
        	progressVictory.setProgress(match.victoryPointsReceiver);
        	textVictory.setText(Integer.toString(match.victoryPointsReceiver));
    		totalPoints += match.victoryPointsReceiver;

        }
        	
        
        
		/** setting values for field premium **/
		ProgressBar progressPremium = (ProgressBar) findViewById(R.id.progressPremium);
		TextView textPremium = (TextView) findViewById(R.id.textPremium);
		progressPremium.setMax(1);
		if(sender.playerID == FragmentContainer.userLobby.playerID){
			progressPremium.setProgress(match.premiumPointsSender);
			textPremium.setText(Integer.toString(match.premiumPointsSender));
			totalPoints += match.premiumPointsSender;
		}else{
			progressPremium.setProgress(match.premiumPointsReceiver);
			textPremium.setText(Integer.toString(match.premiumPointsReceiver));
			totalPoints += match.premiumPointsReceiver;
		}
		
		/** setting values for field opponent level **/
		ProgressBar progressOpponent = (ProgressBar) findViewById(R.id.progressOpponent);
		TextView textOpponent = (TextView) findViewById(R.id.textOpponent);
		
		progressOpponent.setMax(15);
		if (send == 1){
			
			int levelFactor = (int) (Math.ceil((totalPoints * Float.parseFloat(match.levelFactorReceiver))) - totalPoints);
			textOpponent.setText(Integer.toString(levelFactor));
			progressOpponent.setProgress(levelFactor);
			
		}else{
			
			int levelFactor = (int) (Math.ceil((totalPoints * Float.parseFloat(match.levelFactorSender))) - totalPoints);
			textOpponent.setText(Integer.toString(levelFactor));
			progressOpponent.setProgress(levelFactor);
		}
		
		
		/** setting values for field perfect **/
		ProgressBar progressPerfect = (ProgressBar) findViewById(R.id.progressPerfect);
		TextView textPerfect = (TextView) findViewById(R.id.textPerfect);
		progressPerfect.setMax(1);
		if(send == 1){
			if(match.pointsSender == 15){
				progressPerfect.setProgress(1);
				textPerfect.setText("1");
			}else{
				progressPerfect.setProgress(0);
				textPerfect.setText("0");
			}
		}else{
			if(match.pointsReceiver == 15){
				progressPerfect.setProgress(1);
				textPerfect.setText("1");
			}else{
				progressPerfect.setProgress(0);
				textPerfect.setText("0");
			}
		}
		
		
		
		
		
		
		
		/** calculate the tot points for the actual match **/
		int totmatch = 0;
		int totpoints = 0;
		if (send == 1){
			totmatch = match.totalPointsSender;
			totpoints = sender.playerPoints;
		}else{
			totmatch = match.totalPointsReceiver;
			totpoints = receiver.playerPoints;
		}
		
		if(send == 1){
			//dbHelper.addPoints(totpoints, match.IDsender_match);
		}else //dbHelper.addPoints(totpoints, match.IDreceiver_match);
		
		Log.w("totmatch", Integer.toString(totmatch));
		Log.w("totpoints", Integer.toString(totpoints));
		
		/* set values for the exp-bar */
        ProgressBar expbar = (ProgressBar) findViewById(R.id.progress_bar);
        
        long nextLevel = actualLevel + 1;
		int min = (int) (Math.pow(actualLevel, 1.7));
		Log.w("min", Integer.toString(min));
        int max = (int) ((Math.pow(nextLevel, 1.7) - min));
        Log.w("max", Integer.toString(max));
        expbar.setMax(max);
        int progress = FragmentContainer.userLobby.playerPoints - min; 
        Log.w("progress", Integer.toString(progress));
        expbar.setProgress(progress);
        TextView currentLevel = (TextView) findViewById(R.id.currentLevel);
        currentLevel.setText(Long.toString(actualLevel));
        TextView next = (TextView) findViewById(R.id.nextLevel);
        next.setText(Long.toString(nextLevel));
		
	}
	
	public static double integerPart(double d) {
		  return (d <= 0) ? Math.ceil(d) : Math.floor(d);
		}
	
	public boolean isSender(int id){
		if(id == match.IDsender_match){
			return true;
		}else return false;			
	}
	
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
	  	  // TODO Auto-generated method stub
	  	  int targetWidth = 500;
	  	  int targetHeight = 500;
	  	  Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,Bitmap.Config.ARGB_8888);
	  	  Canvas canvas = new Canvas(targetBitmap);
	  	  Path path = new Path();
	  	  path.addCircle(((float) targetWidth - 1) / 2,
	  	  ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),  Path.Direction.CCW);
	  	  canvas.clipPath(path);
	  	  Bitmap sourceBitmap = scaleBitmapImage;
	  	  canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
	  	  sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);
	  	
	  	  
	  	  final int width=targetBitmap.getWidth(),height=targetBitmap.getHeight();
	  	  
	 
	  	  return targetBitmap;
	  	 }
	
}
