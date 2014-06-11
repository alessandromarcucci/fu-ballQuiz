package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.splashscreen.DatabaseHelper.User;

public class Ranking extends Activity{
	DatabaseHelper dbHelper = new DatabaseHelper();
	User myUser = null;
	InputStream inputStream = null;
	ListAdapter adapterList = null;
	ListView list;
	static List<User> rankingList = new ArrayList<User>();
	Typeface roboto_black = null;
	Typeface roboto_medium_italic = null;
	Typeface roboto_black_italic = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ranking_layout);
		Intent intent = getIntent();
		
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
		
		
		
		/** SET PERSONAL RANK TILE */
		
		
		/* set ranking number */
		TextView myrank = (TextView) findViewById(R.id.myrank);
		int rank = dbHelper.getMyRank(FragmentContainer.userLobby.playerID);
		myrank.setText("#" + rank);
		myrank.setTypeface(roboto_black);
		
		/* set image */
		ImageView myrank_image = (ImageView) findViewById(R.id.myrank_image);
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
			myrank_image.setImageBitmap(bitmap);
			/* set fb connected icon */
			ImageView fbconnected = (ImageView) findViewById(R.id.myrank_fbconnected);
			fbconnected.setImageResource(R.drawable.facebook_round);
		}else{
			if(FragmentContainer.userLobby.playerImage.contains("default")){
					myrank_image.setImageResource(R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
		
		/* set user level */
		TextView myrank_level = (TextView) findViewById(R.id.myrank_level);
		
		myrank_level.setText(Long.toString(actualLevel2));
		
		/*set user name */
		TextView myrank_name = (TextView) findViewById(R.id.myrank_name);
		myrank_name.setText(FragmentContainer.userLobby.playerName);
		myrank_name.setTypeface(roboto_black_italic);
		
		
		
		/** SET UP RANKING LIST **/
		
		rankingList.clear();
		rankingList = dbHelper.getRank();
		adapterList = new RankingAdapter(Ranking.this, rankingList);
		list = (ListView) findViewById(R.id.listRanking);
	    list.setAdapter(adapterList);
		
		
		
	}
	
	public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
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
  	  sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
  	  targetHeight), null);
  	  return targetBitmap;
  	 }
}
