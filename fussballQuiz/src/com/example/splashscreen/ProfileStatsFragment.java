package com.example.splashscreen;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileStatsFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	Spinner spinner_time = null;
	Spinner spinner_category = null;
	Typeface roboto_black = null;
	Typeface roboto_medium_italic = null;
	Typeface roboto_black_italic = null;
	/**************/
	RelativeLayout firstStat = null;
	static RelativeLayout first_back = null;
	
	RelativeLayout secondStat = null;
	static RelativeLayout second_back = null;
	
	RelativeLayout thirdStat = null;
	static RelativeLayout third_back = null;
	
	RelativeLayout fourthStat = null;
	static RelativeLayout fourth_back = null;
	
	RelativeLayout fifthStat = null;
	static RelativeLayout fifth_back = null;
	
	RelativeLayout sixthStat = null;
	static RelativeLayout sixth_back = null;
	
	/*************/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		
		
		
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/* get the fonts from the asset */
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/* set layout for time spinner */
		spinner_time = (Spinner) getActivity().findViewById(R.id.selectAllTime);
		String[] items_time = getResources().getStringArray(R.array.array_time);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, items_time);
        spinner_time.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		/* set layout for category spinner */
		spinner_category = (Spinner) getActivity().findViewById(R.id.selectAllCategories);
		String[] items_category = getResources().getStringArray(R.array.array_category);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, items_category);
        spinner_category.setAdapter(adapter2);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		/** SET LAYOUT AND VALUES FOR THE CIRCLES (INITIAL STATUS) **/
		
		/********************* first circle ***************************/
		
		/** set tag to background **/
		first_back = (RelativeLayout) getActivity().findViewById(R.id.firstStat_back);
		
		first_back.setTag(1);
		firstStat = (RelativeLayout) getActivity().findViewById(R.id.firstStat);
		firstStat.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				int tag_first_back = (Integer) first_back.getTag();
				if(tag_first_back == 1){
					first_back.setVisibility(View.GONE);
					
					first_back.setTag(0);
				}else{
					first_back.setVisibility(View.VISIBLE);
					
					first_back.setTag(1);
				}
			}
			
			
		});
		
		/** 1. background **/
		TextView stats_points = (TextView) getActivity().findViewById(R.id.stats_points);
		stats_points.setText(Integer.toString(FragmentContainer.userLobby.playerPoints));
		stats_points.setTypeface(roboto_black);
		
		TextView points_text = (TextView) getActivity().findViewById(R.id.pointsText);
		points_text.setTypeface(roboto_medium_italic);
		
		/** 2. background **/
		String[] values = dbHelper.getPointStats(FragmentContainer.userLobby.playerID);
		
		TextView points_win = (TextView) getActivity().findViewById(R.id.points_win);
		points_win.setTypeface(roboto_medium_italic);
		points_win.setText(values[1]);
		Log.w("won", values[1]);
		
		TextView points_win_text = (TextView) getActivity().findViewById(R.id.points_win_text);
		points_win_text.setTypeface(roboto_medium_italic);
		
		TextView points_draw = (TextView) getActivity().findViewById(R.id.points_draw);
		points_draw.setTypeface(roboto_medium_italic);
		points_draw.setText(values[2]);
		
		TextView points_draw_text = (TextView) getActivity().findViewById(R.id.points_draw_text);
		points_draw_text.setTypeface(roboto_medium_italic);
		
		TextView points_lost = (TextView) getActivity().findViewById(R.id.points_lost);
		points_win.setTypeface(roboto_medium_italic);
		points_lost.setText(values[0]);
		
		TextView points_lost_text = (TextView) getActivity().findViewById(R.id.points_lost_text);
		points_lost_text.setTypeface(roboto_medium_italic);
		
		/** set progress bar progress **/
		
		ProgressBar pointsStat_progress = (ProgressBar) getActivity().findViewById(R.id.pointsStat_progress);
		//set the max with playerPoints
		int max_pointStats = Integer.parseInt(values[0]) + Integer.parseInt(values[1]) + Integer.parseInt(values[2]);
		Log.w("max", Integer.toString(max_pointStats));
		pointsStat_progress.setMax(max_pointStats);
		//set won matches
		int won_pointStats = Integer.parseInt(values[1]);
		pointsStat_progress.setProgress(won_pointStats);
		//set draw matches
		int lost_pointStats = won_pointStats + Integer.parseInt(values[0]);
		pointsStat_progress.setSecondaryProgress(lost_pointStats);
				
		/*************************************************************************************/
		
		/********************* second circle ***************************/
		
		/** set tag to background **/
		second_back = (RelativeLayout) getActivity().findViewById(R.id.secondStat_back);
		
		second_back.setTag(1);
		secondStat = (RelativeLayout) getActivity().findViewById(R.id.secondStat);
		secondStat.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				int tag_second_back = (Integer) second_back.getTag();
				if(tag_second_back == 1){
					second_back.setVisibility(View.GONE);
					
					second_back.setTag(0);
				}else{
					second_back.setVisibility(View.VISIBLE);
					
					second_back.setTag(1);
				}
			}
			
			
		});
		
		/** 1. background **/
		int numMatches = dbHelper.getNumMatches(FragmentContainer.userLobby.playerID);
		
		
		TextView match_total = (TextView) getActivity().findViewById(R.id.match_total);
		match_total.setText(Integer.toString(numMatches));
		match_total.setTypeface(roboto_black);
		
		TextView match_text = (TextView) getActivity().findViewById(R.id.matchText);
		match_text.setTypeface(roboto_medium_italic);
		
		/** 2. background **/
		int win_number = FragmentContainer.userLobby.won;
		int lost_number = FragmentContainer.userLobby.lost;
		int draw_number = FragmentContainer.userLobby.draw;
		
		/** calculate percentage **/
		
		int max_matches = win_number + lost_number + draw_number;
		
		int win_percentage = (int)((100 * win_number)/max_matches);
		Log.w("win_percentage", Integer.toString(win_percentage));
		int lost_percentage = (int)((100 * lost_number)/max_matches);
		int draw_percentage = (int)((100 * draw_number)/max_matches);
		
		TextView match_win = (TextView) getActivity().findViewById(R.id.match_win);
		match_win.setTypeface(roboto_medium_italic);
		match_win.setText(win_percentage + "% ");
		
		
		TextView match_win_text = (TextView) getActivity().findViewById(R.id.match_win_text);
		match_win_text.setTypeface(roboto_medium_italic);
		
		TextView match_draw = (TextView) getActivity().findViewById(R.id.match_draw);
		match_draw.setTypeface(roboto_medium_italic);
		match_draw.setText(draw_percentage + "% ");
		
		
		TextView match_draw_text = (TextView) getActivity().findViewById(R.id.match_draw_text);
		match_draw_text.setTypeface(roboto_medium_italic);
		
		TextView match_lost = (TextView) getActivity().findViewById(R.id.match_lost);
		match_lost.setTypeface(roboto_medium_italic);
		match_lost.setText(lost_percentage + "% ");
		
		
		TextView match_lost_text = (TextView) getActivity().findViewById(R.id.match_lost_text);
		match_lost_text.setTypeface(roboto_medium_italic);
		
		
		
		/** set progress bar progress **/
		
		ProgressBar matchStat_progress = (ProgressBar) getActivity().findViewById(R.id.matchStat_progress);
		//set the max with playerPoints
		
		
		matchStat_progress.setMax(max_matches);
		//set won matches
		
		matchStat_progress.setProgress(win_number);
		//set draw matches
		
		matchStat_progress.setSecondaryProgress(win_number + lost_number);
				
		/*************************************************************************************/
		
		/************************** third circle ********************************************/
		
		/** set tag to background **/
		third_back = (RelativeLayout) getActivity().findViewById(R.id.thirdStat_back);
		
		third_back.setTag(1);
		thirdStat = (RelativeLayout) getActivity().findViewById(R.id.thirdStat);
		thirdStat.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				int tag_third_back = (Integer) third_back.getTag();
				if(tag_third_back == 1){
					third_back.setVisibility(View.GONE);
					
					third_back.setTag(0);
				}else{
					third_back.setVisibility(View.VISIBLE);
					
					third_back.setTag(1);
				}
			}
			
			
		});
		
		
		/** 1. background **/
		String[] questions = dbHelper.getQuestionStats(FragmentContainer.userLobby.playerID);
		Log.w("numCorrectAnswers", questions[0]);
		Log.w("numWrongAnswers", questions[1]);
		
		int totalAnswers = Integer.parseInt(questions[0]) + Integer.parseInt(questions[1]);
		
		TextView stats_question = (TextView) getActivity().findViewById(R.id.stats_question);
		stats_question.setText(Integer.toString(totalAnswers));
		stats_question.setTypeface(roboto_black);
		
		TextView questionText = (TextView) getActivity().findViewById(R.id.questionText);
		questionText.setTypeface(roboto_medium_italic);
		
		/** 2. background **/
		
		
		/** calculate percentage **/
		
		
		
		int percentage_correct = (int) ((Integer.parseInt(questions[0]) * 100)/ totalAnswers);
		int percentage_wrong = (int) ((Integer.parseInt(questions[1]) * 100)/ totalAnswers);
		
		TextView question_win = (TextView) getActivity().findViewById(R.id.question_win);
		question_win.setTypeface(roboto_medium_italic);
		question_win.setText(percentage_correct + "% ");
		
		
		TextView question_win_text = (TextView) getActivity().findViewById(R.id.question_win_text);
		question_win_text.setTypeface(roboto_medium_italic);
		
		
		
		TextView question_lost = (TextView) getActivity().findViewById(R.id.question_lost);
		question_lost.setTypeface(roboto_medium_italic);
		question_lost.setText(percentage_wrong + "% ");
		
		
		TextView question_lost_text = (TextView) getActivity().findViewById(R.id.question_lost_text);
		question_lost_text.setTypeface(roboto_medium_italic);
		
		/** set progress bar progress **/
		
		ProgressBar questionStat_progress = (ProgressBar) getActivity().findViewById(R.id.questionStat_progress);
		//set the max with playerPoints
		
		
		questionStat_progress.setMax(totalAnswers);
		//set won matches
		
		questionStat_progress.setProgress(Integer.parseInt(questions[0]));
		//set draw matches
		
		questionStat_progress.setSecondaryProgress(totalAnswers);
		
		
		/***********************************************************************************/
		
		/************************** fourth circle ********************************************/
		
		/** set tag to background **/
		fourth_back = (RelativeLayout) getActivity().findViewById(R.id.fourthStat_back);
		
		fourth_back.setTag(1);
		fourthStat = (RelativeLayout) getActivity().findViewById(R.id.fourthStat);
		fourthStat.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				int tag_fourth_back = (Integer) fourth_back.getTag();
				if(tag_fourth_back == 1){
					fourth_back.setVisibility(View.GONE);
					
					fourth_back.setTag(0);
				}else{
					fourth_back.setVisibility(View.VISIBLE);
					
					fourth_back.setTag(1);
				}
			}
			
			
		});
		
		/** 1. background **/
		String[] conceded = dbHelper.getConcededStats(FragmentContainer.userLobby.playerID);
		Log.w("numConcededSelf", conceded[0]);
		Log.w("numConcededOpponents", conceded[1]);
		
		int totalConceded = Integer.parseInt(conceded[0]) + Integer.parseInt(conceded[1]);
		
		TextView stats_conceded = (TextView) getActivity().findViewById(R.id.conceded_total);
		stats_conceded.setText(Integer.toString(totalConceded));
		stats_conceded.setTypeface(roboto_black);
		
		TextView concededText = (TextView) getActivity().findViewById(R.id.concededText);
		concededText.setTypeface(roboto_medium_italic);
		
		/** 2. background **/
		
		
		/** calculate percentage **/
		
		
		
		int percentage_conceded_self = (int) ((Integer.parseInt(conceded[0]) * 100)/ totalAnswers);
		int percentage_conceded_opponent = (int) ((Integer.parseInt(conceded[1]) * 100)/ totalAnswers);
		
		TextView conceded_self = (TextView) getActivity().findViewById(R.id.conceded_self);
		conceded_self.setTypeface(roboto_medium_italic);
		conceded_self.setText(percentage_conceded_self + "% ");
		
		
		TextView conceded_self_text = (TextView) getActivity().findViewById(R.id.conceded_self_text);
		conceded_self_text.setTypeface(roboto_medium_italic);
		
		
		
		TextView conceded_opponent = (TextView) getActivity().findViewById(R.id.conceded_opponent);
		conceded_opponent.setTypeface(roboto_medium_italic);
		conceded_opponent.setText(percentage_conceded_opponent + "% ");
		
		
		TextView conceded_opponent_text = (TextView) getActivity().findViewById(R.id.conceded_opponent_text);
		conceded_opponent_text.setTypeface(roboto_medium_italic);
		
		/** set progress bar progress **/
		
		ProgressBar concededStat_progress = (ProgressBar) getActivity().findViewById(R.id.concededStat_progress);
		//set the max with playerPoints
		
		
		concededStat_progress.setMax(totalAnswers);
		//set won matches
		
		concededStat_progress.setProgress(Integer.parseInt(conceded[0]));
		//set draw matches
		
		concededStat_progress.setSecondaryProgress(totalConceded);
		
		/*********************************************************************************/
		
		/************************** fifth circle ********************************************/
		
		/** set tag to background **/
		fifth_back = (RelativeLayout) getActivity().findViewById(R.id.fifthStat_back);
		
		fifth_back.setTag(1);
		fifthStat = (RelativeLayout) getActivity().findViewById(R.id.fifthStat);
		fifthStat.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				int tag_fifth_back = (Integer) fifth_back.getTag();
				if(tag_fifth_back == 1){
					fifth_back.setVisibility(View.GONE);
					
					fifth_back.setTag(0);
				}else{
					fifth_back.setVisibility(View.VISIBLE);
					
					fifth_back.setTag(1);
				}
			}
			
			
		});
		
		
		/** 1. background **/
		String[] perfect = dbHelper.getPerfectStats(FragmentContainer.userLobby.playerID);
		Log.w("numPerfectSelf", conceded[0]);
		Log.w("numPerfectOpponents", conceded[1]);
		
		int totalPerfect = Integer.parseInt(perfect[0]) + Integer.parseInt(perfect[1]);
		
		TextView stats_perfect = (TextView) getActivity().findViewById(R.id.stats_perfect);
		stats_perfect.setText(Integer.toString(totalPerfect));
		stats_perfect.setTypeface(roboto_black);
		
		TextView perfectText = (TextView) getActivity().findViewById(R.id.perfectText);
		perfectText.setTypeface(roboto_medium_italic);
		
		/** 2. background **/
		
		
		/** calculate percentage **/
		
		int percentage_perfect_self = 0;
		int percentage_perfect_opponent = 0;
		if(totalPerfect != 0){
			percentage_perfect_self = (int) ((Integer.parseInt(perfect[0]) * 100)/ totalPerfect);
			percentage_perfect_opponent = (int) ((Integer.parseInt(perfect[1]) * 100)/ totalAnswers);
		}
		 
		
		
		TextView perfect_self = (TextView) getActivity().findViewById(R.id.perfect_self);
		perfect_self.setTypeface(roboto_medium_italic);
		perfect_self.setText(percentage_perfect_self + "% ");
		
		
		TextView perfect_self_text = (TextView) getActivity().findViewById(R.id.perfect_self_text);
		perfect_self_text.setTypeface(roboto_medium_italic);
		
		
		
		TextView perfect_opponent = (TextView) getActivity().findViewById(R.id.perfect_opponent);
		perfect_opponent.setTypeface(roboto_medium_italic);
		perfect_opponent.setText(percentage_perfect_opponent + "% ");
		
		
		TextView perfect_opponent_text = (TextView) getActivity().findViewById(R.id.perfect_opponent_text);
		perfect_opponent_text.setTypeface(roboto_medium_italic);
		
		/** set progress bar progress **/
		
		ProgressBar perfectStat_progress = (ProgressBar) getActivity().findViewById(R.id.perfectStat_progress);
		//set the max with playerPoints
		
		
		perfectStat_progress.setMax(totalPerfect);
		//set won matches
		
		perfectStat_progress.setProgress(Integer.parseInt(perfect[0]));
		//set draw matches
		
		perfectStat_progress.setSecondaryProgress(totalPerfect);
		
		/************************** sixth circle ********************************************/
		
		/** set tag to background **/
		sixth_back = (RelativeLayout) getActivity().findViewById(R.id.sixthStat_back);
		
		sixth_back.setTag(1);
		sixthStat = (RelativeLayout) getActivity().findViewById(R.id.sixthStat);
		sixthStat.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				
				int tag_sixth_back = (Integer) sixth_back.getTag();
				if(tag_sixth_back == 1){
					sixth_back.setVisibility(View.GONE);
					
					sixth_back.setTag(0);
				}else{
					sixth_back.setVisibility(View.VISIBLE);
					
					sixth_back.setTag(1);
				}
			}
			
			
		});
		/** 1. background **/
		String[] timeout = dbHelper.getTimeoutStats(FragmentContainer.userLobby.playerID);
		Log.w("numPerfectSelf", conceded[0]);
		Log.w("numPerfectOpponents", conceded[1]);
		
		int totalTimeout = Integer.parseInt(timeout[0]) + Integer.parseInt(timeout[1]);
		
		TextView stats_timeout = (TextView) getActivity().findViewById(R.id.timeout_total);
		stats_timeout.setText(Integer.toString(totalTimeout));
		stats_timeout.setTypeface(roboto_black);
		
		TextView timeoutText = (TextView) getActivity().findViewById(R.id.timeoutText);
		timeoutText.setTypeface(roboto_medium_italic);
		
		/** 2. background **/
		
		
		/** calculate percentage **/
		
		int percentage_timeout_self = 0;
		int percentage_timeout_opponent = 0;
		if(totalTimeout != 0){
			percentage_timeout_self = (int) ((Integer.parseInt(timeout[0]) * 100)/ totalTimeout);
			percentage_timeout_opponent = (int) ((Integer.parseInt(timeout[1]) * 100)/ totalTimeout);
		}
		 
		
		
		TextView timeout_self = (TextView) getActivity().findViewById(R.id.timeout_self);
		timeout_self.setTypeface(roboto_medium_italic);
		timeout_self.setText(percentage_timeout_self + "% ");
		
		
		TextView timeout_self_text = (TextView) getActivity().findViewById(R.id.timeout_self_text);
		timeout_self_text.setTypeface(roboto_medium_italic);
		
		
		
		TextView timeout_opponent = (TextView) getActivity().findViewById(R.id.timeout_opponent);
		timeout_opponent.setTypeface(roboto_medium_italic);
		timeout_opponent.setText(percentage_timeout_opponent + "% ");
		
		
		TextView timeout_opponent_text = (TextView) getActivity().findViewById(R.id.timeout_opponent_text);
		timeout_opponent_text.setTypeface(roboto_medium_italic);
		
		/** set progress bar progress **/
		
		ProgressBar timeoutStat_progress = (ProgressBar) getActivity().findViewById(R.id.timeoutStat_progress);
		//set the max with playerPoints
		
		
		timeoutStat_progress.setMax(totalTimeout);
		//set won matches
		
		timeoutStat_progress.setProgress(Integer.parseInt(timeout[0]));
		//set draw matches
		
		timeoutStat_progress.setSecondaryProgress(totalTimeout);
		
		
		/**********************************END INITIAL STATUS SETTING *******************************************************/
		
		/* get the spinners */
		final Spinner time_spinner = (Spinner) getActivity().findViewById(R.id.selectAllTime);
		final Spinner category_spinner = (Spinner) getActivity().findViewById(R.id.selectAllCategories);
		
		/**** CREATE LISTENER FOR TIME SPINNER ****/
		time_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	String time = time_spinner.getSelectedItem().toString();
		    	String category = category_spinner.getSelectedItem().toString();
		    	
		    	/********************* first circle ***************************/
				
				/** set tag to background **/
				first_back = (RelativeLayout) getActivity().findViewById(R.id.firstStat_back);
				
				first_back.setTag(1);
				firstStat = (RelativeLayout) getActivity().findViewById(R.id.firstStat);
				firstStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_first_back = (Integer) first_back.getTag();
						if(tag_first_back == 1){
							first_back.setVisibility(View.GONE);
							
							first_back.setTag(0);
						}else{
							first_back.setVisibility(View.VISIBLE);
							
							first_back.setTag(1);
						}
					}
					
					
				});
				
				/** 1. background **/
				int points[] = dbHelper.getTimeCategoryPointStats(FragmentContainer.userLobby.playerID, time, category);
				int totalPoints = points[0] + points[1] + points[2];
				Log.w("totalPoints", Integer.toString(totalPoints));
				
				TextView stats_points = (TextView) getActivity().findViewById(R.id.stats_points);
				stats_points.setText(Integer.toString(totalPoints));
				stats_points.setTypeface(roboto_black);
				
				TextView points_text = (TextView) getActivity().findViewById(R.id.pointsText);
				points_text.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
								
				TextView points_win = (TextView) getActivity().findViewById(R.id.points_win);
				points_win.setTypeface(roboto_medium_italic);
				points_win.setText(Integer.toString(points[1]));
				
				
				TextView points_win_text = (TextView) getActivity().findViewById(R.id.points_win_text);
				points_win_text.setTypeface(roboto_medium_italic);
				
				TextView points_draw = (TextView) getActivity().findViewById(R.id.points_draw);
				points_draw.setTypeface(roboto_medium_italic);
				points_draw.setText(Integer.toString(points[2]));
				
				TextView points_draw_text = (TextView) getActivity().findViewById(R.id.points_draw_text);
				points_draw_text.setTypeface(roboto_medium_italic);
				
				TextView points_lost = (TextView) getActivity().findViewById(R.id.points_lost);
				points_win.setTypeface(roboto_medium_italic);
				points_lost.setText(Integer.toString(points[0]));
				
				TextView points_lost_text = (TextView) getActivity().findViewById(R.id.points_lost_text);
				points_lost_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar pointsStat_progress = (ProgressBar) getActivity().findViewById(R.id.pointsStat_progress);
				//set the max with playerPoints
				
				
				pointsStat_progress.setMax(totalPoints);
				//set won matches
				pointsStat_progress.setProgress(points[1]);
				//set draw matches
				pointsStat_progress.setSecondaryProgress(points[1] + points[0]);
						
				/*************************************************************************************/
				/********************* second circle ***************************/
				
				/** set tag to background **/
				second_back = (RelativeLayout) getActivity().findViewById(R.id.secondStat_back);
				
				second_back.setTag(1);
				secondStat = (RelativeLayout) getActivity().findViewById(R.id.secondStat);
				secondStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_second_back = (Integer) second_back.getTag();
						if(tag_second_back == 1){
							second_back.setVisibility(View.GONE);
							
							second_back.setTag(0);
						}else{
							second_back.setVisibility(View.VISIBLE);
							
							second_back.setTag(1);
						}
					}
					
					
				});
				
				/** 1. background **/
				int[] matches = dbHelper.getTimeCategoryMatchStats(FragmentContainer.userLobby.playerID, time, category);
				
				int totalMatches = matches[0] + matches[1] + matches[2];
				TextView match_total = (TextView) getActivity().findViewById(R.id.match_total);
				match_total.setText(Integer.toString(totalMatches));
				match_total.setTypeface(roboto_black);
				
				TextView match_text = (TextView) getActivity().findViewById(R.id.matchText);
				match_text.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int lost_percentage = 0;
				int win_percentage = 0;
				int draw_percentage = 0;
				if (totalMatches != 0){
					lost_percentage = (int) ((100 * matches[0])/totalMatches);
					win_percentage = (int) ((100 * matches[1])/totalMatches);
					draw_percentage = (int) ((100 * matches[2])/totalMatches);
				}
				
				TextView match_win = (TextView) getActivity().findViewById(R.id.match_win);
				match_win.setTypeface(roboto_medium_italic);
				match_win.setText(win_percentage + "% ");
				
				
				TextView match_win_text = (TextView) getActivity().findViewById(R.id.match_win_text);
				match_win_text.setTypeface(roboto_medium_italic);
				
				TextView match_draw = (TextView) getActivity().findViewById(R.id.match_draw);
				match_draw.setTypeface(roboto_medium_italic);
				match_draw.setText(draw_percentage + "% ");
				
				
				TextView match_draw_text = (TextView) getActivity().findViewById(R.id.match_draw_text);
				match_draw_text.setTypeface(roboto_medium_italic);
				
				TextView match_lost = (TextView) getActivity().findViewById(R.id.match_lost);
				match_lost.setTypeface(roboto_medium_italic);
				match_lost.setText(lost_percentage + "% ");
				
				
				TextView match_lost_text = (TextView) getActivity().findViewById(R.id.match_lost_text);
				match_lost_text.setTypeface(roboto_medium_italic);
				
				
				
				/** set progress bar progress **/
				
				ProgressBar matchStat_progress = (ProgressBar) getActivity().findViewById(R.id.matchStat_progress);
				//set the max with playerPoints
				
				
				matchStat_progress.setMax(totalMatches);
				//set won matches
				
				matchStat_progress.setProgress(matches[1]);
				//set draw matches
				
				matchStat_progress.setSecondaryProgress(matches[1] + matches[0]);
						
				/*************************************************************************************/
				/************************** third circle ********************************************/
				
				/** set tag to background **/
				third_back = (RelativeLayout) getActivity().findViewById(R.id.thirdStat_back);
				
				third_back.setTag(1);
				thirdStat = (RelativeLayout) getActivity().findViewById(R.id.thirdStat);
				thirdStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_third_back = (Integer) third_back.getTag();
						if(tag_third_back == 1){
							third_back.setVisibility(View.GONE);
							
							third_back.setTag(0);
						}else{
							third_back.setVisibility(View.VISIBLE);
							
							third_back.setTag(1);
						}
					}
					
					
				});
				
				
				/** 1. background **/
				Log.w("time", time);
				Log.w("category", category);
				int[] questions = dbHelper.getTimeCategoryQuestionStats(FragmentContainer.userLobby.playerID, time, category);
				Log.w("numCorrectAnswers", Integer.toString(questions[0]));
				Log.w("numWrongAnswers", Integer.toString(questions[1]));
				
				int totalAnswers = questions[0] + questions[1];
				
				TextView stats_question = (TextView) getActivity().findViewById(R.id.stats_question);
				stats_question.setText(Integer.toString(totalAnswers));
				stats_question.setTypeface(roboto_black);
				
				TextView questionText = (TextView) getActivity().findViewById(R.id.questionText);
				questionText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				
				int percentage_correct = 0;
				int percentage_wrong = 0;
				if(totalAnswers != 0){
					percentage_correct = (int) ((questions[0] * 100)/ totalAnswers);
					percentage_wrong = (int) ((questions[1] * 100)/ totalAnswers);
				}
				TextView question_win = (TextView) getActivity().findViewById(R.id.question_win);
				question_win.setTypeface(roboto_medium_italic);
				question_win.setText(percentage_correct + "% ");
				
				
				TextView question_win_text = (TextView) getActivity().findViewById(R.id.question_win_text);
				question_win_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView question_lost = (TextView) getActivity().findViewById(R.id.question_lost);
				question_lost.setTypeface(roboto_medium_italic);
				question_lost.setText(percentage_wrong + "% ");
				
				
				TextView question_lost_text = (TextView) getActivity().findViewById(R.id.question_lost_text);
				question_lost_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar questionStat_progress = (ProgressBar) getActivity().findViewById(R.id.questionStat_progress);
				//set the max with playerPoints
				
				
				questionStat_progress.setMax(totalAnswers);
				//set won matches
				
				questionStat_progress.setProgress(questions[0]);
				//set draw matches
				
				questionStat_progress.setSecondaryProgress(totalAnswers);
				
				
				/***********************************************************************************/
				/************************** fourth circle ********************************************/
				
				/** set tag to background **/
				fourth_back = (RelativeLayout) getActivity().findViewById(R.id.fourthStat_back);
				
				fourth_back.setTag(1);
				fourthStat = (RelativeLayout) getActivity().findViewById(R.id.fourthStat);
				fourthStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_fourth_back = (Integer) fourth_back.getTag();
						if(tag_fourth_back == 1){
							fourth_back.setVisibility(View.GONE);
							
							fourth_back.setTag(0);
						}else{
							fourth_back.setVisibility(View.VISIBLE);
							
							fourth_back.setTag(1);
						}
					}
					
					
				});
				
				/** 1. background **/
				int[] conceded = dbHelper.getTimeCategoryConcededStats(FragmentContainer.userLobby.playerID, time, category);
				Log.w("numConcededSelf", Integer.toString(conceded[0]));
				Log.w("numConcededOpponents", Integer.toString(conceded[1]));
				
				int totalConceded = conceded[0] + conceded[1];
				
				TextView stats_conceded = (TextView) getActivity().findViewById(R.id.conceded_total);
				stats_conceded.setText(Integer.toString(totalConceded));
				stats_conceded.setTypeface(roboto_black);
				
				TextView concededText = (TextView) getActivity().findViewById(R.id.concededText);
				concededText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int percentage_conceded_self = 0;
				int percentage_conceded_opponent = 0;
		
				if(totalConceded != 0){
					percentage_conceded_self = (int) ((conceded[0] * 100)/ totalConceded);
					percentage_conceded_opponent = (int) ((conceded[1] * 100)/ totalConceded);
				}
				TextView conceded_self = (TextView) getActivity().findViewById(R.id.conceded_self);
				conceded_self.setTypeface(roboto_medium_italic);
				conceded_self.setText(percentage_conceded_self + "% ");
				
				
				TextView conceded_self_text = (TextView) getActivity().findViewById(R.id.conceded_self_text);
				conceded_self_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView conceded_opponent = (TextView) getActivity().findViewById(R.id.conceded_opponent);
				conceded_opponent.setTypeface(roboto_medium_italic);
				conceded_opponent.setText(percentage_conceded_opponent + "% ");
				
				
				TextView conceded_opponent_text = (TextView) getActivity().findViewById(R.id.conceded_opponent_text);
				conceded_opponent_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar concededStat_progress = (ProgressBar) getActivity().findViewById(R.id.concededStat_progress);
				//set the max with playerPoints
				
				
				concededStat_progress.setMax(totalConceded);
				//set won matches
				
				concededStat_progress.setProgress(conceded[0]);
				//set draw matches
				
				concededStat_progress.setSecondaryProgress(totalConceded);
				
				/*********************************************************************************/
				/************************** fifth circle ********************************************/
				
				/** set tag to background **/
				fifth_back = (RelativeLayout) getActivity().findViewById(R.id.fifthStat_back);
				
				fifth_back.setTag(1);
				fifthStat = (RelativeLayout) getActivity().findViewById(R.id.fifthStat);
				fifthStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_fifth_back = (Integer) fifth_back.getTag();
						if(tag_fifth_back == 1){
							fifth_back.setVisibility(View.GONE);
							
							fifth_back.setTag(0);
						}else{
							fifth_back.setVisibility(View.VISIBLE);
							
							fifth_back.setTag(1);
						}
					}
					
					
				});
				
				
				/** 1. background **/
				int[] perfect = dbHelper.getTimeCategoryPerfectStats(FragmentContainer.userLobby.playerID, time, category);
				
				
				int totalPerfect = perfect[0] + perfect[1];
				
				TextView stats_perfect = (TextView) getActivity().findViewById(R.id.stats_perfect);
				stats_perfect.setText(Integer.toString(totalPerfect));
				stats_perfect.setTypeface(roboto_black);
				
				TextView perfectText = (TextView) getActivity().findViewById(R.id.perfectText);
				perfectText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int percentage_perfect_self = 0;
				int percentage_perfect_opponent = 0;
				if(totalPerfect != 0){
					percentage_perfect_self = (int) ((perfect[0] * 100)/ totalPerfect);
					percentage_perfect_opponent = (int) ((perfect[1] * 100)/ totalPerfect);
				}
				 
				
				
				TextView perfect_self = (TextView) getActivity().findViewById(R.id.perfect_self);
				perfect_self.setTypeface(roboto_medium_italic);
				perfect_self.setText(percentage_perfect_self + "% ");
				
				
				TextView perfect_self_text = (TextView) getActivity().findViewById(R.id.perfect_self_text);
				perfect_self_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView perfect_opponent = (TextView) getActivity().findViewById(R.id.perfect_opponent);
				perfect_opponent.setTypeface(roboto_medium_italic);
				perfect_opponent.setText(percentage_perfect_opponent + "% ");
				
				
				TextView perfect_opponent_text = (TextView) getActivity().findViewById(R.id.perfect_opponent_text);
				perfect_opponent_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar perfectStat_progress = (ProgressBar) getActivity().findViewById(R.id.perfectStat_progress);
				//set the max with playerPoints
				
				
				perfectStat_progress.setMax(totalPerfect);
				//set won matches
				
				perfectStat_progress.setProgress(perfect[0]);
				//set draw matches
				
				perfectStat_progress.setSecondaryProgress(totalPerfect);
				
				/************************** sixth circle ********************************************/
				
				/** set tag to background **/
				sixth_back = (RelativeLayout) getActivity().findViewById(R.id.sixthStat_back);
				
				sixth_back.setTag(1);
				sixthStat = (RelativeLayout) getActivity().findViewById(R.id.sixthStat);
				sixthStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_sixth_back = (Integer) sixth_back.getTag();
						if(tag_sixth_back == 1){
							sixth_back.setVisibility(View.GONE);
							
							sixth_back.setTag(0);
						}else{
							sixth_back.setVisibility(View.VISIBLE);
							
							sixth_back.setTag(1);
						}
					}
					
					
				});
				/** 1. background **/
				int[] timeout = dbHelper.getTimeCategoryTimeoutStats(FragmentContainer.userLobby.playerID, time, category);
				
				int totalTimeout = timeout[0] + timeout[1];
				
				TextView stats_timeout = (TextView) getActivity().findViewById(R.id.timeout_total);
				stats_timeout.setText(Integer.toString(totalTimeout));
				stats_timeout.setTypeface(roboto_black);
				
				TextView timeoutText = (TextView) getActivity().findViewById(R.id.timeoutText);
				timeoutText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int percentage_timeout_self = 0;
				int percentage_timeout_opponent = 0;
				if(totalTimeout != 0){
					percentage_timeout_self = (int) ((timeout[0] * 100)/ totalTimeout);
					percentage_timeout_opponent = (int) ((timeout[1] * 100)/ totalTimeout);
				}
				 
				
				
				TextView timeout_self = (TextView) getActivity().findViewById(R.id.timeout_self);
				timeout_self.setTypeface(roboto_medium_italic);
				timeout_self.setText(percentage_timeout_self + "% ");
				
				
				TextView timeout_self_text = (TextView) getActivity().findViewById(R.id.timeout_self_text);
				timeout_self_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView timeout_opponent = (TextView) getActivity().findViewById(R.id.timeout_opponent);
				timeout_opponent.setTypeface(roboto_medium_italic);
				timeout_opponent.setText(percentage_timeout_opponent + "% ");
				
				
				TextView timeout_opponent_text = (TextView) getActivity().findViewById(R.id.timeout_opponent_text);
				timeout_opponent_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar timeoutStat_progress = (ProgressBar) getActivity().findViewById(R.id.timeoutStat_progress);
				//set the max with playerPoints
				
				
				timeoutStat_progress.setMax(totalTimeout);
				//set won matches
				
				timeoutStat_progress.setProgress(timeout[0]);
				//set draw matches
				
				timeoutStat_progress.setSecondaryProgress(totalTimeout);
				
		    	
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		
		/**** CREATE LISTENER FOR CATEGORY SPINNER ****/
		category_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		        // your code here
		    	String time = time_spinner.getSelectedItem().toString();
		    	String category = category_spinner.getSelectedItem().toString();
		    	
		    	/********************* first circle ***************************/
				
				/** set tag to background **/
				first_back = (RelativeLayout) getActivity().findViewById(R.id.firstStat_back);
				
				first_back.setTag(1);
				firstStat = (RelativeLayout) getActivity().findViewById(R.id.firstStat);
				firstStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_first_back = (Integer) first_back.getTag();
						if(tag_first_back == 1){
							first_back.setVisibility(View.GONE);
							
							first_back.setTag(0);
						}else{
							first_back.setVisibility(View.VISIBLE);
							
							first_back.setTag(1);
						}
					}
					
					
				});
				
				/** 1. background **/
				int points[] = dbHelper.getTimeCategoryPointStats(FragmentContainer.userLobby.playerID, time, category);
				int totalPoints = points[0] + points[1] + points[2];
				Log.w("totalPoints", Integer.toString(totalPoints));
				
				TextView stats_points = (TextView) getActivity().findViewById(R.id.stats_points);
				stats_points.setText(Integer.toString(totalPoints));
				stats_points.setTypeface(roboto_black);
				
				TextView points_text = (TextView) getActivity().findViewById(R.id.pointsText);
				points_text.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
								
				TextView points_win = (TextView) getActivity().findViewById(R.id.points_win);
				points_win.setTypeface(roboto_medium_italic);
				points_win.setText(Integer.toString(points[1]));
				
				
				TextView points_win_text = (TextView) getActivity().findViewById(R.id.points_win_text);
				points_win_text.setTypeface(roboto_medium_italic);
				
				TextView points_draw = (TextView) getActivity().findViewById(R.id.points_draw);
				points_draw.setTypeface(roboto_medium_italic);
				points_draw.setText(Integer.toString(points[2]));
				
				TextView points_draw_text = (TextView) getActivity().findViewById(R.id.points_draw_text);
				points_draw_text.setTypeface(roboto_medium_italic);
				
				TextView points_lost = (TextView) getActivity().findViewById(R.id.points_lost);
				points_win.setTypeface(roboto_medium_italic);
				points_lost.setText(Integer.toString(points[0]));
				
				TextView points_lost_text = (TextView) getActivity().findViewById(R.id.points_lost_text);
				points_lost_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar pointsStat_progress = (ProgressBar) getActivity().findViewById(R.id.pointsStat_progress);
				//set the max with playerPoints
				
				
				pointsStat_progress.setMax(totalPoints);
				//set won matches
				pointsStat_progress.setProgress(points[1]);
				//set draw matches
				pointsStat_progress.setSecondaryProgress(points[1] + points[0]);
						
				/*************************************************************************************/
				/********************* second circle ***************************/
				
				/** set tag to background **/
				second_back = (RelativeLayout) getActivity().findViewById(R.id.secondStat_back);
				
				second_back.setTag(1);
				secondStat = (RelativeLayout) getActivity().findViewById(R.id.secondStat);
				secondStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_second_back = (Integer) second_back.getTag();
						if(tag_second_back == 1){
							second_back.setVisibility(View.GONE);
							
							second_back.setTag(0);
						}else{
							second_back.setVisibility(View.VISIBLE);
							
							second_back.setTag(1);
						}
					}
					
					
				});
				
				/** 1. background **/
				int[] matches = dbHelper.getTimeCategoryMatchStats(FragmentContainer.userLobby.playerID, time, category);
				
				int totalMatches = matches[0] + matches[1] + matches[2];
				TextView match_total = (TextView) getActivity().findViewById(R.id.match_total);
				match_total.setText(Integer.toString(totalMatches));
				match_total.setTypeface(roboto_black);
				
				TextView match_text = (TextView) getActivity().findViewById(R.id.matchText);
				match_text.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int lost_percentage = 0;
				int win_percentage = 0;
				int draw_percentage = 0;
				if (totalMatches != 0){
					lost_percentage = (int) ((100 * matches[0])/totalMatches);
					win_percentage = (int) ((100 * matches[1])/totalMatches);
					draw_percentage = (int) ((100 * matches[2])/totalMatches);
				}
				
				TextView match_win = (TextView) getActivity().findViewById(R.id.match_win);
				match_win.setTypeface(roboto_medium_italic);
				match_win.setText(win_percentage + "% ");
				
				
				TextView match_win_text = (TextView) getActivity().findViewById(R.id.match_win_text);
				match_win_text.setTypeface(roboto_medium_italic);
				
				TextView match_draw = (TextView) getActivity().findViewById(R.id.match_draw);
				match_draw.setTypeface(roboto_medium_italic);
				match_draw.setText(draw_percentage + "% ");
				
				
				TextView match_draw_text = (TextView) getActivity().findViewById(R.id.match_draw_text);
				match_draw_text.setTypeface(roboto_medium_italic);
				
				TextView match_lost = (TextView) getActivity().findViewById(R.id.match_lost);
				match_lost.setTypeface(roboto_medium_italic);
				match_lost.setText(lost_percentage + "% ");
				
				
				TextView match_lost_text = (TextView) getActivity().findViewById(R.id.match_lost_text);
				match_lost_text.setTypeface(roboto_medium_italic);
				
				
				
				/** set progress bar progress **/
				
				ProgressBar matchStat_progress = (ProgressBar) getActivity().findViewById(R.id.matchStat_progress);
				//set the max with playerPoints
				
				
				matchStat_progress.setMax(totalMatches);
				//set won matches
				
				matchStat_progress.setProgress(matches[1]);
				//set draw matches
				
				matchStat_progress.setSecondaryProgress(matches[1] + matches[0]);
						
				/*************************************************************************************/
				/************************** third circle ********************************************/
				
				/** set tag to background **/
				third_back = (RelativeLayout) getActivity().findViewById(R.id.thirdStat_back);
				
				third_back.setTag(1);
				thirdStat = (RelativeLayout) getActivity().findViewById(R.id.thirdStat);
				thirdStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_third_back = (Integer) third_back.getTag();
						if(tag_third_back == 1){
							third_back.setVisibility(View.GONE);
							
							third_back.setTag(0);
						}else{
							third_back.setVisibility(View.VISIBLE);
							
							third_back.setTag(1);
						}
					}
					
					
				});
				
				
				/** 1. background **/
				Log.w("time", time);
				Log.w("category", category);
				int[] questions = dbHelper.getTimeCategoryQuestionStats(FragmentContainer.userLobby.playerID, time, category);
				Log.w("numCorrectAnswers", Integer.toString(questions[0]));
				Log.w("numWrongAnswers", Integer.toString(questions[1]));
				
				int totalAnswers = questions[0] + questions[1];
				
				TextView stats_question = (TextView) getActivity().findViewById(R.id.stats_question);
				stats_question.setText(Integer.toString(totalAnswers));
				stats_question.setTypeface(roboto_black);
				
				TextView questionText = (TextView) getActivity().findViewById(R.id.questionText);
				questionText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				
				int percentage_correct = 0;
				int percentage_wrong = 0;
				if(totalAnswers != 0){
					percentage_correct = (int) ((questions[0] * 100)/ totalAnswers);
					percentage_wrong = (int) ((questions[1] * 100)/ totalAnswers);
				}
				TextView question_win = (TextView) getActivity().findViewById(R.id.question_win);
				question_win.setTypeface(roboto_medium_italic);
				question_win.setText(percentage_correct + "% ");
				
				
				TextView question_win_text = (TextView) getActivity().findViewById(R.id.question_win_text);
				question_win_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView question_lost = (TextView) getActivity().findViewById(R.id.question_lost);
				question_lost.setTypeface(roboto_medium_italic);
				question_lost.setText(percentage_wrong + "% ");
				
				
				TextView question_lost_text = (TextView) getActivity().findViewById(R.id.question_lost_text);
				question_lost_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar questionStat_progress = (ProgressBar) getActivity().findViewById(R.id.questionStat_progress);
				//set the max with playerPoints
				
				
				questionStat_progress.setMax(totalAnswers);
				//set won matches
				
				questionStat_progress.setProgress(questions[0]);
				//set draw matches
				
				questionStat_progress.setSecondaryProgress(totalAnswers);
				
				
				/***********************************************************************************/
				/************************** fourth circle ********************************************/
				
				/** set tag to background **/
				fourth_back = (RelativeLayout) getActivity().findViewById(R.id.fourthStat_back);
				
				fourth_back.setTag(1);
				fourthStat = (RelativeLayout) getActivity().findViewById(R.id.fourthStat);
				fourthStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_fourth_back = (Integer) fourth_back.getTag();
						if(tag_fourth_back == 1){
							fourth_back.setVisibility(View.GONE);
							
							fourth_back.setTag(0);
						}else{
							fourth_back.setVisibility(View.VISIBLE);
							
							fourth_back.setTag(1);
						}
					}
					
					
				});
				
				/** 1. background **/
				int[] conceded = dbHelper.getTimeCategoryConcededStats(FragmentContainer.userLobby.playerID, time, category);
				Log.w("numConcededSelf", Integer.toString(conceded[0]));
				Log.w("numConcededOpponents", Integer.toString(conceded[1]));
				
				int totalConceded = conceded[0] + conceded[1];
				
				TextView stats_conceded = (TextView) getActivity().findViewById(R.id.conceded_total);
				stats_conceded.setText(Integer.toString(totalConceded));
				stats_conceded.setTypeface(roboto_black);
				
				TextView concededText = (TextView) getActivity().findViewById(R.id.concededText);
				concededText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int percentage_conceded_self = 0;
				int percentage_conceded_opponent = 0;
		
				if(totalConceded != 0){
					percentage_conceded_self = (int) ((conceded[0] * 100)/ totalConceded);
					percentage_conceded_opponent = (int) ((conceded[1] * 100)/ totalConceded);
				}
				TextView conceded_self = (TextView) getActivity().findViewById(R.id.conceded_self);
				conceded_self.setTypeface(roboto_medium_italic);
				conceded_self.setText(percentage_conceded_self + "% ");
				
				
				TextView conceded_self_text = (TextView) getActivity().findViewById(R.id.conceded_self_text);
				conceded_self_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView conceded_opponent = (TextView) getActivity().findViewById(R.id.conceded_opponent);
				conceded_opponent.setTypeface(roboto_medium_italic);
				conceded_opponent.setText(percentage_conceded_opponent + "% ");
				
				
				TextView conceded_opponent_text = (TextView) getActivity().findViewById(R.id.conceded_opponent_text);
				conceded_opponent_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar concededStat_progress = (ProgressBar) getActivity().findViewById(R.id.concededStat_progress);
				//set the max with playerPoints
				
				
				concededStat_progress.setMax(totalConceded);
				//set won matches
				
				concededStat_progress.setProgress(conceded[0]);
				//set draw matches
				
				concededStat_progress.setSecondaryProgress(totalConceded);
				
				/*********************************************************************************/
				/************************** fifth circle ********************************************/
				
				/** set tag to background **/
				fifth_back = (RelativeLayout) getActivity().findViewById(R.id.fifthStat_back);
				
				fifth_back.setTag(1);
				fifthStat = (RelativeLayout) getActivity().findViewById(R.id.fifthStat);
				fifthStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_fifth_back = (Integer) fifth_back.getTag();
						if(tag_fifth_back == 1){
							fifth_back.setVisibility(View.GONE);
							
							fifth_back.setTag(0);
						}else{
							fifth_back.setVisibility(View.VISIBLE);
							
							fifth_back.setTag(1);
						}
					}
					
					
				});
				
				
				/** 1. background **/
				int[] perfect = dbHelper.getTimeCategoryPerfectStats(FragmentContainer.userLobby.playerID, time, category);
				
				
				int totalPerfect = perfect[0] + perfect[1];
				
				TextView stats_perfect = (TextView) getActivity().findViewById(R.id.stats_perfect);
				stats_perfect.setText(Integer.toString(totalPerfect));
				stats_perfect.setTypeface(roboto_black);
				
				TextView perfectText = (TextView) getActivity().findViewById(R.id.perfectText);
				perfectText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int percentage_perfect_self = 0;
				int percentage_perfect_opponent = 0;
				if(totalPerfect != 0){
					percentage_perfect_self = (int) ((perfect[0] * 100)/ totalPerfect);
					percentage_perfect_opponent = (int) ((perfect[1] * 100)/ totalPerfect);
				}
				 
				
				
				TextView perfect_self = (TextView) getActivity().findViewById(R.id.perfect_self);
				perfect_self.setTypeface(roboto_medium_italic);
				perfect_self.setText(percentage_perfect_self + "% ");
				
				
				TextView perfect_self_text = (TextView) getActivity().findViewById(R.id.perfect_self_text);
				perfect_self_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView perfect_opponent = (TextView) getActivity().findViewById(R.id.perfect_opponent);
				perfect_opponent.setTypeface(roboto_medium_italic);
				perfect_opponent.setText(percentage_perfect_opponent + "% ");
				
				
				TextView perfect_opponent_text = (TextView) getActivity().findViewById(R.id.perfect_opponent_text);
				perfect_opponent_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar perfectStat_progress = (ProgressBar) getActivity().findViewById(R.id.perfectStat_progress);
				//set the max with playerPoints
				
				
				perfectStat_progress.setMax(totalPerfect);
				//set won matches
				
				perfectStat_progress.setProgress(perfect[0]);
				//set draw matches
				
				perfectStat_progress.setSecondaryProgress(totalPerfect);
				
				/************************** sixth circle ********************************************/
				
				/** set tag to background **/
				sixth_back = (RelativeLayout) getActivity().findViewById(R.id.sixthStat_back);
				
				sixth_back.setTag(1);
				sixthStat = (RelativeLayout) getActivity().findViewById(R.id.sixthStat);
				sixthStat.setOnClickListener(new View.OnClickListener() {
					
					
					@Override
					public void onClick(View v) {
						
						int tag_sixth_back = (Integer) sixth_back.getTag();
						if(tag_sixth_back == 1){
							sixth_back.setVisibility(View.GONE);
							
							sixth_back.setTag(0);
						}else{
							sixth_back.setVisibility(View.VISIBLE);
							
							sixth_back.setTag(1);
						}
					}
					
					
				});
				/** 1. background **/
				int[] timeout = dbHelper.getTimeCategoryTimeoutStats(FragmentContainer.userLobby.playerID, time, category);
				
				int totalTimeout = timeout[0] + timeout[1];
				
				TextView stats_timeout = (TextView) getActivity().findViewById(R.id.timeout_total);
				stats_timeout.setText(Integer.toString(totalTimeout));
				stats_timeout.setTypeface(roboto_black);
				
				TextView timeoutText = (TextView) getActivity().findViewById(R.id.timeoutText);
				timeoutText.setTypeface(roboto_medium_italic);
				
				/** 2. background **/
				
				
				/** calculate percentage **/
				
				int percentage_timeout_self = 0;
				int percentage_timeout_opponent = 0;
				if(totalTimeout != 0){
					percentage_timeout_self = (int) ((timeout[0] * 100)/ totalTimeout);
					percentage_timeout_opponent = (int) ((timeout[1] * 100)/ totalTimeout);
				}
				 
				
				
				TextView timeout_self = (TextView) getActivity().findViewById(R.id.timeout_self);
				timeout_self.setTypeface(roboto_medium_italic);
				timeout_self.setText(percentage_timeout_self + "% ");
				
				
				TextView timeout_self_text = (TextView) getActivity().findViewById(R.id.timeout_self_text);
				timeout_self_text.setTypeface(roboto_medium_italic);
				
				
				
				TextView timeout_opponent = (TextView) getActivity().findViewById(R.id.timeout_opponent);
				timeout_opponent.setTypeface(roboto_medium_italic);
				timeout_opponent.setText(percentage_timeout_opponent + "% ");
				
				
				TextView timeout_opponent_text = (TextView) getActivity().findViewById(R.id.timeout_opponent_text);
				timeout_opponent_text.setTypeface(roboto_medium_italic);
				
				/** set progress bar progress **/
				
				ProgressBar timeoutStat_progress = (ProgressBar) getActivity().findViewById(R.id.timeoutStat_progress);
				//set the max with playerPoints
				
				
				timeoutStat_progress.setMax(totalTimeout);
				//set won matches
				
				timeoutStat_progress.setProgress(timeout[0]);
				//set draw matches
				
				timeoutStat_progress.setSecondaryProgress(totalTimeout);
				
		    	
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.stats_stats, container, false);
		
		return view;
	}
	
	
	
		
		

}

