package com.example.splashscreen;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.splashscreen.DatabaseHelper.User;

public class ProfileView extends Activity {
	DatabaseHelper dbHelper = new DatabaseHelper();
	static User selectedUser = null;
	static User lobbyUser = FragmentContainer.userLobby;
	private static int[] COLORS_1GRAF = new int[] { Color.GREEN, Color.BLUE,Color.MAGENTA }; 
	private static String[] NAME_LIST_1GRAF = new String[] {"Won","Draw","Defeat"};
	private CategorySeries mSeries_1GRAF = new CategorySeries("");
	private DefaultRenderer mRenderer_1GRAF = new DefaultRenderer();
	private GraphicalView mChartView_1GRAF = null;
	
	private static int[] COLORS_2GRAF = new int[] { Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN };
	private static String[] NAME_LIST_2GRAF = new String[] {"Portogallo", "Argentina", "Messico", "Italia"};
	private CategorySeries mSeries_2GRAF = new CategorySeries("");
	private DefaultRenderer mRenderer_2GRAF = new DefaultRenderer();
	private GraphicalView mChartView_2GRAF = null;
	
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);
        Intent intent = getIntent();
        int id = intent.getIntExtra(SearchPlayer.IDSEARCH, -1);
        selectedUser = dbHelper.getUser(id);
        /** if users are already friends the add friend button will be deactivated*/
        Button addFriend = (Button) findViewById(R.id.search_addfriend);
		boolean isFriend = dbHelper.ifFriends(lobbyUser.playerID, selectedUser.playerID);
		if ((isFriend)){
			
			addFriend.setText("Unfriend");
			
		}
        
        
        //creation of user tile (image + playerPoints + playerName)
		
		ImageView userimage = (ImageView) findViewById(R.id.search_searchuser);
		TextView username = (TextView) findViewById(R.id.search_nameuser);
		TextView playerPoints = (TextView) findViewById(R.id.search_pointsuser);
		username.setText(selectedUser.playerName);
		playerPoints.setText("level: " + Integer.toString(selectedUser.playerPoints));
		if(selectedUser.playerImage.contains("default")){
			userimage.setBackgroundResource(R.drawable.player_image);
		}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
		}
		
		//1.graphic
		double[] VALUES_1GRAF = new double[] { selectedUser.won, selectedUser.draw, selectedUser.lost };
		mRenderer_1GRAF.setPanEnabled(false);
		mRenderer_1GRAF.setShowLegend(false);
		for (int i = 0; i < VALUES_1GRAF.length; i++) {  
			mSeries_1GRAF.add(NAME_LIST_1GRAF[i] + " " + VALUES_1GRAF[i], VALUES_1GRAF[i]);  
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();  
			renderer.setColor(COLORS_1GRAF[(mSeries_1GRAF.getItemCount() - 1) % COLORS_1GRAF.length]);  
			mRenderer_1GRAF.addSeriesRenderer(renderer);  
		}  
		if (mChartView_1GRAF != null) {  
			mChartView_1GRAF.repaint();  
		}  
		if (mChartView_1GRAF == null) {  
			LinearLayout layout_1graf = (LinearLayout) findViewById(R.id.firstgraph);  
			mChartView_1GRAF = ChartFactory.getPieChartView(this, mSeries_1GRAF, mRenderer_1GRAF);  
			mRenderer_1GRAF.setClickEnabled(true);  
			mRenderer_1GRAF.setSelectableBuffer(10);  
	  		mChartView_1GRAF.setOnLongClickListener(new View.OnLongClickListener() {  
	  			public boolean onLongClick(View v) {  
	  				return true;
	  			}  
	  		});  
	  		layout_1graf.addView(mChartView_1GRAF, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
		}  
		else {  
			mChartView_1GRAF.repaint();  
		} 
		
		//2. graphic
		double[] VALUES_2GRAF = new double[] { 100, 11, 24, 390 };
		mRenderer_2GRAF.setPanEnabled(false);
		mRenderer_2GRAF.setShowLegend(false);
		for (int i = 0; i < VALUES_2GRAF.length; i++) {  
			mSeries_2GRAF.add(NAME_LIST_2GRAF[i] + " " + VALUES_2GRAF[i], VALUES_2GRAF[i]);  
			SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();  
			renderer.setColor(COLORS_2GRAF[(mSeries_2GRAF.getItemCount() - 1) % COLORS_2GRAF.length]);  
			mRenderer_2GRAF.addSeriesRenderer(renderer);  
		}  
		if (mChartView_2GRAF != null) {  
			mChartView_2GRAF.repaint();  
		}  
		if (mChartView_2GRAF == null) {  
			LinearLayout layout_2graf = (LinearLayout) findViewById(R.id.secondgraph);  
			mChartView_2GRAF = ChartFactory.getPieChartView(this, mSeries_2GRAF, mRenderer_2GRAF);  
			mRenderer_2GRAF.setClickEnabled(true);  
			mRenderer_2GRAF.setSelectableBuffer(10);  
	  		mChartView_2GRAF.setOnLongClickListener(new View.OnLongClickListener() {  
	  			public boolean onLongClick(View v) {  
	  				  
	  				return true;
	  			}  
	  		});  
	  		layout_2graf.addView(mChartView_2GRAF, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
		}  
		else {  
			mChartView_2GRAF.repaint();  
		} 
	}
	
	
	public void addFriend(View view){
		Button addFriend = (Button) findViewById(R.id.search_addfriend);
		int id_send = lobbyUser.playerID;
		int id_rec = selectedUser.playerID;
		if(addFriend.getText().equals("Add Friend")){
			Log.w("sender", Integer.toString(id_send));
			Log.w("receiver", Integer.toString(id_rec));
			dbHelper.addFriend(id_send, id_rec);
			
			addFriend.setText("Unfriend");
		}
		else{
			dbHelper.deleteFriend(id_send, id_rec);
			addFriend.setText("Add Friend");
		}
		
	}
	
	public void play(View view){
		int id_send = lobbyUser.playerID;
		int id_rec = selectedUser.playerID;
		String category = "";
		if (!(dbHelper.alreadyAMatch(id_send, id_rec))){
			dbHelper.setUsersToMatch(id_send, id_rec, category);
			Toast.makeText(this, "new game created VS. " + Integer.toString(id_rec), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "already a match opened with " + Integer.toString(id_rec), Toast.LENGTH_SHORT).show();
		}
	}
	
	  
	
}
