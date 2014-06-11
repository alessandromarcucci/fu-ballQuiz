package com.example.splashscreen;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;

import com.example.splashscreen.DatabaseHelper.Match;
import com.example.splashscreen.DatabaseHelper.User;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.ClipData.Item;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActiveFragment extends Fragment {
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic =null;
	private Context globalContext = null;
	String[] items = null;
	Spinner spinner_club = null;
	EditText name_account = null;
	EditText email = null;
	EditText first_password = null;
	EditText second_password = null;
	DatabaseHelper dbHelper = new DatabaseHelper();
	public final static String EXTRA_MESSAGE = "com.example.splashscreen.MESSAGE";
	public final static String APP_PREFS ="footballQuizPrefs";
	SharedPreferences settings = null;
	SharedPreferences.Editor editor = null;
	MyAdapterActive adapter = null;
	ArrayList<Item> pending = new ArrayList<Item>();
	static ListView listView = null;
	static Timer myTimer = new Timer();
	InputStream inputStream = null;
	
	static ArrayList<Match> arrayMatch = new ArrayList<Match>();
	static ArrayList<Match> arrayPending = new ArrayList<Match>();
	static User challenger = null;
	static ArrayList<User> challengerList = new ArrayList<User>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		settings = getActivity().getSharedPreferences(APP_PREFS, getActivity().MODE_PRIVATE);
		
		editor = settings.edit();
		
		
		
        
        
        
        /* load the matches in the main list: All */
        arrayMatch = FragmentContainer.allMatches;
    	
        arrayPending.clear();
    	
        /* set listView */
		listView = (ListView) getActivity().findViewById(R.id.list_mygames2);
		/* set view for empty list */
		listView.setEmptyView(getActivity().findViewById(android.R.id.empty));
		/*clear pending, arrayPending and challengerList list*/
		pending.clear();
		arrayPending.clear();
		challengerList.clear();
		
		int sender =0;
		
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
				adapter = new MyAdapterActive(getActivity(), pending);
				adapter.notifyDataSetChanged();
				listView.refreshDrawableState();
				listView.setAdapter(adapter);
    
				
		}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list2_layout, container, false);
		
		return view;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);

	    // Make sure that we are currently visible
	    if (this.isVisible()) {
	        // If we are becoming invisible, then...
	        if (isVisibleToUser) {
	        	/* set title */
	            TextView allespiele = (TextView) getActivity().findViewById(R.id.alleSpiele_text);
	            Typeface face=Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
	            allespiele.setText("YOUR TURN");
	            allespiele.setTypeface(face);
	            
	            // TODO stop audio playback
	        }
	        }
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
	
	

}

