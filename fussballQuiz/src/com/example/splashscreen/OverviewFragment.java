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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class OverviewFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		
		
		
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
	
		/* set font for textviews */
		TextView quickText = (TextView) getActivity().findViewById(R.id.quickview_text);
		quickText.setTypeface(roboto_medium_italic);
		
		TextView currentLevel = (TextView) getActivity().findViewById(R.id.currentLevel);
		currentLevel.setTypeface(roboto_black);
		
		TextView nextLevel = (TextView) getActivity().findViewById(R.id.nextLevel);
		nextLevel.setTypeface(roboto_black);
		
		TextView rankingText =(TextView) getActivity().findViewById(R.id.rankingText);
		rankingText.setTypeface(roboto_black);
		
		TextView quick_ranking = (TextView) getActivity().findViewById(R.id.quick_ranking);
		quick_ranking.setTypeface(roboto_black);
		
		TextView statsText = (TextView) getActivity().findViewById(R.id.statsText);
		statsText.setTypeface(roboto_black);
		
		TextView quick_stats = (TextView) getActivity().findViewById(R.id.quick_stats);
		quick_stats.setTypeface(roboto_black);
		
		/** set the rank **/
		
		int rank = dbHelper.getMyRank(FragmentContainer.userLobby.playerID);
		quick_ranking.setText(Integer.toString(rank));
		
		/** set the victory / total text **/
		
		int total = FragmentContainer.userLobby.lost + FragmentContainer.userLobby.draw + FragmentContainer.userLobby.won;
		quick_stats.setText(Integer.toString(FragmentContainer.userLobby.won) + " / " + total);
		
		
		
		
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.overview_layout, container, false);
		
		return view;
	}
	
	
	
		
		

}
