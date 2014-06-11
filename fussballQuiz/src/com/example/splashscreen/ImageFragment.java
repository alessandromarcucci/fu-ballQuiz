package com.example.splashscreen;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ImageFragment extends Fragment {
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
		View view = inflater.inflate(R.layout.register_layout, container, false);
		
		return view;
	}
}

