package com.example.splashscreen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;


public class SplashScreen extends Activity {
	static Thread lTimer = null;
	static int id = 0;
	public static final String APP_PREFS = "footballQuizPrefs";
	static int progress;
	ProgressBar splashbar = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Context context = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.welcome_screen);
	    Intent intent = getIntent();
	    SharedPreferences settings = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
	    
	    /* get the fonts from the asset */
		Typeface roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		Typeface roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		Typeface roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
	    
		
		/* set splash message **/
		String[] array = getResources().getStringArray(R.array.splashmessages);
		int randomNum = (0 + (int)(Math.random()*5)); 
	    TextView splash_message = (TextView) findViewById(R.id.splash_message);
	    splash_message.setText(array[randomNum]);
	    splash_message.setTypeface(roboto_medium_italic);
	    /** set progress bar **/
	    splashbar = (ProgressBar) findViewById(R.id.splash_bar);
        splashbar.setMax(100);
        splashbar.setProgress(0);
        progress = 0; 
	    /** activate this to close the login session **/
	     //id = settings.getInt("user_id", 0);
	    /********************************************/
	    Log.w("SplashScreen", Integer.toString(id));
	    if(id == 0){
	   	        lTimer = new Thread() {

		        public void run() {

		            try {
		                int lTimer1 = 0;
		                while (lTimer1 < 3000) {
		                    sleep(100);
		                    lTimer1 = lTimer1 + 100;
		                    progress += 4;
		                    splashbar.setProgress(progress);
		                }
		                
		                
		            } catch (InterruptedException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }

		            finally {
		            	Intent i = new Intent(SplashScreen.this, Login.class);
		                startActivity(i);
		                
		            	finish();
		            }
		        }
		    };
		    lTimer.start();
	    }else {
	   	        lTimer = new Thread() {

		        public void run() {

		            try {
		                int lTimer1 = 0;
		                while (lTimer1 < 400) {
		                    sleep(100);
		                    lTimer1 = lTimer1 + 100;
		                    progress += 25;
		                    splashbar.setProgress(progress);
		                }
		                
		            } catch (InterruptedException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }

		            finally {
		            	Log.w("Splashscreen2", Integer.toString(id));
		                Intent i = new Intent(SplashScreen.this, FragmentContainer.class);
		                i.putExtra("userID", id);
		                startActivity(i);
		                finish();
		            }
		        }
		    };
		    lTimer.start();
		    }
	    	
	}		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
