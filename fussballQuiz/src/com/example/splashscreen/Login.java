package com.example.splashscreen;



import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;

public class Login extends FragmentActivity {
	private MyAdapter mAdapter;
	private ViewPager mPager;
	DatabaseHelper dbHelper = new DatabaseHelper();
	SharedPreferences settings = null;
	SharedPreferences.Editor editor = null;
	public final static String EXTRA_MESSAGE = "com.example.splashscreen.MESSAGE";
	public final static String APP_PREFS ="footballQuizPrefs";
	static Button filter_new_player = null;
	static Button filter_returning_player = null;
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup_layout);
		mAdapter = new MyAdapter(getSupportFragmentManager());

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/** SET ACTIONBAR COMPONENTS **/
        ActionBar actionbar = getActionBar();
        actionbar.setIcon(null);
        actionbar.setDisplayShowTitleEnabled(false);
        //actionbar.setDisplayShowHomeEnabled(false);
        
        actionbar.setDisplayUseLogoEnabled(false);
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
     // Create a tab listener that is called when the user changes tabs.
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

        // Add 3 tabs, specifying the tab's text and TabListener
        
         actionbar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.FilterBar)));
         actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM  | ActionBar.DISPLAY_SHOW_HOME);
         
         for (int i = 0; i < 2; i++) {
             // Create a tab with text corresponding to the page title defined by
             // the adapter. Also specify this Activity object, which implements
             // the TabListener interface, as the callback (listener) for when
             // this tab is selected.

             LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.custom_tab, null);

             

             TextView title = (TextView) view.findViewById(R.id.title);
             if(i == 0){
            	 title.setText("New Player");
            	 title.setTypeface(roboto_medium_italic);
             }else{
            	 title.setText("Returning Player");
            	 title.setTypeface(roboto_medium_italic);
             }
             actionbar.addTab(actionbar.newTab().setTabListener(tabListener).setCustomView(view));
             //actionbar.addTab(actionbar.newTab().setCustomView(view));
         }
         //actionbar.setDisplayShowCustomEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setHomeButtonEnabled(true);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.actionbar_layout, null);
        
        /* set the logo */
        ImageView logo = (ImageView) v.findViewById(R.id.logo_login);
        logo.setBackgroundResource(R.drawable.logo_splashscreen);
        actionbar.setCustomView(v);
        
        
        
		
		/*set font for filter buttons*/
		
		
		mPager.setOnPageChangeListener(
	            new ViewPager.SimpleOnPageChangeListener() {
	                @Override
	                public void onPageSelected(int position) {
	                    // When swiping between pages, select the
	                    // corresponding t
	                    getActionBar().setSelectedNavigationItem(position);
	                    
	                }
	            });
        
	}

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new RegisterFragment();
			case 1:
				return new LoginFragment();
			

			default:
				return null;
			}
		}
	}
	
	/** FUNCTIONS FOR LOGIN FRAGMENT **/
	
	
	public void facebookLogin(View view){
		try {

			   PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);

			   for (Signature signature : info.signatures) 
			   {
			    MessageDigest md = MessageDigest.getInstance("SHA");
			    md.update(signature.toByteArray());
			    Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			   }

			  } catch (NameNotFoundException e) {
			   Log.e("name not found", e.toString());
			  } catch (NoSuchAlgorithmException e) {
			   Log.e("no such an algorithm", e.toString());
			  }
		
		// start Facebook Login
	    Session.openActiveSession(this, true, new Session.StatusCallback() {
	    	
	      // callback when session changes state
	      @SuppressWarnings("deprecation")
	      @Override
	      public void call(Session session, SessionState state, Exception exception) {
	        
	    	  if (session != null && session.isOpened()  ) {
	    	         
	        	
	          // make request to the /me API
	          Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
	        	  
	            // callback after Graph API response with user object
	            @Override
	            public void onCompleted(GraphUser user, Response response) {
	            	Log.w("fb username", user.getId());
	            	if (user != null) {
	                //Log.w("fb username", user.getUsername());
	                //welcome.setText("Hello " + user.getId() + "!");
	                int idPlayer = 0;
	                String fbID = user.getId();
	                
	                idPlayer = dbHelper.getIDFromFacebook(fbID);
	                Log.w("Login ID",Integer.toString(idPlayer));
	                if (idPlayer != 0){
	                	dbHelper.setIsLoggedIn(idPlayer);
	      				//editor.putInt("user_id", idPlayer);
	      				//editor.commit();
	                	Intent i = new Intent(Login.this, FragmentContainer.class);
	      				i.putExtra("ID", idPlayer);
	      				startActivity(i);
	      				finish();
	                }else{
	                	int id = 0;
	                	if(user.getUsername() == null){
	                		dbHelper.createNewFbUser(user.getId(), user.getId());
	                		id = dbHelper.getIDbyName(user.getId());
	                	}
	                	else {dbHelper.createNewFbUser(user.getUsername(), user.getId());
	                	
	                	id = dbHelper.getIDbyName(user.getUsername());
	                	}
	                	//editor.putInt("user_id", idPlayer);
	      				//editor.commit();
	                	Intent i = new Intent(Login.this, FragmentContainer.class);
						i.putExtra("ID", id);
						startActivity(i);
						finish();
	                }
	              }
	            }
	          });
	        }
	      }
	    });

	}
	
	@Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	  }
	
	
	/** FUNCTIONS FOR REGISTER FRAGMENT **/
	public void forgotLogin(View view){
		Intent i = new Intent(Login.this, ForgotLogin.class);
		startActivity(i);
		
	}
	
	
	
}