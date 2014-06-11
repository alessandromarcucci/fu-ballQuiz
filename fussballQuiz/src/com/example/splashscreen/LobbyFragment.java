package com.example.splashscreen;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.content.ClipData.Item;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.graphics.Typeface;

import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;
import com.example.splashscreen.Login.MyAdapter;
 
public class LobbyFragment extends Fragment  {
	private MyAdapter mAdapter;
	private ViewPager mPage;
	MyAdapterPending adapter = null;
	ArrayList<Item> pending = new ArrayList<Item>();
	static ListView listView = null;
	static Timer myTimer = new Timer();
	InputStream inputStream = null;
	DatabaseHelper dbHelper = new DatabaseHelper();
	static ArrayList<Match> arrayMatch = new ArrayList<Match>();
	static ArrayList<Match> arrayPending = new ArrayList<Match>();
	static User challenger = null;
	static ArrayList<User> challengerList = new ArrayList<User>();
	
	
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.mygames_layout, container, false);
        return rootView;
        
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
		
		

        /* set title */
        TextView allespiele = (TextView) getActivity().findViewById(R.id.alleSpiele_text);
        Typeface face=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
        allespiele.setTypeface(face);
        
        /* set New Game Button */
        Button newgame = (Button) getActivity().findViewById(R.id.startNewGame);
        Typeface face2=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
        newgame.setTypeface(face2);
        listView = (ListView) getActivity().findViewById(R.id.list_mygames);
        listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
		
        
        
        
        /* load the matches in the main list: All */
        arrayMatch = FragmentContainer.allMatches;
    	
        arrayPending.clear();
    	
    	//adapter = new MyAdapterPending(getActivity(), pending);
    	listView.setAdapter(adapter);
    	
    	Log.w("arrayMatch", Integer.toString(arrayMatch.size()));
    	adapter.clear();
    	arrayPending.clear();
    	challengerList.clear();
		pending.clear();
		for (int i = 0 ; i < arrayMatch.size() ; i++){
    		if (FragmentContainer.userLobby.playerID == arrayMatch.get((arrayMatch.size()-1)-i).IDsender_match){
    			challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDreceiver_match);
    		}else challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDsender_match);
    		challengerList.add(challenger);
    		pending.add(new Item(Integer.toString(arrayMatch.get((arrayMatch.size()-1)-i).IDmatch)));
			arrayPending.add(arrayMatch.get((arrayMatch.size()-1)-i));
		}
		//adapter = new MyAdapterPending(getActivity(), pending);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
    				
   		/** SET ADAPTER FOR FILTERS BAR **/
    				
   		/*ALLE SPIELE*/
   		Button alle_spiele = (Button) getActivity().findViewById(R.id.filter_all);
   		alle_spiele.setOnClickListener(new View.OnClickListener(){
   		   		@Override
   		   		public void onClick(View v) {
   		   			
   		   			/* set highlighting buttons */
   		   			v.setBackgroundResource(R.drawable.allespiele);
   		   			Button filter_active = (Button) getActivity().findViewById(R.id.filter_active);
   		   			filter_active.setBackgroundResource(R.drawable.activeoff);
   		   			Button filter_waiting = (Button) getActivity().findViewById(R.id.filter_waiting);
   		   			filter_waiting.setBackgroundResource(R.drawable.clessidraoff);
    				Button filter_finished = (Button) getActivity().findViewById(R.id.filter_finished);
    				filter_finished.setBackgroundResource(R.drawable.finishedoff);
    				Button filter_invite = (Button) getActivity().findViewById(R.id.filter_invite);
    				filter_invite.setBackgroundResource(R.drawable.invitationoff);
    				
    				/* set listView */
    				listView = (ListView) getActivity().findViewById(R.id.list_mygames);
    				/* set view for empty list */
    				listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
    				/*clear pending, arrayPending and challengerList list*/
    				pending.clear();
    				arrayPending.clear();
    				challengerList.clear();
    				for (int i = 0 ; i < arrayMatch.size() ; i++){
    					if (FragmentContainer.userLobby.playerID == arrayMatch.get((arrayMatch.size()-1)-i).IDsender_match){
    						challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDreceiver_match);
    					}else challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDsender_match);
    					/* set challenger */
    					challengerList.add(challenger);
    				    /*add an item to the pending list */
    					pending.add(new Item(Integer.toString(arrayMatch.get((arrayMatch.size()-1)-i).IDmatch)));
    				    /* add a match in the arrayPending list */
    				    arrayPending.add(arrayMatch.get((arrayMatch.size()-1)-i));
    				}
    				/*set adapter */
    			//	adapter = new MyAdapterPending(getActivity(), pending);
    				adapter.notifyDataSetChanged();
    				listView.setAdapter(adapter);
    			}
    	});
    	 				
    	/*ACTIVE*/
    	Button filter_active = (Button) getActivity().findViewById(R.id.filter_active);
    	filter_active.setOnClickListener(new View.OnClickListener() {
    			@Override
    			public void onClick(View v) {
    				/* set highlighting buttons */
   		   			v.setBackgroundResource(R.drawable.active);
    				
   		   			Button filter_all = (Button) getActivity().findViewById(R.id.filter_all);
   		   			filter_all.setBackgroundResource(R.drawable.allespieleoff);
   		   			Button filter_waiting = (Button) getActivity().findViewById(R.id.filter_waiting);
   		   			filter_waiting.setBackgroundResource(R.drawable.clessidraoff);
    				Button filter_finished = (Button) getActivity().findViewById(R.id.filter_finished);
    				filter_finished.setBackgroundResource(R.drawable.finishedoff);
    				Button filter_invite = (Button) getActivity().findViewById(R.id.filter_invite);
    				filter_invite.setBackgroundResource(R.drawable.invitationoff);
    				  						
    				int sender =0;
    				/* set view for empty listview */
    				listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
    				/*clear pending, arrayPending and challengerList list*/
    				pending.clear();
    	    		arrayPending.clear();
    	    		challengerList.clear();
    	    		
    	    		for (int i = 0 ; i < arrayMatch.size() ; i++){
    	    			if(arrayMatch.get((arrayMatch.size()-1)-i).state == 2){
    	    				int myTurn = 0;
    	    				if(arrayMatch.get((arrayMatch.size()-1)-i).IDsender_match == FragmentContainer.userLobby.playerID){
    	    					sender = 1;
    	    				}else sender = 0;
    	    				int[] answer_sender = fillAnswersSender(arrayMatch.get((arrayMatch.size()-1)-i));
    	    				int[] answer_receiver = fillAnswersReceiver(arrayMatch.get((arrayMatch.size()-1)-i));
    	    				int currentAnswer = (arrayMatch.get((arrayMatch.size()-1)-i).currentRoundNumber) -1;
    	    				if (sender == 1){
    	    					Log.w("currentAnswer", Integer.toString(currentAnswer));
    	    					if(answer_sender[(arrayMatch.get((arrayMatch.size()-1)-i).currentRoundNumber - 1)*3] == 0) myTurn = 1; 
    	    				}else if(sender == 0){
    	    					if(answer_receiver[(arrayMatch.get((arrayMatch.size()-1)-i).currentRoundNumber - 1)*3] == 0) myTurn = 1;
    	    				}
    	    						if (myTurn == 1){
    	    							pending.add(new Item(Integer.toString(arrayMatch.get((arrayMatch.size()-1)-i).IDmatch)));
    	    							arrayPending.add(arrayMatch.get((arrayMatch.size()-1)-i));
    	    							challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDreceiver_match);
    	    							challengerList.add(challenger);
    	    						}
    	    						
    	    					}
    	    				}
    	    				
    	    				for (int i = arrayPending.size() - 1; i == 0 ; i--){
    	    					Log.w("match", Integer.toString(arrayPending.get(i).IDmatch));
    	    				}
    	    				/*set adapter*/
    	    				//adapter = new MyAdapterPending(getActivity(), pending);
    	    				adapter.notifyDataSetChanged();
    	    				listView.refreshDrawableState();
    	    				listView.setAdapter(adapter);
    						
    				}
    		});
    				
    		/*WAITING MATCH*/
    		Button filter_waiting = (Button) getActivity().findViewById(R.id.filter_waiting);
    		filter_waiting.setOnClickListener(new View.OnClickListener(){
    			@Override
    			public void onClick(View v) {
    				/* set highlighting buttons */
    				v.setBackgroundResource(R.drawable.clessidraon);
	
					Button filter_all = (Button) getActivity().findViewById(R.id.filter_all);
   		   			filter_all.setBackgroundResource(R.drawable.allespieleoff);
   		   			Button filter_active = (Button) getActivity().findViewById(R.id.filter_active);
   		   			filter_active.setBackgroundResource(R.drawable.activeoff);
    				Button filter_finished = (Button) getActivity().findViewById(R.id.filter_finished);
    				filter_finished.setBackgroundResource(R.drawable.finishedoff);
    				Button filter_invite = (Button) getActivity().findViewById(R.id.filter_invite);
    				filter_invite.setBackgroundResource(R.drawable.invitationoff);
  						
					
   					int sender =0;
					/* set view for empty listview */
   					listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
   					/* clear pending, arrayPending and challengerList list */
   					pending.clear();
					arrayPending.clear();
    	    		challengerList.clear();
    	    		  		
    	    		for (int i = 0 ; i < arrayMatch.size() ; i++){
    	    			if(arrayMatch.get((arrayMatch.size()-1)-i).state == 2){
    	    				int myTurn = 0;
    	    				if(arrayMatch.get((arrayMatch.size()-1)-i).IDsender_match == FragmentContainer.userLobby.playerID){
    	    					sender = 1;
    	    				}else sender = 0;
    	    				int[] answer_sender = fillAnswersSender(arrayMatch.get((arrayMatch.size()-1)-i));
    	    				int[] answer_receiver = fillAnswersReceiver(arrayMatch.get((arrayMatch.size()-1)-i));
    	    				int currentAnswer = (arrayMatch.get((arrayMatch.size()-1)-i).currentRoundNumber) -1;
    	    				if (sender == 1){
    	    					Log.w("currentAnswer", Integer.toString(currentAnswer));
    	    					if(answer_sender[(arrayMatch.get((arrayMatch.size()-1)-i).currentRoundNumber - 1)*3] == 0) myTurn = 1; 
    	    				}else if(sender == 0){
    	    					if(answer_receiver[(arrayMatch.get((arrayMatch.size()-1)-i).currentRoundNumber - 1)*3] == 0) myTurn = 1;
    	    				}
    	    				if (myTurn == 0){
    	    					pending.add(new Item(Integer.toString(arrayMatch.get((arrayMatch.size()-1)-i).IDmatch)));
    	    					arrayPending.add(arrayMatch.get((arrayMatch.size()-1)-i));
    	    					challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDreceiver_match);
    	    					challengerList.add(challenger);
    	    				}
    	    			}
    	    		}
    	    				
    	    		//adapter = new MyAdapterPending(getActivity(), pending);
    	    		adapter.notifyDataSetChanged();
    	    		listView.refreshDrawableState();
    	    		listView.setAdapter(adapter);
    				
    			}
    	});
    				
    	/* FINISHED MATCH */
    	Button filter_finished = (Button) getActivity().findViewById(R.id.filter_finished);
    	filter_finished.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			/* set highlighting buttons */
				v.setBackgroundResource(R.drawable.finished);

				Button filter_all = (Button) getActivity().findViewById(R.id.filter_all);
		   		filter_all.setBackgroundResource(R.drawable.allespieleoff);
		   		Button filter_active = (Button) getActivity().findViewById(R.id.filter_active);
		   		filter_active.setBackgroundResource(R.drawable.activeoff);
				Button filter_waiting = (Button) getActivity().findViewById(R.id.filter_waiting);
				filter_waiting.setBackgroundResource(R.drawable.clessidraoff);
				Button filter_invite = (Button) getActivity().findViewById(R.id.filter_invite);
				filter_invite.setBackgroundResource(R.drawable.invitationoff);
    						
    			int sender =0;
    			/* set view for empty listView */
    			listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
    			/* clear pending, arrayPending and challengerList list */
    			pending.clear();
    			arrayPending.clear();
    			challengerList.clear();
    			
    			for (int i = 0 ; i < arrayMatch.size() ; i++){
    	    		if(arrayMatch.get((arrayMatch.size()-1)-i).state == 3){
    	    			pending.add(new Item(Integer.toString(arrayMatch.get((arrayMatch.size()-1)-i).IDmatch)));
    	    			arrayPending.add(arrayMatch.get((arrayMatch.size()-1)-i));
    	    			challenger = dbHelper.getUser(arrayMatch.get((arrayMatch.size()-1)-i).IDreceiver_match);
    	    			challengerList.add(challenger);
    	    		}
    	    	}
    	    	/* set adapter */
    			//adapter = new MyAdapterPending(getActivity(), pending);
    	    	adapter.notifyDataSetChanged();
    	    	listView.refreshDrawableState();
    	    	listView.setAdapter(adapter);
    						
    		}
    	});
    				
    	/*INVITE MATCH*/
    	Button filter_invite = (Button) getActivity().findViewById(R.id.filter_invite);
    	filter_invite.setOnClickListener(new View.OnClickListener(){
    		@Override
    		public void onClick(View v) {
    			/* set highlighting buttons */
				v.setBackgroundResource(R.drawable.invitation);

				Button filter_all = (Button) getActivity().findViewById(R.id.filter_all);
		   		filter_all.setBackgroundResource(R.drawable.allespieleoff);
		   		Button filter_active = (Button) getActivity().findViewById(R.id.filter_active);
		   		filter_active.setBackgroundResource(R.drawable.activeoff);
				Button filter_waiting = (Button) getActivity().findViewById(R.id.filter_waiting);
				filter_waiting.setBackgroundResource(R.drawable.clessidraoff);
				Button filter_finished = (Button) getActivity().findViewById(R.id.filter_finished);
				filter_finished.setBackgroundResource(R.drawable.finishedoff);
				
				
				/* set view for empty listView */
    			listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
    			/* clear pending, arrayPending and challengerList list */
    			pending.clear();
    			arrayPending.clear();
    			challengerList.clear();
    			for (int i = 0 ; i < arrayMatch.size() ; i++){
    				if(arrayMatch.get((arrayMatch.size()-1)-i).state == 1){
    					pending.add(new Item(Integer.toString(arrayMatch.get((arrayMatch.size()-1)-i).IDmatch)));
    					arrayPending.add(arrayMatch.get((arrayMatch.size()-1)-i));
    					challengerList.add(challenger);
    				}
    			}
    			/* set adapter */
    			//adapter = new MyAdapterPending(getActivity(), pending);
    			adapter.notifyDataSetChanged();
    			listView.refreshDrawableState();
    			listView.setAdapter(adapter);
    		}
    	});
        
    }

    /** fillAnswerSender -> create an array with all the sender answers **/
    public int[] fillAnswersSender(Match match){
		int[] answers = new int[15];
		answers[0] = match.answer1_send;
		answers[1] = match.answer2_send;
		answers[2] = match.answer3_send;
		answers[3] = match.answer4_send;
		answers[4] = match.answer5_send;
		answers[5] = match.answer6_send;
		answers[6] = match.answer7_send;
		answers[7] = match.answer8_send;
		answers[8] = match.answer9_send;
		answers[9] = match.answer10_send;
		answers[10] = match.answer11_send;
		answers[11] = match.answer12_send;
		answers[12] = match.answer13_send;
		answers[13] = match.answer14_send;
		answers[14] = match.answer15_send;
	
		return answers;
    }

    /** fillAnswersReceiver -> create an array with all the receiver answers **/
    public int[] fillAnswersReceiver(Match match){
    	int[] answers = new int[15];
    	answers[0] = match.answer1_rec;
    	answers[1] = match.answer2_rec;
    	answers[2] = match.answer3_rec;
    	answers[3] = match.answer4_rec;
    	answers[4] = match.answer5_rec;
    	answers[5] = match.answer6_rec;
    	answers[6] = match.answer7_rec;
    	answers[7] = match.answer8_rec;
    	answers[8] = match.answer9_rec;
    	answers[9] = match.answer10_rec;
    	answers[10] = match.answer11_rec;
    	answers[11] = match.answer12_rec;
    	answers[12] = match.answer13_rec;
    	answers[13] = match.answer14_rec;
    	answers[14] = match.answer15_rec;
    	
    	return answers;
    
    }
    
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 500;
        int targetHeight = 500;

        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

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
    
    
}
