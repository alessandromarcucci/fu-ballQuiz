package com.example.splashscreen;



import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountFragment extends Fragment {
	DatabaseHelper dbHelper = new DatabaseHelper();
	InputStream inputStream = null;
	
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
		/* get the fonts from the asset */
		roboto_black = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Black.ttf");
		roboto_black_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-BlackItalic.ttf");
		roboto_medium_italic = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-MediumItalic.ttf");
		
		/* set title */
		TextView account_text = (TextView) getActivity().findViewById(R.id.account_text);
		account_text.setTypeface(roboto_medium_italic);
		
		/* set progress bar numbers */
		TextView currentLevel = (TextView) getActivity().findViewById(R.id.currentLevel);
		currentLevel.setTypeface(roboto_black);
		
		TextView nextLevel = (TextView) getActivity().findViewById(R.id.nextLevel);
		nextLevel.setTypeface(roboto_black);
		
		/* set font for textviews and edittext */
		TextView displayName = (TextView) getActivity().findViewById(R.id.displayName);
		displayName.setTypeface(roboto_medium_italic);
		
		EditText username_login = (EditText) getActivity().findViewById(R.id.username_login);
		username_login.setTypeface(roboto_medium_italic);
		
		TextView displayEmail = (TextView) getActivity().findViewById(R.id.displayEmail);
		displayEmail.setTypeface(roboto_medium_italic);
		
		EditText email_login = (EditText) getActivity().findViewById(R.id.email_login);
		email_login.setTypeface(roboto_medium_italic);
		
		
		TextView displayPassword = (TextView) getActivity().findViewById(R.id.displayPassword);
		displayPassword.setTypeface(roboto_medium_italic);
		
		EditText password_login = (EditText) getActivity().findViewById(R.id.password_login);
		password_login.setTypeface(roboto_medium_italic);
		
		/* set fonts for buttons */
		Button fb_connect = (Button) getActivity().findViewById(R.id.button_fbconnect);
		fb_connect.setTypeface(roboto_black);
		
		Button premium = (Button) getActivity().findViewById(R.id.button_premium);
		premium.setTypeface(roboto_black);
		
		Button submit = (Button) getActivity().findViewById(R.id.button_submit);
		submit.setTypeface(roboto_black);
		
		
		
		
		
		
		
		
		
		/* set user image */
		Bitmap bitmap= null;
		ImageView user_image = (ImageView) getActivity().findViewById(R.id.account_image);
		if(!(FragmentContainer.userLobby.FbConnect.equals("0"))){
			String url = String.format(FragmentContainer.userLobby.playerImage);
			try {
				inputStream = new URL(url).openStream();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			bitmap = BitmapFactory.decodeStream(inputStream);
			
			
			/* set fb connected icon */
			/*ImageView sender_fb = (ImageView) getActivity().findViewById(R.id.game_sender_fb);
			sender_fb.setImageResource(R.drawable.facebook_round);*/
		}else{
			if(FragmentContainer.userLobby.playerImage.contains("default")){
				
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
        bitmap = getRoundedShape(bitmap);
        user_image.setImageBitmap(bitmap);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.account_layout, container, false);
		
		return view;
	}
	
	
	public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
  	  // TODO Auto-generated method stub
  	  int targetWidth = 500;
  	  int targetHeight = 500;
  	  Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight,Bitmap.Config.ARGB_8888);
  	  
  	  Canvas canvas = new Canvas(targetBitmap);
  	  Path path = new Path();
  	  path.addCircle(((float) targetWidth - 1) / 2,
  	  ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),  Path.Direction.CCW);
  	  canvas.clipPath(path);
  	  Bitmap sourceBitmap = scaleBitmapImage;
  	  canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(),
  	  sourceBitmap.getHeight()), new Rect(0, 0, targetWidth,
  	  targetHeight), null);
  	  return targetBitmap;
  	 }
		
		

}
