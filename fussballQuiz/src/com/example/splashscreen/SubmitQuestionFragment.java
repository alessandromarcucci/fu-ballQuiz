package com.example.splashscreen;



import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitQuestionFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
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
		
		/* set the title font */
		TextView submit_title = (TextView) getActivity().findViewById(R.id.submitTitle);
		submit_title.setTypeface(roboto_medium_italic);
		
		/* set layout for time spinner */
		Spinner spinner_category = (Spinner) getActivity().findViewById(R.id.category_spinner);
		String[] items_time = getResources().getStringArray(R.array.array_category);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, items_time);
        spinner_category.setAdapter(adapter);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		/* set layout for textviews and edittexts */
		EditText deine_frage = (EditText) getActivity().findViewById(R.id.deinefrage);
		deine_frage.setTypeface(roboto_medium_italic);
		
		EditText submit_correct = (EditText) getActivity().findViewById(R.id.submit_correct);
		submit_correct.setTypeface(roboto_medium_italic);
		
		EditText submit_wrong1 = (EditText) getActivity().findViewById(R.id.submit_wrong1);
		submit_wrong1.setTypeface(roboto_medium_italic);
		
		EditText submit_wrong2 = (EditText) getActivity().findViewById(R.id.submit_wrong2);
		submit_wrong2.setTypeface(roboto_medium_italic);
		
		EditText submit_wrong3 = (EditText) getActivity().findViewById(R.id.submit_wrong3);
		submit_wrong3.setTypeface(roboto_medium_italic);
		
		TextView submit_text = (TextView) getActivity().findViewById(R.id.submit_text);
		submit_text.setTypeface(roboto_medium_italic);
		
		Button submit_button = (Button) getActivity().findViewById(R.id.submit_button);
		submit_button.setTypeface(roboto_black);
		
		
		
		
		
		
		
		
		
		/* set font for textviews and edittexts */
		
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.submitquestion_layout, container, false);
		
		return view;
	}
	
	public void submitQuestion(View view){
		EditText question = (EditText) view.findViewById(R.id.deinefrage);
		EditText correct = (EditText) view.findViewById(R.id.submit_correct);
		EditText wrong1 = (EditText) view.findViewById(R.id.submit_wrong1);
		EditText wrong2 = (EditText) view.findViewById(R.id.submit_wrong2);
		EditText wrong3 = (EditText) view.findViewById(R.id.submit_wrong3);
		
		
		
		if(!(question.getText().toString().equals("")) && !(correct.getText().toString().equals("")) && !(wrong1.getText().toString().equals("")) && !(wrong2.getText().toString().equals("")) && !(wrong3.getText().toString().equals(""))){
			dbHelper.submitQuestion(question.getText().toString(), correct.getText().toString(), wrong1.getText().toString(), wrong2.getText().toString(), wrong3.getText().toString());
			//Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
			
		}//else Toast.makeText(this, "you must fill all the fields to submit", Toast.LENGTH_SHORT).show();
	}
}
