package com.example.splashscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Context context = this;
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.welcome_screen);
	    SharedPreferences settings = this.getSharedPreferences("appInfo", 0);
	    boolean firstTime = settings.getBoolean("first_time", true);
	 
	    if (firstTime) {
	        SharedPreferences.Editor editor = settings.edit();
	        editor.putBoolean("first_time", false);
	        editor.commit();
	        Thread lTimer = new Thread() {

		        public void run() {

		            try {
		                int lTimer1 = 0;
		                while (lTimer1 < 3000) {
		                    sleep(100);
		                    lTimer1 = lTimer1 + 100;
		                }
		                
		                Intent i = new Intent(MainActivity.this, Login.class);
		                startActivity(i);
		                
		            } catch (InterruptedException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }

		            finally {
		                finish();
		            }
		        }
		    };
		    lTimer.start();
	    }else{
	    	Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
            finish();
	    }
	    
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
