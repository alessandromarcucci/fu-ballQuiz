package com.example.splashscreen;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class FirstFragment extends Fragment {
     
    public FirstFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //TextView text = (TextView) getActivity().findViewById(R.id.txtLabel);
        //text.setText("Text from a fragment");
        return rootView;
        
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    	TextView text = (TextView) getActivity().findViewById(R.id.txtLabel);
        text.setText("Text from a fragment");
    }
}