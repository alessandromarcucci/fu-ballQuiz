package com.example.splashscreen;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.splashscreen.DatabaseHelper.User;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class OverviewFriendsFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	static int id;
	User user = null;
	static String[] fbIDs = null;
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		Bundle args = getArguments();
		id = args.getInt("ID", 0);
		
		
		
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		user = dbHelper.getUser(id);
		
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/* set quickview text */
		TextView quickview_title = (TextView) getActivity().findViewById(R.id.quickviewFriends);
		quickview_title.setText(user.playerName + "'s Quick View");
		quickview_title.setTypeface(roboto_medium_italic);
		
		/* set user image */
		ImageView imageuser = (ImageView) getActivity().findViewById(R.id.accountfriend_image);
		Bitmap bitmap = null;
        if(!(user.FbConnect.equals("0"))){
			String url = String.format(user.playerImage);
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
			
			
			
		}else{
			if(user.playerImage.contains("default")){
				
				bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
        
        imageuser.setImageBitmap(bitmap);
		
		/* set user club */
		TextView userclub = (TextView) getActivity().findViewById(R.id.player_club);
		userclub.setText(user.club);
		userclub.setTypeface(roboto_medium_italic);
		
		/* set user name */
		TextView userName = (TextView) getActivity().findViewById(R.id.player_name);
		userName.setText(user.playerName);
		userName.setTypeface(roboto_black_italic);
		
		/* set fonts */
		TextView currentLevel = (TextView) getActivity().findViewById(R.id.currentLevel);
		currentLevel.setTypeface(roboto_black);
		
		TextView nextLevel = (TextView) getActivity().findViewById(R.id.nextLevel);
		nextLevel.setTypeface(roboto_black);
		
		/* set values for the exp-bar */
        ProgressBar expbar = (ProgressBar) getActivity().findViewById(R.id.progress_bar);
        long actualLevel = (long) (Math.pow(user.playerPoints, (1/1.7)));
        long next_level = actualLevel + 1;
		int min = (int) (Math.pow(actualLevel, 1.7));
		Log.w("min", Integer.toString(min));
        int max = (int) ((Math.pow(next_level, 1.7) - min));
        Log.w("max", Integer.toString(max));
        expbar.setMax(max);
        int progress = user.playerPoints - min; 
        Log.w("progress", Integer.toString(progress));
        expbar.setProgress(progress);
        
        currentLevel.setText(Long.toString(actualLevel));
        
        nextLevel.setText(Long.toString(next_level));
		
		TextView rankingText = (TextView) getActivity().findViewById(R.id.rankingText);
		rankingText.setTypeface(roboto_black_italic);
		
		int ranking = dbHelper.getMyRank(id);
		TextView quick_ranking = (TextView) getActivity().findViewById(R.id.quick_ranking);
		quick_ranking.setText(Integer.toString(ranking));
		quick_ranking.setTypeface(roboto_black);
		
		TextView statsText = (TextView) getActivity().findViewById(R.id.statsText);
		statsText.setTypeface(roboto_black_italic);
		
		int total = user.lost + user.draw + user.won;
		TextView quick_stats = (TextView) getActivity().findViewById(R.id.quick_stats);
		quick_stats.setText(Integer.toString(user.won) + " / " + total);
		quick_stats.setTypeface(roboto_black);
		
		Button addRemoveFriends = (Button) getActivity().findViewById(R.id.addRemoveFriends);
		addRemoveFriends.setTypeface(roboto_black);
		
		
		
		
		/* set add/remove friend button */
		
		if(!(FragmentContainer.userLobby.FbConnect.equals("0"))){	
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
		
                    	}
                    	
                    	int fbFriend = 0;
                        for(int i = 0; i < fbIDs.length; i++){
                       	 if(fbIDs[i].equals(user.FbConnect)){
                       		 fbFriend = 1;
                       	 }
                       	 
                        }
                        Button addRemove = (Button) getActivity().findViewById(R.id.addRemoveFriends);
                        if(fbFriend == 1){
                       	addRemove.setBackgroundResource(R.drawable.invite_fb_button);
                			addRemove.setText("FB CONNECTED");
                        }else{
                        
                       	 if(!(dbHelper.ifFriends(id, FragmentContainer.userLobby.playerID))){
                       		 addRemove.setBackgroundResource(R.drawable.btn_quickprofile_addfriends);
                       		 addRemove.setText("add new friend");
                       	 }else{
                       		 addRemove.setBackgroundResource(R.drawable.btn_quickview_removefriend);
                       		 addRemove.setText("remove as friend");
                       	 }
                        }
                    }
            });
         Request.executeBatchAsync(request);
		}else{
			Button addRemove = (Button) getActivity().findViewById(R.id.addRemoveFriends);
			if(!(dbHelper.ifFriends(id, FragmentContainer.userLobby.playerID))){
          		 addRemove.setBackgroundResource(R.drawable.btn_quickprofile_addfriends);
          		 addRemove.setText("add new friend");
          	 }else{
          		 addRemove.setBackgroundResource(R.drawable.btn_quickview_removefriend);
          		 addRemove.setText("remove as friend");
          	 }
		}
         
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.overviewfriends_layout, container, false);
		
		return view;
	}
	
	
	
	
		
		

}
