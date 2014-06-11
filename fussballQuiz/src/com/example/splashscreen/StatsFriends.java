package com.example.splashscreen;



import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;

import com.example.splashscreen.DatabaseHelper.User;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StatsFriends extends FragmentActivity {
	private MyAdapter mAdapter;
	private ViewPager mPager;
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	/** Called when the activity is first created. */
	static Button quickview = null;
	static Button achievements = null;
	static int id;
	static User user = null;
	static LinearLayout view = null;
	static ImageView tab_image = null;
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statsfriends_layout);
		
		Intent intent = getIntent();
		id = intent.getIntExtra("ID", -1);
		user = dbHelper.getUser(id);
		
		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager_mystats);
		mPager.setAdapter(mAdapter);
		
		
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
		
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // Create a tab listener that is called when the user changes tabs.
           

           // Add 3 tabs, specifying the tab's text and TabListener
           
            actionbar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.FilterBar)));
            actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM  | ActionBar.DISPLAY_SHOW_HOME);
            ActionBar.TabListener tabListener = new ActionBar.TabListener() {
                

    			@Override
    			public void onTabSelected(Tab tab, FragmentTransaction ft) {
    				// TODO Auto-generated method stub
    				mPager.setCurrentItem(tab.getPosition());
    				
    			}

    			@Override
    			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    				// TODO Auto-generated method stub
    				
    			}

    			@Override
    			public void onTabReselected(Tab tab, FragmentTransaction ft) {
    				// TODO Auto-generated method stub
    				
    			}
            };
            
            for (int i = 0; i < 4; i++) {
                // Create a tab with text corresponding to the page title defined by
                // the adapter. Also specify this Activity object, which implements
                // the TabListener interface, as the callback (listener) for when
                // this tab is selected.

                if(i == 0){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_mystats_tab1, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image1);
                }
                if(i == 1){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_mystats_tab2, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image2);
                }
                if(i == 2){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_mystats_tab3, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image3);
                }
                if(i == 3){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_mystats_tab4, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image4);
                }
                

                

                
                
                actionbar.addTab(actionbar.newTab().setTabListener(tabListener).setCustomView(view));
                //actionbar.addTab(actionbar.newTab().setCustomView(view));
            }
		
        actionbar.setCustomView(v);	
        mPager.setOffscreenPageLimit(2);
        mPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding t
	                    getActionBar().setSelectedNavigationItem(position);
	                    
	                   
	                }
	            });
        /***************************************************************************************************/

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
  	  sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
  	  targetHeight), null);
  	  return targetBitmap;
  	 }

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				
				OverviewFriendsFragment o = new OverviewFriendsFragment();
				Bundle args = new Bundle();
				args.putInt("ID", id);
				o.setArguments(args);
				return o;
			
			case 1:
				

				RankingFriendsFragment r = new RankingFriendsFragment();
				Bundle args3 = new Bundle();
				args3.putInt("ID", id);
				r.setArguments(args3);
				return r;				
				
			case 2:
				

				ProfileStatsFriendsFragment p = new ProfileStatsFriendsFragment();
				Bundle args4 = new Bundle();
				args4.putInt("ID", id);
				p.setArguments(args4);
				return p;
				
			case 3:
				ChatFragment c = new ChatFragment();
				Bundle args5 = new Bundle();
				args5.putInt("ID", id);
				c.setArguments(args5);
				return c;
			
				
			
			default:
				return null;
			}
		}
	}
	
	public void sendMessage(View view){
		EditText Editmessage = (EditText) findViewById(R.id.editText_chat);
		String message = Editmessage.getText().toString();
		
		dbHelper.createChatMessage(FragmentContainer.userLobby.playerID, StatsFriends.id, message);
	}
}
