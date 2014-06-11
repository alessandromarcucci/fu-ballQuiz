package com.example.splashscreen;



import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends Fragment {
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic =null;
	private Context globalContext = null;
	String[] items = null;
	Spinner spinner_club = null;
	EditText name_account = null;
	EditText email = null;
	EditText first_password = null;
	EditText second_password = null;
	DatabaseHelper dbHelper = new DatabaseHelper();
	public final static String EXTRA_MESSAGE = "com.example.splashscreen.MESSAGE";
	public final static String APP_PREFS ="footballQuizPrefs";
	SharedPreferences settings = null;
	SharedPreferences.Editor editor = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Test", "hello");
		
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		settings = getActivity().getSharedPreferences(APP_PREFS, getActivity().MODE_PRIVATE);
		
		editor = settings.edit();
		
		
		/* set items and style to spinner club */
		
		
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/*set font for TextView and EditText */
		
		TextView name_account_text = (TextView) getActivity().findViewById(R.id.register_name_account_text);
		name_account_text.setTypeface(roboto_medium_italic);

		name_account = (EditText) getActivity().findViewById(R.id.register_name_account);
		name_account.setTypeface(roboto_medium_italic);
		
		TextView email_text = (TextView) getActivity().findViewById(R.id.register_email_text);
		email_text.setTypeface(roboto_medium_italic);

		email = (EditText) getActivity().findViewById(R.id.register_email);
		email.setTypeface(roboto_medium_italic);
		
		TextView first_password_text = (TextView) getActivity().findViewById(R.id.register_first_password_text);
		first_password_text.setTypeface(roboto_medium_italic);

		first_password = (EditText) getActivity().findViewById(R.id.register_first_password);
		first_password.setTypeface(roboto_medium_italic);
		
		TextView second_password_text = (TextView) getActivity().findViewById(R.id.register_second_password_text);
		second_password_text.setTypeface(roboto_medium_italic);

		second_password = (EditText) getActivity().findViewById(R.id.register_second_password);
		second_password.setTypeface(roboto_medium_italic);
		
		/* set font for buttons */
		
		Button register_facebook_connect = (Button) getActivity().findViewById(R.id.register_facebook_connect);
		register_facebook_connect.setTypeface(roboto_black);

		Button register_register = (Button) getActivity().findViewById(R.id.register_register);
		register_register.setTypeface(roboto_black);
		
		
		register_register.setOnClickListener(new View.OnClickListener(){
    		@Override
    		public void onClick(View v) {
    			/* set highlighting buttons */
    			new RegisterFragment.xyz().execute();
		}
		});
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.register_fragment, container, false);
		
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
        	String cl = spinner_club.getSelectedItem().toString();
        	String us = name_account.getText().toString();
      		String pw = first_password.getText().toString();
      		String pw2 = second_password.getText().toString();
      		String em = email.getText().toString();
      		
      		if(!(us.equals("") && !(pw.equals("") && !(em.equals("") && !(pw2.equals("")))))){
      			if((pw.length() > 6 ) && (pw.equals(pw2))){
      			if((us.length() < 15) && (us.length() > 5)){
      				if((em.contains("@") && (em.contains(".")))){
      					if (dbHelper.getIDbyEmail(us) == 0){
      						if (dbHelper.getIDbyName(us) == 0){
      							
      							Intent i = new Intent(getActivity(), SelectClub.class);
      							i.putExtra("username", us);
      							i.putExtra("password", pw);
      							i.putExtra("email", em);
      							startActivity(i);
      							getActivity().finish();
      						}else{
      							//Toast.makeText(getActivity(), "username already taken", Toast.LENGTH_SHORT).show();
      						}
      					}else{
      						//Toast.makeText(getActivity(), "email already present", Toast.LENGTH_SHORT).show();
      					}
      					}else{
      						//Toast.makeText(getActivity(), "email format not valid", Toast.LENGTH_SHORT).show();
      					}
      				}
      		}
      			else{
      					//Toast.makeText(getActivity(), "two passwords dont match", Toast.LENGTH_SHORT).show();
      				}
      			}else{
      				//Toast.makeText(getActivity(), "please fill all the fields", Toast.LENGTH_SHORT).show();
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

