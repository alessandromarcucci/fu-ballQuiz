package com.example.splashscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyFragment extends Fragment {
	static int p;
	static float s;
	public static Fragment newInstance(MainActivity2 context, int pos, float scale)
	{
		
		Bundle b = new Bundle();
		p = pos;
		s = scale;
		//b.putInt("pos", pos);
		//b.putFloat("scale", scale);
		return Fragment.instantiate(context, MyFragment.class.getName(), b);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		
		LinearLayout l = (LinearLayout) inflater.inflate(R.layout.mf, container, false);
		
		/*int pos = p;
		TextView tv = (TextView) l.findViewById(R.id.text);
		tv.setText("Position = " + pos);
		
		MyLinearLayout root = (MyLinearLayout) l.findViewById(R.id.root);
		float scale = s;
		root.setScaleBoth(scale);*/
		
		return l;
	}
}
