package com.example.splashscreen;



import java.util.ArrayList;
import java.util.List;

import android.preference.PreferenceManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
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

public class LoginFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	EditText username = null;
	EditText password = null;
	public final static String EXTRA_MESSAGE = "com.example.splashscreen.MESSAGE";
	public final static String APP_PREFS ="footballQuizPrefs";
	SharedPreferences settings = null;
	SharedPreferences.Editor editor = null;
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
		/* get shared preferences */
		settings = getActivity().getSharedPreferences(APP_PREFS, getActivity().MODE_PRIVATE);
		editor = settings.edit();
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/* set font for layout */
		
		TextView login_text = (TextView) getActivity().findViewById(R.id.username_login_text);
		login_text.setTypeface(roboto_medium_italic);
		
		username = (EditText) getActivity().findViewById(R.id.username_login);
		username.setTypeface(roboto_medium_italic);
		
		TextView password_text = (TextView) getActivity().findViewById(R.id.password_login_text);
		password_text.setTypeface(roboto_medium_italic);
		
		password = (EditText) getActivity().findViewById(R.id.password_login);
		password.setTypeface(roboto_medium_italic);
		
		Button fbconnect_login = (Button) getActivity().findViewById(R.id.register_facebook_connect_login);
		fbconnect_login.setTypeface(roboto_black);
		
		Button password_forgot = (Button) getActivity().findViewById(R.id.password_forgot_login);
		password_forgot.setTypeface(roboto_black);
		
		Button confirm = (Button) getActivity().findViewById(R.id.confirm_login);
		confirm.setTypeface(roboto_black);
		confirm.setOnClickListener(new View.OnClickListener(){
    		@Override
    		public void onClick(View v) {
    			/* set highlighting buttons */
    			new LoginFragment.xyz().execute();
		}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.login_layout, container, false);
		
		return view;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);

	    // Make sure that we are currently visible
	    if (this.isVisible()) {
	        // If we are becoming invisible, then...
	        if (isVisibleToUser) {
	            
	            // TODO stop audio playback
	        }
	    }
	}
	
	final class xyz extends AsyncTask<Void, Void, Void> {
        private final ProgressDialog dialog = new ProgressDialog(getActivity());
        protected void onPreExecute() {
          this.dialog.setMessage("Please Wait...");
          this.dialog.show();
          }
          // put your code which preload with processDialog  
          @Override
          protected Void doInBackground(Void... arg0) {
        	String us = username.getText().toString();
      		String pw = password.getText().toString();
      		
      		if(!(us.equals("")) && !(pw.equals(""))){
      			int id = dbHelper.getIDbyNameAndPassword(us,pw);
      			
      			if(id != 0){
      				editor.putInt("user_id", id);
      				editor.commit();
      				/** TODO set the value isOnline (user) to 1 **/
      				dbHelper.setIsLoggedIn(id);
      				Intent i = new Intent(getActivity(), FragmentContainer.class);
      				i.putExtra("ID", id);
      				startActivity(i);
      				
      			}else{
      			//Toast.makeText(this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
      			}
      		}
               return null;
          }

         @Override
         protected void onPostExecute(final Void unused) {
            if (this.dialog.isShowing()) {
            this.dialog.dismiss();
         }   
      }

	
	
	}
	
}
    

