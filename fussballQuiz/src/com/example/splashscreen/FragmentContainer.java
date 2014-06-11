package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.example.splashscreen.DatabaseHelper.User;
import com.example.splashscreen.Login.MyAdapter;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
public class FragmentContainer extends FragmentActivity implements
ActionBar.TabListener{
	private static DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    InputStream inputStream = null;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    int id = 0;
    static User userLobby = null;
    DatabaseHelper dbHelper = new DatabaseHelper();
    MyAdapterSlider adapter = null;
    static List<User> pending = new ArrayList<User>();
    static ArrayList<Match> allMatches = new ArrayList<Match>();
    User user = null;
    int myrank= 0;
    private MyAdapter mAdapter;
	private ViewPager mPager;
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	static Button filter_new_player = null;
	static Button filter_returning_player = null;
	static ListView listView = null;
	static Timer myTimer = new Timer();
	static LinearLayout view = null;
	static ImageView tab_image = null;
	
	static ArrayList<Match> arrayMatch = new ArrayList<Match>();
	static ArrayList<Match> arrayPending = new ArrayList<Match>();
	static User challenger = null;
	static ArrayList<User> challengerList = new ArrayList<User>();
	static DrawerLayout drawer_container = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager_lobby);
		mPager.setAdapter(mAdapter);
		
		roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		Intent intent = getIntent();
		id = intent.getIntExtra("ID", -1);
		
		/* get user info*/
		userLobby = dbHelper.getUser(id);
		/* delete expired games: older than 1 week *
        dbHelper.deleteExpiredGames(userLobby.playerID);
		/*get all matches*/
        allMatches = dbHelper.searchPendingAndActiveGames(userLobby.playerID);
        
        drawer_container = (DrawerLayout) findViewById(R.id.drawer_layout);
        
        
        /** set start new game button layout */
        Button newgame = (Button) findViewById(R.id.startNewGame);
        newgame.setTypeface(roboto_black);
		

        
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
        if(!(userLobby.FbConnect.equals("0"))){
			String url = String.format(userLobby.playerImage);
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
			if(userLobby.playerImage.contains("default")){
					actionbar_image.setImageResource(R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
        
        /* set username */
		TextView user_name = (TextView) v.findViewById(R.id.playerName);
		
		user_name.setTypeface(roboto_black_italic);
		
		user_name.setText(userLobby.playerName);
		
		/* set club */
		TextView user_club = (TextView) v.findViewById(R.id.playerclub);
		
		user_name.setTypeface(roboto_medium_italic);
		user_club.setText(userLobby.club);
		
		/** set user level **/
		TextView user_level = (TextView) v.findViewById(R.id.playerLevel);
		long actualLevel2 = (long) (Math.pow(userLobby.playerPoints, (1/1.7)));
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
            
            for (int i = 0; i < 5; i++) {
                // Create a tab with text corresponding to the page title defined by
                // the adapter. Also specify this Activity object, which implements
                // the TabListener interface, as the callback (listener) for when
                // this tab is selected.

                if(i == 0){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_matches_tab1, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image1);
                }
                if(i == 1){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_matches_tab2, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image2);
                }
                if(i == 2){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_matches_tab3, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image3);
                }
                if(i == 3){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_matches_tab4, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image4);
                }
                if(i == 4){
                	view = (LinearLayout) getLayoutInflater().inflate(R.layout.filter_matches_tab5, null);
                	tab_image = (ImageView) view.findViewById(R.id.tab_image5);
                }

                

                
                
                actionbar.addTab(actionbar.newTab().setTabListener(tabListener).setCustomView(view));
                //actionbar.addTab(actionbar.newTab().setCustomView(view));
            }
		
        actionbar.setCustomView(v);	
        mPager.setOffscreenPageLimit(4);
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
			
			
/************* SET NAVIGATION DRAWER COMPONENTS ********************************/
        
        
        getOverflowMenu();
        
		/* set values for the exp-bar */
        ProgressBar expbar = (ProgressBar) findViewById(R.id.progress_bar);
        long actualLevel = (long) (Math.pow(userLobby.playerPoints, (1/1.7)));
        long nextLevel = actualLevel + 1;
		int min = (int) (Math.pow(actualLevel, 1.7));
		Log.w("min", Integer.toString(min));
        int max = (int) ((Math.pow(nextLevel, 1.7) - min));
        Log.w("max", Integer.toString(max));
        expbar.setMax(max);
        int progress = userLobby.playerPoints - min; 
        Log.w("progress", Integer.toString(progress));
        expbar.setProgress(progress);
        TextView currentLevel = (TextView) findViewById(R.id.currentLevel);
        currentLevel.setText(Long.toString(actualLevel));
        TextView next = (TextView) findViewById(R.id.nextLevel);
        next.setText(Long.toString(nextLevel));
		/* load FragmentLobby */
       /* FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();   
        LobbyFragment fragment = new LobbyFragment();
        fragmentTransaction.add(R.id.content_frame, fragment);
        fragmentTransaction.commit();*/

        mTitle = mDrawerTitle = getTitle();
        //mPlanetTitles = getResources().getStringArray(R.array.slider_menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        myrank = dbHelper.getMyRank(userLobby.playerID);
        
        Log.w("myrank", Integer.toString(myrank));
        pending.clear();
        userLobby.rank = dbHelper.getMyRank(id);
        pending.add(userLobby);
        pending.addAll(dbHelper.getRankingTop3());
        adapter = new MyAdapterSlider(FragmentContainer.this, pending);
        mDrawerList = (ListView) findViewById(R.id.listview_slider);
        mDrawerList.setScrollContainer(false);
        mDrawerList.setAdapter(adapter);
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        
        

        

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                
                
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        
        /*************************************************************************************************/
        
        
        
        
        
        Session.openActiveSession(this, true, new Session.StatusCallback() {
   		 
     	      // callback when session changes state
     	      @SuppressWarnings("deprecation")
     	      @Override
     	      public void call(Session session, SessionState state, Exception exception) {
     	        
     	    	  if (session != null && session.isOpened()  ) {
     	    	// make request to the /me API
     		          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {

  					@Override
  					public void onCompleted(GraphUser arg0, Response arg1) {
  						// TODO Auto-generated method stub
  						
  					}
     		        	  
     		          });
     	    	  }
     	      }
      	 });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
       
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        //boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        //hideMenuItems(menu, !drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
        case R.id.action_settings:
            String item_title = item.getTitle().toString();
            Log.w("itemID", item_title);
            if(item_title.equals("Matches")){
            	Intent i = new Intent(FragmentContainer.this, FragmentContainer.class);
            	startActivity(i);
            	finish();
            }
            if(item_title.equals("Stats")){
            	Intent i = new Intent(FragmentContainer.this, Stats.class);
            	startActivity(i);
            	finish();
            	
            }
            if(item_title.equals("Options")){
            	Intent i = new Intent(FragmentContainer.this, Options2.class);
            	startActivity(i);
            	finish();
            	
            }
            // create intent to perform web search for this planet
            /*Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
            // catch event that there's no activity to handle intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
            }*/
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
   

   

    private void selectItem(int position) {
    	Log.w("position", Integer.toString(position));
        
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    
    

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
    
    private void getOverflowMenu() {

	     try {
	        ViewConfiguration config = ViewConfiguration.get(this);
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
    
    
    /** METHODS FOR LOBBY FRAGMENT **/
    
    /* open new game page */
    public void newGame(View view){
    	Intent i = new Intent(FragmentContainer.this, NewGame.class);
		startActivity(i);
    }
    
    /** METHODS FOR STATS FRAGMENT **/
    
    /* open my stats page */
    public void myStats(View view){
    	Intent i = new Intent(FragmentContainer.this, ProfileView.class);
		i.putExtra(SearchPlayer.IDSEARCH, userLobby.playerID);
		startActivity(i);
		
	}
    /* open my friends page */
    public void myFriends(View view){
		Intent i = new Intent(FragmentContainer.this, MyFriends.class);
		startActivity(i);
	}
    
    /* open ranking page */
    public void ranking(View view){
    	Intent i = new Intent(FragmentContainer.this, Ranking.class);
    	startActivity(i);
    
    }
    
    
    /** METHODS FOR OPTION FRAGMENT **/
    public void account(View view){
    	Intent i = new Intent(FragmentContainer.this, Account.class);
    	i.putExtra("userID", userLobby.playerID);
    	startActivity(i);
    	finish();
    }
    
    public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 5;
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			switch (position) {
			case 0:
				
				return new ListAllFragment();
				
				
			case 1:
				

				return new ActiveFragment();

			case 2:
				
				return new ActiveOpponentFragment();
				
			case 3:
				
				return new FinishedFragment();
			case 4:
				
				return new InvitesFragment();
			default:
				return null;
			}
		}
	}

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
    
}
    
    
    
    
    