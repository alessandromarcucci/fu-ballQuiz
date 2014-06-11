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

public class AchievementFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		
		
		
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		/*Button button1 = (Button) getActivity().findViewById(R.id.button1);
		button1.setBackgroundResource(R.drawable.active);
		Button button2 = (Button) getActivity().findViewById(R.id.button2);
		button2.setBackgroundResource(R.drawable.allespieleoff);*/
		/* set user image */
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.achievement_layout, container, false);
		
		return view;
	}
	
	
	
		
		

}

