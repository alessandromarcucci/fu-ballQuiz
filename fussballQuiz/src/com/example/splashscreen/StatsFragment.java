package com.example.splashscreen;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.InputStream;

public class StatsFragment extends Fragment {
    String premium = null;
    InputStream inputStream = null;
    public StatsFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.mystats_layout, container, false);
        return rootView;
        
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //myTimer.cancel();
        
        /** SET ELEMENTS FOR THE USER TILE **/
		
		/*set user image */
		ImageView user_picture=(ImageView) getActivity().findViewById(R.id.playerImage);
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
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
			
			user_picture.setImageBitmap(bitmap);
		}else{
			if(FragmentContainer.userLobby.playerImage.contains("default")){
					user_picture.setImageResource(R.drawable.player_image);
			}else{
			/**TODO add the path of the user image here and set the lobbyImage**/
			}
		}
		/* set username */
		TextView user_name = (TextView) getActivity().findViewById(R.id.playerName);
		user_name.setText(FragmentContainer.userLobby.playerName);
		
		/** set user level **/
		TextView user_level = (TextView) getActivity().findViewById(R.id.playerLevel);
		user_level.setText("level: " + FragmentContainer.userLobby.playerPoints);
		
		/** set premium / not premium **/
		TextView user_premium = (TextView) getActivity().findViewById(R.id.playerPremium);
		if (FragmentContainer.userLobby.isPremium == 0){
			user_premium.setText("Non - Premium");
		}else{
			user_premium.setText("Premium User");
		}
    }
    
    
}
