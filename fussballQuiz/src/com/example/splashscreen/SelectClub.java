package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.preference.PreferenceManager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.splashscreen.DatabaseHelper.User;

public class SelectClub extends Activity{
	public static ArrayList<User> challengers = null;
	public static ArrayList<User> challengersAvailable = new ArrayList<User>(); 
	DatabaseHelper dbHelper = new DatabaseHelper();
	SharedPreferences settings = null;
	SharedPreferences.Editor editor = null;
	public final static String EXTRA_MESSAGE = "com.example.splashscreen.MESSAGE";
	public final static String APP_PREFS ="footballQuizPrefs";
	InputStream inputStream = null;
	static View lasttoolbar = null;
	static String title = null;
	static String username = null;
	static String password = null;
	static String email = null;
	static String club = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_layout);
		//FragmentContainer.myTimer.cancel();
		settings = getSharedPreferences(APP_PREFS, MODE_PRIVATE);
		editor = settings.edit();
		
		Intent intent = getIntent();
		username = intent.getExtras().getString("username");
		email = intent.getExtras().getString("email");
		password = intent.getExtras().getString("password");
		Log.w("username", username);
		Log.w("email", email);
		Log.w("password", password);
		
		
        
        /** set drop-down list **/
        final ListView list = (ListView)findViewById(R.id.categoriesList);

        // Creating the list adapter and populating the list
        ArrayAdapter<String> listAdapter = new CustomListAdapter(this, R.layout.list_item);
        /* fill the titles */
        listAdapter.add("FC Augsburg");
        listAdapter.add("Hertha BSC");
        listAdapter.add("Eintracht Braunschweig");
        listAdapter.add("SV Werder Brema");
        listAdapter.add("Borussia Dortmund");
        listAdapter.add("Eintracht Frankfurt");
        listAdapter.add("Hamburger SV");
        listAdapter.add("Hannover 96");
        listAdapter.add("TSG 1899 Hoffenheim");
        listAdapter.add("Bayer 04 Leverkusen");
        listAdapter.add("1. FSV Mainz 05");
        listAdapter.add("Borussia Mönchengladbach");
        listAdapter.add("FC Bayer München");
        listAdapter.add("1. FC Nürnberg");
        listAdapter.add("FC Schalke 04");
        listAdapter.add("VfB Stuttgart");
        listAdapter.add("Vfl Wolfsburg");
        
        list.setAdapter(listAdapter);

        // Creating an item click listener, to open/close our toolbar for each item
        
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

            	for (int i = 0; i < 7; i++){ 
                	parent.getChildAt(i).findViewById(R.id.toolbar).setVisibility(View.GONE);
                }
            	
            	
                View toolbar = view.findViewById(R.id.toolbar);
                TextView title_categories = (TextView) view.findViewById(R.id.title_club);
                title = title_categories.getText().toString();
                
                // Creating the expand animation for the item
                ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
                
                /** set adapter for "easy" button **/
                
                
        }
        });
        
        
		
	}
	
	class CustomListAdapter extends ArrayAdapter<String> {

        public CustomListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.selectclub_item, null);
            }

            ((TextView)convertView.findViewById(R.id.title_club)).setText(getItem(position));

            // Resets the toolbar to be closed
            View toolbar = convertView.findViewById(R.id.toolbar);
            ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
            toolbar.setVisibility(View.GONE);

            return convertView;
        }
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
	
	public void clubButton(View view){
		if(title.equals("FC Augsburg")){
			club = "FC Augsburg";
			
		}
		if(title.equals("Hertha BSC")){
			club = "Hertha BSC";
			
		}
		if(title.equals("Eintracht Braunschweig")){
			club = "Eintracht Braunschweig";
		}
		if(title.equals("SV Werder Brema")){
			club = "SV Werder Brema";
		}
		if(title.equals("Borussia Dortmund")){
			club = "Borussia Dortmund";
		}
		if(title.equals("Eintracht Frankfurt")){
			club = "Eintracht Frankfurt";
		}
		if(title.equals("Hamburger SV")){
			club = "Hamburger SV";
		}
		if(title.equals("Hamburger SV")){
			club = "Hamburger SV";
		}
		if(title.equals("Hannover 96")){
			club = "Hannover 96";
		}
		if(title.equals("TSG 1899 Hoffenheim")){
			club = "TSG 1899 Hoffenheim";
		}
		if(title.equals("Bayer 04 Leverkusen")){
			club = "Bayer 04 Leverkusen";
		}
		if(title.equals("1. FSV Mainz 05")){
			club = "1. FSV Mainz 05";
		}
		if(title.equals("Borussia Mönchengladbach")){
			club = "Borussia Mönchengladbach";
		}
		if(title.equals("FC Bayern München")){
			club = "FC Bayern München";
		}
		if(title.equals("1. FC Nürnberg")){
			club = "1. FC Nürnberg";
		}
		if(title.equals("FC Schalke 04")){
			club = "FC Schalke 04";
		}
		if(title.equals("VfB Stuttgart")){
			club = "VfB Stuttgart";
		}
		if(title.equals("VfL Wolfsburg")){
			club = "VfL Wolfsburg";
		}
		
		
		
		
	
	
		
		dbHelper.createNewUser(username,password,email,club);
			int id = dbHelper.getIDbyName(username);
			Log.w("id porco cazzo", Integer.toString(id));
			/** TODO set the value of the isLoggedIn field to 1 */
			// Storing login value as TRUE
		editor.putInt("user_id", id);
		editor.commit(); 
		Intent i = new Intent(SelectClub.this, FragmentContainer.class);
		i.putExtra("ID", id);
		startActivity(i);
		finish();
	}
	
	public void onBackPressed(){
		Intent i = new Intent(SelectClub.this, NewGame.class);
		
		startActivity(i);
		finish();
	
	}
}
