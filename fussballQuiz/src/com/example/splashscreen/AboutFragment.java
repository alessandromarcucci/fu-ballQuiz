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

public class AboutFragment extends Fragment {
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
		
		TextView about_header = (TextView) getActivity().findViewById(R.id.about_header);
		about_header.setTypeface(roboto_medium_italic);
		
		TextView version_text = (TextView) getActivity().findViewById(R.id.version_text);
		version_text.setTypeface(roboto_medium_italic);
		
		TextView feedback_text = (TextView) getActivity().findViewById(R.id.feedback_text);
		feedback_text.setTypeface(roboto_medium_italic);
		
		TextView teilen_text = (TextView) getActivity().findViewById(R.id.teilen_text);
		teilen_text.setTypeface(roboto_medium_italic);
		
		TextView anbieter = (TextView) getActivity().findViewById(R.id.anbieter);
		anbieter.setTypeface(roboto_medium_italic);
		
		TextView pokermania_text =(TextView) getActivity().findViewById(R.id.poker_text);
		pokermania_text.setTypeface(roboto_medium_italic);
		
		TextView hinweis_text = (TextView) getActivity().findViewById(R.id.hinweis_text);
		hinweis_text.setTypeface(roboto_medium_italic);
		
		TextView haftung_text = (TextView) getActivity().findViewById(R.id.haf_text);
		haftung_text.setTypeface(roboto_medium_italic);
		
		TextView contact_text = (TextView) getActivity().findViewById(R.id.conta_text);
		contact_text.setTypeface(roboto_medium_italic);
		
		TextView last_text = (TextView) getActivity().findViewById(R.id.last_text);
		last_text.setTypeface(roboto_medium_italic);
		
		
		
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.about_layout, container, false);
		
		return view;
	}
	
	
	
		
		

}