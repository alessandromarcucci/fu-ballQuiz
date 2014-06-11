package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.security.auth.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.splashscreen.DatabaseHelper.User;
import com.facebook.HttpMethod;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.model.GraphObject;
import com.facebook.widget.WebDialog;
import com.facebook.Request;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.widget.WebDialog.OnCompleteListener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.ClipData;
import android.content.Context;
import android.content.ClipData.Item;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyFriends extends Activity {
	DatabaseHelper dbHelper = new DatabaseHelper();
	static List<User> friends = new ArrayList<User>();
	static List<User> FBListID = new ArrayList<User>();
	static List<User> sortedByName = null;
	static List<Integer> fbFriends = new ArrayList<Integer>();
	GridAdapter adapter = null;
	ListView grid;
	static String[] fbIDs = null;
	InputStream inputStream = null;
	Typeface roboto_black= null;
	Typeface roboto_medium_italic = null;
	Typeface roboto_black_italic = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfriends_layout);
		Intent intent = getIntent();
		
		roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/* set the facebook invitation button */
		Button fbInvite = (Button) findViewById(R.id.sendRequestButton);
		if(FragmentContainer.userLobby.FbConnect.equals("0")){
			fbInvite.setVisibility(View.GONE);
		}
		/* set listener for the facebook */
		fbInvite.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendRequestDialog();
			}
		});
		fbInvite.setTypeface(roboto_black);
		
		
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
        
        /***************************************************************************************************/
		 
		final List<User> sortedByName = new ArrayList<User>();
		/** create grid for friends (platform) **/
		friends = dbHelper.getFriends(FragmentContainer.userLobby.playerID);
		for (int i=0; i <friends.size(); i++){
			//items.add(new Item(Integer.toString(friends.get(i))));
			Log.w("userfriends id", friends.get(i).playerName);
		}
		if (friends != null){
			sortedByName.addAll(friends);
		}
		Collections.sort(sortedByName, new Comparator<User>() {
	        public int compare(User p1, User p2) {
	           return p1.playerName.toString().compareTo(p2.playerName.toString());
	        }
		});
	     
	     
	     /** create grid for friends (facebook) **/
	    
		//if (!(Lobby.userLobby.FbConnect.equals("0"))){
		 Session session = Session.getActiveSession();
         String fqlQuery = "SELECT uid FROM user WHERE is_app_user  AND uid IN (SELECT uid2 FROM friend WHERE uid1 = me())";

         Bundle params = new Bundle();
         params.putString("q", fqlQuery);
         //Session session = Session.getActiveSession();
         Request request = new Request(session, "/fql", params, HttpMethod.GET, new com.facebook.Request.Callback() {
                    public void onCompleted(Response response) {
                    	if (!(FragmentContainer.userLobby.FbConnect.equals("0"))){   
                    	//Create the GraphObject from the response
                       GraphObject responseGraphObject = response.getGraphObject();

                       //Create the JSON object
                       JSONArray json;
					try {
						json = responseGraphObject.getInnerJSONObject().getJSONArray("data");
						 Log.w("friendsFB", json.toString());
						 fbIDs = new String[json.length()];
						 for(int i=0;i<json.length();i++){
		                       JSONObject json_data = json.getJSONObject(i);
		                       fbIDs[i] = json_data.getString("uid");
		                       Log.w("json object", json_data.getString("uid"));
		                       
				    	 }
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					int idFromFb = 0;
					for (int i = 0; i< fbIDs.length; i++){
						idFromFb = dbHelper.getIDFromFacebook(fbIDs[i]);
						Log.w("id from facebook retrieved", Integer.toString(idFromFb));
						if(idFromFb != 0){
							fbFriends.add(idFromFb);
						}
						
					}
                    for(int i = 0; i < fbFriends.size();i++){
                    	Log.w("fbFriends in the list", Integer.toString(fbFriends.get(i)));
                    }
                    for(int i = 0; i < fbFriends.size();i++){
                    	Log.w("fbFriends in the list 2 - il ritorno", Integer.toString(fbFriends.get(i)));
                    }
            		String fbIdtoString = "";
            		for(int i=0; i < fbFriends.size(); i++){
            			if(i == fbFriends.size() -1){
            				fbIdtoString += fbFriends.get(i);
            			}else{
            				fbIdtoString += fbFriends.get(i) + ",";
            			}
            		}
            		
            		
            		Log.w("fbFriends", fbIdtoString);
            		FBListID = dbHelper.getFBFriends(fbIdtoString);
            		for(int i = 0; i < FBListID.size(); i++){
            			Log.w("e daje annamo", Integer.toString(FBListID.get(i).playerID));
            			Log.w("e daje annamo", FBListID.get(i).playerName);
            		}
            		
            		
            		sortedByName.addAll(FBListID);
            		}
            		Collections.sort(sortedByName, new Comparator<User>() {
            		        public int compare(User p1, User p2) {
            		           return p1.playerName.toLowerCase().toString().compareTo(p2.playerName.toLowerCase().toString());
            		        }
            		});
            		/* delete double friends */
            		for(int i = 0; i < sortedByName.size()-1; i++){
            			if(sortedByName.get(i).playerID == sortedByName.get(i+1).playerID){
            				sortedByName.remove(i+1);
            			}
            		}
            		for(int i = 0; i < sortedByName.size(); i++){
            			Log.w("sortedByName", sortedByName.get(i).playerName);
            		}
            		
            		adapter = new GridAdapter(MyFriends.this, sortedByName);
            	    grid = (ListView) findViewById(R.id.listMyFriends);
            	    grid.setAdapter(adapter);
            	     
            	    grid.setOnItemClickListener(new OnItemClickListener() {
            	       @Override
            	       public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
            	       		   ClipData.Item listItem = (Item) grid.getItemAtPosition(position);
            	      		   int id = Integer.parseInt(listItem.getText().toString());
            	      		   Log.w("waka", Integer.toString(id));
            	      		   Intent i = new Intent(MyFriends.this, ProfileView.class);
            	      		   i.putExtra(SearchPlayer.IDSEARCH, id);
            	      		   startActivity(i);
            	      	   } 
            	      	});                    
                    	
                    }
                           });
         
         Request.executeBatchAsync(request);
	     
		
		
		
	//}  
	
	
         
	
	
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
	
	private void sendRequestDialog() {
		   Bundle params = new Bundle();
		   params.putString("message", "Learn how to make your Android apps social");

		   WebDialog requestsDialog = (new WebDialog.RequestsDialogBuilder(MyFriends.this, Session.getActiveSession(), params)).setOnCompleteListener(new OnCompleteListener() {

		               @Override
		               public void onComplete(Bundle values,
		                   FacebookException error) {
		                   if (error != null) {
		                       if (error instanceof FacebookOperationCanceledException) {
		                           Toast.makeText(MyFriends.this.getApplicationContext(), 
		                               "Request cancelled", 
		                               Toast.LENGTH_SHORT).show();
		                       } else {
		                           Toast.makeText(MyFriends.this.getApplicationContext(), 
		                               "Network Error", 
		                               Toast.LENGTH_SHORT).show();
		                       }
		                   } else {
		                       final String requestId = values.getString("request");
		                       if (requestId != null) {
		                           Toast.makeText(MyFriends.this.getApplicationContext(), 
		                               "Request sent",  
		                               Toast.LENGTH_SHORT).show();
		                       } else {
		                           Toast.makeText(MyFriends.this.getApplicationContext(), 
		                               "Request cancelled", 
		                               Toast.LENGTH_SHORT).show();
		                       }
		                   }   
		               }

		           })
		           .build();
		   requestsDialog.show();
		}
	
	public void onBackPressed(){
		Intent i = new Intent(MyFriends.this, NewGame.class);
		
		startActivity(i);
		finish();
	
	}

	

}
