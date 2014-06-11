package com.example.splashscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.Typeface;

public class ForgotLogin extends Activity {
	DatabaseHelper dbHelper = new DatabaseHelper();
	Typeface roboto_black = null;
	Typeface roboto_black_italic = null;
	Typeface roboto_medium_italic = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forgotlogin_layout);
		
		roboto_black = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		TextView forgotLogin = (TextView) findViewById(R.id.forgotlogin_text);
		forgotLogin.setTypeface(roboto_black_italic);
		
		EditText forgotLogin_mail = (EditText) findViewById(R.id.forgotlogin_email);
		forgotLogin_mail.setTypeface(roboto_medium_italic);
		
		Button forgotLogin_button = (Button) findViewById(R.id.forgotlogin_button);
		forgotLogin_button.setTypeface(roboto_black);
		
		
	}
	
	
	public void sendMail(View view){
		EditText forgot_text = (EditText) findViewById(R.id.forgotlogin_email);
		String email = forgot_text.getText().toString();
		if((email != null) && (email.contains("@")) && (email.contains("."))){
			int id = dbHelper.getIDbyEmail(email);
			if(id != 0){
				dbHelper.forgotPassword(id, email);
				
			}
		
		}
	}
}

