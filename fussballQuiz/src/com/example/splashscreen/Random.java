package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.splashscreen.DatabaseHelper.User;

public class Random extends Activity{
	public static ArrayList<User> challengers = null;
	public static ArrayList<User> challengersAvailable = new ArrayList<User>(); 
	DatabaseHelper dbHelper = new DatabaseHelper();
	String cat = null;
	InputStream inputStream = null;
	static View lasttoolbar = null;
	static String title = null;
	static String[] categories = null;
	Typeface roboto_black = null;
	Typeface roboto_black_italic= null;
	Typeface roboto_medium_italic = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_layout);
		//FragmentContainer.myTimer.cancel();
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
        
        /** set drop-down list **/
        final ListView list = (ListView)findViewById(R.id.categoriesList);

        // Creating the list adapter and populating the list
        ArrayAdapter<String> listAdapter = new CustomListAdapter(this, R.layout.list_item);
        /* fill the titles */
        categories = dbHelper.getCategories();
        listAdapter.add("Alle Kategorien");
        for(int i = 0; i < categories.length; i++){
        	listAdapter.add(categories[i]);
        }
        listAdapter.add("Fan Battle");
        
        list.setAdapter(listAdapter);

        // Creating an item click listener, to open/close our toolbar for each item
        
        
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

            	for (int i = 0; i < 7; i++){ 
                	parent.getChildAt(i).findViewById(R.id.toolbar).setVisibility(View.GONE);
                }
            	
            	
                View toolbar = view.findViewById(R.id.toolbar);
                TextView title_categories = (TextView) view.findViewById(R.id.title_categories);
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
                convertView = getLayoutInflater().inflate(R.layout.categorieslist_item, null);
            }
            
            

            ((TextView)convertView.findViewById(R.id.title_categories)).setText(getItem(position));
            ((TextView)convertView.findViewById(R.id.title_categories)).setTypeface(roboto_black_italic);
            // Resets the toolbar to be closed
            View toolbar = convertView.findViewById(R.id.toolbar);
            Button easy_button = (Button) toolbar.findViewById(R.id.easyButton);
            easy_button.setTypeface(roboto_black);
            Button normal_button = (Button) toolbar.findViewById(R.id.normalButton);
            normal_button.setTypeface(roboto_black);
            Button hard_button = (Button) toolbar.findViewById(R.id.hardButton);
            hard_button.setTypeface(roboto_black);
            
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
	
	public void easyButton(View view){
		if(title.equals("Alle Kategorien")){
			cat = "Alle Kategorien";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals(categories[0])){
			cat = categories[0];
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals(categories[1])){
			cat = categories[1];
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals(categories[2])){
			cat = categories[2];
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals(categories[3])){
			cat = categories[3];
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals(categories[4])){
			cat = categories[4];
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals(categories[5])){
			cat = categories[5];
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Miscellaneous")){
			cat = "Euro/World Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		
		
		Intent i = new Intent(Random.this, FragmentContainer.class);
		i.putExtra("ID", FragmentContainer.userLobby.playerID);
		startActivity(i);
	}
	
	public void normalButton(View view){
		if(title.equals("All Categories")){
			cat = "AllCategories";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Bundesliga")){
			cat = "Bundesliga";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Foreign Leagues")){
			cat = "Bundesliga";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("European Cups")){
			cat = "European Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("European Cups")){
			cat = "European Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("National Teams")){
			cat = "National Teams";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Euro/World Cups")){
			cat = "Euro/World Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Miscellaneous")){
			cat = "Euro/World Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 2);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		
		
		Intent i = new Intent(Random.this, FragmentContainer.class);
		i.putExtra("ID", FragmentContainer.userLobby.playerID);
		startActivity(i);
	}
	
	public void hardButton(View view){
		if(title.equals("All Categories")){
			cat = "AllCategories";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Bundesliga")){
			cat = "Bundesliga";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Foreign Leagues")){
			cat = "Bundesliga";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("European Cups")){
			cat = "European Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("European Cups")){
			cat = "European Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("National Teams")){
			cat = "National Teams";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Euro/World Cups")){
			cat = "Euro/World Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 3);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		if(title.equals("Miscellaneous")){
			cat = "Euro/World Cups";
			/*challengers = dbHelper.getFriendsByDifficulty(FragmentContainer.userLobby.playerPoints, 0);
			Log.w("myPlayersPoint", Integer.toString(FragmentContainer.userLobby.playerPoints));
			Log.w("challengers size" , Integer.toString(challengers.size()));
			challengersAvailable.clear();
			for (int i = 0; i < challengers.size(); i++){
				
				
				if(!(dbHelper.alreadyAMatch(FragmentContainer.userLobby.playerID, challengers.get(i).playerID))){
					challengersAvailable.add(challengers.get(i));
				}
			}
			/* if challenger.size() is != the match will be against a real user,
			 * otherwise a bot will be loaded
			 
			if(!(challengersAvailable.size() == 0)){
				/** START A MATCH WITH A NORMAL USER *
				Log.w("challengersAvailableSize", Integer.toString(challengersAvailable.size()));
				int random = (int)(Math.random() * ((challengersAvailable.size()) + 1));
				Log.w("selected user random", challengersAvailable.get(random).playerName);
				Log.w("category", cat);
					dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, challengersAvailable.get(random).playerID, cat);
			}else{*/
				int botID = dbHelper.selectBotByDifficulty(FragmentContainer.userLobby.playerPoints, 1);
				dbHelper.setUsersToMatch(FragmentContainer.userLobby.playerID, botID, cat);
				
			//}
		}
		
		
		Intent i = new Intent(Random.this, FragmentContainer.class);
		i.putExtra("ID", FragmentContainer.userLobby.playerID);
		startActivity(i);
		finish();
	}
	
	public void onBackPressed(){
		Intent i = new Intent(Random.this, NewGame.class);
		
		startActivity(i);
		finish();
	
	}
}
