package com.example.splashscreen;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.splashscreen.DatabaseHelper.User;
import com.example.splashscreen.DatabaseHelper.chatMessage;
import com.example.splashscreen.Game.AsyncTaskRunner;

import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ChatFragment extends Fragment {
	
	InputStream inputStream = null;
	static int id = 0;
	MyAdapterChat adapter = null;
	static int send = 0;
	static int rec = 0;
	DatabaseHelper dbHelper = new DatabaseHelper();
	
	Handler handler = new Handler();
	private Runnable updateTask;
	
	static ListView listView = null;
	static ArrayList<chatMessage> messageList = new ArrayList<chatMessage>();
	//static Timer myTimer = new Timer();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		Bundle args2 = getArguments();
		id = args2.getInt("ID", 0);
		
		
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		
		messageList.clear();
		messageList = dbHelper.getConversation(FragmentContainer.userLobby.playerID, id);
		
		listView = (ListView) getActivity().findViewById(R.id.chatList);
		adapter = new MyAdapterChat(getActivity(), messageList);
		adapter.notifyDataSetChanged();
		listView.setAdapter(adapter);
		
		/*myTimer.schedule(new TimerTask() {
	         @Override
	         public void run() {updateGUI();}
	     }, 0, 1000);*/
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_layout, container, false);
		
		return view;
	}
	
	
	
	public void updateGUI(){
		
	      //tv.setText(String.valueOf(i));
	      handler.post(myRunnable);
	}
	
	final Runnable myRunnable = new Runnable() {
	      public void run() {
	    	  messageList.clear();
	  		messageList = dbHelper.getConversation(FragmentContainer.userLobby.playerID, id);
	  		
	  		listView = (ListView) getActivity().findViewById(R.id.chatList);
	  		adapter = new MyAdapterChat(getActivity(), messageList);
	  		adapter.notifyDataSetChanged();
	  		listView.setAdapter(adapter);
	    	  AsyncTaskRunner runner = new AsyncTaskRunner();
		  	    
		  	    runner.execute("0");			
    }
    
 
	      class AsyncTaskRunner extends AsyncTask<String,String,String>{

			   @Override
			   protected String doInBackground(String... arg0) {
				   // TODO Auto-generated method stub
				   return null;
			   }
			   
			   
	    	        
		   }
 };
 
 public void onPause(){
	 //myTimer.cancel();
 }
 
 public void onBackPressed(){
	 //myTimer.cancel();
	 
 }
 
 @Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);

	    // Make sure that we are currently visible
	    if (this.isVisible()) {
	        // If we are becoming invisible, then...
	        if (!(isVisibleToUser)) {
	        	/* stop the timer */
	        	/*try {
					myTimer.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
	        }
	        }
	}
	
 
	
	
		
		

}



