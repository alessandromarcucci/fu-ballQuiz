package com.example.splashscreen;









import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreen.DatabaseHelper.User;

@SuppressLint("ResourceAsColor")
public class Account extends Activity {
	static User userLobby = null;
	DatabaseHelper dbHelper = new DatabaseHelper();
	int id = 0;
	String playerPass = null;
	String premium = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_layout);
		Intent intent = getIntent();
		//id = intent.getIntExtra(Login.EXTRA_MESSAGE, -1);
		
		ActionBar actionBar = getActionBar();
		/*actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setTitle("FußballQuiz");
		actionBar.setDisplayHomeAsUpEnabled(true);*/
		//actionBar.hide();
		
		userLobby = dbHelper.getUser(id);	
		/*TextView playerName = (TextView) findViewById(R.id.account_nameuser);
		TextView pointsuser = (TextView) findViewById(R.id.account_pointsuser);
		TextView ispremiumuser = (TextView) findViewById(R.id.account_ispremiumuser);
		ImageView imageuser = (ImageView) findViewById(R.id.account_imageuser);
   
		/**footer buttons*
		//update
		Button update = (Button) findViewById(R.id.update);
		update.setText("Update");
		Drawable image0 = getBaseContext().getResources().getDrawable( R.drawable.update );
		image0.setBounds( 0,0,50,50);
		update.setCompoundDrawables(null,image0 , null, null);
		update.setBackgroundDrawable(null);
		
		//logout
		Button logout = (Button) findViewById(R.id.logout);
		logout.setText("Logout");
		Drawable image1 = getBaseContext().getResources().getDrawable( R.drawable.logout );
		image1.setBounds( 0,0,50,50);
		logout.setCompoundDrawables(null,image1 , null, null);
		logout.setBackgroundDrawable(null);
		
		//fbconnect
		Button fbconnect = (Button) findViewById(R.id.fbconnect_account);
		fbconnect.setText("FB Connect");
		Drawable image2 = getBaseContext().getResources().getDrawable( R.drawable.fbconnect );
		image2.setBounds( 0,0,50,50);
		fbconnect.setCompoundDrawables(null,image2 , null, null);
		fbconnect.setBackgroundDrawable(null);
		
		
		
		
		
		
		
		if (userLobby.isPremium == 0){
			premium = "Non - Premium";
		}else{
			premium = "";
		}
    
		if(userLobby.playerImage.contains("default")){
			imageuser.setBackgroundResource(R.drawable.player_image);
		}else{
			/**TODO add the path of the user image here and set the lobbyImage*
		}
       
		playerName.setText(userLobby.playerName);
		pointsuser.setText("level: " + userLobby.playerPoints);
		ispremiumuser.setText(premium);	
	}
	
	public void c(View view){
		EditText username_changed = (EditText) findViewById(R.id.account_user);
		EditText password_changed = (EditText) findViewById(R.id.account_password);
		final String us = username_changed.getText().toString();
		final String pw = password_changed.getText().toString();
		id= userLobby.playerID;
		playerPass = userLobby.playerPassword;
		// first case: username and password are not empty -> change username and pass in the db
		if(!(us.equals("")) && !(pw.equals(""))){
			if((us.length() > 5) && (us.length() < 15)){
				if(pw.length() > 5){
					if (dbHelper.getIDbyName(us) == 0){
						final Dialog dialog = new Dialog(this);
		                dialog.setContentView(R.layout.password_confirmation);
		                dialog.setTitle("Custom Alert Dialog");
		                Button save=(Button)dialog.findViewById(R.id.save);
		                Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
		                dialog.show();
		                save.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								EditText editText=(EditText)dialog.findViewById(R.id.confirmPassword);
								String password_confirmed = editText.getText().toString();
								if(password_confirmed.equals(playerPass)){
									dbHelper.setUsernameAndPassword(id, us, pw);
									dialog.cancel();
								}
				          	}
		                });
		                btnCancel.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.cancel();
				          	}
		                });
		                
					}else Toast.makeText(this, "username already taken", Toast.LENGTH_SHORT).show();
				}else Toast.makeText(this, "password too short", Toast.LENGTH_SHORT).show();
			}else Toast.makeText(this, "username too short", Toast.LENGTH_SHORT).show();
		}
		//second case: password is empty
		if(!(us.equals("")) && (pw.equals(""))){
			if((us.length() > 5) && (us.length() < 15)){
				if (dbHelper.getIDbyName(us) == 0){
					final Dialog dialog = new Dialog(this);
	                dialog.setContentView(R.layout.password_confirmation);
	                dialog.setTitle("Custom Alert Dialog");
	                Button save=(Button)dialog.findViewById(R.id.save);
	                Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
	                dialog.show();
	                save.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							EditText editText=(EditText)dialog.findViewById(R.id.confirmPassword);
							String password_confirmed = editText.getText().toString();
							if(password_confirmed.equals(playerPass)){
								dbHelper.setUsername(id, us);
								dialog.cancel();
							}
			          	}
	                });
	                btnCancel.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.cancel();
			          	}
	                });
				}else Toast.makeText(this, "username already taken", Toast.LENGTH_SHORT).show();
			}else Toast.makeText(this, "username too short", Toast.LENGTH_SHORT).show();
		}
		//third case: username is empty
		if(us.equals("") && !(pw.equals(""))){
			if(pw.length() > 5){
				final Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.password_confirmation);
                dialog.setTitle("Custom Alert Dialog");
                Button save=(Button)dialog.findViewById(R.id.save);
                Button btnCancel=(Button)dialog.findViewById(R.id.cancel);
                dialog.show();
                save.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						EditText editText=(EditText)dialog.findViewById(R.id.confirmPassword);
						String password_confirmed = editText.getText().toString();
						if(password_confirmed.equals(playerPass)){
							dbHelper.setPassword(id,pw);
							dialog.cancel();
						}
		          	}
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
		          	}
                });				
			}else Toast.makeText(this, "password too short", Toast.LENGTH_SHORT).show();
		}	
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

	    
	     
	     finish();
	    return true;
	}
	
	public void Logout(View view){
		id = userLobby.playerID;
		dbHelper.setIsLoggedOut(id);
		Intent i = new Intent(Account.this, Login.class);
		startActivity(i);
	}*/
	}
}
