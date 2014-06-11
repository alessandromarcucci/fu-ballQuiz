package com.example.splashscreen;



import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SoundFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
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
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sound_layout, container, false);
		
		return view;
	}
	
	
	
		
		



	
	public void switchSound(View view){
		
		Button soundButton = (Button) view.findViewById(R.id.soundButton);
		Drawable actualDrawable = soundButton.getBackground();
		Drawable on = view.getResources().getDrawable(R.drawable.sound_on);
		
		if(actualDrawable.getConstantState().equals(on.getConstantState())){
			soundButton.setBackgroundResource(R.drawable.sound_off);
			/** TODO deactivate sounds **/
		}else{
			soundButton.setBackgroundResource(R.drawable.sound_on);
			/** TODO activate sounds **/
		}
		
	}
}
